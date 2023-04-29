package com.sogeti.filmland.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sogeti.filmland.models.Category;
import com.sogeti.filmland.models.CategoryDto;
import com.sogeti.filmland.models.Customer;
import com.sogeti.filmland.models.JsonDataResponse;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.CustomerRepository;
import com.sogeti.filmland.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CustomerRepository customerRepository, SubscriptionRepository subscriptionRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }
    @GetMapping()
    public JsonDataResponse<?> getCategories() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Customer customer = customerRepository.findByEmail("admin");
        List<Category> allCategories = categoryRepository.findAll();
        List<Category>  subscribedCategories = subscriptionRepository.findCategoriesByCustomer_Id(customer.getId());
        List<CategoryDto> availableCategories = new ArrayList<>();

        for (Category category : allCategories) {
            boolean isSubscribed = subscribedCategories.stream().anyMatch(subscribedCategory -> subscribedCategory.getId().equals(category.getId()));
            if (!isSubscribed) {
                availableCategories.add(new CategoryDto(category.getName(), category.getAvailableContent(), category.getPrice()));
            }
        }

        List<CategoryDto> subscribedCategoryDtos = subscribedCategories.stream()
                .map(category -> new CategoryDto(
                        category.getName(),
                        subscriptionRepository.findRemainingContentByCustomerIdAndCategoryId(customer.getId(), category.getId()),
                        category.getPrice(),
                        subscriptionRepository.findStartDateByCustomerIdAndCategoryId(customer.getId(), category.getId()).toString()))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("availableCategories", availableCategories);
        response.put("subscribedCategories", subscribedCategoryDtos);

        JsonNode jsonResponse = objectMapper.valueToTree(response);

        return new JsonDataResponse<>("200", "List of available and subscribed categories", jsonResponse);

    }
}

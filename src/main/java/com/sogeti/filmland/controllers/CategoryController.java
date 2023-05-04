package com.sogeti.filmland.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sogeti.filmland.models.*;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.CustomerRepository;
import com.sogeti.filmland.repository.SubscriptionRepository;
import com.sogeti.filmland.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

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

        Customer customer = customerRepository.findByEmail("john.doe@example.com").orElseThrow();
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


        return new JsonDataResponse<>("200", "List of categories for: "+ customer.getEmail(), objectMapper.valueToTree(response));

    }


}

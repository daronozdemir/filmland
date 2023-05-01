package com.sogeti.filmland.services;

import com.sogeti.filmland.models.Category;
import com.sogeti.filmland.models.Customer;
import com.sogeti.filmland.models.JsonResponse;
import com.sogeti.filmland.models.Subscription;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.CustomerRepository;
import com.sogeti.filmland.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public PaymentService(CategoryRepository categoryRepository, CustomerRepository customerRepository, SubscriptionRepository subscriptionRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public JsonResponse subscribeToCategory(String email, String categoryName) {

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return new JsonResponse("Failed", "Invalid Email");
        }
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return new JsonResponse("Failed", "Invalid Category");
        }
        Subscription existingSubscription = subscriptionRepository.findByCustomerIdAndCategoryId(customer.getId(), category.getId());
        if (existingSubscription != null) {
            return new JsonResponse("Failed", "Already subscribed to this category");
        }

        LocalDate now = LocalDate.now();
        Subscription subscription = Subscription.builder()
                .startDate(now)
                .paymentDate(now.plusMonths(1))
                .remainingContent(10)
                .category(category)
                .customer(customer)
                .build();
        subscriptionRepository.save(subscription);

        return new JsonResponse("Success", "Subscribed to category");
    }
}

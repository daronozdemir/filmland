package com.sogeti.filmland.controllers;

import com.sogeti.filmland.models.*;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.CustomerRepository;
import com.sogeti.filmland.repository.SubscriptionRepository;
import com.sogeti.filmland.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscribe")
public class SubscriptionController {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final PaymentService paymentService;

    @Autowired
    public SubscriptionController(CategoryRepository categoryRepository, CustomerRepository customerRepository, SubscriptionRepository subscriptionRepository, PaymentService paymentService) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentService = paymentService;
    }

    @PostMapping()
    public JsonResponse subscribeToCategory(@RequestBody SubscriptionRequest subscriptionRequest) {
        return paymentService.subscribeToCategory(subscriptionRequest.getEmail(), subscriptionRequest.getAvailableCategory());
    }

    @PostMapping("/share")
    public JsonResponse shareSubscription(@RequestBody ShareSubscriptionRequest request) {
        Customer subscriber = customerRepository.findByEmail(request.getSubscriberEmail()).orElseThrow();
        if (subscriber == null) {
            return new JsonResponse("Failed", "Invalid subscriber email");
        }
        Category category = categoryRepository.findByName(request.getCategoryName());
        if (category == null) {
            return new JsonResponse("Failed", "Invalid Category");
        }
        Subscription subscription = subscriptionRepository.findByCustomerIdAndCategoryId(subscriber.getId(), category.getId());
        if (subscription == null) {
            return new JsonResponse("Failed", "Subscriber is not subscribed to this category");
        }
        Customer sharedWithCustomer = customerRepository.findByEmail(request.getSharedWithEmail()).orElseThrow();
        if (sharedWithCustomer == null) {
            return new JsonResponse("Failed", "Invalid shared with email");
        }
        if (subscription.getSharedWithCustomers().contains(sharedWithCustomer)) {
            return new JsonResponse("Failed", "Subscription is already shared with this customer");
        }

        subscription.getSharedWithCustomers().add(sharedWithCustomer);
        subscriptionRepository.save(subscription);

        return new JsonResponse("Success", "Subscription shared with customer");
    }
}

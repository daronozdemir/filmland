package com.sogeti.filmland.controllers;

import com.sogeti.filmland.models.JsonResponse;
import com.sogeti.filmland.models.LoginRequest;
import com.sogeti.filmland.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sogeti.filmland.models.Customer;

@RestController
@RequestMapping("auth")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            Customer customer = customerRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            if (customer != null) {
                return new JsonResponse("Login successful", "Welcome " + customer.getEmail());
            } else {
                return new JsonResponse("Login failed", "Invalid email or password");
            }

        }catch (Exception e) {
            return new JsonResponse("Error","Something went wrong");
        }
    }
}

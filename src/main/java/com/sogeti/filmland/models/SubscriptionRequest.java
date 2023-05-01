package com.sogeti.filmland.models;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private String email;
    private String availableCategory;
}
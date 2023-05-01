package com.sogeti.filmland.models;

import lombok.Data;

@Data
public class ShareSubscriptionRequest {
    private String subscriberEmail;
    private String sharedWithEmail;
    private String categoryName;
}
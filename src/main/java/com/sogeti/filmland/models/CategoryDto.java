package com.sogeti.filmland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private Integer availableContent;
    private Double price;
    private Integer remainingContent;
    private String startDate;

    public CategoryDto(String name, Integer availableContent, Double price) {
        this.name = name;
        this.availableContent = availableContent;
        this.price = price;
    }

    public CategoryDto(String name, Integer remainingContent, Double price, String startDate) {
        this.name = name;
        this.remainingContent = remainingContent;
        this.price = price;
        this.startDate = startDate;
    }
}
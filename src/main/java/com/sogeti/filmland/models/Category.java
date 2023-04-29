package com.sogeti.filmland.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer availableContent;

    private Double price;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

}

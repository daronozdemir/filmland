package com.sogeti.filmland.models;


import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Subscription subscription;

}
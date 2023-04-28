//package com.sogeti.filmland.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "payments")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subscriber_id")
//    private Subscriber subscriber;
//
//    @Column(nullable = false)
//    private Double amount;
//
//    @Column(nullable = false)
//    private LocalDate paymentDate;
//
//
//}
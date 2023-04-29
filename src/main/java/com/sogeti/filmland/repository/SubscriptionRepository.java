package com.sogeti.filmland.repository;

import com.sogeti.filmland.models.Category;
import com.sogeti.filmland.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.customer.id = :customerId")
    List<Subscription> findByCustomerId(Long customerId);

    @Query("SELECT s FROM Subscription s WHERE s.customer.id = :customerId AND s.category.id = :categoryId")
    Subscription findByCustomerIdAndCategoryId(Long customerId, Long categoryId);

    @Query("select s.category from Subscription s where s.customer.id = :customerId")
    List<Category> findCategoriesByCustomer_Id(@Param("customerId") Long customerId);

    @Query("SELECT s.remainingContent FROM Subscription s WHERE s.customer.id = :customerId AND s.category.id = :categoryId")
    Integer findRemainingContentByCustomerIdAndCategoryId(@Param("customerId") Long customerId, @Param("categoryId") Long categoryId);

    @Query("SELECT s.startDate FROM Subscription s WHERE s.customer.id = :customerId AND s.category.id = :categoryId")
    LocalDate findStartDateByCustomerIdAndCategoryId(@Param("customerId") Long customerId, @Param("categoryId") Long categoryId);

}
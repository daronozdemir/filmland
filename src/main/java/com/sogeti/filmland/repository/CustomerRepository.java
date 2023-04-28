package com.sogeti.filmland.repository;


import com.sogeti.filmland.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmailAndPassword(String email, String password);
}

package com.sogeti.filmland.repository;

import com.sogeti.filmland.models.Category;
import com.sogeti.filmland.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

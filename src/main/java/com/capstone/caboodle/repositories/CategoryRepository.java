package com.capstone.caboodle.repositories;

import com.capstone.caboodle.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByProfile_id(Long profile_id);
}

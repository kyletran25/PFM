package com.finace.management.repository;

import com.finace.management.entity.AppUser;
import com.finace.management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCreatedByAndIsDeletedIsFalse(AppUser user);
    boolean existsByNameAndCreatedByAndIsDeletedIsFalse(String name, AppUser createdBy);
    Optional<Category> findByIdAndCreatedByAndIsDeletedIsFalse(Integer id, AppUser createdBy);
}

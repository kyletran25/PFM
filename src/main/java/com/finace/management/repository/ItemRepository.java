package com.finace.management.repository;

import com.finace.management.entity.AppUser;
import com.finace.management.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByIdAndCreatedBy(Integer id, AppUser createdBy);
}

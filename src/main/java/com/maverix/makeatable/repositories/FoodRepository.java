package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByRestaurantId(Long restaurantId);
}

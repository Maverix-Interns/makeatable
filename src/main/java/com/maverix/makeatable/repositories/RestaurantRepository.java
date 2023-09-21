package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
}

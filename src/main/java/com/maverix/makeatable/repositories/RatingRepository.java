package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Long> {
}

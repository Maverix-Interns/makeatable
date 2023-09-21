package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
}

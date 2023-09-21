package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}

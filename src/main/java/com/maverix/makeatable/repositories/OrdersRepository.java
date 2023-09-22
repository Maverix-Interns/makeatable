package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}

package com.maverix.makeatable.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}

package com.maverix.makeatable.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double foodRating;

    @Column(nullable = false)
    private Long foodRatingCount;

    @Column(nullable = false)
    private Double restaurantRating;

    @Column(nullable = false)
    private Long restaurantRatingCount;

    @OneToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}

package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    private String subCategory;

    private Double price;

    @ManyToOne(optional = true)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(optional = true)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    private String imageUrl;

    private Long calories;

    private String description;
}

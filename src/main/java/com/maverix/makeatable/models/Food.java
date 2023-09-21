package com.maverix.makeatable.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String subCategory;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;
    private String imageUrl;
    private Long calories;
    private String description;
}

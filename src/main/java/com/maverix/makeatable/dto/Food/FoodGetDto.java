package com.maverix.makeatable.dto.Food;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

@Data
public class FoodGetDto {
    private Long id;
    private String name;
    private FoodCategory category;
    private String subCategory;
    private Double price;
    private Long restaurantId;
    private Double averageRating;
    private String imageUrl;
    private Long calories;
    private String description;
}

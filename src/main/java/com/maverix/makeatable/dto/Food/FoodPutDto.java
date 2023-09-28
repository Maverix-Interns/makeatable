package com.maverix.makeatable.dto.Food;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

@Data
public class FoodPutDto {
    private String name;
    private FoodCategory category;
    private String subCategory;
    private Double price;
    private Long restaurantId;
    private String imageUrl;
    private Long calories;
    private String description;
}

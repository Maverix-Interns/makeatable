package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Food.FoodDTO;
import com.maverix.makeatable.dto.Food.FoodGetDto;
import com.maverix.makeatable.dto.Food.FoodPostDto;
import com.maverix.makeatable.dto.Food.FoodPutDto;
import com.maverix.makeatable.models.Food;
import com.maverix.makeatable.repositories.FoodRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodGetDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(this::convertToFoodDto)
                .collect(Collectors.toList());
    }

    public FoodGetDto getFoodById(Long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        return optionalFood.map(this::convertToFoodDto).orElse(null);
    }

    public FoodGetDto createFood(FoodPostDto foodPostDto) {
        Food food = convertToFoodEntity(foodPostDto);
        Food savedFood = foodRepository.save(food);
        return convertToFoodDto(savedFood);
    }

    public FoodGetDto updateFood(Long id, FoodPutDto foodPutDto) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (optionalFood.isPresent()) {
            Food existingFood = optionalFood.get();
            BeanUtils.copyProperties(foodPutDto, existingFood);
            existingFood.setUpdatedAt(LocalDateTime.now());
            Food updatedFood = foodRepository.save(existingFood);
            return convertToFoodDto(updatedFood);
        }
        return null;
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    private FoodGetDto convertToFoodDto(Food food) {
        FoodGetDto foodDto = new FoodGetDto();
        BeanUtils.copyProperties(food, foodDto);
        return foodDto;
    }

    private Food convertToFoodEntity(FoodPostDto foodPostDto) {
        Food food = new Food();
        BeanUtils.copyProperties(foodPostDto, food);
        food.setCreatedAt(LocalDateTime.now());
        food.setUpdatedAt(LocalDateTime.now());
        return food;
    }
    public List<FoodDTO> getFoodByRestaurantId(Long restaurantId) {
        List<Food> foodItems = foodRepository.findByRestaurantId(restaurantId);
        return foodItems.stream()
                .map(this::mapFoodToFoodDTO)
                .collect(Collectors.toList());
    }

    private FoodDTO mapFoodToFoodDTO(Food food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(food.getId());
        foodDTO.setName(food.getName());
        foodDTO.setCategory(food.getCategory());
        foodDTO.setSubCategory(food.getSubCategory());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setImageUrl(food.getImageUrl());
        foodDTO.setCalories(food.getCalories());
        foodDTO.setDescription(food.getDescription());
        return foodDTO;
    }
}

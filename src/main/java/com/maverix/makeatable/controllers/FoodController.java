package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Food.FoodDTO;
import com.maverix.makeatable.dto.Food.FoodGetDto;
import com.maverix.makeatable.dto.Food.FoodPostDto;
import com.maverix.makeatable.dto.Food.FoodPutDto;
import com.maverix.makeatable.services.FoodService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<Response<List<FoodGetDto>>> getAllFoods() {
        List<FoodGetDto> foods = foodService.getAllFoods();
        Response<List<FoodGetDto>> response = Response.<List<FoodGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Foods retrieved successfully")
                .data(foods)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FoodGetDto>> getFoodById(@PathVariable Long id) {
        FoodGetDto food = foodService.getFoodById(id);
        Response<FoodGetDto> response = Response.<FoodGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Food retrieved successfully")
                .data(food)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<FoodGetDto>> createFood(@RequestBody FoodPostDto foodPostDto) {

        FoodGetDto createdFood = foodService.createFood(foodPostDto);

        Response<FoodGetDto> response = Response.<FoodGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Food created successfully")
                .data(createdFood)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<FoodGetDto>> updateFood(@PathVariable Long id, @RequestBody FoodPutDto foodPutDto) {

        FoodGetDto updatedFood = foodService.updateFood(id, foodPutDto);

        Response<FoodGetDto> response = Response.<FoodGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Food updated successfully")
                .data(updatedFood)
                .build();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/food")
    public ResponseEntity<Response<List<FoodDTO>>> getFoodForRestaurant(@PathVariable("id") Long restaurantId) {
        List<FoodDTO> food = foodService.getFoodByRestaurantId(restaurantId);
        Response<List<FoodDTO>> response = Response.<List<FoodDTO>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(food)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Food deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

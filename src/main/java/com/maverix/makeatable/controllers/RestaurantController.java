package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Restaurent.*;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RestaurantGetDto>> getRestaurantById(@PathVariable Long id) {
        RestaurantGetDto restaurant = restaurantService.getRestaurantById(id);
        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant retrieved successfully")
                .data(restaurant)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<RestaurantGetDto>> createRestaurant(@RequestBody RestaurantPostDto restaurantPostDto) {

        RestaurantGetDto createdRestaurant = restaurantService.createRestaurant(restaurantPostDto);

        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Restaurant created successfully")
                .data(createdRestaurant)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Restaurant deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Response<RestaurantDetailsDTO>> getRestaurantDetails(@PathVariable("name") String name) {
        RestaurantDetailsDTO restaurantDetails = restaurantService.getRestaurantDetailsByName(name);
        Response<RestaurantDetailsDTO> response = Response.<RestaurantDetailsDTO>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(restaurantDetails)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<String>> updateRestaurant(
            @PathVariable("id") Long restaurantId,
            @RequestBody UpdateRestaurantDTO updateRestaurantDTO
    ) {
        restaurantService.updateRestaurant(restaurantId, updateRestaurantDTO);
        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant updated successfully.")
                .build();
        return ResponseEntity.ok(response);
    }
}

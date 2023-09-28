package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Restaurent.RestaurantGetDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantPostDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantPutDto;
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

    @PutMapping("/{id}")
    public ResponseEntity<Response<RestaurantGetDto>> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantPutDto restaurantPutDto) {

        RestaurantGetDto updatedRestaurant = restaurantService.updateRestaurant(id, restaurantPutDto);

        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant updated successfully")
                .data(updatedRestaurant)
                .build();

        return ResponseEntity.ok(response);
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
    @GetMapping("/top-rated")
    public ResponseEntity<Response<List<RestaurantGetDto>>> getTop5RatedRestaurants() {
        List<RestaurantGetDto> topRatedRestaurants = restaurantService.getTop5RatedRestaurants();

        Response<List<RestaurantGetDto>> response = Response.<List<RestaurantGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("Top 5 rated restaurants fetched successfully")
                .data(topRatedRestaurants)
                .build();

        return ResponseEntity.ok(response);
    }
}

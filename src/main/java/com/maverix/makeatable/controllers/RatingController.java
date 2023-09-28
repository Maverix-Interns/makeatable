package com.maverix.makeatable.controllers;

import com.maverix.makeatable.models.Rating;
import com.maverix.makeatable.services.RatingService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {


    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/food/{foodId}")
    public ResponseEntity<Response<Rating>> rateFood(@PathVariable Long foodId, @RequestParam Double rating) {
        if (rating < 0 || rating > 5) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Rating must be between 0 and 5.")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        Rating ratedFood = ratingService.rateFood(foodId, rating);

        if (ratedFood == null) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("Food not found.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Response.<Rating>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Food rated successfully.")
                .data(ratedFood)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Response<Rating>> rateRestaurant(@PathVariable Long restaurantId, @RequestParam Double rating) {
        if (rating < 0 || rating > 5) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Rating must be between 0 and 5.")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        Rating ratedRestaurant = ratingService.rateRestaurant(restaurantId, rating);

        if (ratedRestaurant == null) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("Restaurant not found.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Response.<Rating>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant rated successfully.")
                .data(ratedRestaurant)
                .build(), HttpStatus.OK);
    }


    @GetMapping("/food/{foodId}")
    public ResponseEntity<Response<Rating>> getFoodRating(@PathVariable Long foodId) {
        Rating foodRating = ratingService.getFoodRating(foodId);

        if (foodRating == null) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("Food not found.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Response.<Rating>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(foodRating)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Response<Rating>> getRestaurantRating(@PathVariable Long restaurantId) {
        Rating restaurantRating = ratingService.getRestaurantRating(restaurantId);

        if (restaurantRating == null) {
            return new ResponseEntity<>(Response.<Rating>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("Restaurant not found.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Response.<Rating>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(restaurantRating)
                .build(), HttpStatus.OK);
    }
}

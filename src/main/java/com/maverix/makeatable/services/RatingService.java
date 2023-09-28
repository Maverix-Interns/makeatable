package com.maverix.makeatable.services;

import com.maverix.makeatable.models.Food;
import com.maverix.makeatable.models.Rating;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.repositories.FoodRepository;
import com.maverix.makeatable.repositories.RatingRepository;
import com.maverix.makeatable.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {


    private final RatingRepository ratingRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public RatingService(RatingRepository ratingRepository, FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.ratingRepository = ratingRepository;
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Rating rateFood(Long foodId, Double rating) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if (food == null) {
            // Handle not found scenario
            return null;
        }

        Rating foodRating = food.getRating();
        if (foodRating == null) {
            foodRating = new Rating();
            foodRating.setFood(food);
        }

        updateRating(foodRating, rating);
        return ratingRepository.save(foodRating);
    }

    public Rating rateRestaurant(Long restaurantId, Double rating) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            // Handle not found scenario
            return null;
        }

        Rating restaurantRating = restaurant.getRating();
        if (restaurantRating == null) {
            restaurantRating = new Rating();
            restaurantRating.setRestaurant(restaurant);
        }

        updateRating(restaurantRating, rating);
        return ratingRepository.save(restaurantRating);
    }

    public Rating getFoodRating(Long foodId) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if (food == null) {
            // Handle not found scenario
            return null;
        }

        return food.getRating();
    }

    public Rating getRestaurantRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            // Handle not found scenario
            return null;
        }

        return restaurant.getRating();
    }

    private void updateRating(Rating rating, Double newRating) {
        double totalRating = rating.getFoodRating() * rating.getFoodRatingCount() + newRating;
        long newRatingCount = rating.getFoodRatingCount() + 1;
        rating.setFoodRating(totalRating / newRatingCount);
        rating.setFoodRatingCount(newRatingCount);
    }
}

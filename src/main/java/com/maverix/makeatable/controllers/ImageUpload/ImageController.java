package com.maverix.makeatable.controllers.ImageUpload;

import com.maverix.makeatable.exceptions.ResourceNotFoundException;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.services.StorageService;
import com.maverix.makeatable.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${app.baseURL}")
    private String baseURL;

    private final UserService userService;
    private final StorageService storageService;
    private final RestaurantService restaurantService;

    public ImageController(UserService userService, StorageService storageService, RestaurantService restaurantService) {
        this.userService = userService;
        this.storageService = storageService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<String> uploadImageRest(@PathVariable @Positive(message = "Invalid restaurant ID") Long restaurantId,
                                                  @RequestParam("file") @NotNull(message = "File cannot be null") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantFullById(restaurantId);

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            String fileName = storageService.storeFile(file);
            restaurant.setImageUrl(fileName);
            restaurantService.saveRestaurant(restaurant);

            String resourceURL = baseURL +  fileName;
            return ResponseEntity.accepted().body("Image uploaded. URL: " + resourceURL);
        } else {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> uploadImage(@PathVariable @Positive(message = "Invalid user ID") Long userId,
                                              @RequestParam("file") @NotNull(message = "File cannot be null") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        Optional<User> optionalUser = userService.getUserFullById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String fileName = storageService.storeFile(file);
            user.setImageUrl(fileName);
            userService.saveUser(user);

            String resourceURL = baseURL + "/static/uploads/" + fileName;
            return ResponseEntity.accepted().body("Image uploaded. URL: " + resourceURL);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }
}

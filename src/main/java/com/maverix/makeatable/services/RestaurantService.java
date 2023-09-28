package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Restaurent.*;
import com.maverix.makeatable.enums.RestStatus;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantGetDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    public RestaurantGetDto getRestaurantById(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant.map(this::convertToGetDto).orElse(null);
    }

    public RestaurantGetDto createRestaurant(RestaurantPostDto restaurantPostDto) {
        Restaurant restaurant = convertToEntity(restaurantPostDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToGetDto(savedRestaurant);
    }

    public RestaurantGetDto updateRestaurant(Long id, RestaurantPutDto restaurantPutDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant existingRestaurant = optionalRestaurant.get();
            BeanUtils.copyProperties(restaurantPutDto, existingRestaurant);
            existingRestaurant.setUpdatedAt(LocalDateTime.now());
            Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
            return convertToGetDto(updatedRestaurant);
        }
        return null;
    }
    public RestaurantGetDto approveRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setStatus(RestStatus.APPROVED);
        restaurantRepository.save(restaurant);

        return convertToGetDto(restaurant);

    }
    public RestaurantGetDto declineRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setStatus(RestStatus.DECLINED);
        restaurantRepository.save(restaurant);

        return convertToGetDto(restaurant);

    }

    public void deleteRestaurant(Long id) {

        restaurantRepository.deleteById(id);
    }

    private RestaurantGetDto convertToGetDto(Restaurant restaurant) {
        RestaurantGetDto restaurantGetDto = new RestaurantGetDto();
        BeanUtils.copyProperties(restaurant, restaurantGetDto);
        return restaurantGetDto;
    }

    private Restaurant convertToEntity(RestaurantPostDto restaurantPostDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantPostDto, restaurant);
        restaurant.setStatus(RestStatus.PENDING);
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant.setUpdatedAt(LocalDateTime.now());
        return restaurant;
    }
    public RestaurantDetailsDTO getRestaurantDetailsByName(String name) {
        Restaurant restaurant = restaurantRepository.findByFullName(name);
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found with name: " + name);
        }

        RestaurantDetailsDTO restaurantDetailsDTO = new RestaurantDetailsDTO();
        restaurantDetailsDTO.setName(restaurant.getFullName());
        restaurantDetailsDTO.setLocation(restaurant.getLocation());
        restaurantDetailsDTO.setPhoneNumber(restaurant.getMobileNum());

        return restaurantDetailsDTO;
    }

    // Function to update restaurant name and location
    public void updateRestaurant(Long restaurantId, UpdateRestaurantDTO updateRestaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId));

        if (updateRestaurantDTO.getName() != null) {
            restaurant.setFullName(updateRestaurantDTO.getName());
        }

        if (updateRestaurantDTO.getLocation() != null) {
            restaurant.setLocation(updateRestaurantDTO.getLocation());
        }

        // Save the updated restaurant
        restaurantRepository.save(restaurant);
    }


}

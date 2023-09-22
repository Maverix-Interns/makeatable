package com.maverix.makeatable.dto.Restaurent;


import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class RestaurantGetDto {
    private Long id;
    private String fullName;
    private Long mobileNum;
    private String location;
    private String email;
    private String imageUrl;
    private Long userId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private FoodCategory foodType;
    private Long seatNum;
    private String description;
    private Long ratingId;
    private RoomType typeRoom;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

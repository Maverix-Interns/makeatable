package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RestStatus;
import com.maverix.makeatable.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private Long mobileNum;

    private String location;

    private String email;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodType;

    private Long seatNum;

    private String description;

    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;

    @Enumerated(EnumType.STRING)
    private RoomType typeRoom;

    private RestStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}

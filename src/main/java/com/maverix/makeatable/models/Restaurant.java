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
import java.util.List;

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
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantRating> ratings;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodType;

    private Long seatNum;

    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType typeRoom;

    private RestStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    public Double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (RestaurantRating rating : ratings) {
            totalRating += rating.getRating();
        }

        return totalRating / ratings.size();
    }


}

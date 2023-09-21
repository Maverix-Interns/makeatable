package com.maverix.makeatable.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String foodType;
    private Long seatNum;
    private String description;
    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;
    private String typeRoom;
    private boolean status;


}

package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String mobileNum;

    @Enumerated(EnumType.STRING)
    private FoodCategory preference;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String usertoken;

}

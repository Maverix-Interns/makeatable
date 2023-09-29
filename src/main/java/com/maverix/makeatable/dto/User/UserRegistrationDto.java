package com.maverix.makeatable.dto.User;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String fullName;
    private String email;
    private String password;
    private String mobileNumber;
    private FoodCategory preference; //VEG,NON-VEG,MIXED
    private UserType userType;
    private UserStatus status;  // PENDING,VERIFIED
}

package com.maverix.makeatable.config.Security.Dto;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String mobileNumber;
    private FoodCategory preference; //VEG,NON-VEG,MIXED
    private UserType userType; //MANAGER, CUSTOMER
}

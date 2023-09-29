package com.maverix.makeatable.dto.User;

import lombok.Data;

@Data

public class UserProfileUpdateDto {

    private String fullName;
    private String preference;
    private String imageUrl;

}

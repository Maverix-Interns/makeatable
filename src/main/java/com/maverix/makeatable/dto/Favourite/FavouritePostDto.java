package com.maverix.makeatable.dto.Favourite;

import lombok.Data;

@Data
public class FavouritePostDto {
    private Long id;
    private Long userId;
    private Long restaurantId;

}

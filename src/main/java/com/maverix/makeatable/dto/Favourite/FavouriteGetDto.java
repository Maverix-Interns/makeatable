package com.maverix.makeatable.dto.Favourite;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FavouriteGetDto {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}

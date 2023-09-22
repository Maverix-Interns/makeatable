package com.maverix.makeatable.dto.Rating;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class RatingGetDto {
    private Long id;
    private Double rating;
    private Long rateNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.maverix.makeatable.dto;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class RatingDto {
    private Long id;
    private Double Rating;
}

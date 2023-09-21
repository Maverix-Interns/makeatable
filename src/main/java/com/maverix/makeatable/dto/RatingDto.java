package com.maverix.makeatable.dto;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class RatingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double Rating;
}

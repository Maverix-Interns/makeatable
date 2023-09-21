package com.maverix.makeatable.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double Rating;
    private Long rateNum;
}

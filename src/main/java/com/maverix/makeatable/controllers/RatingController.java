package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Rating.RatingGetDto;
import com.maverix.makeatable.dto.Rating.RatingPostDto;
import com.maverix.makeatable.dto.Rating.RatingPutDto;
import com.maverix.makeatable.services.RatingService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response<RatingGetDto>> getRatingById(@PathVariable Long id) {
        RatingGetDto rating = ratingService.getRatingById(id);
        Response<RatingGetDto> response = Response.<RatingGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Rating retrieved successfully")
                .data(rating)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<RatingGetDto>> createRating(@RequestBody RatingPostDto ratingPostDto) {

        RatingGetDto createdRating = ratingService.createRating(ratingPostDto);

        Response<RatingGetDto> response = Response.<RatingGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Rating created successfully")
                .data(createdRating)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<RatingGetDto>> updateRating(@PathVariable Long id, @RequestBody RatingPutDto ratingPutDto) {

        RatingGetDto updatedRating = ratingService.updateRating(id, ratingPutDto);

        Response<RatingGetDto> response = Response.<RatingGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Rating updated successfully")
                .data(updatedRating)
                .build();

        return ResponseEntity.ok(response);
    }
}

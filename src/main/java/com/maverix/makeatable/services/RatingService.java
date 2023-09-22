package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Rating.RatingGetDto;
import com.maverix.makeatable.dto.Rating.RatingPostDto;
import com.maverix.makeatable.dto.Rating.RatingPutDto;
import com.maverix.makeatable.models.Rating;
import com.maverix.makeatable.repositories.RatingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingGetDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    public RatingGetDto getRatingById(Long id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.map(this::convertToGetDto).orElse(null);
    }

    public RatingGetDto createRating(RatingPostDto ratingPostDto) {
        Rating rating = convertToEntity(ratingPostDto);
        Rating savedRating = ratingRepository.save(rating);
        return convertToGetDto(savedRating);
    }

    public RatingGetDto updateRating(Long id, RatingPutDto ratingPutDto) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating existingRating = optionalRating.get();
            BeanUtils.copyProperties(ratingPutDto, existingRating);
            existingRating.setUpdatedAt(LocalDateTime.now());
            Rating updatedRating = ratingRepository.save(existingRating);
            return convertToGetDto(updatedRating);
        }
        return null;
    }

    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    private RatingGetDto convertToGetDto(Rating rating) {
        RatingGetDto ratingGetDto = new RatingGetDto();
        BeanUtils.copyProperties(rating, ratingGetDto);
        return ratingGetDto;
    }

    private Rating convertToEntity(RatingPostDto ratingPostDto) {
        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingPostDto, rating);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUpdatedAt(LocalDateTime.now());
        return rating;
    }
}

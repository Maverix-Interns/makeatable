package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.Favourite.FavouriteGetDto;
import com.maverix.makeatable.dto.Favourite.FavouritePostDto;
import com.maverix.makeatable.services.FavouriteService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<FavouriteGetDto>> getFavouriteById(@PathVariable Long id) {
        FavouriteGetDto favourite = favouriteService.getFavouriteById(id);
        Response<FavouriteGetDto> response = Response.<FavouriteGetDto>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Favourite retrieved successfully")
                .data(favourite)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<FavouritePostDto>> createFavourite(@RequestBody FavouritePostDto favouritepostDto) {

        FavouritePostDto createdFavourite = favouriteService.createFavourite(favouritepostDto);

        Response<FavouritePostDto> response = Response.<FavouritePostDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Favourite created successfully")
                .data(createdFavourite)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);

        Response<Void> response = Response.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Favourite deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

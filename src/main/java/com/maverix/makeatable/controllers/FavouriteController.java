package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.Favourite.FavouritegetDto;
import com.maverix.makeatable.dto.Favourite.FavouritepostDto;
import com.maverix.makeatable.services.FavouriteService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping
    public ResponseEntity<Response<List<FavouritegetDto>>> getAllFavourites() {
        List<FavouritegetDto> favourites = favouriteService.getAllFavourites();
        Response<List<FavouritegetDto>> response = Response.<List<FavouritegetDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Favourites retrieved successfully")
                .data(favourites)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FavouritegetDto>> getFavouriteById(@PathVariable Long id) {
        FavouritegetDto favourite = favouriteService.getFavouriteById(id);
        Response<FavouritegetDto> response = Response.<FavouritegetDto>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Favourite retrieved successfully")
                .data(favourite)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<FavouritepostDto>> createFavourite(@RequestBody FavouritepostDto favouritepostDto) {

        FavouritepostDto createdFavourite = favouriteService.createFavourite(favouritepostDto);

        Response<FavouritepostDto> response = Response.<FavouritepostDto>builder()
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

package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Favourite.FavouritegetDto;
import com.maverix.makeatable.dto.Favourite.FavouritepostDto;
import com.maverix.makeatable.models.Favourite;
import com.maverix.makeatable.repositories.FavouriteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;

    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public List<FavouritegetDto> getAllFavourites() {
        List<Favourite> favourites = favouriteRepository.findAll();
        return favourites.stream()
                .map(this::convertToFavouritegetDto)
                .collect(Collectors.toList());
    }

    public FavouritegetDto getFavouriteById(Long id) {
        Optional<Favourite> optionalFavourite = favouriteRepository.findById(id);
        return optionalFavourite.map(this::convertToFavouritegetDto).orElse(null);
    }

    public FavouritepostDto createFavourite(FavouritepostDto favouritepostDto) {
        Favourite favourite = convertToEntity(favouritepostDto);
        Favourite savedFavourite = favouriteRepository.save(favourite);
        return convertToFavouritepostDto(savedFavourite);
    }


    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }
    private FavouritegetDto convertToFavouritegetDto(Favourite favourite) {
        FavouritegetDto favouritegetDto = new FavouritegetDto();
        BeanUtils.copyProperties(favourite, favouritegetDto);
        return favouritegetDto;
    }


    private FavouritepostDto convertToFavouritepostDto(Favourite favourite) {
        FavouritepostDto favouritepostDto = new FavouritepostDto();
        BeanUtils.copyProperties(favourite, favouritepostDto);
        return favouritepostDto;
    }

    private Favourite convertToEntity(FavouritepostDto favouritepostDto) {
        Favourite favourite = new Favourite();
        BeanUtils.copyProperties(favouritepostDto, favourite);
        favourite.setCreatedDate(LocalDateTime.now());
        favourite.setLastModifiedDate(LocalDateTime.now());
        return favourite;
    }

}

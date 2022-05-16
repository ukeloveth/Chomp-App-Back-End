package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.exceptions.NoFavoriteException;
import com.decagon.chompapp.exceptions.ProductNotFoundException;
import com.decagon.chompapp.exceptions.UserNotFoundException;
import com.decagon.chompapp.models.Favorites;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.FavoriteRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.FavoritesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;


    @Override
    public ResponseEntity<String> addProductToFavorite(Long productId) {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        User user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User with this id does not exist. " +
                        "Please check and try again."));
        Product favoriteProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("This product", "id", productId));

        Boolean favorites = favoriteRepository.existsByUserIdAndFavoriteProductId(user.getUserId(), productId);

        if (!favorites){
            Favorites fav = new Favorites();
            fav.setUserId(user.getUserId());
            fav.setFavoriteProductId(favoriteProduct.getProductId());
            Favorites favorites1 = favoriteRepository.save(fav);
            return new ResponseEntity<>(favorites1.getFavoriteId()+ " added to favorite", HttpStatus.OK);
        }

        return new ResponseEntity<>(favoriteProduct.getProductName() + " is already your favorite", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> removeProductFromFavorite(Long favoriteId) {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        User user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User with this id does not exist. " +
                        "Please check and try again."));

        Favorites favoriteProduct = favoriteRepository.findById(favoriteId)
                .orElseThrow(()-> new NoFavoriteException("Favorite dose not exist"));

        if(favoriteProduct.getUserId().equals(user.getUserId())) {
            favoriteRepository.delete(favoriteProduct);
            return new ResponseEntity<>("Your favorite has been removed.", HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not authorised to perform this operation", HttpStatus.FORBIDDEN);
    }
}

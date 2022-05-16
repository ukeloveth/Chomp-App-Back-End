package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.models.Favorites;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.FavoriteRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class FavoritesServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    FavoriteRepository favoriteRepository;

    @InjectMocks
    FavoritesServiceImpl favoritesServiceImpl;

    User user1 = User.builder()
            .userId(1L)
            .email("james@mail.com")
            .password("password")
            .username("james")
            .firstName("James")
            .build();

    Product favoriteProduct = new Product();
    Favorites favorite = Favorites.builder().favoriteId(1L).favoriteProductId(1L).userId(1L).build();
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        favoriteProduct.setProductId(1L);
        favoriteProduct.setProductName("Product 1");
        favorite.setUserId(1L);
    }

    @Test
    public void testAddProductToFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user1));

        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(favoriteProduct));

        Mockito.when(favoriteRepository.existsByUserIdAndFavoriteProductId(anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<String > responseEntity = favoritesServiceImpl.addProductToFavorite(1L);
        Assertions.assertEquals(responseEntity.getBody(), favoriteProduct.getProductId() + " is already your favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST );
    }

    @Test
    public void testAddProductToFavoriteThatIsAlreadyInFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));

        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(favoriteProduct));

        Mockito.when(favoriteRepository.existsByUserIdAndFavoriteProductId(any(), any())).thenReturn(false);
        Mockito.when(favoriteRepository.save(any())).thenReturn(favorite);

        ResponseEntity<String> responseEntity = favoritesServiceImpl.addProductToFavorite(favoriteProduct.getProductId());

        Assertions.assertEquals(responseEntity.getBody(), favorite.getFavoriteId() + " added to favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testRemoveProductFromFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(favoriteRepository.findById(any())).thenReturn(Optional.of(favorite));

        ResponseEntity<String> responseEntity1 = favoritesServiceImpl.removeProductFromFavorite(1L);

        Assertions.assertEquals(responseEntity1.getBody(), "You are not authorised to perform this operation");
    }

    @Test
    void removeProductFromFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));
        Mockito.when(favoriteRepository.findById(any())).thenReturn(Optional.of(favorite));

        ResponseEntity<String> responseEntity1 = favoritesServiceImpl.removeProductFromFavorite(1L);

        Assertions.assertEquals(responseEntity1.getBody(), "Your favorite has been removed.");
    }
}
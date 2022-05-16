package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    Boolean existsByUserIdAndFavoriteProductId(Long userId, Long favoriteProductId);
}
package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.CartItemDto;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

public interface CartService {
    @Transactional
    ResponseEntity<CartItemDto> addToCart(long productId);

    @Transactional
    ResponseEntity<String> clearCart();

    @Transactional
    ResponseEntity<String> removeCartItem(long cartItemId);

    @Transactional
    ResponseEntity<String> reduceQuantityInCart(long cartItemId);

    @Transactional
    ResponseEntity<String> increaseQuantityInCart(long cartItemId);
}

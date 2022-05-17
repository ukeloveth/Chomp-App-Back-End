package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.CartItemDto;
import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.CartItem;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.CartItemRepository;
import com.decagon.chompapp.repositories.CartRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;


    private User getLoggedInUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(authentication, authentication)
                .orElseThrow(()-> new RuntimeException("User not authorized"));
    }

    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        cart.setCartTotal(cart.getCartTotal() - cartItem.getSubTotal());
        cart.setQuantity(cart.getQuantity()-1);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public ResponseEntity<CartItemDto> addToCart(long productId){
        User user = getLoggedInUser();
        return addToCartAndReturnDto(productId, user);

    }

    private ResponseEntity<CartItemDto> addToCartAndReturnDto(long productId, User user) {
        Optional<Product> productCheck = productRepository.findById(productId);
        if (productCheck.isPresent()) {
            Product product = productCheck.get();
            Cart userCart = user.getCart();
            Optional<CartItem> productAlreadyInCart = cartItemRepository.findByCartAndProduct(userCart, product);
            if (productAlreadyInCart.isEmpty()) {
                CartItem cartItem = new CartItem();
                cartItem.setCart(userCart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setSubTotal(product.getProductPrice());

                userCart.getCartItemList().add(cartItem);
                userCart.setQuantity(userCart.getQuantity() + 1);
                return getCartItemDtoResponseEntity(userCart, cartItem);
            } else {
                CartItem itemInCart = productAlreadyInCart.get();
                itemInCart.setQuantity(itemInCart.getQuantity() + 1);
                itemInCart.setSubTotal(itemInCart.getSubTotal() + product.getProductPrice());
                CartItem savedCartItem = cartItemRepository.save(itemInCart);
                return getCartItemDtoResponseEntity(userCart, savedCartItem);
            }
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    private ResponseEntity<CartItemDto> getCartItemDtoResponseEntity(Cart userCart, CartItem cartItem) {
        var cartItemList = userCart.getCartItemList();
        var total = cartItemList.stream().mapToDouble(CartItem::getSubTotal).sum();
        userCart.setCartTotal(total);
        cartRepository.save(userCart);
        CartItemDto cartItemDto = convertCartItemToDto(cartItem);
        return ResponseEntity.ok(cartItemDto);
    }

    private CartItemDto convertCartItemToDto(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        Product product = cartItem.getProduct();

         cartItemDto.setId(cartItem.getCartItemId());
         cartItemDto.setProductId(product.getProductId());
         cartItemDto.setProductName(product.getProductName());
         cartItemDto.setProductImage(product.getProductImage());
         cartItemDto.setProductSize(product.getSize());
         cartItemDto.setQuantity(cartItem.getQuantity());
         cartItemDto.setUnitPrice(product.getProductPrice());
         cartItemDto.setSubTotal(cartItem.getSubTotal());
         cartItemDto.setCartId(cartItem.getCart().getCartId());

         return cartItemDto;
    }


    @Override
    @Transactional
    public ResponseEntity<String> clearCart() {
        User user = getLoggedInUser();
        Cart userCart = user.getCart();
        cartItemRepository.deleteAllByCart_CartId(userCart.getCartId());
        userCart.setCartTotal(0);
        userCart.setQuantity(0);
        cartRepository.save(userCart);
        return ResponseEntity.ok("Cart cleared successfully");

    }


    @Override
    @Transactional
    public ResponseEntity<String> removeCartItem(
            long cartItemId){
        User user = getLoggedInUser();
        Cart cart = user.getCart();
        Optional<CartItem> cartItemCheck = cartItemRepository.findByCart_CartIdAndCartItemId(cart.getCartId(), cartItemId);
        if (cartItemCheck.isPresent()) {
            CartItem cartItem = cartItemCheck.get();
            removeItem(cartItemId, cart, cartItem);
            return ResponseEntity.ok("Item removed from user cart");
        } else {
            throw new RuntimeException("Item is not in user cart");
        }
    }



    @Override
    @Transactional
    public ResponseEntity<String> reduceQuantityInCart(long cartItemId){
        User user = getLoggedInUser();
        Cart cart = user.getCart();
        Optional<CartItem> cartItemCheck = cartItemRepository.findByCart_CartIdAndCartItemId(user.getCart().getCartId(), cartItemId);
        if (cartItemCheck.isPresent()) {
            CartItem cartItem = cartItemCheck.get();
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                double productPrice = cartItem.getProduct().getProductPrice();
                cartItem.setSubTotal(cartItem.getSubTotal() - productPrice);
                cartItemRepository.save(cartItem);
                cart.setCartTotal(cart.getCartTotal()- productPrice);
                cartRepository.save(cart);
                return ResponseEntity.ok("Quantity reduced by 1");
            }else {
                removeItem(cartItemId, cart, cartItem);
                return ResponseEntity.ok("Item removed from user cart");
            }
        } else{
            throw new RuntimeException("Item is not in user cart");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> increaseQuantityInCart(long cartItemId){
        User user = getLoggedInUser();
        Cart cart = user.getCart();
        Optional<CartItem> cartItemCheck = cartItemRepository.findByCart_CartIdAndCartItemId(user.getCart().getCartId(), cartItemId);
        if (cartItemCheck.isPresent()) {
            CartItem cartItem = cartItemCheck.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            double productPrice = cartItem.getProduct().getProductPrice();
            cartItem.setSubTotal(cartItem.getSubTotal() + productPrice);
            cartItemRepository.save(cartItem);
            cart.setCartTotal(cart.getCartTotal() + productPrice);
            cartRepository.save(cart);
            return ResponseEntity.ok("Quantity increased by 1");
        } else{
            throw new RuntimeException("Item is not in user cart");
        }
    }

}

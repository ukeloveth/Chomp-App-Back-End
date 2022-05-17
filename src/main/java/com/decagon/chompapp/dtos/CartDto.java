package com.decagon.chompapp.dtos;

import java.util.List;

public class CartDto {
    private long Id;
    private int quanity;
    private double total;
    private List<CartItemDto> listOfItems;
    private long userId;
}

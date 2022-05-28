package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.OrderResponse;
import org.springframework.http.ResponseEntity;

public interface OrderServices {

    ResponseEntity<OrderResponse> viewOrderHistoryForAPremiumUser (int pageNo, int pageSize, String sortBy, String sortDir);

}

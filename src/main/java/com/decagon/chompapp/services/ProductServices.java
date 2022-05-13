package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.ProductResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;

public interface ProductServices {

    ResponseEntity<ProductResponse> getAllProducts (int pageNo, int pageSize, String sortBy, String sortDir, String filterBy, String filterParam, String startRange, String endRange)throws ServletException;
}

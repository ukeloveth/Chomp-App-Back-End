package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.dtos.ProductResponse;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.services.ProductServices;
import com.decagon.chompapp.services.UserService;
import com.decagon.chompapp.utils.AppConstants;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductServices productServices;

    ResponseEntity<ProductResponse> responseEntity;

    @BeforeEach
    void setUp() throws ServletException {
        Category burger = Category.builder().categoryId(1L).categoryName("Burger").build();
        Product product = Product.builder().productId(1L).productName("Product 1").size("small").category(burger).build();
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product);
        Page<Product> products = new PageImpl<>(listOfProducts);
        ProductDto productDto = ProductDto.builder().productId(1L).productName("Product 1").size("small").categoryName(product.getCategory().getCategoryName()).build();
        List<ProductDto> content = new ArrayList<>();
        content.add(productDto);
        ProductResponse productResponse= ProductResponse.builder()
                .content(content)
                .pageNo(products.getNumber())
                .totalPages(products.getTotalPages())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .last(products.isLast())
                .build();

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        responseEntity = new ResponseEntity<>(productResponse, httpHeaders, HttpStatus.OK);
        Mockito.when(productServices.getAllProducts(anyInt(),anyInt(),anyString(),anyString(),anyString(),anyString())).thenReturn(responseEntity);
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageSize","10").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGetAndThrows405IfNotGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/users/getAllProducts").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
    }

    @Test
    void testsThatBusinessLogicIsCalledWhenTheUrlIsHit () throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER).param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).param("sortBy", AppConstants.DEFAULT_SORT_BY)).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productServices, times(1)).getAllProducts(integerArgumentCaptor.capture(), integerArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        Assertions.assertEquals(integerArgumentCaptor.getValue(),10);
    }

    @Test
    void testsThatTheCorrectResponseIsReturnedWhichInThisCaseIsAResponseEntityOfProductResponse () throws Exception {
        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER).param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).param("sortBy", AppConstants.DEFAULT_SORT_BY)).param("sortDir",AppConstants.DEFAULT_SORT_DIRECTION).param("filterBy","").param("filterParam",AppConstants.DEFAULT_FILTER_PARAMETER)).andExpect(jsonPath("$.pageNo").value(0)).andExpect(jsonPath("$.pageSize").value(1)).andExpect(jsonPath("$.totalElements").value(1)).andExpect(jsonPath("$.totalPages").value(1)).andExpect(jsonPath("$.last").value(true)).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(expectedResponse,actualResponse);
    }
}

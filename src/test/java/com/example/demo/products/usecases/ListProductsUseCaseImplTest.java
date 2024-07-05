package com.example.demo.products.usecases;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.demo.products.domain.dtos.ProductResponseDTO;
import com.example.demo.products.domain.mappers.ProductResponseMapper;
import com.example.demo.products.domain.models.ProductModel;
import com.example.demo.products.infra.repositories.ProductRepository;

class ListProductsUseCaseImplTest {
    @InjectMocks
    ListProductsUseCaseImpl listProductsUseCaseImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductResponseMapper productResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldListProducts() {
        var LENGTH = 5;
        var productModelList = new ArrayList<ProductModel>(LENGTH);
        var productResponseDTOList = new ArrayList<ProductResponseDTO>(LENGTH);

        var page = 1;
        var pageSize = 10;
        var pageRequest = PageRequest.of(page - 1, pageSize);
        var paginatedResponse = new PageImpl<>(productModelList, pageRequest, productModelList.size());

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(paginatedResponse);
        when(productResponseMapper.toDTOList(productModelList)).thenReturn(productResponseDTOList);

        var result = listProductsUseCaseImpl.execute(page, pageSize);

        assertEquals(productResponseDTOList, result.getProducts());
        verify(productRepository).findAll(pageRequest);
        verify(productResponseMapper).toDTOList(productModelList);
    }
}

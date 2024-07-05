package com.example.demo.products.domain.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductPaginatedResponseDTO {
    private final List<ProductResponseDTO> products;
    private final int pages;
    private final long elements;

}

package com.example.demo.products.domain.usecases;

import com.example.demo.products.domain.dtos.ProductPaginatedResponseDTO;

public interface ListProductsUseCase {
    public ProductPaginatedResponseDTO execute(int page, int pageSize);
}

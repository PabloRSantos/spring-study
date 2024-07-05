package com.example.demo.products.domain.usecases;

import java.util.UUID;

import com.example.demo.products.domain.dtos.ProductResponseDTO;

public interface GetProductByIdUseCase {
    public ProductResponseDTO execute(UUID id);
}

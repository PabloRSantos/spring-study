package com.example.demo.products.domain.usecases;

import java.util.UUID;

import com.example.demo.products.domain.dtos.ProductRequestDTO;

public interface UpdateProductUseCase {
    public void execute(UUID id, ProductRequestDTO productRequestDTO);
}

package com.example.demo.products.usecases;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.products.domain.dtos.ProductResponseDTO;
import com.example.demo.products.domain.mappers.ProductResponseMapper;
import com.example.demo.products.domain.usecases.GetProductByIdUseCase;
import com.example.demo.products.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductResponseDTO execute(UUID id) {
        var productModel = this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return this.productResponseMapper.toDTO(productModel);
    }
}

package com.example.demo.products.usecases;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.mappers.ProductRequestMapper;
import com.example.demo.products.domain.usecases.UpdateProductUseCase;
import com.example.demo.products.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductRequestMapper productRequestMapper;

    @Override
    public void execute(UUID id, ProductRequestDTO productRequestDTO) {
        var productModel = this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        this.productRequestMapper.merge(productRequestDTO, productModel);

        this.productRepository.save(productModel);
    }
}

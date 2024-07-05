package com.example.demo.products.usecases;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.products.domain.dtos.ProductPaginatedResponseDTO;
import com.example.demo.products.domain.mappers.ProductResponseMapper;
import com.example.demo.products.domain.usecases.ListProductsUseCase;
import com.example.demo.products.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProductsUseCaseImpl implements ListProductsUseCase {
    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductPaginatedResponseDTO execute(int page, int pageSize) {
        var pageRequest = PageRequest.of(page - 1, pageSize);
        var result = productRepository.findAll(pageRequest);

        return new ProductPaginatedResponseDTO(
                this.productResponseMapper.toDTOList(result.getContent()),
                result.getTotalPages(),
                result.getTotalElements());
    }
}

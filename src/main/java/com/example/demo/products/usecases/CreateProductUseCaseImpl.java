package com.example.demo.products.usecases;

import org.springframework.stereotype.Service;

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.usecases.CreateProductUseCase;
import com.example.demo.products.infra.repositories.ProductRepository;
import com.example.demo.users.domain.models.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public void execute(ProductRequestDTO productRequestDTO, UserModel userModel) {
        var productModel = productRequestDTO.toModel();
        productModel.setAuthor(userModel);

        this.productRepository.save(productModel);
    }
}

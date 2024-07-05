package com.example.demo.products.domain.usecases;

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.users.domain.models.UserModel;

public interface CreateProductUseCase {
    public void execute(ProductRequestDTO productRequestDTO, UserModel userModel);
}

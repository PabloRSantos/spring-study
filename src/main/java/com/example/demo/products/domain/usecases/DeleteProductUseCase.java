package com.example.demo.products.domain.usecases;

import java.util.UUID;

public interface DeleteProductUseCase {
    public void execute(UUID id);
}

package com.example.demo.products.presentation.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.products.domain.dtos.ProductPaginatedResponseDTO;
import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.dtos.ProductResponseDTO;
import com.example.demo.products.domain.usecases.CreateProductUseCase;
import com.example.demo.products.domain.usecases.DeleteProductUseCase;
import com.example.demo.products.domain.usecases.GetProductByIdUseCase;
import com.example.demo.products.domain.usecases.ListProductsUseCase;
import com.example.demo.products.domain.usecases.UpdateProductUseCase;
import com.example.demo.users.domain.models.UserModel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO,
            Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        this.createProductUseCase.execute(productRequestDTO, userModel);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ProductPaginatedResponseDTO> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.listProductsUseCase.execute(page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.getProductByIdUseCase.execute(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        this.updateProductUseCase.execute(id, productRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") UUID id) {
        this.deleteProductUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}

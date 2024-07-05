package com.example.demo.products.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.products.domain.dtos.ProductResponseDTO;
import com.example.demo.products.domain.mappers.ProductResponseMapper;
import com.example.demo.products.domain.models.ProductModel;
import com.example.demo.products.infra.repositories.ProductRepository;
import com.example.demo.users.domain.dtos.UserResponseDTO;

class GetProductByIdUseCaseImplTest {
    @InjectMocks
    GetProductByIdUseCaseImpl getProductByIdUseCaseImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductResponseMapper productResponseMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindProduct() {
        var id = UUID.randomUUID();
        var name = "some_name";
        var value = new BigDecimal(10);
        var createdAt = LocalDateTime.now();
        var updatedAt = LocalDateTime.now();

        var userResponseDTO = new UserResponseDTO(UUID.randomUUID(), "some_name", "some_email");
        var productResponseDTO = new ProductResponseDTO(id, name, value, userResponseDTO, createdAt, updatedAt);
        var productModel = new ProductModel();

        when(productRepository.findById(id)).thenReturn(Optional.of(productModel));
        when(productResponseMapper.toDTO(productModel)).thenReturn(productResponseDTO);

        var result = getProductByIdUseCaseImpl.execute(id);

        verify(productRepository).findById(id);
        assertEquals(result, productResponseDTO);
    }

    @Test
    void shouldThrowIfProductNotExists() {
        var id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        var exception = assertThrows(ResponseStatusException.class, () -> {
            getProductByIdUseCaseImpl.execute(id);
        });

        verify(productRepository).findById(id);
        assertEquals(404, exception.getBody().getStatus());
        assertEquals("Product not found", exception.getReason());
    }
}

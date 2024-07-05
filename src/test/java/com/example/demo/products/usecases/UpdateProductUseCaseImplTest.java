package com.example.demo.products.usecases;

import java.math.BigDecimal;
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

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.mappers.ProductRequestMapper;
import com.example.demo.products.domain.models.ProductModel;
import com.example.demo.products.infra.repositories.ProductRepository;

class UpdateProductUseCaseImplTest {
    @InjectMocks
    UpdateProductUseCaseImpl updateProductUseCaseImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductRequestMapper productRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateProduct() {
        var id = UUID.randomUUID();
        var name = "some_product";
        var value = new BigDecimal(10);

        var productModel = new ProductModel();
        var productRequestDTO = new ProductRequestDTO(name, value);

        when(productRepository.findById(id)).thenReturn(Optional.of(productModel));

        updateProductUseCaseImpl.execute(id, productRequestDTO);

        verify(productRepository).findById(id);
        verify(productRequestMapper).merge(productRequestDTO, productModel);
        verify(productRepository).save(productModel);
    }

    @Test
    void shouldThrowIfProductNotExists() {
        var id = UUID.randomUUID();
        var name = "some_product";
        var value = new BigDecimal(10);

        var productRequestDTO = new ProductRequestDTO(name, value);

        var exception = assertThrows(ResponseStatusException.class, () -> {
            updateProductUseCaseImpl.execute(id, productRequestDTO);
        });

        assertEquals(404, exception.getBody().getStatus());
        assertEquals("Product not found", exception.getReason());
    }

}

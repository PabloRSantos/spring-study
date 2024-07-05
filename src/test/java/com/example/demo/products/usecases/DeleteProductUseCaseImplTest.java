package com.example.demo.products.usecases;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.products.domain.models.ProductModel;
import com.example.demo.products.infra.repositories.ProductRepository;

class DeleteProductUseCaseImplTest {
    @InjectMocks
    DeleteProductUseCaseImpl deleteProductUseCaseImpl;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteProduct() {
        var id = UUID.randomUUID();
        var productModel = new ProductModel();

        when(productRepository.findById(id)).thenReturn(Optional.of(productModel));

        deleteProductUseCaseImpl.execute(id);

        verify(productRepository).findById(id);
        verify(productRepository).delete(productModel);
    }

    @Test
    void shouldThrowIfProductNotExists() {
        var id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResponseStatusException.class,
                () -> {
                    deleteProductUseCaseImpl.execute(id);
                });

        verify(productRepository, times(1)).findById(id);
        assertEquals(404, exception.getBody().getStatus());
        assertEquals("Product not found", exception.getReason());
    }
}

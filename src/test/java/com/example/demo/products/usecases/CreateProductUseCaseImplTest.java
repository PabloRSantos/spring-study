package com.example.demo.products.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.models.ProductModel;
import com.example.demo.products.infra.repositories.ProductRepository;
import com.example.demo.users.domain.models.UserModel;

class CreateProductUseCaseImplTest {

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCaseImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRequestDTO productRequestDTO;

    @Mock
    private ProductModel productModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProduct() {
        var userModel = new UserModel();

        when(productRequestDTO.toModel()).thenReturn(productModel);

        createProductUseCaseImpl.execute(productRequestDTO, userModel);

        verify(productRequestDTO).toModel();
        verify(productModel).setAuthor(userModel);
        verify(productRepository).save(productModel);
    }
}

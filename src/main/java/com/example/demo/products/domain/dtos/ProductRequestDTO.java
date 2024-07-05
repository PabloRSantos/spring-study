package com.example.demo.products.domain.dtos;

import java.math.BigDecimal;

import com.example.demo.products.domain.models.ProductModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
public class ProductRequestDTO {
        @NotBlank
        private final String name;

        @NotNull
        private final BigDecimal value;

        public ProductModel toModel() {
                var productModel = new ProductModel();
                productModel.setName(this.name);
                productModel.setValue(this.value);

                return productModel;
        }
}

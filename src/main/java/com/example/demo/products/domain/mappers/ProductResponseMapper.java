package com.example.demo.products.domain.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.products.domain.dtos.ProductResponseDTO;
import com.example.demo.products.domain.models.ProductModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductResponseMapper {
    ProductResponseDTO toDTO(ProductModel source);

    @Mapping(source = "payload", target = "payload")
    List<ProductResponseDTO> toDTOList(List<ProductModel> source);
}

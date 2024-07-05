package com.example.demo.products.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.demo.products.domain.dtos.ProductRequestDTO;
import com.example.demo.products.domain.models.ProductModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductRequestMapper {
    void merge(ProductRequestDTO source, @MappingTarget ProductModel target);
}

package com.example.demo.users.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.users.domain.dtos.SignUpRequestDTO;
import com.example.demo.users.domain.models.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignUpRequestMapper {
    UserModel toModel(SignUpRequestDTO source);
}

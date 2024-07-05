package com.example.demo.users.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.users.domain.dtos.SignInResponseDTO;
import com.example.demo.users.domain.models.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignInResponseMapper {
    @Mapping(source = "userModel", target = "user")
    @Mapping(source = "payload", target = "payload")
    SignInResponseDTO toDTO(UserModel userModel, String payload);
}

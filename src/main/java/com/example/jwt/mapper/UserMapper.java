package com.example.jwt.mapper;

import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper (
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper extends WebMapper<SignUpRequest, User> {

    SignUpRequest toCreateSignUpRequest(User entity);

    User createSignUpRequestToEntity(SignUpRequest dto);



}
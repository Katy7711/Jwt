package com.example.jwt.mapper;



public interface WebMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

}
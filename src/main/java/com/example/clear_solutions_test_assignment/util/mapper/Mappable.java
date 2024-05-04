package com.example.clear_solutions_test_assignment.util.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface Mappable<E, D> {
    D toDto(E entity);

    List<D> toDtos(List<E> entities);

    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    void updateFromDto(D source, @MappingTarget E target);

}

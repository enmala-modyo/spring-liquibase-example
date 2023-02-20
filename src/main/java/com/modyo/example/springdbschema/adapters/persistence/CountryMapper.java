package com.modyo.example.springdbschema.adapters.persistence;

import com.modyo.example.springdbschema.domain.model.Country;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    componentModel = "spring")
public interface CountryMapper {

  @Mapping(source = "id", target = "code")
  Country toEntity(CountryJpaEntity jpaEntity);

}

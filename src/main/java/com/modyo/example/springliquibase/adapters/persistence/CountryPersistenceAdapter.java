package com.modyo.example.springliquibase.adapters.persistence;

import com.modyo.example.springliquibase.application.port.out.LoadCountryPort;
import com.modyo.example.springliquibase.domain.model.Country;
import com.modyo.ms.commons.core.exceptions.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CountryPersistenceAdapter implements LoadCountryPort {
  private final CountryJpaRepository repository;
  private final CountryMapper mapper;

  @Override
  public Country loadCountry(String code) {
    return mapper.toEntity(repository.findById(code).orElseThrow(NotFoundException::new));
  }

  @Override
  public List<Country> loadAllCountries() {
    return repository.findAll().stream().map(mapper::toEntity).collect(Collectors.toList());
  }
}

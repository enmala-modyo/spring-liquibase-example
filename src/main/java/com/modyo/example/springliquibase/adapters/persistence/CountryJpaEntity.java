package com.modyo.example.springliquibase.adapters.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Country")
@Getter
@Setter
public class CountryJpaEntity {

  @Id
  @Column(name = "code", nullable = false, columnDefinition = "char(2)")
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "iso3", nullable = false, columnDefinition = "char(3)")
  private String iso3;

}

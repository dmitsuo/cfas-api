package com.algaworks.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "categoria")
@Data
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotNull
	@Size(min = 3, max = 20)
	@ToString.Include
	private String nome;
}

package com.algaworks.algamoney.api.model;

import java.beans.Transient;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "pessoa")
@Data
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotEmpty
	@ToString.Include
	private String nome;
	@NotEmpty
	private String naturalidade;
	private boolean ativo = true;
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}
}

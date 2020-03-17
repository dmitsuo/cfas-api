package com.algaworks.algamoney.api.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "permissao")
@Data
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permissao implements Serializable {

	@Id
	@EqualsAndHashCode.Include
	private Long codigo;
	@ToString.Include
	private String descricao;
}

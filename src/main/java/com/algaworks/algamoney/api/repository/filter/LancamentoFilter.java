package com.algaworks.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	public boolean isEmptyDataVencimentoDe() {		
		return dataVencimentoDe == null;
	}
	
	public boolean isEmptyDataVencimentoAte() {
		return dataVencimentoAte == null;
	}
	
	public boolean isEmptyDescricao() {
		return descricao == null || descricao.trim().isEmpty();
	}

}



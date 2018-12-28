package com.algaworks.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter f) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Lancamento> c = b.createQuery(Lancamento.class);		
		Root<Lancamento> r = c.from(Lancamento.class);
		
		// Implementação dos filtros
		Predicate[] p = criarRestricoes(f, b, r);
		c.where(p);
		
		
		
		TypedQuery<Lancamento> q = em.createQuery(c);		
		return q.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter f, CriteriaBuilder b, Root<Lancamento> r) {
		List<Predicate> p = new ArrayList<>();
		
		if (!f.isEmptyDataVencimentoDe()) {
			p.add(b.greaterThanOrEqualTo(r.get("dataVencimento"), f.getDataVencimentoDe()));
		}
		
		if (!f.isEmptyDataVencimentoAte()) {
			p.add(b.lessThanOrEqualTo(r.get("dataVencimento"), f.getDataVencimentoAte()));			
		}
		
		if (!f.isEmptyDescricao()) {
			p.add(b.like(b.lower(r.get("descricao")), "%" + f.getDescricao().toLowerCase() + "%"));
		}

		return p.toArray(new Predicate[p.size()]);
	}

}

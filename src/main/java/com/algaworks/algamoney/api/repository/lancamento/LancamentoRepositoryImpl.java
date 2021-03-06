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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Categoria_;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Lancamento_;
import com.algaworks.algamoney.api.model.Pessoa_;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter f, Pageable pageable) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Lancamento> c = b.createQuery(Lancamento.class);		
		Root<Lancamento> r = c.from(Lancamento.class);
		
		// Implementação dos filtros
		Predicate[] p = criarRestricoes(f, b, r);
		c.where(p);	
		
		TypedQuery<Lancamento> q = em.createQuery(c);
				
		adicionarRestricoesDePaginacao(q, pageable);
		
		return new PageImpl<>(q.getResultList(), pageable, total(f));
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
				, root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
				, root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
				, root.get(Lancamento_.categoria).get(Categoria_.nome)
				, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = em.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter f, CriteriaBuilder b, Root<Lancamento> r) {
		List<Predicate> p = new ArrayList<>();
		
		if (!f.isEmptyDataVencimentoDe()) {
			p.add(b.greaterThanOrEqualTo(r.get(Lancamento_.DATA_VENCIMENTO), f.getDataVencimentoDe()));
		}
		
		if (!f.isEmptyDataVencimentoAte()) {
			p.add(b.lessThanOrEqualTo(r.get(Lancamento_.DATA_VENCIMENTO), f.getDataVencimentoAte()));			
		}
		
		if (!f.isEmptyDescricao()) {
			p.add(b.like(b.lower(r.get(Lancamento_.DESCRICAO)), "%" + f.getDescricao().toLowerCase() + "%"));
		}

		return p.toArray(new Predicate[p.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> q, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		q.setFirstResult(primeiroRegistroDaPagina);
		q.setMaxResults(totalRegistrosPorPagina);		
	}
	
	private Long total(LancamentoFilter f) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(f, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return em.createQuery(criteria).getSingleResult();
	}
}

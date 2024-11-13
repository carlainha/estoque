package br.com.caroline.estoque.repositories.produto;

import br.com.caroline.estoque.dto.ProdutoDto;
import br.com.caroline.estoque.model.Produto;
import br.com.caroline.estoque.repositories.filter.ProdutoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;
    @Override
    public Page<ProdutoDto> filtrar(ProdutoFilter produtoFilter, Pageable pegeable){
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDto> criteria = builder.createQuery(ProdutoDto.class);
        Root<Produto> root = criteria.from(Produto.class);

        criteria.select(builder.construct(ProdutoDto.class
                , root.get("id")
                , root.get("nomeproduto")
                , root.get("preco")
                , root.get("categoria"). get("nome")));

        Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("nomeproduto")));

        TypedQuery<ProdutoDto> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pegeable);

        return new PageImpl<>(query.getResultList(), pegeable, total(produtoFilter));
    }

    private Long total(ProdutoFilter produtoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Produto> root = criteria.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter,builder,root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(produtoFilter.getNomeproduto())){
            predicates.add(builder.like(
                    builder.lower(root.get("nomeproduto")),
                    "%" + produtoFilter.getNomeproduto().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(produtoFilter.getCategoria())){
            predicates.add(builder.like(
                    builder.lower(root.get("categoria")),
                    "%" + produtoFilter.getCategoria().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<ProdutoDto> query, Pageable pageable){
        int paginaAtual = pageable.getPageNumber();;
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual*totalRegistroPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }
}

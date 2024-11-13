package br.com.caroline.estoque.repositories.produto;

import br.com.caroline.estoque.dto.ProdutoDto;
import br.com.caroline.estoque.repositories.filter.ProdutoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoRepositoryQuery {

    public Page<ProdutoDto> filtrar(ProdutoFilter produtoFilter, Pageable pageable);
}

package br.com.caroline.estoque.repositories.categoria;

import br.com.caroline.estoque.model.Categoria;
import br.com.caroline.estoque.repositories.filter.CategoriaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepositoryQuery {
    public Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);
}

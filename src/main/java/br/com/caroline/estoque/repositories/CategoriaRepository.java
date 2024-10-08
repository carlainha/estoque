package br.com.caroline.estoque.repositories;

import br.com.caroline.estoque.model.Categoria;
import br.com.caroline.estoque.repositories.categoria.CategoriaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>, CategoriaRepositoryQuery {

}

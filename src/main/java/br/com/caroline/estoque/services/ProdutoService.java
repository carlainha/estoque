package br.com.caroline.estoque.services;

import br.com.caroline.estoque.model.Produto;
import br.com.caroline.estoque.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }
}

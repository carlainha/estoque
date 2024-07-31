package br.com.caroline.estoque.controllers;

import br.com.caroline.estoque.model.Produto;
import br.com.caroline.estoque.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @PostMapping()
    public ResponseEntity<Produto> inserir (@RequestBody Produto produto){
        Produto produtoSalva = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalva);
    }
}

package com.example.produtoapi.service;

import com.example.produtoapi.dto.ProdutoDTO;
import com.example.produtoapi.exception.ProdutoNaoEncontradoException;
import com.example.produtoapi.model.Produto;
import com.example.produtoapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
            produtoDTO.getNome(),
            produtoDTO.getDescricao(),
            produtoDTO.getPreco(),
            produtoDTO.getQuantidadeEstoque()
        );
        Produto produtoSalvo = produtoRepository.save(produto);
        return toDTO(produtoSalvo);
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        return toDTO(produto);
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produtoExistente.setNome(produtoDTO.getNome());
        produtoExistente.setDescricao(produtoDTO.getDescricao());
        produtoExistente.setPreco(produtoDTO.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return toDTO(produtoAtualizado);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        produtoRepository.delete(produto);
    }

    public Long contarRegistros() {
        return produtoRepository.count();
    }

    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getQuantidadeEstoque()
        );
    }
}

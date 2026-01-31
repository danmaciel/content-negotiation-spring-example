package com.example.produtoapi.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado com id: " + id);
    }
}

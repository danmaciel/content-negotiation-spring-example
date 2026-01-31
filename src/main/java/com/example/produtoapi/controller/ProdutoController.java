package com.example.produtoapi.controller;

import com.example.produtoapi.dto.ProdutoDTO;
import com.example.produtoapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ProdutoDTO.class))
            }),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos campos")
    })
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoCriado = produtoService.criar(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Listar todos os produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
        content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
            @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
            @Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ProdutoDTO.class))
        })
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado",
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ProdutoDTO.class))
            }),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> buscarPorId(@Parameter(description = "ID do produto") @PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ProdutoDTO.class)),
                @Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ProdutoDTO.class))
            }),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos campos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> atualizar(
        @Parameter(description = "ID do produto") @PathVariable Long id,
        @Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> deletar(@Parameter(description = "ID do produto") @PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/count", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Contar produtos", description = "Retorna a quantidade total de produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Quantidade de produtos retornada com sucesso",
        content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "integer")),
            @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(type = "integer")),
            @Content(mediaType = "application/x-yaml", schema = @Schema(type = "integer"))
        })
    public ResponseEntity<Long> contarRegistros() {
        Long quantidade = produtoService.contarRegistros();
        return ResponseEntity.ok(quantidade);
    }
}

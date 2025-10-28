package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.dto.ProdutoRequestDTO;
import org.serratec.ecommerce.dto.ProdutoResponseDTO;
import org.serratec.ecommerce.entity.Categoria;
import org.serratec.ecommerce.entity.Produto;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.CategoriaRepository;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional 
    public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + dto.getCategoriaId()));
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional 
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id)); 
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + dto.getCategoriaId())); 
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional(readOnly = true) 
    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponseDTO> listaDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            listaDTO.add(new ProdutoResponseDTO(produto));
        }
        return listaDTO;
    }

    @Transactional(readOnly = true) 
    public Produto buscarEntidadePorId(Long id) { 
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id)); 
    }

     @Transactional(readOnly = true) 
    public ProdutoResponseDTO buscarResponsePorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return new ProdutoResponseDTO(produto);
    }


    @Transactional 
    public void deletar(Long id) {
        Produto produto = buscarEntidadePorId(id); 
        produtoRepository.delete(produto);
    }
}
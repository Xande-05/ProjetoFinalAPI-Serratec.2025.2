package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.dto.ItemPedidoRequestDTO;
import org.serratec.ecommerce.dto.ProdutoRequestDTO;
import org.serratec.ecommerce.dto.ProdutoResponseDTO;
import org.serratec.ecommerce.entity.Categoria;
import org.serratec.ecommerce.entity.Produto;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.CategoriaRepository;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }
    
    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponseDTO> listaDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            listaDTO.add(new ProdutoResponseDTO(produto));
        }
        return listaDTO;
    }


    public ItemPedidoRequestDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return new ItemPedidoRequestDTO(produto);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }
}

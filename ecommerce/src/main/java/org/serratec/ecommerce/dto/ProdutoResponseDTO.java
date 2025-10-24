package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

import org.serratec.ecommerce.entity.Produto;

public class ProdutoResponseDTO {
	
	private Long id;
	private String nome;
	private BigDecimal preco;
	private CategoriaDTO categoria;
	
	//constructors
	public ProdutoResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ProdutoResponseDTO(Produto produto) {
		super();
		this.id = produto.getId(); // call the getId() of Produto entity
		this.nome = produto.getNome(); 
		this.preco = produto.getPreco();
		this.categoria = new CategoriaDTO(produto.getCategoria());
	}

//getters and setters
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public BigDecimal getPreco() {
		return preco;
	}


	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}


	public CategoriaDTO getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

}

package org.serratec.ecommerce.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
	
	private String nome;
	private BigDecimal preco;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoria_id",nullable = false)
	private Categoria categoria;
	
	// Constructors
	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produto(String nome, BigDecimal preco, Categoria categoria) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.categoria = categoria;
	}
	
// Getters and Setters
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	

}

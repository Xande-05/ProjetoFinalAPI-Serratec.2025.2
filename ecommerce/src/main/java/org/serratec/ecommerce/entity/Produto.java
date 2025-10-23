package org.serratec.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto  extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Double preço;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id",nullable = false)
	private Categoria categoria;
	
	// Constructors
	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produto(String nome, Double preço, Categoria categoria) {
		super();
		this.nome = nome;
		this.preço = preço;
		this.categoria = categoria;
	}
	
// Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreço() {
		return preço;
	}

	public void setPreço(Double preço) {
		this.preço = preço;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

}

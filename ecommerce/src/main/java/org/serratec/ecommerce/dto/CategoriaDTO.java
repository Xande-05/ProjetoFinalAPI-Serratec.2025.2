package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.entity.Categoria;

public class CategoriaDTO {

	private Long id;
	private String nome;
	private Categoria categoria;
	
	public CategoriaDTO(Categoria categoria) {
		super();
		this.id = categoria.getId(); // call the getId() of  Categoria entity
		this.nome = categoria.getNome();
		this.setCategoria(categoria);
	}



	// Getters and Setters
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


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}

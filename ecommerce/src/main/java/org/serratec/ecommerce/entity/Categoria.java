package org.serratec.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String nome;

	// Constructors
	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Categoria(String nome) {
		super();
		this.nome = nome;
	}

	// Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
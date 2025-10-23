package org.serratec.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	private String nome;
	
	// Constructors
	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categoria(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	
	
	
}
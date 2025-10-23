package org.serratec.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String CEP;
	
	@Column
	private String logradouro;
	
	@Column
	private String bairro;
	
	@Column
	private String localidade;
	
	@Column
	private String uf;
}

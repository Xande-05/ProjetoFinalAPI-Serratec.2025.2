package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.Endereco;

import jakarta.persistence.Id;

public class ClienteResponseDTO {
	
	@Id
	private long id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
	private Endereco endereco;
	
	public ClienteResponseDTO(Cliente cliente) {
	    this.id = cliente.getId();
	    this.nome = cliente.getNome();
	    this.email = cliente.getEmail();
	    this.telefone = cliente.getTelefone();
	    this.cpf = cliente.getCpf();
	    this.endereco = cliente.getEndereco();
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
}

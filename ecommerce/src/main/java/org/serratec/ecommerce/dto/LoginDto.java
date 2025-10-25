package org.serratec.ecommerce.dto;

import java.io.Serializable;

public class LoginDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

	public LoginDto() {
	}

	public LoginDto(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsername() {
		return email;
	}

	public String getPassword() {
		return senha;
	}
}
package org.serratec.ecommerce.entity;

import java.time.LocalDate;
import java.util.Objects; 

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil {

	@EmbeddedId
	private UsuarioPerfilPK id = new UsuarioPerfilPK();

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;


	public UsuarioPerfil() {
	}

	
	public UsuarioPerfil(Usuario usuario, Perfil perfil, LocalDate dataCriacao) {
		super();
	
		this.id = new UsuarioPerfilPK(usuario, perfil);
		this.dataCriacao = dataCriacao;
	}


	public UsuarioPerfilPK getId() {
		return id;
	}

	
	public void setId(UsuarioPerfilPK id) {
		this.id = id;
	}

	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPerfil other = (UsuarioPerfil) obj;
		return Objects.equals(id, other.id);
	}

	
	@Override
	public String toString() {
		return "UsuarioPerfil [id=" + id + ", dataCriacao=" + dataCriacao + "]";
	}
}
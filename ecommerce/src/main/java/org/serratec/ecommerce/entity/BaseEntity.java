package org.serratec.ecommerce.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// Classe abstrata base para todas as entidades com ID.

@MappedSuperclass // informa ao jpa que esta classe é uma superclasse mapeada
public abstract class BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}

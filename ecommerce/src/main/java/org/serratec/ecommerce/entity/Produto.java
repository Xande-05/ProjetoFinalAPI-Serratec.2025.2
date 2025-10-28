package org.serratec.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Objects; 

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull; 

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto não pode ser vazio") 
    @Column(nullable = false) 
    private String nome;

    @NotNull(message = "Preço não pode ser nulo") 
    @DecimalMin(value = "0.01", message = "Preço deve ser positivo") 
    @Column(nullable = false) 
    private BigDecimal preco;

    @NotNull(message = "Produto deve pertencer a uma categoria") 
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "categoria_id", nullable = false) 
    private Categoria categoria;

   
    public Produto() {
        super();
    }

    
    public Produto(Long id, String nome, BigDecimal preco, Categoria categoria) {
        super();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

  
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

   
	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + "]";
	}
}
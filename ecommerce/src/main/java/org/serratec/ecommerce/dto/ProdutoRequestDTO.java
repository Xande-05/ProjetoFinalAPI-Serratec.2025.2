package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

public class ProdutoRequestDTO {
	
	private String nome;
    private BigDecimal preco;
    private Long categoriaId;
    
    
	
   
	public BigDecimal getPreco() {
		return preco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public Long getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	  
}

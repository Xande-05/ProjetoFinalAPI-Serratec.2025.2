package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

public class ProdutoRequestDTO {
	
	private Long nome;
    private BigDecimal preco;
    private Long categoriaId;
    
    //getters and setters
	
      public Long getNome() {
		return nome;
	}
	public void setNome(Long nome) {
		this.nome = nome;
	}
	public BigDecimal getPreco() {
		return preco;
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

package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProdutoRequestDTO {

	@NotBlank(message = "O nome do produto não pode estar em branco")
    @Size(max = 100, message = "O nome do produto não pode exceder 100 caracteres") // Ajuste o tamanho
	private String nome;

    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O ID da categoria é obrigatório")
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
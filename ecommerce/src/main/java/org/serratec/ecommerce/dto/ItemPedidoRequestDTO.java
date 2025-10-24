package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.entity.Produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório para o item do pedido.")
    private Long produtoId;

    @NotNull(message = "A quantidade do produto é obrigatória.")
    @Min(value = 1, message = "A quantidade mínima para um item é 1.")
    private Integer quantidade;

    public ItemPedidoRequestDTO(Produto produto) {
		// TODO Auto-generated constructor stub
	}

	public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
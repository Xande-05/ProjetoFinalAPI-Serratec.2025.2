package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.entity.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "O nome não pode exceder 50 caracteres") // Ajuste o tamanho se necessário
    private String nome;

    public CategoriaDTO(Categoria categoria) {
        super();
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public CategoriaDTO() {
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
}
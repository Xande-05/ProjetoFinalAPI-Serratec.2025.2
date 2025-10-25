package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.entity.Categoria;

public class CategoriaDTO {

    private Long id;
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
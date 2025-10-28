package org.serratec.ecommerce.dto;

import java.util.Set;
import java.util.stream.Collectors;
import org.serratec.ecommerce.entity.Perfil;
import org.serratec.ecommerce.entity.Usuario;


public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String urlFotoPerfil;
    private Set<String> perfis; 

    
    public UsuarioResponseDTO() {
    }

    
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.urlFotoPerfil = usuario.getUrlFotoPerfil(); 
       
        if (usuario.getUsuarioPerfis() != null) { 
            this.perfis = usuario.getUsuarioPerfis().stream()
                .map(up -> up.getId().getPerfil().getNome()) 
                .collect(Collectors.toSet());
        }
    }

 
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

     public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public Set<String> getPerfis() {
        return perfis;
    }
}
package org.serratec.ecommerce.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors; 

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails, Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "senha")
    private String senha;

    @OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) // Adicionado orphanRemoval
    private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();

    public Usuario() {
        super();
    }

    public Usuario(Long id, String nome, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Set<UsuarioPerfil> getUsuarioPerfis() { return usuarioPerfis; }
    public void setUsuarioPerfis(Set<UsuarioPerfil> usuarioPerfis) { this.usuarioPerfis = usuarioPerfis; }
    public static long getSerialversionuid() { return serialVersionUID; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(id, other.id); 
    }

     @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.usuarioPerfis.stream()
                .map(up -> new SimpleGrantedAuthority(up.getId().getPerfil().getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email; 
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

	public static void setFotoPerfilPath(String novoCaminhoFoto) {
		// TODO Auto-generated method stub
		
	}

	public void setUrlFotoPerfil(String urlDaFoto) {
		// TODO Auto-generated method stub
		
	}

    
}
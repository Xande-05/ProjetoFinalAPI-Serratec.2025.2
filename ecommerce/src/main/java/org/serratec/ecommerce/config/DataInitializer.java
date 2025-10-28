package org.serratec.ecommerce.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.serratec.ecommerce.entity.Perfil;
import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.entity.UsuarioPerfil;
import org.serratec.ecommerce.repository.PerfilRepository;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        

        Perfil perfilAdmin = perfilRepository.findByNome("ADMIN");
        if (perfilAdmin == null) {
            perfilAdmin = new Perfil();
            perfilAdmin.setNome("ADMIN");
            perfilRepository.save(perfilAdmin);
        }

        if (usuarioRepository.findByEmail("grupo2@gmail.com").isEmpty()) {
            Usuario adminUsuario = new Usuario();
            adminUsuario.setNome("Administrador Padrão");
            adminUsuario.setEmail("grupo2@gmail.com");
            adminUsuario.setSenha(passwordEncoder.encode("123456")); 

          
            Set<UsuarioPerfil> perfis = new HashSet<>();
            UsuarioPerfil adminPerfilAssoc = new UsuarioPerfil(adminUsuario, perfilAdmin, LocalDate.now());
            perfis.add(adminPerfilAssoc);
            
            adminUsuario.setUsuarioPerfis(perfis);
            
            usuarioRepository.save(adminUsuario);
        }
    }
}
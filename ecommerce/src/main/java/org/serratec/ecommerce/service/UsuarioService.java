package org.serratec.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException; 
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.FotoPerfilResponse;
import org.serratec.ecommerce.dto.UsuarioResponseDTO;
import org.serratec.ecommerce.entity.Perfil;
import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.entity.UsuarioPerfil;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.PerfilRepository;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsuarioService {

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${app.upload.dir:${user.home}/ecommerce/uploads}")
	private String uploadDir;

	//Procura se o usuario tem foto
	@Transactional(readOnly = true)
    public FotoPerfilResponse buscarFotoResponse(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        String nomeArquivo = usuario.getUrlFotoPerfil();
        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            throw new ResourceNotFoundException("Usuário não possui foto de perfil.");
        }

        try {
            Path arquivoPath = Paths.get(uploadDir).resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(arquivoPath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(arquivoPath);
                MediaType mediaType = MediaType.parseMediaType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);

                return new FotoPerfilResponse(resource, mediaType);
            } else {
                throw new ResourceNotFoundException("Arquivo da foto de perfil não encontrado ou ilegível: " + nomeArquivo);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao formar URL para o arquivo: " + nomeArquivo, e);
        } catch (NoSuchFileException e) {
             throw new ResourceNotFoundException("Arquivo da foto de perfil não encontrado no disco: " + nomeArquivo);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o tipo de conteúdo do arquivo: " + nomeArquivo, e);
        }
    }
	
	@Transactional(readOnly = true)
	public List<UsuarioResponseDTO> listarTodos() {
	    List<Usuario> usuarios = usuarioRepository.findAll();
	  
	    return usuarios.stream()
	                   .map(UsuarioResponseDTO::new) 
	                   .collect(Collectors.toList());
	}

	// Salva a foto 
	@Transactional
	public void salvarFotoPerfil(Long usuarioId, MultipartFile file) throws IOException { // Adicionado throws IOException
	    Usuario usuario = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

	    
	    String nomeArquivo = salvarArquivoFisico(file, usuarioId);

	    usuario.setUrlFotoPerfil(nomeArquivo);
	    usuarioRepository.save(usuario);
	}

    
	private String salvarArquivoFisico(MultipartFile file, Long usuarioId) throws IOException {
	    String nomeOriginal = StringUtils.cleanPath(file.getOriginalFilename());
        String extensao = "";
        int i = nomeOriginal.lastIndexOf('.');
        if (i > 0) {
            extensao = nomeOriginal.substring(i);
        }
	    String nomeArquivo = "usuario_" + usuarioId + "_" + System.currentTimeMillis() + extensao;

	    Path diretorioPath = Paths.get(uploadDir);
	    Files.createDirectories(diretorioPath); 
	    Path arquivoPath = diretorioPath.resolve(nomeArquivo);

	    Files.copy(file.getInputStream(), arquivoPath, StandardCopyOption.REPLACE_EXISTING); 

	    return nomeArquivo;
	}

	
	@Transactional
	public Usuario criarUsuarioParaCliente(String nome, String email) {
		if (usuarioRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email '" + email + "' já cadastrado para outro usuário.");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(passwordEncoder.encode("defaultPassword"));

		Perfil perfilCliente = perfilRepository.findByNome("CLIENTE");
		if (perfilCliente == null) {
			perfilCliente = new Perfil();
			perfilCliente.setNome("CLIENTE");
			perfilCliente = perfilRepository.save(perfilCliente);
		}

		Set<UsuarioPerfil> perfis = new HashSet<>();
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil(usuario, perfilCliente, LocalDate.now());
		perfis.add(usuarioPerfil);
		usuario.setUsuarioPerfis(perfis);

		return usuarioRepository.save(usuario);
		
	}
	
	@Transactional
    public Usuario promoverUsuarioParaAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        Perfil perfilAdmin = perfilRepository.findByNome("ADMIN");
        if (perfilAdmin == null) {
            throw new ResourceNotFoundException("Perfil 'ADMIN' não encontrado.");
        }

        UsuarioPerfil associacaoAdmin = new UsuarioPerfil(usuario, perfilAdmin, LocalDate.now());
        
        
        usuario.getUsuarioPerfis().add(associacaoAdmin);

     
        return usuarioRepository.save(usuario);
    }
}
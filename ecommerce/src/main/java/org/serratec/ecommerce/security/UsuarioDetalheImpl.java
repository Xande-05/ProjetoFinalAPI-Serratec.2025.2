package org.serratec.ecommerce.security;

import java.util.Optional;

import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioDetalheImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UserDetails LoadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> user = usuarioRepository.findByEmail(email);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Usuario não encontrado :" + email);
		}

		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getSenha(),
				user.get().getPerfis());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}

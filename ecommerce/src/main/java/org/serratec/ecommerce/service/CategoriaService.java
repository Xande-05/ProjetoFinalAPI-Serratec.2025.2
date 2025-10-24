package org.serratec.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.entity.Categoria;
import org.serratec.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service

public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Transactional(readOnly = true)
	public List<CategoriaDTO> listarTodos() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Optional<CategoriaDTO> buscarPorId(Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

		if (categoriaOptional.isPresent()) {
			return Optional.of(new CategoriaDTO(categoriaOptional.get()));
		}
		return Optional.empty();
	}

	@Transactional
	public CategoriaDTO inserir(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria.setNome(categoriaDTO.getNome());

		Categoria categoriaSalva = categoriaRepository.save(categoria);

		return new CategoriaDTO(categoriaSalva);
	}

	@Transactional
	public Optional<CategoriaDTO> editar(Long id, CategoriaDTO categoriaDTO) {

		if (categoriaRepository.existsById(id)) {

			Categoria categoria = new Categoria();
			categoria.setNome(categoriaDTO.getNome());
			categoria.setId(id);

			Categoria categoriaAtualizada = categoriaRepository.save(categoria);

			return Optional.of(new CategoriaDTO(categoriaAtualizada));
		} else {

			return Optional.empty();
		}
	}

	@Transactional
	public boolean deletar(Long id) {

		if (categoriaRepository.existsById(id)) {
			categoriaRepository.deleteById(id);

			return true;
		} else {
			return false;
		}
	}
}

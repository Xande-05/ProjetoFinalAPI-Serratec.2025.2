package org.serratec.ecommerce.service;

import java.util.Optional;
import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.entity.Categoria;
import org.serratec.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Transactional(readOnly = true)
	public Page<CategoriaDTO> listarTodosPaginado(Pageable pageable) { // Renomeado e alterado
		Page<Categoria> categoriasPage = categoriaRepository.findAll(pageable);
		return categoriasPage.map(CategoriaDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<CategoriaDTO> buscarPorId(Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		return categoriaOptional.map(CategoriaDTO::new); // Simplificado com map
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
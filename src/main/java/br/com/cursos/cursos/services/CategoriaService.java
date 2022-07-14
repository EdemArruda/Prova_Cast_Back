package br.com.cursos.cursos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursos.cursos.entity.Categoria;
import br.com.cursos.cursos.repositories.ICategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaService {
	@Autowired
	private ICategoriaRepository categoriaRepository;

	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

}

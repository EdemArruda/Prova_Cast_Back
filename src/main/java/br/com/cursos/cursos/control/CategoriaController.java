package br.com.cursos.cursos.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursos.cursos.entity.Categoria;
import br.com.cursos.cursos.services.CategoriaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@ApiOperation("Serviço para consulta de Categorias")
	@GetMapping
	public ResponseEntity<List<Categoria>> listagem() {
		return ResponseEntity.ok(categoriaService.listar());
	}

}

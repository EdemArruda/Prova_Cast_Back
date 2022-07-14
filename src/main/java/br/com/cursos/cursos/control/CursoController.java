package br.com.cursos.cursos.control;



import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursos.cursos.entity.Curso;
import br.com.cursos.cursos.services.CursoService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/cursos")
public class CursoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	private CursoService service;
	@ApiOperation("Serviço para cadastrar novo curso")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrar(@RequestBody Curso curso) {
		try {
			service.cadastrar(curso);
			LOGGER.info("Mensagem de log: sucesso ao cadastrar");
			return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso");
		} catch (Exception e) {
			LOGGER.info("Mensagem de log: erro ao cadastrar");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	@ApiOperation("Serviço para deletar curso por ID")
	@DeleteMapping(value = "/{IdCurso}")
	public ResponseEntity<String> deletar(@PathVariable("IdCurso") Integer IdCurso) {
		try {
			service.validaDelete(IdCurso);
			LOGGER.info("Mensagem de log: sucesso ao deletar");
			return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso");

		} catch (Exception e) {
			LOGGER.info("Mensagem de log: erro ao deletar");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
		
	}
	@ApiOperation("Serviço de atualização dos dados dos Cursos")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Curso curso) {
		try {
			service.atualizar(curso);
			LOGGER.info("Mensagem de log: curso editado");
			return ResponseEntity.status(HttpStatus.OK).body("Curso editado");
		} catch (Exception e) {
			LOGGER.error("Mensagem de log: erro ao editar curso");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro:" + e.getMessage());
		}
	}
	@ApiOperation("Busca por Id")
	@GetMapping(value = "/{IdCurso}")
    public ResponseEntity<Curso> getCurso(@PathVariable("IdCurso") Integer IdCurso) {
        try {

            LOGGER.info("Mensagem de log: busca realizada com sucesso");
            return ResponseEntity.ok(service.buscarId(IdCurso));

        } catch (Exception e) {
            LOGGER.error("Mensagem de log: erro ao buscar");
            return null;
        }
    }
    
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Curso>> listarTudo(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAbertura,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFechamento) {
		List<Curso> curso = service.consultar(descricao, dataAbertura, dataFechamento);

		return ResponseEntity.ok().body(curso);
	}
	
}
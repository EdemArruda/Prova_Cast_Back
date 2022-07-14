package br.com.cursos.cursos.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cursos.cursos.control.CursoController;
import br.com.cursos.cursos.entity.Curso;
import br.com.cursos.cursos.repositories.ICursoRepository;

@Service
public class CursoService {
	@Autowired
	ICursoRepository cursoRepository;
	@PersistenceContext
	EntityManager em;
	private static final Logger LOGGER = LoggerFactory.getLogger(CursoController.class);

	@Transactional
	public void cadastrar(Curso curso) {
		LOGGER.info("Mensagem de log: curso cadastrado com sucesso");
		validaData(curso);
		validacao(curso);
		cursoRepository.save(curso);
	}

	@Transactional
	public void atualizar(Curso IdCurso) {
		validacaoEdita(IdCurso);
		validaData(IdCurso);
		LOGGER.info("Mensagem de log: curso atualizado com sucesso");
		cursoRepository.save(IdCurso);
	}
	
	private ResponseEntity<String> validacao (Curso curso){
		Optional<Curso> cursos = cursoRepository.cadastra(curso.getDataAbertura(), curso.getDataFechamento());
		if (curso != null && !cursos.isEmpty()) {
			LOGGER.info("Mensagem de log: data não validada");
			throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");	
		}
		
		return null;
	}
	
	private ResponseEntity<String> validacaoEdita (Curso curso){
		Optional<Curso> cursos = cursoRepository.edita(curso.getDataAbertura(), curso.getDataFechamento(),curso.getIdCurso());
		if (curso != null && !cursos.isEmpty()) {
			LOGGER.info("Mensagem de log: data não validada");
			throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");	
		}
		
		return null;
	}
	
	private void validaData(Curso curso) {
		if (curso.getDataAbertura().isAfter(curso.getDataFechamento())) {
			LOGGER.info("Mensagem de log: data validada com sucesso");
			throw new RuntimeException("Data de abertura é maior que a data de fechamento");
		}
		if (curso.getDataAbertura().isBefore(LocalDate.now())) {
			LOGGER.info("Mensagem de log: data não validada");
			throw new RuntimeException("Data de abertura é menor que a data atual");
		}
	}

	public Curso buscarId(Integer IdCurso) {
		return cursoRepository.findById(IdCurso).get();
	}

	public void validaDelete(Integer IdCurso) {
		Optional<Curso> curso = cursoRepository.findById(IdCurso);
		Curso c = curso.get();
		if (c.getDataAbertura().isBefore(LocalDate.now())) {
			LOGGER.info("Mensagem de log: data não validada");
			throw new RuntimeException("Não é possível excluir este curso, já realizado.");
		}
		cursoRepository.deleteById(IdCurso);
	}

	public List<Curso> consultar(String descricao, LocalDate dataAbertura, LocalDate dataFechamento) {
		System.out.println(dataAbertura);
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<Curso> criteriaQuery = criteria.createQuery(Curso.class);

        Root<Curso> curso = criteriaQuery.from(Curso.class);
        List<Predicate> predList = new ArrayList<>();
        
        //Lógicas de Filtros
        if (descricao != "") {
            Predicate descricaoPredicate = criteria.equal(curso.get("descricao"), descricao);
            predList.add(descricaoPredicate);
        }

        if (dataAbertura != null) {
            Predicate dataAberturaPredicate = criteria.greaterThanOrEqualTo(curso.get("dataAbertura"), dataAbertura);
            predList.add(dataAberturaPredicate);
        }

        if (dataFechamento != null) {
            Predicate dataFechamentoPredicate = criteria.lessThanOrEqualTo(curso.get("dataFechamento"), dataFechamento);
            predList.add(dataFechamentoPredicate);
        }

        Predicate[] predicateArray = new Predicate[predList.size()];

        predList.toArray(predicateArray);

        criteriaQuery.where(predicateArray);

        TypedQuery<Curso> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    
    }
}

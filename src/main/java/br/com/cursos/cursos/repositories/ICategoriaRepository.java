package br.com.cursos.cursos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursos.cursos.entity.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

}

package com.pablo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso);

}

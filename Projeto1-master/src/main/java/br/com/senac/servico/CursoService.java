package br.com.senac.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Curso;
import br.com.senac.exception.ObjectNotFoundException;
import br.com.senac.repositorio.CursoRepositorio;

@Service
public class CursoService {
	
	@Autowired
	CursoRepositorio repoCurso;
	
	public List<Curso> listaCursos() {
		return repoCurso.findAll();
	}
	
	public Curso inserir(Curso objCurso) {
		objCurso.setId(null);
		return repoCurso.save(objCurso);
	}
	
	public Curso buscar(Integer id) {
		Optional<Curso> objCurso = repoCurso.findById(id);
		return objCurso.orElseThrow(() -> new ObjectNotFoundException(
				"Curso não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName()));
	}
	
	public Curso alterar(Curso objCurso) {
		Curso objCursoEncontrado = buscar(objCurso.getId());
		objCursoEncontrado.setNome(objCurso.getNome());
		objCursoEncontrado.setDescricao(objCurso.getDescricao());
		objCursoEncontrado.setPreco(objCurso.getPreco());
		objCursoEncontrado.setCategorias(objCurso.getCategorias());
		return repoCurso.save(objCursoEncontrado);
	}

	public void excluir(Integer id) {
		repoCurso.deleteById(id);
	}

}

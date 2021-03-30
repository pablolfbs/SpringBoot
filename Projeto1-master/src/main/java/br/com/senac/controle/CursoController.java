package br.com.senac.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Curso;
import br.com.senac.servico.CategoriaService;
import br.com.senac.servico.CursoService;

@Controller
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CategoriaService catService;
	
	@GetMapping("/listarCursos")
	public ModelAndView listaCursos() {
		ModelAndView mv = new ModelAndView("curso/paginaCursos");
		mv.addObject("cursos", cursoService.listaCursos());
		return mv;
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("curso/index");
		mv.addObject("cursos", cursoService.listaCursos());
		return mv;
	}
	
	@GetMapping("/adicionarCurso")
	public ModelAndView add(Curso curso) {
		ModelAndView mv = new ModelAndView("curso/paginaAdicionarCurso");
		mv.addObject("curso", curso);
		mv.addObject("categorias", catService.listaCategorias());
		return mv;
	}
	
	@GetMapping("/alterarCurso/{id}")
	public ModelAndView alterar(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("curso/paginaAlterarCurso");
		mv.addObject("curso", cursoService.buscar(id));
		mv.addObject("categorias", catService.listaCategorias());
		return mv;
	}
	
	@GetMapping("/alterarCurso")
	public ModelAndView alterar(Curso curso) {
		cursoService.alterar(curso);
		return listaCursos();
	}
	
	@PostMapping("/salvarCurso")
	public ModelAndView inserir(Curso curso) {
		cursoService.inserir(curso);
		return listaCursos();
	}
	
	@GetMapping("/excluirCurso/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id) {
		cursoService.excluir(id);
		return listaCursos();
	}

}

package br.com.senac.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;
import br.com.senac.servico.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/validar")
	public String login(Aluno aluno) {
		boolean decision = loginService.login(aluno);
		if (decision) {
			return "redirect:/menu";
		}
		return "erro/paginaErro.html";
	}
	
	@GetMapping("/login")
	public ModelAndView validar() {
		ModelAndView mv = new ModelAndView("menu/paginaLogin");
		mv.addObject("aluno", new Aluno());
		return mv;
	}
	
}

package br.com.senac.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
	
	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("menu/paginaMenu");
		return mv;
	}
	
	@GetMapping("/erro")
	public String erro() {
		return "erro/paginaErro.html";
	}
	
}

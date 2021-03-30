package br.com.senac.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.servico.PedidoService;

@Controller
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/listarPedido")
	public ModelAndView listaPedidos() {
		ModelAndView mv = new ModelAndView("carrinho/finalizarCompra");
		mv.addObject("pedidos", pedidoService.listaPedidos());
		return mv;
	}
	
}

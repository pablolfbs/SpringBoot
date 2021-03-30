package br.com.senac.controle;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Curso;
import br.com.senac.dominio.Item;
import br.com.senac.dominio.ItemPedido;
import br.com.senac.dominio.Pagamento;
import br.com.senac.dominio.PagamentoComBoleto;
import br.com.senac.dominio.Pedido;
import br.com.senac.dominio.enums.StatusPagamento;
import br.com.senac.exception.ObjectNotFoundException;
import br.com.senac.servico.AlunoService;
import br.com.senac.servico.CursoService;
import br.com.senac.servico.ItemPedidoService;
import br.com.senac.servico.PagamentoService;
import br.com.senac.servico.PedidoService;

@Controller
public class CarrinhoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	CursoService cursoService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PagamentoService pagamentoService;

	@GetMapping("/carrinho/comprar/{id}")
	public String comprar(@PathVariable("id") Integer id, HttpSession session) {
		if (session.getAttribute("cart") == null) {
			List<Item> listaCarrinho = new ArrayList<Item>();
			listaCarrinho.add(new Item(cursoService.buscar(id), 1));
			session.setAttribute("cart", listaCarrinho);
		} else {
			@SuppressWarnings("unchecked")
			List<Item> listaCarrinho = (List<Item>) session.getAttribute("cart");
			int index = isExists(id, listaCarrinho);
			// se for -1 o item é novo, caso contrário item já existe no carrinho
			if (index == -1) {
				listaCarrinho.add(new Item(cursoService.buscar(id), 1));
			} else {
				int quantidade = listaCarrinho.get(index).getQuantidade() + 1;
				listaCarrinho.get(index).setQuantidade(quantidade);
			}
			session.setAttribute("cart", listaCarrinho);
		}
		return "redirect:/indexCarrinho";
	}

	private int isExists(int id, List<Item> listaCarrinho) {
		for (int i = 0; i < listaCarrinho.size(); i++) {
			if (listaCarrinho.get(i).getCurso().getId() == id) {
				return i;
			}
		}
		return -1;
	}

	@GetMapping("/indexCarrinho")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("carrinho/index");
		return mv;
	}

	@GetMapping("finalizar")
	public String finalizar(HttpSession session) throws ParseException, ObjectNotFoundException {

		try {
			Pedido pedido = new Pedido();

//			Aluno cliente = (Aluno) session.getAttribute("user");
//			cliente = alunoService.buscar(cliente.getId());
//			pedido.setAluno(cliente);
//			pedido.setEnderecoDeEntrega(cliente.getEnderecos().get(0));

			@SuppressWarnings("unchecked")
			List<Item> cart = (List<Item>) session.getAttribute("cart");

			Set<ItemPedido> itensPedido = new HashSet<ItemPedido>();

			Set<Curso> cursos = new HashSet<Curso>();

			for (Item item : cart) {
				item.setCurso(cursoService.buscar(item.getCurso().getId()));
				cursos.add(item.getCurso());
				itensPedido.add(new ItemPedido(pedido, item.getCurso(), 0.0D, item.getQuantidade(),
						item.getCurso().getPreco()));
			}

			for (Curso c : cursos) {
				if (c.getItens().isEmpty()) {
					c.setItens(itensPedido);
				} else {
					for (ItemPedido ip : itensPedido) {
						c.getItens().add(ip);
					}
				}
			}
			pedido.setItens(itensPedido);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			pedido.setDataPedido(new Date());

			Pagamento pag = new PagamentoComBoleto(null, StatusPagamento.PENDENTE, pedido,
					sdf.parse("30/06/2018 00:00"), sdf.parse("29/06/2018 00:00"));
			pedido.setPagamento(pag);

			pedidoService.inserir(pedido);
			pagamentoService.inserir(pag);
			itemPedidoService.inserirVarios(itensPedido);
			limparCarrinho(session);

			return "carrinho/compraRealizada.html";

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "erro/paginaErro.html";
		}
	}

	private void limparCarrinho(HttpSession session) {
		session.setAttribute("cart", null);
	}

	@GetMapping("/carrinho/remover/{id}")
	public String remover(@PathVariable("id") Integer id, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Item> listaCarrinho = (List<Item>) session.getAttribute("cart");
		int index = isExists(id, listaCarrinho);
		listaCarrinho.remove(index);
		session.setAttribute("cart", listaCarrinho);
		return "redirect:/indexCarrinho";
	}

}

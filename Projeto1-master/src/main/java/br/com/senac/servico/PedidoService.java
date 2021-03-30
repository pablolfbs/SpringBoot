package br.com.senac.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Curso;
import br.com.senac.dominio.Pedido;
import br.com.senac.exception.ObjectNotFoundException;
import br.com.senac.repositorio.CursoRepositorio;
import br.com.senac.repositorio.PedidoRepositorio;

@Service
public class PedidoService {
	
	@Autowired
	CursoRepositorio repoCurso;
	
	@Autowired
	private PedidoRepositorio repoPed;
	
	public List<Pedido> listaPedidos() {
		return repoPed.findAll();
	}

	public Pedido inserir(Pedido objPedido) {
		objPedido.setId(null);
		return repoPed.save(objPedido);
	}
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> objPedido = repoPed.findById(id);
		return objPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido alterar(Pedido objPedido) {
		Pedido objPedidoEncontrado = buscar(objPedido.getId());
		objPedidoEncontrado.setDataPedido(objPedido.getDataPedido());
		return repoPed.save(objPedidoEncontrado);
	}

	public void excluir(Integer id) {
		repoPed.deleteById(id);
	}
	
}

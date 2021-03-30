package br.com.senac.inicializacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Cidade;
import br.com.senac.dominio.Curso;
import br.com.senac.dominio.Endereco;
import br.com.senac.dominio.Estado;
import br.com.senac.dominio.ItemPedido;
import br.com.senac.dominio.Pagamento;
import br.com.senac.dominio.PagamentoComBoleto;
import br.com.senac.dominio.Pedido;
import br.com.senac.dominio.enums.StatusPagamento;
import br.com.senac.repositorio.AlunoRepositorio;
import br.com.senac.repositorio.CategoriaRepositorio;
import br.com.senac.repositorio.CidadeRepositorio;
import br.com.senac.repositorio.CursoRepositorio;
import br.com.senac.repositorio.EnderecoRepositorio;
import br.com.senac.repositorio.EstadoRepositorio;
import br.com.senac.repositorio.ItemPedidoRepositorio;
import br.com.senac.repositorio.PagamentoRepositorio;
import br.com.senac.repositorio.PedidoRepositorio;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	AlunoRepositorio alunoRepositorio;

	@Autowired
	CidadeRepositorio cidadeRepositorio;

	@Autowired
	EstadoRepositorio estadoRepositorio;

	@Autowired
	EnderecoRepositorio enderecoRepositorio;

	@Autowired
	PedidoRepositorio pedidoRepositorio;

	@Autowired
	PagamentoRepositorio pagamentoRepositorio;

	@Autowired
	CursoRepositorio cursoRepositorio;

	@Autowired
	CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	ItemPedidoRepositorio itemPedidoRepositorio;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Aluno aluno1 = new Aluno();
		aluno1.setNome("Lucas");
		aluno1.setEmail("lucas@gmail.com");

		Aluno aluno2 = new Aluno();
		aluno2.setNome("Pablo");
		aluno2.setEmail("pablo@gmail.com");

		alunoRepositorio.save(aluno1);
		alunoRepositorio.save(aluno2);

		// Aluno alunoGravado = alunoRepositorio.findByEmail("lucas@gmail.com");

		Estado estado1 = new Estado();
		estado1.setNome("Rio de Janeiro");
		Estado estado2 = new Estado();
		estado2.setNome("São Paulo");

		Cidade cidade1 = new Cidade();
		cidade1.setNome("Angra dos Reis");
		cidade1.setEstado(estado1);
		Cidade cidade2 = new Cidade();
		cidade2.setNome("Cabo Frio");
		cidade2.setEstado(estado1);
		Cidade cidade3 = new Cidade();
		cidade3.setNome("Mogi das Cruzes");
		cidade3.setEstado(estado2);

		estadoRepositorio.saveAll(Arrays.asList(estado1, estado2));

		cidadeRepositorio.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		Endereco end1 = new Endereco();
		end1.setRua("Rua dos Andradas");
		end1.setNumero("20");
		end1.setBairro("Centro");
		end1.setComplemento("Bloco B");
		end1.setCep("22341-175");
		end1.setCidade(cidade1);
		end1.setAluno(aluno1);

		Endereco end2 = new Endereco();
		end2.setRua("Rua dos Marrecos");
		end2.setNumero("68");
		end2.setBairro("Laje");
		end2.setComplemento("Fundos");
		end2.setCep("21572-201");
		end2.setCidade(cidade2);
		end2.setAluno(aluno2);

		aluno1.getTelefones().addAll(Arrays.asList("2323232323", "2323232324"));

		alunoRepositorio.save(aluno1);
		alunoRepositorio.save(aluno2);

		enderecoRepositorio.saveAll(Arrays.asList(end1, end2));

		// Criando o pedido
		Pedido ped1 = new Pedido();
		ped1.setAluno(aluno1);
		ped1.setEnderecoDeEntrega(end1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
			// fez o pedido nesta data
			ped1.setDataPedido(sdf.parse("27/06/2018 09:08"));

			Pagamento pag1 = new PagamentoComBoleto(null, StatusPagamento.QUITADO, ped1, sdf.parse("30/06/2018 00:00"),
					sdf.parse("29/06/2018 00:00"));

			ped1.setPagamento(pag1);

			// salvando o pedido
			pedidoRepositorio.save(ped1);

			// salvando o pagamento
			pagamentoRepositorio.save(pag1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Curso c1 = new Curso();
		Curso c2 = new Curso();
		Curso c3 = new Curso();
		Curso c4 = new Curso();

		Categoria cat1 = new Categoria();
		Categoria cat2 = new Categoria();
		Categoria cat3 = new Categoria();
		Categoria cat4 = new Categoria();

		c1.setNome("Java");
		c2.setNome("HTML");
		c3.setNome("Python");
		c4.setNome("C#");

		cat1.setNome("BackEnd");
		cat2.setNome("FrontEnd");
		cat3.setNome("Servidor");
		cat4.setNome("Serviço");
		
		c1.setPreco(1000);
		c2.setPreco(200);
		c3.setPreco(800);
		c4.setPreco(600);

		List<Categoria> categorias = new ArrayList<>();
		categorias.add(cat1);
		categorias.add(cat2);
		categorias.add(cat3);
		categorias.add(cat4);

		c1.setCategorias(categorias);
		c2.setCategorias(categorias);
		c3.setCategorias(categorias);
		c4.setCategorias(categorias);

		cursoRepositorio.saveAll(Arrays.asList(c1, c2, c3, c4));

		categoriaRepositorio.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		
		// criando os itens
		ItemPedido item1 = new ItemPedido(ped1, c1, 0.00, 1, 200.00);
		ItemPedido item2 = new ItemPedido(ped1, c2, 10.00, 1, 390.00);
		
		// criando a lista de itens para um pedido
		Set<ItemPedido> listaItens1 = new HashSet<>();
		listaItens1.add(item1);
		listaItens1.add(item2);
		ped1.setItens(listaItens1);
		
		// indicando qual lista de itens o curso está
		c1.setItens(listaItens1);
		c2.setItens(listaItens1);
		
		itemPedidoRepositorio.saveAll(Arrays.asList(item1, item2));

	}
	
}

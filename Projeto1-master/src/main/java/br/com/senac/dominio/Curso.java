package br.com.senac.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Curso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;
	private String descricao;
	private BigDecimal preco;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "curso_categoria", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias = new ArrayList<>();

	public Curso(Integer id, String nome, String descricao, BigDecimal preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}
	
	public Curso() {
		super();
	}

	@JsonIgnore
	@OneToMany(mappedBy="id.curso")
	private Set<ItemPedido> itens = new HashSet<>();
	
	// para saber os pedidos do produto
	@JsonIgnore
	private List<Pedido> getPedidos() {
		List<Pedido> lista = new ArrayList<>();
		for (ItemPedido i : itens) {
			lista.add(i.getPedido());
		}
		return lista;
	}
	
	@JsonIgnore
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		if (this.preco == null) {
			return new BigDecimal(0).doubleValue();
		}
		return preco.doubleValue();
	}

	public void setPreco(double preco) {
		this.preco = new BigDecimal(preco);
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}

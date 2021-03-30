package br.com.senac.dominio;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double desconto;
	private Integer quantidade;
	private Double valor;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	public ItemPedido(Pedido pedido, Curso curso, Double desconto, Integer quantidade, Double valor) {
		super();
		id.setPedido(pedido);
		id.setCurso(curso);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	public ItemPedido() {
		super();
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	
	// isso aqui ajuda a ter acesso ao pedido e ao produto sem ter que acessar a classe ItemPedidoPK
	@JsonIgnore // não precisa saber em itemPedido, mas precisa saber em Pedido.
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Curso getCurso() {
		 return id.getCurso();
	}

}

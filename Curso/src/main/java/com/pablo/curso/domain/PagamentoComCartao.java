package com.pablo.curso.domain;

import javax.persistence.Entity;

import com.pablo.curso.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = -7258897329074958416L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
		super();
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

}

package com.pablo.curso.dto;

import java.io.Serializable;

import com.pablo.curso.domain.Categoria;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 6517708170945219179L;

	private Integer id;
	private String nome;

	public CategoriaDTO() {
		super();
	}

	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
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

}

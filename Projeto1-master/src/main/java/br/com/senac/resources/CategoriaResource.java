package br.com.senac.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senac.dominio.Categoria;
import br.com.senac.dto.CategoriaDTO;
import br.com.senac.servico.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
//	@RequestMapping(method=RequestMethod.GET)
//	public String testar() {
//		return "ESTÁ FUNCIONANDO!";
//	}
	
	// indica que agora a chamada da url vai incluir um id ex: categorias/1 ou categorias/2
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria objCategoria = service.buscar(id);
		return ResponseEntity.ok().body(objCategoria);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody Categoria objCategoria) { // RequestBody faz objetos json ser convertidos em objetos em Java
		objCategoria = service.inserir(objCategoria);
		
		// vamos montar a url resposta da categoria inserida
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objCategoria.getId()).toUri();
		
		// código http de criação de objeto
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Categoria objCategoria, @PathVariable Integer id) {
		objCategoria.setId(id);
		objCategoria = service.alterar(objCategoria);
		// não queremos que retorne nada
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id) { 
		service.excluir(id);
		// não queremos que retorne nada
		return ResponseEntity.noContent().build();
	}
	
//	@RequestMapping(method=RequestMethod.GET)
//	public ResponseEntity<List<Categoria>> listarCategorias() {
//		List<Categoria> listaCategorias = service.listaCategorias();
//		return ResponseEntity.ok().body(listaCategorias);
//	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
		List<Categoria> listaCategorias = service.listaCategorias();
		List<CategoriaDTO> listDTO = listaCategorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

}

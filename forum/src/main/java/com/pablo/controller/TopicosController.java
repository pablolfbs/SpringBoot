package com.pablo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pablo.controller.dto.DetalhesDoTopicoDTO;
import com.pablo.controller.dto.TopicoDTO;
import com.pablo.controller.form.TopicoForm;
import com.pablo.model.Topico;
import com.pablo.repository.CursoRepository;
import com.pablo.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		List<Topico> topicos = null;
		if (nomeCurso == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		}
		return TopicoDTO.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
	public DetalhesDoTopicoDTO detalhar(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id);
		return new DetalhesDoTopicoDTO(topico);
	}

}

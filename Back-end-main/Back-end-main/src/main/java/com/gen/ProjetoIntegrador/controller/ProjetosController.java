package com.gen.ProjetoIntegrador.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.gen.ProjetoIntegrador.model.Projetos;
import com.gen.ProjetoIntegrador.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gen.ProjetoIntegrador.repository.ProjetosRepository;

@RestController
@RequestMapping("/projetos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjetosController {
	
	@Autowired
	private ProjetosRepository projetosRepository;
	
	@GetMapping
	public ResponseEntity<List<Projetos>>getAll(){
		return ResponseEntity.ok(projetosRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Projetos>getById(@PathVariable Long id){
		return projetosRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
	}
	
	@GetMapping("/projeto/{nome_projeto}")
	public ResponseEntity<List<Projetos>>getByNome(@PathVariable String nome_projeto){

		return ResponseEntity.ok(projetosRepository.findAllByNomeContainingIgnoreCase(nome_projeto));
	}
	
	@PostMapping
	public ResponseEntity<Projetos>post(@Valid @RequestBody Projetos projetos){
		return ResponseEntity.status(HttpStatus.CREATED).body(projetosRepository.save(projetos));
	}
	
	@PutMapping
	public ResponseEntity<Projetos>put(@Valid @RequestBody Projetos projetos){
        return projetosRepository.findById(projetos.getId())
        		.map(resposta -> ResponseEntity.status(HttpStatus.OK)
        		.body(projetosRepository.save(projetos)))
        		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Projetos> projetos = projetosRepository.findById(id);
		
		if(projetos.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		projetosRepository.deleteById(id);
	}
}

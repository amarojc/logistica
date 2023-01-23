package br.com.amaro.logistica.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.service.ConsultaEntregaService;
import br.com.amaro.logistica.domain.service.SolicitacaoEntregaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	private ConsultaEntregaService consultaEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Entrega solicitarEntregar(@RequestBody @Valid Entrega entrega) {
		return solicitacaoEntregaService.solicitarEntrega(entrega);
	}
	
	@GetMapping
	public ResponseEntity<Page<Entrega>> listarEntregas(Pageable pageable){
		Page<Entrega> entregas = consultaEntregaService.listarEntregas(pageable);
		return ResponseEntity.ok().body(entregas);
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<Entrega> buscarEntrega(@PathVariable Long entregaId){
		return consultaEntregaService.buscar(entregaId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}	
}

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

import br.com.amaro.logistica.api.assembler.EntregaAssembler;
import br.com.amaro.logistica.api.model.EntregaModel;
import br.com.amaro.logistica.api.model.input.EntregaInput;
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
	private EntregaAssembler entregaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitarEntregar(@RequestBody @Valid EntregaInput  entregaInput) {
		Entrega novaEntrega =  entregaAssembler.toEntity(entregaInput);
		Entrega entregaCriada =	solicitacaoEntregaService.solicitarEntrega(novaEntrega); 
		return entregaAssembler.toModel(entregaCriada); 
	}
	
	@GetMapping
	public ResponseEntity<Page<EntregaModel>> listarEntregas(Pageable pageable){
		Page<Entrega> entregas = consultaEntregaService.listarEntregas(pageable);
		Page<EntregaModel> entregasModel = entregaAssembler.toCollectionModel(entregas);
		return ResponseEntity.ok().body(entregasModel);
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscarEntrega(@PathVariable Long entregaId){
		   return consultaEntregaService.buscar(entregaId)
					.map(entrega -> ResponseEntity.ok().body(entregaAssembler.toModel(entrega)))
					.orElse(ResponseEntity.notFound().build());
		}
		
}
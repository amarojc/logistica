package br.com.amaro.logistica.api.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import br.com.amaro.logistica.api.model.EntregaModel;
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
	
	/* 
	 Biblioteca java independende, não pertence ao eco-sistema do Spring.
	 Necessário configurar o tipo ModelMapper para que possa se tornar um bean
	 gerenciado pelo Spring.
	*/
	private ModelMapper modelMapper;
	
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
	public ResponseEntity<EntregaModel> buscarEntrega(@PathVariable Long entregaId){
		return consultaEntregaService.buscar(entregaId)
				.map(entrega -> {
					//modelMapper, passando o objeto (entrega) e o tipo de destino que será convertido (EntregaDTO).
					EntregaModel entregaModel = modelMapper.map(entrega, EntregaModel.class);
					return ResponseEntity.ok().body(entregaModel);
				})	.orElse(ResponseEntity.notFound().build());
	}	
}
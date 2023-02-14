package br.com.amaro.logistica.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaro.logistica.api.assembler.OcorrenciaAssembler;
import br.com.amaro.logistica.api.model.OcorrenciaModel;
import br.com.amaro.logistica.api.model.input.OcorrenciaInput;
import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.model.Ocorrencia;
import br.com.amaro.logistica.domain.service.ConsultaEntregaService;
import br.com.amaro.logistica.domain.service.RegistroOcorrenciaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entrega_id}/ocorrencias")
public class OcorrenciaController {
	
	private ConsultaEntregaService consultaEntregaService;
	private RegistroOcorrenciaService registroOcorrenciaService;
	private OcorrenciaAssembler ocorrenciaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaModel registrarOcorrencia(@PathVariable Long entrega_id,
				@Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
		Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
						.registrarOcorrencia(entrega_id, ocorrenciaInput.getDescricao());
		
		return ocorrenciaAssembler.toModel(ocorrenciaRegistrada); 
	}

	@GetMapping
	public ResponseEntity<List<OcorrenciaModel>>listarOcorrenciasPorEntrega(@PathVariable Long entrega_id){
		Entrega entrega = consultaEntregaService.verificarEntrega(entrega_id);
	    List<OcorrenciaModel> ocorrencias = ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
		return  ResponseEntity.ok().body(ocorrencias);
	}
}

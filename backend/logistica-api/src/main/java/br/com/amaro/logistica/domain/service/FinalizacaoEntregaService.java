package br.com.amaro.logistica.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

	private EntregaRepository entregaRepository;
	private ConsultaEntregaService consultaEntregaService;
	
	@Transactional
	public void finalizarEntrega(Long idEntrega) {
		Entrega entrega = consultaEntregaService.verificarEntrega(idEntrega);
		
		entrega.finalizar();
		entrega.adicionarOcorrencia("Entrega realizada com sucesso!");
		 
		entregaRepository.save(entrega);
	}
}

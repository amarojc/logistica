package br.com.amaro.logistica.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.model.StatusEntrega;
import br.com.amaro.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CancelarEntregaService {

	private EntregaRepository entregaRepository;
	private ConsultaEntregaService consultaEntregaService;
	
	@Transactional
	public Entrega cancelarEntrega(Long idEntrega) {
		Entrega entrega  = consultaEntregaService.verificarEntrega(idEntrega);
		
		entrega.cancelar();
		entrega.setStatus(StatusEntrega.CANCELADA);
		entrega.adicionarOcorrencia("Entrega cancelada.");
		
		return entregaRepository.save(entrega);
	}
	
	
}

package br.com.amaro.logistica.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.model.Cliente;
import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.model.StatusEntrega;
import br.com.amaro.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

	private EntregaRepository entregaRepository;
	private CatalogoClienteService catalogoClienteService;
	
	@Transactional
	public Entrega solicitarEntrega(Entrega entrega) {
		Cliente cliente = catalogoClienteService.buscar(entrega.getCliente().getId());
		
		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		
		return entregaRepository.save(entrega);
	}
}

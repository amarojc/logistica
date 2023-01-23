package br.com.amaro.logistica.domain.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConsultaEntregaService {

	private EntregaRepository entregaRepository;
	
	@Transactional
	public Page<Entrega> listarEntregas(Pageable pageable){
		Page<Entrega> entregas = entregaRepository.findAll(pageable);
		return entregas;
	}
	
	@Transactional
	public Optional<Entrega> buscar(Long entregaId){
		return entregaRepository.findById(entregaId);
	}
	
	
}

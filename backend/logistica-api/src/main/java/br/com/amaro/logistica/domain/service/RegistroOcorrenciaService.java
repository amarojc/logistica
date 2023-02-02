package br.com.amaro.logistica.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.model.Entrega;
import br.com.amaro.logistica.domain.model.Ocorrencia;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

	private ConsultaEntregaService consultaEntregaService;
	
	@Transactional
	public Ocorrencia registrarOcorrencia(Long entregaId, String descricaoOcorrencia) {
		Entrega entrega = consultaEntregaService.buscar(entregaId).get();
		return entrega.adicionarOcorrencia(descricaoOcorrencia);
	}
}

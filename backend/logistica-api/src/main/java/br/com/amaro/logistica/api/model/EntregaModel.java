package br.com.amaro.logistica.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.amaro.logistica.domain.model.StatusEntrega;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaModel {
	
	private Long id;
	private String  nomeCliente;
	private BigDecimal taxa;
	private DestinatarioModel destinatario;
	private StatusEntrega status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
	
}

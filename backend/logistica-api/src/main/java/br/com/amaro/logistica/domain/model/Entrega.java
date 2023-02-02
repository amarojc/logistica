package br.com.amaro.logistica.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.amaro.logistica.domain.exception.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Entrega {

	    @EqualsAndHashCode.Include
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
	    @ManyToOne
	    @JoinColumn(name="cliente_id")
	    private Cliente cliente;
	    
	    @Embedded
	    private Destinatario destinatario;
		
	   private BigDecimal taxa;
		
		@Enumerated(EnumType.STRING)
		private StatusEntrega status;
		
		private OffsetDateTime dataPedido;
		
		private OffsetDateTime dataFinalizacao;
		
		public Ocorrencia adicionarOcorrencia(String descricao) {
			Ocorrencia ocorrencia = new  Ocorrencia();
			ocorrencia.setDescricao(descricao);
			ocorrencia.setDataRegistro(OffsetDateTime.now());
			ocorrencia.setEntrega(this);
			
			return ocorrencia;
			
		}
		
		public void finalizar() {
			if(naoPodeSerFinalizada()) {
				  throw new NegocioException("Entrega não pode ser finalizada");
			}
			setStatus(StatusEntrega.FINALIZADA);
			setDataFinalizacao(OffsetDateTime.now());
			
		}
		
		public boolean podeSerFinalizada() {
			return StatusEntrega.PENDENTE.equals(getStatus());
		}
		
		public boolean naoPodeSerFinalizada() {
			return !podeSerFinalizada();
		}
}

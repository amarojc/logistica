package br.com.amaro.logistica.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {
	
		private Long id;
		private String nome;
		private String email;
		private String telefone;

}

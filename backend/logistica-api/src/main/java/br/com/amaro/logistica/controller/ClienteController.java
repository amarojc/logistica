package br.com.amaro.logistica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaro.logistica.domain.model.Cliente;
import br.com.amaro.logistica.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;

//Gerando um construtor, via lombok, com todas as propriedades de ClienteRepository
@AllArgsConstructor
@RestController
public class ClienteController {

	private ClienteRepository clienteRepository;

	@GetMapping("/clientes")
	public List<Cliente> listarClientes(){
			return clienteRepository.findAll();
	}
	
	@GetMapping("/nomecliente")
	public List<Cliente> buscarClientePorNome(){
		return clienteRepository.findByNomeContaining("ki");
	}
	
}

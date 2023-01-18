package br.com.amaro.logistica.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaro.logistica.domain.model.Cliente;

@RestController
public class ClienteController {

	@GetMapping("/clientes")
	public List<Cliente> clientes(){
		var cliente = new Cliente();
		cliente.setId(new Long(1));
		cliente.setNome("Pedro de Lara");
		cliente.setEmail("pl@mail.com");		
		cliente.setTelefone("21 2222-3333");
		
		var cliente2 = new Cliente(new Long(2), "Silvio Santos", "ss@mail.com", "21 4444-5555");
		
		return Arrays.asList(cliente, cliente2); 
	}
}

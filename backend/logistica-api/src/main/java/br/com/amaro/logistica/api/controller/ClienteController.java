package br.com.amaro.logistica.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaro.logistica.domain.model.Cliente;
import br.com.amaro.logistica.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;

//Gerando um construtor, via lombok, com todas as propriedades de ClienteRepository
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente> listarClientes(){
			return catalogoClienteService.buscarTodosClientes();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId){
		return catalogoClienteService.buscarCliente(clienteId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@RequestBody @Valid Cliente cliente) {
		return catalogoClienteService.salvarCliente(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId,
					@RequestBody  @Valid Cliente cliente){

		if(!catalogoClienteService.buscarCliente(clienteId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		cliente = catalogoClienteService.salvarCliente(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!catalogoClienteService.buscarCliente(clienteId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		catalogoClienteService.excluirCliente(clienteId);
		
		return ResponseEntity.noContent().build();
	}
}

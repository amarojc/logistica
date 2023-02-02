package br.com.amaro.logistica.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.amaro.logistica.api.assembler.ClienteAssembler;
import br.com.amaro.logistica.api.model.ClienteModel;
import br.com.amaro.logistica.api.model.input.ClienteInput;
import br.com.amaro.logistica.domain.model.Cliente;
import br.com.amaro.logistica.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;

//Gerando um construtor, via lombok, com todas as propriedades de ClienteRepository
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private CatalogoClienteService catalogoClienteService;
	private ClienteAssembler clienteAssembler;
	
	@GetMapping
	public ResponseEntity<Page<ClienteModel>> listarClientes(Pageable pageable){
		Page<Cliente> clientes = catalogoClienteService.buscarTodosClientes(pageable); 
		Page<ClienteModel> clientesModel = clienteAssembler.toCollectionModel(clientes);
			return ResponseEntity.ok().body(clientesModel);
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<ClienteModel> buscar(@PathVariable Long clienteId){
		return catalogoClienteService.buscarCliente(clienteId)
				.map(cliente -> ResponseEntity.ok().body(clienteAssembler.toModel(cliente)))
				.orElse(ResponseEntity.notFound().build());
	}
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteModel adicionar(@RequestBody @Valid ClienteInput clienteInput) {
		Cliente novoCliente = clienteAssembler.toEntity(clienteInput);
		Cliente clienteCriado = catalogoClienteService.salvarCliente(novoCliente);
		return clienteAssembler.toModel(clienteCriado);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<ClienteModel> atualizar(@PathVariable Long clienteId,
					@RequestBody  @Valid ClienteModel clienteModel){

		if(!catalogoClienteService.buscarCliente(clienteId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
				
		clienteModel.setId(clienteId);
		Cliente cliente =  catalogoClienteService.salvarCliente(
				clienteAssembler.modelToEntity(clienteModel));
		
		return ResponseEntity.ok(clienteAssembler.toModel(cliente));
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

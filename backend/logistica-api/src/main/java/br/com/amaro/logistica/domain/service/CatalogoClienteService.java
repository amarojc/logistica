package br.com.amaro.logistica.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaro.logistica.domain.exception.NegocioException;
import br.com.amaro.logistica.domain.model.Cliente;
import br.com.amaro.logistica.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

	private ClienteRepository clienteRepository;
	
	/*
	 @Transactional
	 Declarando que o método deve ser executando dentro de uma transação, ou seja,
	caso algo que esteja executando dentro do método de errado, todas as operações
	que estão sendo realizadas dentro do BD serão descartadas
	 ("Ou tudo ou nada, quando for persistir no BD").
	*/
	
	@Transactional
	public Cliente salvarCliente(Cliente cliente) {
			boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
					.stream()
					.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

			if(emailEmUso) {
				throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
			}
			return clienteRepository.save(cliente);
	}
	
	@Transactional
	public List<Cliente> buscarTodosClientes(){
		return clienteRepository.findAll();
	}
	
	@Transactional
	public Optional<Cliente> buscarCliente(Long clienteId) {
		return clienteRepository.findById(clienteId);
	}
	
	public Cliente atualizarCliente(Cliente cliente, Long clienteId){
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluirCliente(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
	/*
	 Método para verificar se o cliente existe.
	 Retorno o cliente caso ele exista, e 
	Caso não exista, lanço uma exceção, passando uma lambda expression sem argumentos.
  */
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado."));
	}
}

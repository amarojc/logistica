package br.com.amaro.logistica.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.amaro.logistica.api.model.ClienteModel;
import br.com.amaro.logistica.api.model.input.ClienteInput;
import br.com.amaro.logistica.domain.model.Cliente;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteAssembler {

		private ModelMapper modelMapper;
		
		public ClienteModel toModel(Cliente cliente) {
			return modelMapper.map(cliente, ClienteModel.class);
		}

		public Page<ClienteModel> toCollectionModel(Page<Cliente> clientes){
			return clientes.map(this::toModel);
		}
		
		public Cliente toEntity(ClienteInput clienteInput) {
			return modelMapper.map(clienteInput, Cliente.class);
		}
		
		public Cliente modelToEntity(ClienteModel clienteModel) {
			return modelMapper.map(clienteModel, Cliente.class);
		}
}

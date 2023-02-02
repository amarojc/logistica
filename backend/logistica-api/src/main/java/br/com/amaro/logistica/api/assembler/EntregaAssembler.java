package br.com.amaro.logistica.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.amaro.logistica.api.model.EntregaModel;
import br.com.amaro.logistica.api.model.input.EntregaInput;
import br.com.amaro.logistica.domain.model.Entrega;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
//Classe responsavel em realizar a montagem de objetos, convertendo de um tipo para outro.
public class EntregaAssembler {

	/* 
	 Biblioteca java independende, não pertence ao eco-sistema do Spring.
	 Necessário configurar  (./common/ModelMapperConfig.java)
	  o tipo ModelMapper para que possa se tornar um bean
	 gerenciado pelo Spring.
	*/
	private ModelMapper modelMapper;
	
	public  EntregaModel toModel(Entrega entrega) {
		//modelMapper, passando o objeto (entrega) e o tipo de destino que será convertido (EntregaModel).
		return modelMapper.map(entrega, EntregaModel.class);
	}
	
	public Page<EntregaModel> toCollectionModel(Page<Entrega> entregas){
		return entregas
				.map(this::toModel);
	}
	
	//Método para converter representation model (EntregaInput) para uma entidade (Entrega)
	public Entrega toEntity(EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
	
}

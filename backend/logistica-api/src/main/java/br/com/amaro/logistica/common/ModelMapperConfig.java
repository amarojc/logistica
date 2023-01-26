package br.com.amaro.logistica.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Declarando que a classe é um componente Spring com o objetivo de configuração de beans,
adicionando métodos que definem beans Spring.
*/
@Configuration
public class ModelMapperConfig {
    /*
        Indicando que o método instância, inicializa e configura um bean que será gerenciado pelo Spring,
        ficando disponível para ser injetado em outras classes.
     */	
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

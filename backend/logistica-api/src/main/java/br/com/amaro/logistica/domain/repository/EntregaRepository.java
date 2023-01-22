package br.com.amaro.logistica.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.amaro.logistica.domain.model.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega,Long>{

}

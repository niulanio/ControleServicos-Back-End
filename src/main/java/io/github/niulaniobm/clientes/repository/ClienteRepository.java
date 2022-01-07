package io.github.niulaniobm.clientes.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import io.github.niulaniobm.clientes.model.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	
	
	
	
}

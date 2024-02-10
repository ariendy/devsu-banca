package org.example.clientemicroservicio.repository;

import org.example.clientemicroservicio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteDao extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findFirstByIdentificacion(String identificacion);
}

package org.example.cuentamicroservicio.repository;

import org.example.cuentamicroservicio.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface MovimientoDao extends JpaRepository<Movimiento, Long> {

    Optional<Movimiento> findTopByCuenta_CuentaIdOrderByFechaDesc(Long cuentaId);
    Collection<Movimiento> findAllByFechaGreaterThanEqualAndFechaLessThanEqual(LocalDateTime fechaStart, LocalDateTime fechaEnd);
}

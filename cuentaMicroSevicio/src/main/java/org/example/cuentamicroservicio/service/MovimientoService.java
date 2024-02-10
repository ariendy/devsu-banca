package org.example.cuentamicroservicio.service;

import org.example.cuentamicroservicio.dtos.MovimientoDTO;
import org.example.cuentamicroservicio.exception.NotFoundException;
import org.example.cuentamicroservicio.exception.ServiceException;
import org.example.cuentamicroservicio.model.Cuenta;
import org.example.cuentamicroservicio.model.Movimiento;
import org.example.cuentamicroservicio.repository.CuentaDao;
import org.example.cuentamicroservicio.repository.MovimientoDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    private final MovimientoDao movimientoDao;
    private final CuentaDao cuentaDao;

    public MovimientoService(MovimientoDao movimientoDao, CuentaDao cuentaDao) {
        this.movimientoDao = movimientoDao;
        this.cuentaDao = cuentaDao;
    }

    @Transactional(readOnly = true)
    public List<MovimientoDTO> getAllMovimientos() {
        return movimientoDao.findAll().stream()
                .map(MovimientoDTO::fromMovimiento)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovimientoDTO getMovimientoById(Long id) {
        return movimientoDao.findById(id)
                .map(MovimientoDTO::fromMovimiento)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<MovimientoDTO> getAllMovimientosByDateRange(LocalDate fechaStart, LocalDate fechaEnd) {
        return movimientoDao
                .findAllByFechaGreaterThanEqualAndFechaLessThanEqual(fechaStart.atStartOfDay(), fechaEnd.atTime(23, 59, 59)).stream()
                .map(MovimientoDTO::fromMovimiento)
                .collect(Collectors.toList());
    }

    public MovimientoDTO saveMovimiento(MovimientoDTO movimientoDTO) {
        validateMovimiento(movimientoDTO);
        Movimiento movimiento = movimientoDTO.toMovimiento();

        Movimiento lastMovimiento = movimientoDao.findTopByCuenta_CuentaIdOrderByFechaDesc(movimientoDTO.getCuentaId())
                .orElse(new Movimiento());

        if (lastMovimiento.getSaldo() == null) {
            Cuenta cuenta = cuentaDao.findById(movimientoDTO.getCuentaId())
                    .orElseThrow(() -> new NotFoundException("Movimiento no encontrado."));
            movimiento.setSaldo(cuenta.getSaldoInicial().add(movimiento.getValor()));
            validateSaldoDisponible(cuenta.getSaldoInicial(), movimientoDTO.getValor());
        } else {
            movimiento.setSaldo(lastMovimiento.getSaldo().add(movimiento.getValor()));
            validateSaldoDisponible(lastMovimiento.getSaldo(), movimientoDTO.getValor());
        }

        movimientoDao.save(movimiento);
        return getMovimientoById(movimiento.getMovimientoId());
    }

     public MovimientoDTO updateMovimiento(MovimientoDTO movimientoDTO) {
        validateMovimiento(movimientoDTO);
        Movimiento movimiento = movimientoDTO.toMovimiento();
        Movimiento lastMovimiento = movimientoDao.findTopByCuenta_CuentaIdOrderByFechaDesc(movimientoDTO.getCuentaId())
                .orElse(new Movimiento());

        if (lastMovimiento.getMovimientoId() != movimiento.getMovimientoId()) {
            throw new ServiceException("Solo puede actualizar el último movimiento de esta cuenta.");
        }

        movimiento.setSaldo(lastMovimiento.getSaldo()
                .add(lastMovimiento.getValor().multiply(new BigDecimal(-1)))
                .add(movimiento.getValor())
        );
        validateSaldoDisponible(lastMovimiento.getSaldo().add(lastMovimiento.getValor().multiply(new BigDecimal(-1))), movimientoDTO.getValor());

        movimiento = movimientoDao.save(movimiento);
        return MovimientoDTO.fromMovimiento(movimiento);
    }


    public void deleteMovimiento(Long id) {
        Movimiento movimientoToDelete = movimientoDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado."));
        Movimiento lastMovimiento = movimientoDao.findTopByCuenta_CuentaIdOrderByFechaDesc(movimientoToDelete.getCuenta().getCuentaId())
                .orElse(new Movimiento());

        if (lastMovimiento.getMovimientoId() != movimientoToDelete.getMovimientoId()) {
            throw new ServiceException("Solo puede eliminar el último movimiento de está cuenta.");
        }
        movimientoDao.deleteById(id);
    }


    private void validateMovimiento(MovimientoDTO movimientoDTO) {
        Objects.requireNonNull(movimientoDTO);

        if (movimientoDTO.getCuentaId() == null) {
            throw new ServiceException("Debe seleccionar la cuenta.");
        }

        if (movimientoDTO.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new ServiceException("Debe digitar monto diferente de cero.");
        }
    }

    private void validateSaldoDisponible(BigDecimal saldoDisponible, BigDecimal valor) {
        if (saldoDisponible.compareTo(valor.abs()) < 0 && valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException("Saldo no disponible.");
        }
    }
}

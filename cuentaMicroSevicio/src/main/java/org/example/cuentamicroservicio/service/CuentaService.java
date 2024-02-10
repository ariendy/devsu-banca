package org.example.cuentamicroservicio.service;

import org.example.cuentamicroservicio.dtos.CuentaDTO;
import org.example.cuentamicroservicio.exception.NotFoundException;
import org.example.cuentamicroservicio.exception.ServiceException;
import org.example.cuentamicroservicio.model.Cuenta;
import org.example.cuentamicroservicio.repository.CuentaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CuentaService {

    private final CuentaDao cuentaDao;

    public CuentaService(CuentaDao cuentaDao) {
        this.cuentaDao = cuentaDao;
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> getAllCuentas() {
        return cuentaDao.findAll().stream()
                .map(CuentaDTO::fromCuenta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaDTO getCuentaById(Long id) {
        return cuentaDao.findById(id)
                .map(CuentaDTO::fromCuenta)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada."));
    }

    public CuentaDTO saveCuenta(CuentaDTO cuentaDTO) {
        validateCuenta(cuentaDTO);
        Cuenta cuenta = cuentaDTO.toCuenta();
        cuenta =  cuentaDao.save(cuenta);
        return CuentaDTO.fromCuenta(cuenta);
    }

    public CuentaDTO updateCuenta(CuentaDTO cuentaDTO) {
        validateCuenta(cuentaDTO);
        Cuenta cuenta = cuentaDTO.toCuenta();
        cuenta =  cuentaDao.save(cuenta);
        return CuentaDTO.fromCuenta(cuenta);
    }

    public void deleteCuenta(Long id) {
        cuentaDao.deleteById(id);
    }

    private void validateCuenta(CuentaDTO cuentaDTO) {
        Objects.requireNonNull(cuentaDTO);

        if(cuentaDTO.getPersonaId() == null) {
            throw new ServiceException("Debe seleccionar cliente.");
        }

        if(cuentaDTO.getNumeroCuenta() == null || "".equals(cuentaDTO.getNumeroCuenta())) {
            throw new ServiceException("El número de cuenta no pude estar en blanco.");
        }

        cuentaDao.findFirstByNumeroCuenta(cuentaDTO.getNumeroCuenta()).ifPresent(c -> {
            if(cuentaDTO.getCuentaId() == null || !Objects.equals(c.getCuentaId(), cuentaDTO.getCuentaId())) {
                throw new ServiceException("Este número de cuenta ya esta registrado.");
            }
        });
    }
}

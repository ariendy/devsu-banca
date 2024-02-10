package org.example.cuentamicroservicio.controller;

import org.example.cuentamicroservicio.dtos.CuentaDTO;
import org.example.cuentamicroservicio.service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService CuentaService) {
        this.cuentaService = CuentaService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CuentaDTO> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO getCuentaById(@PathVariable Long id) {
        return cuentaService.getCuentaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO saveCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.saveCuenta(cuentaDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO updateCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.updateCuenta(cuentaDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
    }
}

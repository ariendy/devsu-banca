package org.example.cuentamicroservicio.controller;

import org.example.cuentamicroservicio.dtos.CuentaDTO;
import org.example.cuentamicroservicio.dtos.MovimientoDTO;
import org.example.cuentamicroservicio.service.MovimientoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovimientoDTO> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovimientoDTO getMovimientoById(@PathVariable Long id) {
        return movimientoService.getMovimientoById(id);
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public List<MovimientoDTO> getAllMovimientosByDateRange(@RequestParam(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaStart,
                                                            @RequestParam(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEnd) {
        return movimientoService.getAllMovimientosByDateRange(fechaStart, fechaEnd);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MovimientoDTO saveMovimiento(@RequestBody MovimientoDTO cuentaDTO) {
        return movimientoService.saveMovimiento(cuentaDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MovimientoDTO updateMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        return movimientoService.updateMovimiento(movimientoDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
    }
}

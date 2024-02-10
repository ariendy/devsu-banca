package org.example.cuentamicroservicio.dtos;

import org.example.cuentamicroservicio.model.Cuenta;
import org.example.cuentamicroservicio.model.Movimiento;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "movimientos")
public class MovimientoDTO {
    private Long movimientoId;
    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private Boolean estadoCuenta;
    private Long personaId;
    private String cliente;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private BigDecimal valor;
    private BigDecimal saldo;
    private BigDecimal saldoInicial;


    public static MovimientoDTO fromMovimiento(Movimiento movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setMovimientoId(movimiento.getMovimientoId());
        movimientoDTO.setCuentaId(movimiento.getCuenta().getCuentaId());
        movimientoDTO.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        movimientoDTO.setTipoCuenta(movimiento.getCuenta().getTipoCuenta());
        movimientoDTO.setEstadoCuenta(movimiento.getCuenta().getEstado());
        movimientoDTO.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
        movimientoDTO.setEstadoCuenta(movimiento.getCuenta().getEstado());
        movimientoDTO.setCliente(movimiento.getCuenta().getCliente().getNombre());
        movimientoDTO.setPersonaId(movimiento.getCuenta().getCliente().getPersonaId());
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setValor(movimiento.getValor());
        movimientoDTO.setSaldo(movimiento.getSaldo());
        return movimientoDTO;
    }

    public Movimiento toMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setMovimientoId(this.movimientoId);
        movimiento.setCuenta(new Cuenta());
        movimiento.getCuenta().setCuentaId(this.cuentaId);
        movimiento.setValor(this.valor);
        movimiento.setSaldo(this.saldo);
        return movimiento;
    }

    public Long getMovimientoId() {
        return movimientoId;
    }

    public void setMovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Boolean getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Boolean estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}

package org.example.cuentamicroservicio.dtos;

import org.example.cuentamicroservicio.model.Cliente;
import org.example.cuentamicroservicio.model.Cuenta;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long cuentaId;
    private String cliente;
    private Long personaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;

    public static CuentaDTO fromCuenta(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setCuentaId(cuenta.getCuentaId());
        cuentaDTO.setCliente(cuenta.getCliente().getNombre());
        cuentaDTO.setPersonaId(cuenta.getCliente().getPersonaId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.getEstado());
        return cuentaDTO;
    }

    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(this.cuentaId);
        cuenta.setNumeroCuenta(this.numeroCuenta);
        cuenta.setTipoCuenta(this.tipoCuenta);
        cuenta.setSaldoInicial(this.saldoInicial);
        cuenta.setCuentaId(this.cuentaId);
        cuenta.setEstado(this.estado);
        cuenta.setCliente(new Cliente());
        cuenta.getCliente().setPersonaId(this.personaId);
        return cuenta;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getCliente() {
        return cliente;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

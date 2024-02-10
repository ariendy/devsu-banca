package org.example.cuentamicroservicio.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")

public class Cliente extends Persona {

    private String contrasenia;
    private Boolean estado;

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

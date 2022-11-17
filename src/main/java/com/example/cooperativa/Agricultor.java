package com.example.cooperativa;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor

public class Agricultor {
    private int id;
    private String nombre;
    private String dni;
    private Date fechaNacimiento;
    private String direccion;

    //Creamos manualmente, sin lombok, un Constructor vacio.
    public Agricultor(){}

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}

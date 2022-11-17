package com.example.cooperativa;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Setter
@Getter
@AllArgsConstructor


public class Producto {
    private int id;
    private String nombre;
    private String variedad;
    private String calibre;

    public Producto(){}

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}

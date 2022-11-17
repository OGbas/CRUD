package com.example.cooperativa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor

public class Entradas {

    private int id;
    private Date fecha;
    private Agricultor agricultor;
    private Producto producto;
    private int kgs;
    private int idAgricultor;
    private int idProducto;

    public Entradas(){}

    public Entradas(int id, Date fecha, int  idAgricultor, int idProducto, int kgs) {
        this.id = id;
        this.fecha = fecha;
        this.agricultor = new Agricultor();
        this.agricultor.setId(idAgricultor);
        this.idAgricultor= idAgricultor;
        this.producto = new Producto();
        this.producto.setId(idProducto);
        this.idProducto = idProducto;
        this.kgs = kgs;
    }

    /*Constructor para el controller de botón de añadir entrada
      donde seteamos los Ids pasados a los objetos de ésta clase
      Agricultor y Producto. A parte le damos ese mismo valor
      a los atributos int idProducto e idAgricultor*/
    public Entradas (int producto, int agricultor){
        this.agricultor = new Agricultor();
        this.agricultor.setId(agricultor);
        this.idAgricultor = agricultor;
        this.producto = new Producto();
        this.producto.setId(producto);
        this.idProducto = producto;


    }

    @Override
    public String toString() {
        return "Entradas{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", agricultor=" + agricultor +
                ", producto=" + producto +
                ", kgs=" + kgs +
                '}';
    }
}


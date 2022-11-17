package com.example.cooperativa;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.*;


// TODO: 16/11/22 PASAR TODAS LAS FECHAS A TRAVÉS DEL PICKER DATE
// TODO: 14/11/22 COMBOBOX PARA EL TAB ENTRADAS, DONDE PODREMOS SELECCIONAR LA FECHA, EL PRODUCTO Y EL AGRICULTOR
// TODO: 14/11/22 AMBOS DEBEN YA EXISTIR. UTILIZAR TAMBIÉN UNA OBSERVABLE LIST. 
public class Repositorio {

    Connection conexion;

    /*Creación de la conexión de de las dos tablas
      necesarias, si no existen.*/
    public void setConexion(){
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cooperativa", "root", "");
            Statement st = conexion.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS agricultores(id INT NOT NULL AUTO_INCREMENT, nombre VARCHAR(255), dni VARCHAR(255), fecha_nacimiento DATE, direccion VARCHAR(255), PRIMARY KEY (id));");
            st.execute("CREATE TABLE IF NOT EXISTS productos(id INT NOT NULL AUTO_INCREMENT, nombre VARCHAR(255), variedad VARCHAR(255), calibre VARCHAR(255), PRIMARY KEY (id));");
            st.execute("CREATE TABLE IF NOT EXISTS entradas(id INT NOT NULL AUTO_INCREMENT, fecha DATE, id_agricultor INT, id_producto INT, kgs INT, PRIMARY KEY (id), FOREIGN KEY(id_agricultor) REFERENCES agricultores(id), FOREIGN KEY(id_producto) REFERENCES productos(id));");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //======================================================== AGRICULTOR ========================================================//

    /*ObsarvableList que recoge todos datos metídos
       en la tabla agricultores.*/
    public ObservableList<Agricultor>getAgricultores(){
        ObservableList<Agricultor> agricultores = FXCollections.observableArrayList();
        try{
            PreparedStatement st = conexion.prepareStatement("SELECT * FROM agricultores;");
            ResultSet rs = st.executeQuery();

            /*A cada iteración del bucle, la lista de agricultores se va
              rellenando con los datos correspondientes, hasta llegar
              al último registro*/
            while(rs.next()){
                agricultores.add(new Agricultor(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("direccion")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agricultores;
    }

    ObservableList<Agricultor> agricultores = FXCollections.observableArrayList();


    public void insertarAgricultores(Agricultor a){
        try{
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO agricultores (nombre, dni, fecha_nacimiento, direccion) VALUES (?,?,?,?)");
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDni());
            ps.setDate(3, a.getFechaNacimiento());
            ps.setString(4, a.getDireccion());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificarAgricultor(Agricultor a){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE agricultores SET nombre=?, dni=?, fecha_nacimiento= ?, direccion=?  WHERE id =?");
            ps.setString(1, a.getNombre());
            ps.setString(2,a.getDni());
            ps.setDate(3,a.getFechaNacimiento());
            ps.setString(4, a.getDireccion());
            ps.setInt(5, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buscarAgricultor(String clave){

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM agricultores WHERE  nombre LIKE ? OR dni LIKE ? OR  direccion LIKE ?;");

            ps.setString(1, "%" + clave + "%");
            ps.setString(2, "%" + clave + "%");
            ps.setString(3, "%" + clave + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                agricultores.add(new Agricultor(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("direccion")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //======================================================== PRODUCTO ========================================================//

    /*ObsarvableList que recoge todos datos metidos
       en la tabla productos.*/
    public ObservableList<Producto>getProductos(){
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        try{
            PreparedStatement st = conexion.prepareStatement("SELECT * FROM productos;");
            ResultSet rs = st.executeQuery();

            /*A cada iteración del bucle, la lista de productos se va
              rellenando con los datos correspondientes, hasta llegar
              al último registro*/
            while(rs.next()){
                productos.add(new Producto(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("variedad"),
                        rs.getString("calibre")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    ObservableList<Producto> productos = FXCollections.observableArrayList();

    public void buscarProductos(String clave){

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM productos WHERE  nombre LIKE ? OR variedad LIKE ?;");

            ps.setString(1, "%" + clave + "%");
            ps.setString(2, "%" + clave + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                productos.add(new Producto(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("variedad"),
                        rs.getString("calibre")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    /*Insertamos los datos según lo que hayamos introducido
      en los TextFields del Tab Productos*/
    public void insertarProducto(Producto p){
        try{
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO productos (id, nombre, variedad, calibre) VALUES (NULL, ?,?,?);");
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getVariedad());
            ps.setString(3, p.getCalibre());

            ps.executeUpdate();

            /*Esto siguiente se hace porque el id, cuando no lo haces en javaFX, al ser autoIncrement
              nunca se setearía en el propio objeto, en su parametro. Por eso, sin javaFx debemos hacer un SELECT
              id con su correspondiente ResultSet donde finalmente con el dato sacado se lo seteamos al id de la clase.
              javaFx lo hace automaticamente cuando trabajamos con ObservableList, por eso yo comento éste bloque de codigo

            ps = conexion.prepareStatement("SELECT id FROM productos WHERE nombre=?");
            ps.setString(1, p.getNombre());
            ResultSet rs = ps.executeQuery();
            //Aunque no sea un bucle while, siempre poner el ,next(), porque en un inicio ResulSet siempre apunta a null.
            p.setId(rs.getInt("id"));
            rs.next();*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*Insertamos los datos según lo que hayamos introducido
      en los TextFields del Tab Agricultores*/
    public void modificarProdcuto(Producto p){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE productos SET nombre=?, variedad=?, calibre= ? WHERE id =?");
            ps.setString(1, p.getNombre());
            ps.setString(2,p.getVariedad());
            ps.setString(3,p.getCalibre());
            ps.setInt(4, p.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // TODO: 14/11/22 ENTRADAS DE UN AGRICULTOR / ENTRADAS POR PRODUCTO / ENTRADA POR FECHAS.

    //======================================================== ENTRADAS ========================================================//



    /*Insertar Entradas recibe un agricultor, un producto, una fecha y unos kilos
      por lo que realizamos el insert, seteando los datos primero de agricultor, luego de
      producto, y al final los dos parametros fecha y kilos*/


    public ObservableList<Entradas>getEntradas(){
        ObservableList<Entradas> entradas = FXCollections.observableArrayList();

        try{
            PreparedStatement st = conexion.prepareStatement("SELECT * FROM entradas;");
            ResultSet rs = st.executeQuery();
            // TODO: 15/11/22 REVISAR LINEAS PRODUCTO Y AGRICULTOR 
            /*A cada iteración del bucle, la lista de productos se va
              rellenando con los datos correspondientes, hasta llegar
              al último registro*/
            while(rs.next()){
                entradas.add(new Entradas(rs.getInt("id"),
                                          rs.getDate("fecha"),
                                          rs.getInt("id_agricultor"),
                                          rs.getInt("id_producto"),
                                          rs.getInt("kgs")));

            }
        }catch (SQLException e) {
            throw new RuntimeException(e);

        } return entradas;
    }


    public void insetarEntrada(Entradas ent){
            try {
                PreparedStatement ps = conexion.prepareStatement("INSERT INTO entradas (id_agricultor, id_producto, fecha, kgs) VALUES (?,?,?,?)");
                ps.setInt(1, ent.getIdAgricultor());
                ps.setInt(2, ent.getIdProducto());
                ps.setDate(3,ent.getFecha());
                ps.setInt(4, ent.getKgs());

                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
}

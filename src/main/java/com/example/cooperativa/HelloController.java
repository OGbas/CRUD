package com.example.cooperativa;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable  {

    //======================================================== BOTONES Y TXTFIELD ========================================================//

    @FXML
    private Button btnAdd, btnModificar, btnEliminar, btnAddFr,
            btnModificarFr, btnEliminarFr, btnBuscarFr, btnBuscarAgr,
            btnAddEntrada, btnModificarEntrada, btnEliminarEntrada;
    @FXML
    private TextField txtNombreAgr, txtDniAgr, txtFechaAgr, txtDireccionAgr,
            txtNombreFr, txtVariedadFr, txtCalibreFr, txtClaveFr, txtClaveAgr,
            txtIdAgr, txtIdPr, txtFechaEntrada, txtIdAgricultorEntradas,txtIdProductoEntrada,
            txtKgEntrada, txtIdEntrada;
    ;

    //======================================================== TABLEVIEW PRODCUTOS ========================================================//
    @FXML
    private TableView<Producto> twProductos;
    @FXML
    private TableColumn<Producto, Integer> colIdPr;
    @FXML
    private TableColumn<Producto, String> colNombrePr;
    @FXML
    private TableColumn<Producto, String> colVarPr;
    @FXML
    private TableColumn<Producto, String> colCalPr;

    //======================================================== TABLEVIEW AGRICULTORES ========================================================//
    @FXML
    private TableView<Agricultor>twAgricultores;

    @FXML
    private TableColumn<Agricultor, Integer>colIdAgr;
    @FXML
    private TableColumn<Agricultor, String>colNombreAgr;
    @FXML
    private TableColumn<Agricultor, String>colDniAgr;
    @FXML
    private TableColumn<Agricultor, Date>colFechaAgr;
    @FXML
    private TableColumn<Agricultor, String>colDirAgr;

    //======================================================== TABLEVIEW CONSULTAS ========================================================//

    @FXML
    private TableView<Agricultor>twConsultaAgr;
    @FXML
    private TableColumn<Agricultor, Integer> colIdConAgr;
    @FXML
    private TableColumn<Agricultor, String>colNombreConAgr;
    @FXML
    private TableColumn<Agricultor, String>colDniConAgr;
    @FXML
    private TableColumn<Agricultor, String>colFechaConAgr;
    @FXML
    private TableColumn<Agricultor, String>colDirConAgr;

    @FXML
    private TableView<Producto>twConProducto;
    @FXML
    private TableColumn<Producto, Integer> colIdConPr;
    @FXML
    private TableColumn<Producto, String>colNombreConPr;
    @FXML
    private TableColumn<Producto, String>colVarConPr;
    @FXML
    private TableColumn<Producto, String>colCalConPr;

    //======================================================== TABLEVIEW ENTRADAS ========================================================//

    @FXML
    private TableView<Entradas>twEntradas;
    @FXML
    private TableColumn<Entradas, Integer>colIdEntrada;
    @FXML
    private TableColumn<Entradas, Integer> colNombreAgricultorEntrada;
    @FXML
    private TableColumn<Entradas, Integer> colNombreProductoEntrada;
    @FXML
    private TableColumn<Entradas, Date> colFechaEntrada;
    @FXML
    private TableColumn<Entradas, Integer>colKilosEntrada;


    //======================================================== VARIABLES Y CLASES GLOBALES ========================================================//
    Repositorio miRep;
    Agricultor a;
    Producto p;
    Entradas e;
    Popup pop;

    /*public void start(Stage stage) {
        pop = new Popup();
        Label popLabel = new Label();
        popLabel.setText("Se han añadido los datos correctamente");
        popLabel.setMaxWidth(300);
        popLabel.setMaxHeight(160);
        popLabel.setStyle("-fx-background-color:#D5D5D5;-fx-font-size:25;-fx-font-weight:bold");
        popLabel.setPadding(new Insets(20));
        pop.getContent().add(popLabel);

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!pop.isShowing()){
                    pop.show(stage);
                }else {
                    pop.hide();
                }
            }
        });

    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        miRep = new Repositorio();
        miRep.setConexion();

        gestionarEventosAgricultor();
        gestionarEventosProductos();
        gestionarEventosEntradas();

        mostrarTablaAgricultores();
        mostrarTablaProductos();
        mostrarTablaEntrada();

    }

    //======================================================== GESTIONAR EVENTOS ========================================================//

    public void gestionarEventosAgricultor() {
        twAgricultores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agricultor>() {
            @Override
            public void changed(ObservableValue<? extends Agricultor> arg0,
                                Agricultor valorAnterior, Agricultor valorSelecionado) {
                txtIdAgr.setText(String.valueOf(valorSelecionado.getId()));
                txtNombreAgr.setText(valorSelecionado.getNombre());
                txtDniAgr.setText(valorSelecionado.getDni());
                txtFechaAgr.setText(String.valueOf(valorSelecionado.getFechaNacimiento()));
                txtDireccionAgr.setText(valorSelecionado.getDireccion());

                //Deshabilitamos el boton add hasta que no se selecione un elemento de la tw.
                btnAdd.setDisable(true);
                                        /*Habilitamos los botones cuando se haga click en algún elemento.
                                          De manera predeterminada, en SB los pusimos deshabilitados, por
                                          eso aqui, si les decimos false, es que si estarán habilitados.*/
                btnEliminar.setDisable(false);
                btnModificar.setDisable(false);

            }
        });
    }

    public void gestionarEventosProductos(){
        twProductos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> arg0,
                                 Producto valorAnterior, Producto valorSelecionado) {
                txtIdPr.setText(String.valueOf(valorSelecionado.getId()));
                txtNombreFr.setText(valorSelecionado.getNombre());
                txtVariedadFr.setText(valorSelecionado.getVariedad());
                txtCalibreFr.setText(valorSelecionado.getCalibre());

                //Deshabilitamos el boton add hasta que no se selecione un elemento de la tw.
                btnAddFr.setDisable(true);
                                        /*Habilitamos los botones cuando se haga click en algún elemento.
                                          De manera predeterminada, en SB los pusimos deshabilitados, por
                                          eso aqui, si les decimos false, es que si estarán habilitados.*/
                btnEliminarFr.setDisable(false);
                btnModificarFr.setDisable(false);

            }
        });

    }

    public void gestionarEventosEntradas(){
        twEntradas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Entradas>() {
            @Override
            public void changed(ObservableValue<? extends Entradas> arg0,
                                 Entradas valorAnterior, Entradas valorSelecionado) {
                txtIdEntrada.setText(String.valueOf(valorSelecionado.getId()));
                txtIdAgricultorEntradas.setText(valorSelecionado.getAgricultor().toString());
                txtIdProductoEntrada.setText(valorSelecionado.getAgricultor().toString());
                txtFechaEntrada.setText(String.valueOf(valorSelecionado.getFecha()));
                txtKgEntrada.setText(String.valueOf(valorSelecionado.getKgs()));

                //Deshabilitamos el boton add hasta que no se selecione un elemento de la tw.
                btnAddEntrada.setDisable(true);
                                        /*Habilitamos los botones cuando se haga click en algún elemento.
                                          De manera predeterminada, en SB los pusimos deshabilitados, por
                                          eso aqui, si les decimos false, es que si estarán habilitados.*/
                btnEliminarEntrada.setDisable(false);
                btnModificarEntrada.setDisable(false);

            }
        });
    }




    public void limpiarAgr(){
        txtIdAgr.setText(null);
        txtNombreAgr.setText(null);
        txtDniAgr.setText(null);
        txtFechaAgr.setText(null);
        txtDireccionAgr.setText(null);

        //Dejamos los botones como están de manera predeterminada, solo activo el de add.
        btnAdd.setDisable(false);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
    }
    public void limpiarPr(){
        txtIdPr.setText(null);
        txtNombreFr.setText(null);
        txtVariedadFr.setText(null);
        txtCalibreFr.setText(null);


        //Dejamos los botones como están de manera predeterminada, solo activo el de add.
        btnAddFr.setDisable(false);
        btnModificarFr.setDisable(true);
        btnEliminarFr.setDisable(true);
    }



    //======================================================== OBSERVABLELISTS ========================================================//

    /*Creamos una ObservableList de la clase Agricultor que sea igual
      a la ObservableList creada en la clase Respositorio (.getAgricultores()),
      entonces decimos que cada una de las columnas de la TableView tiene un valor
      para finalmente decir que ésta lista le seteará los datos a la TableView.
      Hacemos lo mismo creando un metodo para mostrar los productos.
      */
    public void mostrarTablaAgricultores(){
        ObservableList<Agricultor> lista = miRep.getAgricultores();
        colIdAgr.setCellValueFactory(new PropertyValueFactory<Agricultor, Integer>("id"));
        colNombreAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("nombre"));
        colDniAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("dni"));
        colFechaAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,Date>("fechaNacimiento"));
        colDirAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("direccion"));

        twAgricultores.setItems(lista);
    }

    public void mostrarTablaProductos(){
        ObservableList<Producto> lista = miRep.getProductos();
        colIdPr.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("id"));
        colNombrePr.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
        colVarPr.setCellValueFactory(new PropertyValueFactory<Producto,String>("variedad"));
        colCalPr.setCellValueFactory(new PropertyValueFactory<Producto,String>("calibre"));

        twProductos.setItems(lista);
    }


    public void mostrarTablaEntrada(){
        ObservableList<Entradas> lista = miRep.getEntradas();
        colIdEntrada.setCellValueFactory(new PropertyValueFactory<Entradas, Integer>("id"));
        colFechaEntrada.setCellValueFactory(new PropertyValueFactory<Entradas,Date>("fecha"));
        colNombreProductoEntrada.setCellValueFactory(new PropertyValueFactory<Entradas,Integer>("idProducto"));
        colNombreAgricultorEntrada.setCellValueFactory(new PropertyValueFactory<Entradas,Integer>("idAgricultor"));
        colKilosEntrada.setCellValueFactory(new PropertyValueFactory<Entradas,Integer>("kgs"));

        twEntradas.setItems(lista);
    }


    //======================================================== BOTONES ========================================================//

    @FXML
    protected void onBtnAdd(ActionEvent actionEvent){
        a = new Agricultor();
        a.setNombre(txtNombreAgr.getText());
        a.setDni(txtDniAgr.getText());
        a.setFechaNacimiento(Date.valueOf(txtFechaAgr.getText()));
        a.setDireccion(txtDireccionAgr.getText());
        miRep.insertarAgricultores(a);

        // TODO: 17/11/22 HACER AQUI EN EVENTO CON LA CLASE ALERT Y CONDICIONALES
        if(txtNombreAgr == null || txtDniAgr == null
            || txtFechaAgr == null || txtDireccionAgr == null){

        }



        //Limpiamos la tabla de datos.
        limpiarAgr();
        mostrarTablaAgricultores();


    }
    @FXML
    protected void onBtnModificar(){
        Agricultor a = new Agricultor();
        a.setId(Integer.parseInt(txtIdAgr.getText()));
        a.setNombre(txtNombreAgr.getText());
        a.setDni(txtDniAgr.getText());
        a.setFechaNacimiento(Date.valueOf(txtFechaAgr.getText()));
        a.setDireccion(txtDireccionAgr.getText());

        miRep.modificarAgricultor(a);
        mostrarTablaAgricultores();
        limpiarAgr();
    }
    @FXML
    protected void onBtnEliminar(){}
    @FXML
    protected void onBtnAddFr(){
        p = new Producto();
        p.setNombre(txtNombreFr.getText());
        p.setVariedad(txtVariedadFr.getText());
        p.setCalibre(txtCalibreFr.getText());

        miRep.insertarProducto(p);
        limpiarPr();
        mostrarTablaProductos();

    }
    @FXML
    protected void OnBtnModificarFr(){
        Producto p = new Producto();
        p.setId(Integer.parseInt(txtIdPr.getText()));
        p.setNombre(txtNombreFr.getText());
        p.setVariedad(txtVariedadFr.getText());
        p.setCalibre(txtCalibreFr.getText());

        miRep.modificarProdcuto(p);
        limpiarPr();
        mostrarTablaProductos();

    }
    @FXML
    protected void onBtniEliminarFr(){}
    @FXML
    protected void onBtniBuscarFr(){
        miRep.buscarProductos(txtClaveFr.getText());
        ObservableList<Producto> lista = miRep.productos;
        colIdConPr.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("id"));
        colNombreConPr.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
        colVarConPr.setCellValueFactory(new PropertyValueFactory<Producto,String>("variedad"));
        colCalConPr.setCellValueFactory(new PropertyValueFactory<Producto,String>("calibre"));

        twConProducto.setItems(lista);

    }
    @FXML
    protected void onBtnBuscarAgr(){
        miRep.buscarAgricultor(txtClaveAgr.getText());
        ObservableList<Agricultor> lista = miRep.agricultores;
        colIdConAgr.setCellValueFactory(new PropertyValueFactory<Agricultor, Integer>("id"));
        colNombreConAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("nombre"));
        colDniConAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("dni"));
        colFechaConAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("fechaNacimiento"));
        colDirConAgr.setCellValueFactory(new PropertyValueFactory<Agricultor,String>("direccion"));

        twConsultaAgr.setItems(lista);

    }


    /*Al darle al botón Entrada, se creará un objeto Entrada donde al constructor,
      como parametros, le pasamos los Ids introducidos en los textFields, usando
      el constructor de la clase Entradas que solo recibe dichos parametros.
      Despues seteamos de manera normal la fecha y los Kgs, con eso ya tendremos el
      objeto Entrada con los datos necesarios.*/
    @FXML
    protected void onBtnAddEntrada(){
        e = new Entradas(Integer.parseInt(txtIdProductoEntrada.getText()),
                        Integer.parseInt(txtIdAgricultorEntradas.getText()));
        e.setFecha(Date.valueOf(txtFechaEntrada.getText()));
        e.setKgs(Integer.parseInt(txtKgEntrada.getText()));
        /*En el Objeto de la clase Repositorio creado al inicio del codigo
          llamamos al método .insertarEntrada, que recibirá como parametro el
          Objeto "e" creado dentro de éste mismo método*/
        miRep.insetarEntrada(e);
        //Cada vez que introduzcamos datos, llamamos a éste método para que se actualice la tabla.
        mostrarTablaEntrada();


    }

    @FXML
    protected void onBtnModificarEntrada(){}


    @FXML
    protected void onBtnEliminarEntrada(){}

}
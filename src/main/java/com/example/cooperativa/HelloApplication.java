package com.example.cooperativa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 511, 450);
        stage.setTitle("La Cooperativa");
        stage.setScene(scene);
        stage.show();
    }
    Popup pop =  new Popup();

    // TODO: 10/11/22 REVISAR EL TXTFIELD QUE RECIBE LA FECHA, INVESTIGAR PARA HACER UN PICKDATE PARA EVITAR PROBLEMAS DE FORMATO. 

    public static void main(String[] args) {
        launch();
    }
}
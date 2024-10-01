package com.curso.tareas.controlador;

import com.curso.tareas.modelo.Tarea;
import com.curso.tareas.servicio.TareaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
public class IndexControlador implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);
    @Autowired
    private TareaServicio tareaServicio;
    @FXML
    private TableView<Tarea> tareaTabla;
    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;
    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;
    @FXML
    private TableColumn<Tarea, String> responsableTareaColumna;
    @FXML
    private TableColumn<Tarea, String> estatusTareaColumna;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    @FXML
    private TextField nombreTareaTexto;
    @FXML
    private TextField responsableTexto;
    @FXML
    private TextField estatusTexto;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableTareaColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatusTareaColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    private void listarTareas(){
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareaList);
    }

    public void agregarTarea() {
        if(nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion","Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }else{
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea Agregada.");
            limpiarFormulario();
            listarTareas();
        }
    }

    private void limpiarFormulario() {
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    private void recolectarDatosFormulario(Tarea tarea) {
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

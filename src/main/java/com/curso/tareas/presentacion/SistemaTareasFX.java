package com.curso.tareas.presentacion;

import com.curso.tareas.TareasApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaTareasFX extends Application {
    private ConfigurableApplicationContext applicationContext;
    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(TareasApplication.class).run();
    }
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}

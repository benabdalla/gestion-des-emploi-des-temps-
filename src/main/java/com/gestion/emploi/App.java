package com.gestion.emploi;

import com.gestion.emploi.controllers.MainSceneController;
import com.gestion.emploi.models.Enseignants;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"));
        primaryStage.setTitle("Gestion d'emploi");
        primaryStage.setScene(new Scene(root, 1100, 570));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

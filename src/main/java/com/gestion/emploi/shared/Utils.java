package com.gestion.emploi.shared;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utils {

    public static String verfiferData(Connection conn, String nameTable, String nameCol, String data) throws SQLException {
        // Vérifier d'abord si la valeur existe
        String verifExisteSql = "SELECT * FROM `" + nameTable + "` WHERE `" + nameCol + "`= ?";
        String mat="";

        PreparedStatement verifExisteStmt = conn.prepareStatement(verifExisteSql);
        verifExisteStmt.setString(1, data);
        int nombreDeLignes = 0;

        try (java.sql.ResultSet result = verifExisteStmt.executeQuery()) {
            if (result.next()) {
              mat= result.getString("matricule");
            }
        }

        return mat;
    }

public static  void alert(String message ){
    Alert alerte = new Alert(Alert.AlertType.ERROR);

    // Définir le titre de la boîte de dialogue
    alerte.setTitle("Error");

    // Définir l'en-tête de la boîte de dialogue
    alerte.setHeaderText("Error message");

    // Définir le contenu de la boîte de dialogue
    alerte.setContentText(message);

    // Afficher la boîte de dialogue
    alerte.showAndWait();
}












}

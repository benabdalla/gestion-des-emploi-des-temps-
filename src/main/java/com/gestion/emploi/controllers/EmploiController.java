package com.gestion.emploi.controllers;

import com.gestion.emploi.dto.DtoEnseignants;
import com.gestion.emploi.dto.DtoSeance;
import com.gestion.emploi.dto.DtoSeanceEns;
import com.gestion.emploi.models.Enseignants;
import com.gestion.emploi.models.Seance;
import com.gestion.emploi.services.SqlConnectionService;
import com.gestion.emploi.shared.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmploiController {


    @FXML
    private TextField matiere;
    @FXML
    private ComboBox classe;
    @FXML
    private TextField idSeance;

    @FXML
    private TableView<DtoSeanceEns> seanceTab;

    @FXML
    private TableColumn<DtoSeanceEns, String> matriculeColumn1;
    @FXML
    private TableColumn<DtoSeanceEns, String> matriculeColumn11;
    @FXML
    private TableColumn<DtoSeanceEns, String> matriculeColumn113;
    @FXML
    private TableColumn<DtoSeanceEns, String> matriculeColumn114;
    @FXML
    private TableColumn<DtoSeanceEns, String> matriculeColumn112;

    @FXML
    private TableColumn<DtoSeanceEns, String> contactColumn1;


    public void initialize() throws IOException {


        matriculeColumn1.setCellValueFactory(cellData1 -> cellData1.getValue().classeProperty());
        matriculeColumn11.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
        matriculeColumn113.setCellValueFactory(cellData -> cellData.getValue().matiereProperty());
        matriculeColumn114.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        matriculeColumn112.setCellValueFactory(cellData -> cellData.getValue().enseignantProperty());
        contactColumn1.setCellValueFactory(cellData -> cellData.getValue().jourProperty());

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            ObservableList<DtoSeanceEns> seanceObservableList = null;
            try {
                seanceObservableList = fetchEnseignantsFromDatabaseOrWherever();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            seanceTab.setItems(seanceObservableList);
        });


        ObservableList<DtoSeanceEns> seancesList = fetchEnseignantsFromDatabaseOrWherever();

        // Set the items of the ListView
        seanceTab.setItems(seancesList);


    }


    private ObservableList<DtoSeanceEns> fetchEnseignantsFromDatabaseOrWherever() throws IOException {

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();

        PreparedStatement preparedStatement = null;
        ObservableList<DtoSeanceEns> seanceObservableList = FXCollections.observableArrayList();

        String sql1 = "SELECT `id_seance`, `classe`, `enseignant_id`, `matiere`, `jour`, `heure` ,`nom`,`contact` FROM `seance`" +
                " INNER JOIN enseignants en on en.matricule =seance.enseignant_id ";

        String sql2 = "SELECT `id_seance`, `classe`, `enseignant_id`, `matiere`, `jour`, `heure` ,`nom`,`contact` FROM `seance`" +
                " INNER JOIN enseignants en on en.matricule =seance.enseignant_id " +
                "WHERE ( `classe` = ?) AND ( `matiere` =?);";

        String sql3 = "SELECT `id_seance`, `classe`, `enseignant_id`, `matiere`, `jour`, `heure` ,`nom`,`contact` FROM `seance`" +
                " INNER JOIN enseignants en on en.matricule =seance.enseignant_id " +
                "WHERE ( `classe` = ?) AND ( `matiere` is not  null);";

        String sql4 = "SELECT `id_seance`, `classe`, `enseignant_id`, `matiere`, `jour`, `heure` ,`nom`,`contact` FROM `seance`" +
                " INNER JOIN enseignants en on en.matricule =seance.enseignant_id " +
                "WHERE ( `classe` is not  null) AND ( `matiere` = ? );";

        String classeName = "", matier = "";

        try {
            classeName = classe.getSelectionModel().getSelectedItem().toString();
        } catch (Exception e) {

        }
        try {
            matier = matiere.getText();
        } catch (Exception e) {

        }


        // Creating a PreparedStatement
        try {
            if (classeName.equals("") && matier.equals("")) {

                preparedStatement = con.prepareStatement(sql1);
            }
            if (!classeName.equals("") && !matier.equals("")) {
                preparedStatement = con.prepareStatement(sql2);
                preparedStatement.setString(1, classeName);
                preparedStatement.setString(2, matier);
            }

            if (!classeName.equals("") && matier.equals("")) {
                preparedStatement = con.prepareStatement(sql3);
                preparedStatement.setString(1, classeName);

            }
            if (classeName.equals("") && !matier.equals("")) {
                preparedStatement = con.prepareStatement(sql4);
                preparedStatement.setString(1, matier);
            }


            // Executing the query and retrieving the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processing the result set
                while (resultSet.next()) {
                    seanceObservableList.add(new DtoSeanceEns(resultSet.getString("id_seance"), resultSet.getString("classe"), resultSet.getString("matiere"), resultSet.getString("jour"),
                            resultSet.getString("heure"), resultSet.getString("nom"), resultSet.getString("contact")));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return seanceObservableList;
    }


    public void rechechechEnsign(ActionEvent event) throws Exception {
        initialize();
    }

    public void deletSeance(ActionEvent event) throws Exception {
        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        Seance seance = new Seance();
        try{
        seance.supprimerSeance(con,idSeance.getText() );
            initialize();
        } catch (Exception e){

        }

    }


}




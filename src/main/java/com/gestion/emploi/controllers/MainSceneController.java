package com.gestion.emploi.controllers;

import com.gestion.emploi.dto.DtoEnseignants;
import com.gestion.emploi.dto.DtoSeance;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainSceneController {

    @FXML
    private TextField nom;
    @FXML
    private TextField contact;
    @FXML
    private TextField matricule;

    @FXML
    private TableView<DtoEnseignants> listEnseignant;

    @FXML
    private TableColumn<DtoEnseignants, String> matriculeColumn;

    @FXML
    private TableColumn<DtoEnseignants, String> nomColumn;

    @FXML
    private TableColumn<DtoEnseignants, String> contactColumn;
    @FXML
    private TextField mat;
    @FXML
    private TextField matiere;
    @FXML
    private ComboBox classe;
    @FXML
    private ComboBox jour;
    @FXML
    private ComboBox heur;
    private String jourStr;
    private String heure;
    private String classeStr;

    @FXML
    private TableView<DtoSeance> seanceTab;

    @FXML
    private TableColumn<DtoSeance, String> matriculeColumn1;
    @FXML
    private TableColumn<DtoSeance, String> matriculeColumn11;
    @FXML
    private TableColumn<DtoSeance, String> matriculeColumn113;
    @FXML
    private TableColumn<DtoSeance, String> matriculeColumn112;

    @FXML
    private TableColumn<DtoSeance, String> nomColumn1;

    @FXML
    private TableColumn<DtoSeance, String> contactColumn1;


    public void initialize() throws IOException {
        matriculeColumn.setCellValueFactory(cellData -> cellData.getValue().matriculeProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            ObservableList<DtoEnseignants> enseignantList = null;
            try {
                enseignantList = fetchEnseignantsFromDatabaseOrWherever();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            listEnseignant.setItems(enseignantList);
        });


        ObservableList<DtoEnseignants> enseignantList = fetchEnseignantsFromDatabaseOrWherever();

        // Set the items of the ListView
        listEnseignant.setItems(enseignantList);


        matriculeColumn1.setCellValueFactory(cellData1 -> cellData1.getValue().classeProperty());
        matriculeColumn11.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
        matriculeColumn113.setCellValueFactory(cellData -> cellData.getValue().matiereProperty());
        matriculeColumn112.setCellValueFactory(cellData -> cellData.getValue().enseignantIdProperty());
        contactColumn1.setCellValueFactory(cellData -> cellData.getValue().jourProperty());

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            ObservableList<DtoSeance> seanceObservableList = null;
            try {
                seanceObservableList = fetchEnseignantsFromDatabaseOrWherever2();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            seanceTab.setItems(seanceObservableList);
        });


        ObservableList<DtoSeance> seancesList = fetchEnseignantsFromDatabaseOrWherever2();

        // Set the items of the ListView
        seanceTab.setItems(seancesList);


    }


    private ObservableList<DtoEnseignants> fetchEnseignantsFromDatabaseOrWherever() throws IOException {

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();


        ObservableList<DtoEnseignants> enseignantList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM `enseignants`";

        // Creating a PreparedStatement
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            // Executing the query and retrieving the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processing the result set
                while (resultSet.next()) {

                    enseignantList.add(new DtoEnseignants(resultSet.getString("matricule"), resultSet.getString("contact"), resultSet.getString("nom")));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return enseignantList;
    }

    private ObservableList<DtoSeance> fetchEnseignantsFromDatabaseOrWherever2() throws IOException {

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();


        ObservableList<DtoSeance> seanceObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM `seance`";

        // Creating a PreparedStatement
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            // Executing the query and retrieving the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processing the result set
                while (resultSet.next()) {
                    seanceObservableList.add(new DtoSeance(resultSet.getString("classe"),resultSet.getString("id_seance"), resultSet.getString("enseignant_id"), resultSet.getString("matiere"), resultSet.getString("jour"), resultSet.getString("heure")));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return seanceObservableList;
    }


    public void save(ActionEvent event) throws Exception {


        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        if (con == null) {
            Utils.alert("check connexion  in  data bas");

        } else {


            Task<Void> insertionTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
//                    // domaine d'affectation

                    Enseignants en = new Enseignants(matricule.getText(), nom.getText(), contact.getText());
                    en.insertEnseignants(con);
                    initialize();
                    return null;
                }
            };

            insertionTask.setOnSucceeded(e -> {
                matricule.setText("");
                nom.setText("");
                contact.setText("");

            });
            insertionTask.setOnFailed(e -> {
                Utils.alert("Echec d'insertion");

            });

            new Thread(insertionTask).start();
        }

    }


    public void save2(ActionEvent event) throws Exception {


        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        if (con == null) {
            Utils.alert("check connexion  in  data bas");

        } else {


            Task<Void> insertionTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
//                    // domaine d'affectation

                    heure = heur.getSelectionModel().getSelectedItem().toString();
                    jourStr = jour.getSelectionModel().getSelectedItem().toString();
                    classeStr = classe.getSelectionModel().getSelectedItem().toString();


                    Seance en = new Seance(classeStr, mat.getText(),
                            matiere.getText(), jourStr, heure);

                    en.insertSeance(con);
                    initialize();
                    return null;
                }
            };

            insertionTask.setOnSucceeded(e -> {
                matricule.setText("");
                nom.setText("");
                contact.setText("");

            });
            insertionTask.setOnFailed(e -> {
                Utils.alert("Echec d'insertion");

            });

            new Thread(insertionTask).start();
        }

    }

    public void rechechechEnsign(ActionEvent event) throws Exception {
        final Enseignants[][] enseignant = {{null}};

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        if (con == null) {
            Utils.alert("check connexion  in  data bas");

        } else {


            Task<Void> insertionTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
//                    // domaine d'affectation
                    Enseignants enseignants = new Enseignants();
                    enseignant[0] = new Enseignants[]{enseignants.recherecherEnseignants(con, matricule.getText())};

                    return null;
                }
            };

            insertionTask.setOnSucceeded(e -> {
                matricule.setText(enseignant[0][0].getMatricule());
                matricule.setDisable(true);
                nom.setText(enseignant[0][0].getNom());
                contact.setText(enseignant[0][0].getContact());

            });
            insertionTask.setOnFailed(e -> {
                Utils.alert("Echec d'insertion");

            });

            new Thread(insertionTask).start();
        }

    }

    public void modifierEnsign(ActionEvent event) throws Exception {

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        if (con == null) {
            Utils.alert("check connexion  in  data bas");

        } else {


            Task<Void> insertionTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Enseignants enseignants = new Enseignants();
                    enseignants.modifierEnseignants(con, matricule.getText(), nom.getText(), contact.getText());
                    initialize();
                    return null;
                }
            };

            insertionTask.setOnSucceeded(e -> {
                matricule.setText("");
                matricule.setDisable(false);
                nom.setText("");
                contact.setText("sk");


            });
            insertionTask.setOnFailed(e -> {
                try {
                    initialize();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            });

            new Thread(insertionTask).start();
        }

    }

    public void deleteEnsign(ActionEvent event) throws Exception {

        SqlConnectionService connexion = new SqlConnectionService();

        Connection con = connexion.getConnection();
        if (con == null) {
            Utils.alert("check connexion  in  data bas");

        } else {


            Task<Void> insertionTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Enseignants enseignants = new Enseignants();
                    enseignants.supprimerEnseignants(con, matricule.getText());
                    initialize();
                    return null;
                }
            };

            insertionTask.setOnSucceeded(e -> {
                matricule.setText("");
                nom.setText("");
                contact.setText("");
                Utils.alert("supprimer avec sucess ");

            });
            insertionTask.setOnFailed(e -> {
                //  Utils.alert("Echec mise à jour");

            });

            new Thread(insertionTask).start();
        }

    }
    public void openInterface(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Emploi.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion Séance");

            stage.setScene(new Scene(root1,1100, 570));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}




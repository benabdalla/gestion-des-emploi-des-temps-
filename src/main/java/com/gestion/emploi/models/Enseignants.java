package com.gestion.emploi.models;

import com.gestion.emploi.shared.Utils;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Enseignants {

    private String matricule;
    private String nom;
    private String contact;

    public Enseignants() {
    }

    public Enseignants(String matricule, String nom, String contact) {
        this.matricule = matricule;
        this.nom = nom;
        this.contact = contact;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void insertEnseignants(Connection con) throws SQLException {

        String nameTbale = "enseignants";
        String nameCol = "matricule";
        if (Utils.verfiferData(con, nameTbale, nameCol, matricule) == "" || Utils.verfiferData(con, nameTbale, nameCol, matricule) == null) {

            String req = "INSERT INTO `enseignants` (`matricule`, `nom`, `contact`) VALUES (?,?,?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
                preparedStatement.setString(1, matricule);
                preparedStatement.setString(2, nom);
                preparedStatement.setString(3, contact);


                // Exécutez la requête d'insertion
                int lignesAffectees = preparedStatement.executeUpdate();

                if (lignesAffectees > 0) {
                    System.out.println("Données insérées avec succès !" + lignesAffectees);
                } else {
                    System.out.println("Aucune donnée insérée.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("is Exsiste");
            Utils.alert("Cette  matricule  exsiste déja !");
        }

    }

    public void modifierEnseignants(Connection con, String mat, String nom, String contact) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE `enseignants` SET `matricule`=?,`nom`=?,`contact`=? WHERE `matricule`=?";

            // Préparer la requête
            preparedStatement = con.prepareStatement(updateQuery);

            // Définir les valeurs des paramètres
            preparedStatement.setString(1, mat);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, mat);

            // Exécuter la mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Afficher le nombre de lignes affectées
            System.out.println("Nombre de lignes affectées : " + rowsAffected);
            Utils.alert("mise à jour avec success");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public void supprimerEnseignants(Connection connection, String mat) {


        PreparedStatement preparedStatement = null;

        try {
            // Établir la connexion

            // Requête SQL de suppression avec un paramètre (par exemple, basé sur l'ID)
            String deleteQuery = "DELETE FROM `enseignants` WHERE `matricule`=?";

            // Préparer la requête
            preparedStatement = connection.prepareStatement(deleteQuery);

            // Définir la valeur du paramètre
            preparedStatement.setString(1, mat); // Remplacez 123 par l'ID réel

            // Exécuter la suppression
            int rowsAffected = preparedStatement.executeUpdate();

            // Afficher le nombre de lignes affectées
            System.out.println("Nombre de lignes affectées : " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public Enseignants recherecherEnseignants(Connection connection, String mat) throws SQLException {
        Enseignants en = new Enseignants();
        String sql = "SELECT * FROM `enseignants` WHERE `matricule`= ?";

        // Creating a PreparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, mat);

            // Executing the query and retrieving the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processing the result set
                while (resultSet.next()) {
                    en.setMatricule(resultSet.getString("matricule"));
                    en.setContact(resultSet.getString("contact"));
                    en.setNom(resultSet.getString("nom"));

                }
            }
        }
        return en;


    }




}

package com.gestion.emploi.models;

import com.gestion.emploi.services.SqlConnectionService;
import com.gestion.emploi.shared.Utils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seance {

    private String classe;
    private String enseignant_id;
    private String matiere;
    private String jour;
    private String heure;

    public Seance() {
    }



    public Seance(String classe, String enseignant_id, String matiere, String jour, String heure) {
        this.classe = classe;
        this.enseignant_id = enseignant_id;
        this.matiere = matiere;
        this.jour = jour;
        this.heure = heure;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEnseignant_id() {
        return enseignant_id;
    }

    public void setEnseignant_id(String enseignant_id) {
        this.enseignant_id = enseignant_id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void insertSeance(Connection con) throws SQLException {

        String nameTbale = "enseignants";
        String nameCol = "matricule";
        if (Utils.verfiferData(con, nameTbale, nameCol, enseignant_id) != "" || Utils.verfiferData(con, nameTbale, nameCol, enseignant_id) != null) {

            String req = "INSERT INTO `seance` (`classe`, `enseignant_id`, `matiere`, `jour`, `heure`) VALUES" +
                    " ( ?, ?, ?, ?, ?);";

            try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
                preparedStatement.setString(1, classe);
                preparedStatement.setString(2, enseignant_id);
                preparedStatement.setString(3, matiere);
                preparedStatement.setString(4, jour);
                preparedStatement.setString(5, heure);


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
            Utils.alert("Cette  matricule n'exsite pas !");
        }

    }


    public Seance recherecherEnseignants(Connection connection, String mat) throws SQLException {
        Seance en = new Seance();
        String sql = "SELECT * FROM `enseignants` WHERE `matricule`= ?";

        // Creating a PreparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, mat);

            // Executing the query and retrieving the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processing the result set
                while (resultSet.next()) {
//                    en.setMatricule(resultSet.getString("matricule"));
//                    en.setContact(resultSet.getString("contact"));
//                    en.setNom(resultSet.getString("nom"));

                }
            }
        }
        return en;


    }

    public void supprimerSeance(Connection connection, String idSeance) {


        PreparedStatement preparedStatement = null;

        try {
            // Établir la connexion

            // Requête SQL de suppression avec un paramètre (par exemple, basé sur l'ID)
            String deleteQuery = "DELETE FROM `seance` WHERE `id_seance`=?";

            // Préparer la requête
            preparedStatement = connection.prepareStatement(deleteQuery);

            // Définir la valeur du paramètre
            preparedStatement.setString(1, idSeance); // Remplacez 123 par l'ID réel

            // Exécuter la suppression
            int rowsAffected = preparedStatement.executeUpdate();

            // Afficher le nombre de lignes affectées
            System.out.println("Nombre de lignes affectées : " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }




}

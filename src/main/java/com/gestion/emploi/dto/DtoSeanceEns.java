package com.gestion.emploi.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DtoSeanceEns {
    private final StringProperty id_seance;
    private final StringProperty classe;
    private final StringProperty matiere;
    private final StringProperty jour;
    private final StringProperty heure;
    private final StringProperty enseignant;
    private final StringProperty contact;


    public DtoSeanceEns(String idSeance, String className, String matiere, String jour, String heure, String ensignant, String matricule) {
        id_seance = new SimpleStringProperty(idSeance);
        this.classe = new SimpleStringProperty(className);
        this.matiere = new SimpleStringProperty(matiere);
        this.jour = new SimpleStringProperty(jour);
        this.heure =new SimpleStringProperty(heure);
        this.enseignant =new SimpleStringProperty(ensignant);
        this.contact = new SimpleStringProperty(matricule);
    }




    public StringProperty classeProperty() {
        return classe;
    }




    public StringProperty matiereProperty() {
        return matiere;
    }



    public StringProperty jourProperty() {
        return jour;
    }





    public StringProperty heureProperty() {
        return heure;
    }

    public StringProperty enseignantProperty() {
        return enseignant;
    }
    public StringProperty contactProperty() {
        return contact;
    }
    public StringProperty id_seanceProperty() {
        return id_seance;
    }


}


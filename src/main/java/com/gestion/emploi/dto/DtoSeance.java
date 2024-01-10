package com.gestion.emploi.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DtoSeance {
    private final StringProperty classe;
    private final StringProperty matiere;
    private final StringProperty jour;
    private final StringProperty heure;
    private final StringProperty enseignantId;


    public DtoSeance(String className, String matiere, String jour, String heure, String ensignant) {
        this.classe = new SimpleStringProperty(className);
        this.matiere = new SimpleStringProperty(matiere);
        this.jour = new SimpleStringProperty(jour);
        this.heure =new SimpleStringProperty(heure);
        this.enseignantId =new SimpleStringProperty(ensignant);
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

    public StringProperty enseignantIdProperty() {
        return enseignantId;
    }


}


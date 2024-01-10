package com.gestion.emploi.dto;

import com.gestion.emploi.models.Enseignants;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DtoEnseignants {
    private final StringProperty matricule;
    private final StringProperty nom;
    private final StringProperty contact;

    public DtoEnseignants(String matricule, String nom, String contact) {
        this.matricule = new SimpleStringProperty(matricule);
        this.nom = new SimpleStringProperty(nom);
        this.contact = new SimpleStringProperty(contact);
    }

    public StringProperty matriculeProperty() {
        return matricule;
    }


    public StringProperty nomProperty() {
        return nom;
    }


    public StringProperty contactProperty() {
        return contact;
    }

}


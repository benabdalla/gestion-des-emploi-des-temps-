-- Table: enseignants
CREATE TABLE enseignants (
    matricule  VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    contact VARCHAR(20) NOT NULL
);

-- Table: seance
CREATE TABLE seance (
id_seance int ,
    classe VARCHAR(255) NOT NULL,
        enseignant_id VARCHAR(20),
           matiere VARCHAR(255) NOT NULL,
           jour VARCHAR(10) NOT NULL,
           heure VARCHAR(10) NOT NULL,
        PRIMARY KEY (id_seance),
        FOREIGN KEY (enseignant_id) REFERENCES enseignants(matricule),



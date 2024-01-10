package com.gestion.emploi.services;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class SqlConnectionService {

    // Accessing properties
    String dbUrl;
    String dbUser;
    String dbPassword;

    public Connection getConnection() throws IOException {

        Connection connection = null;
        Properties properties = new Properties();
        try (InputStream input = SqlConnectionService.class.getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            // Accessing properties
            dbUrl = properties.getProperty("database.url");
            dbUser = properties.getProperty("database.user");
            dbPassword = properties.getProperty("database.password");

            System.out.println("Database URL: " + dbUrl);
            System.out.println("Database User: " + dbUser);
            System.out.println("Database Password: " + dbPassword);

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            // Step 1: Register JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Step 3: Do something with the connection (e.g., execute SQL queries)
            // ...

            System.out.println("Connected to the database.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();}

            // Step 4: Close the connection (cleanup)

        return connection;
    }


}

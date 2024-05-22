package org.example;


import java.sql.*;
import java.util.Random;

public class PopulateCPUtableFromDatabase {

    // JDBC URL, username, and password of PostgreSQL server
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "DCproject123";

    // JDBC variables for opening, closing and querying from the database
    private static Connection connection;
    private static Statement statement;

    public static void InsertTestResultsIntoDatabase(double score, double time, int numDigits) {
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Generate random data and insert into the animals table
            Random random = new Random();
            int id = random.nextInt(1000);
            InsertTestResultsIntoDatabase(id, score, time, numDigits);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and connection
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void InsertTestResultsIntoDatabase(int id, double score, double time, int numDigits) throws SQLException {

        String query = String.format("INSERT INTO cpu (test_id,cpu_score,nr_of_pi_digits,time) VALUES('%d', '%f','%d','%f')", id, score, numDigits, time);
        statement.executeUpdate(query);
    }
}
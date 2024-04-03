package com.example.movieradar;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseTask extends AsyncTask<Void, Void, Connection> {

    //link naar de db

    Connection connection;
    String uname,pass,ip,port,database;

    public DatabaseTask(){
    }

<<<<<<< Updated upstream
    public Connection Connectionclass() {
        ip = "145.48.6.86";
        database = "CodeAcademyJGKJ";
        uname = "jgkj";
        pass = "jgkj2023";
        port = "1443";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionURL = null;

        try{

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connectionURL = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + database + ";user=" + uname + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionURL);
        }catch(Exception ex){
            Log.e("Error",ex.getMessage());
        }
        return connection;
    }
=======
//    public Connection Connectionclass() {
//        ip = "145.48.6.86";
//        database = "CodeAcademyJGKJ";
//        uname = "jgkj";
//        pass = "jgkj2023";
//        port = "1443";
//        Log.d(TAG, "onConnection: ");
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Log.d(TAG, "AfterStrictmode: ");
//
//        Connection connection = null;
//        String connectionURL = null;
//
//        try{
//            Log.d(TAG, "BeforeCon: ");
//
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            connectionURL = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + database + ";user=" + uname + ";password=" + pass + ";";
//            Log.d(TAG, "AtCon: ");
//
//            connection = DriverManager.getConnection(connectionURL);
//            Log.d(TAG, "AfterCon: ");
//
//        }catch(Exception ex){
//            Log.e("Error",ex.getMessage());
//        }
//        return connection;
//    }
>>>>>>> Stashed changes

    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://aei-sql2.avans.nl:1443;DatabaseName=CodeAcademyJGKJ;encrypt=false;";
    private static final String USER = "jgkj";
    private static final String PASSWORD = "jgkj2023";

    private String id;
    private ArrayList<String> list;


    public DatabaseTask(String id, ArrayList<String> list) {
        this.id = id;
        this.list = list;
    }
    @Override
    protected Connection doInBackground(Void... voids) {
        try {
            // Laad de JDBC driver
            Class.forName(JDBC_DRIVER);
            Log.d(TAG, "doInBackground: ");

            // Maak connectie database
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Log.d(TAG, "doInBackground: ");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("An ERROR has occured, trying to connect to the database:");
            e.printStackTrace(); // Als er een exceptie optreed, mooi afhandelen.
            return null;
        }
    }

    @Override
    protected void onPostExecute(Connection connection) {
        super.onPostExecute(connection);
        // Handle the result of the database connection
        if (connection != null) {
            // Connection successful, proceed with further actions
            try {
                Log.d(TAG, "onPostExecute: ");
                switch (id) {
                    case "Insert":
                        InsertRegister(list);

                    case "Login":
                        CheckLoginAuthentication(list);

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void InsertRegister(ArrayList <String> list){
    list.get(0);

    }
    boolean CheckLoginAuthentication(ArrayList<String> list) {
        try {
            Connection connection = doInBackground(); // Obtain connection
            if (connection != null) {
                String query = "SELECT * FROM [MR-Gebruiker] WHERE Gebruikersnaam=? AND Wachtwoord=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, list.get(1)); // Assuming username is at index 1
                statement.setString(2, list.get(2)); // Assuming password is at index 2
                ResultSet resultSet = statement.executeQuery();
                boolean isValidUser = resultSet.next(); // Check if result set has any rows
                resultSet.close();
                statement.close();
                connection.close();
                return isValidUser;
            } else {
                Log.e(TAG, "Connection is null");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        }
        return false;
    }

}

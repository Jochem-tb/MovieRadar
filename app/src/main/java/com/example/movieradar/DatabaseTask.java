package com.example.movieradar;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public Connection Connectionclass() {
        ip = "aei-sql2.avans.nl";
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
            System.out.println("Connection made successfully!");
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
    boolean CheckLoginAuthentication (ArrayList <String> list){
        try {
            ResultSet rs = DatabaseCon.createConnection().prepareStatement("SELECT * FROM MR-Gebruiker WHERE Gebruikersnaam='" + list.get(1) + "' AND Wachtwoord='" + list.get(2) + "'").executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

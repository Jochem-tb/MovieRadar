package com.example.movieradar;
import android.os.AsyncTask;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseTask extends AsyncTask<Void, Void, Connection> {

    private String id;
    private ArrayList<String> list;
    private TextView EmailUserField;
    private TextView PasswordField;


    public DatabaseTask(String id, ArrayList<String> list) {
        this.id = id;
        this.list = list;
    }
    @Override
    protected Connection doInBackground(Void... voids) {
        Connection connection = null;

        try {
            switch(id){
                case "Insert":
                    InsertRegister();

                case "Login":
                    CheckLoginAuthentication();

            }


                    // Perform database connection in background thread
            connection = DatabaseCon.createConnection();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    protected void onPostExecute(Connection connection) {
        super.onPostExecute(connection);
        // Handle the result of the database connection
        if (connection != null) {
            // Connection successful, proceed with further actions
        } else {
            // Connection failed, handle error
        }
    }
    public void InsertRegister(list){
    list.get(0);

    }
    boolean CheckLoginAuthentication (list){
        try {
            ResultSet rs = DatabaseCon.createConnection().prepareStatement("SELECT * FROM MR-Gebruiker WHERE Gebruikersnaam='" + list.get(1) + "' AND Wachtwoord='" + list.get(2) + "'").executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

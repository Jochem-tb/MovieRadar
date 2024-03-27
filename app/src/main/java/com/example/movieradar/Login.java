package com.example.movieradar;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {

    private TextView EmailUserField;

    private TextView PasswordField;


    boolean CheckLoginAuthentication(TextView EmailUserField, TextView PasswordField){
        try {
            ResultSet rs = DatabaseCon.createConnection().prepareStatement("SELECT * FROM MR-Gebruiker WHERE UserName='" + EmailUserField + "' AND PassWord='" + PasswordField + "'").executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    }


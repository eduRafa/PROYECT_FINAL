/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import database.Connect;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.Dialogs.DatabaseWarningDialog;
import view.UI;

/**
 * @author rafa0
 */
public class Application {

    public static void main(String[] args) {
        try {
            Connect c = Connect.getInstance();
            c.startConnection();
            UI.start();
        } catch (ClassNotFoundException ex) {
            new DatabaseWarningDialog(ex.getMessage());
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            new DatabaseWarningDialog(ex.getMessage()+ex.getErrorCode());
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

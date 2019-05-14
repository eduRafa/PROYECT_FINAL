/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.Query;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Communication;
import view.UI;
import view.UiUtils;

/**
 *
 * @author rafa0
 */
public class Controller implements ActionListener {

    private static UI myUI;
    private static Controller me;

    private Controller() {

    }

    public static Controller getInstance() {
        if (me == null) {
            me = new Controller();
            return me;
        } else {
            return me;
        }
    }

    public static void setUi(UI ui) {
        myUI = ui;
        myUI.setController(me);
    }

    public Color getPrimaryColor() {
        return Communication.getPrimaryColor();
    }

    public void setPrimaryColor(Color c) {
        Communication.setPrimaryColor(c);
        UiUtils.printAllComponents(myUI, c);
        UiUtils.printAllComponents(myUI.getImageManager(), c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "add":
        {
            try {
                Query.addSuspect(myUI.getAddSuspect());
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            case "remove":
                ;
                break;

        }
    }

    public static /*String[]*/ void getPhotos(String idSuspect) {
        //Query.getPhotos(idSuspect);
    }

}

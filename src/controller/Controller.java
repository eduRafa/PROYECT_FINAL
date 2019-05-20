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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Communication;
import model.Suspect;
import view.CreateAndFillTables;
import view.PrintComponents;
import view.UI;

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
        PrintComponents.printAllComponents(myUI, c);
        PrintComponents.printAllComponents(myUI.getAddSuspectImageManager(), c);
        PrintComponents.printAllComponents(myUI.getModifySuspectImageManager(), c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "add": 
                Query.addSuspect(myUI.getAddSuspect());
                myUI.removeAddSuspectsFields();
                myUI.getAddSuspectImageManager().resetImageManager();
                CreateAndFillTables.fillMainTable();
            break;
            case "modify":
                Query.Update(myUI.getModifySuspect());
                CreateAndFillTables.fillMainTable();
                break;
            case "search": 
                //try {
                //Query.searchBy(myUI.getAddSuspect());
                //} catch (SQLException ex) {
                //  Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                //}
            break;
        }
    }

    public static Suspect[] getSuspects() {
        return Query.showTen();
    }

    public static Suspect findSuspect(Integer suspectCode) {
        return Query.findSuspect(suspectCode);
    }

    public static void getPhotos(Integer idSuspect) {
        //Query.getPhotos(idSuspect);
    }

    public static void deleteSuspect(Integer id) {
        Query.deleteSuspect(id);
    }

}

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
import java.util.ArrayList;
import model.Communication;
import model.Images;
import model.Suspect;
import view.CreateAndFillTables;
import view.PrintComponents;
import view.UI;

/**
 *
 * @author rafa0
 */
public class Controller implements ActionListener{

    private static UI myUI;
    private static Controller me;

    public static Controller getInstance() {
        if (me == null) {
            me = new Controller();
            return me;
        } else {
            return me;
        }
    }

    public void setUi(UI ui) {
        myUI = ui;
        UI.setController(me);
    }

    public Color getPrimaryColor() {
        return Communication.getPrimaryColor();
    }

    public void setPrimaryColor(Color c) {
        Communication.setPrimaryColor(c);
        PrintComponents.printAllComponents(myUI, c);
        PrintComponents.printAllComponents(myUI.getAddSuspectImageManager(), c);
        PrintComponents.printAllComponents(UI.getModifySuspectImageManager(), c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "add":
                Query.addSuspect(myUI.getAddSuspect());
                myUI.removeAddSuspectsFields();
                myUI.getAddSuspectImageManager().resetImageManager();
                CreateAndFillTables.fillMainTable(null);
                break;
            case "modify":
                Query.Update(myUI.getModifySuspect());
                CreateAndFillTables.fillMainTable(null);
                break;
            case "search":
                ArrayList<Suspect> coincidences = Query.search(myUI.getSearchSuspect());
                break;
        }
    }

    public Suspect[] getSuspects() {
        return Query.showTen();
    }

    public Suspect findSuspect(Integer suspectCode) {
        return Query.findSuspect(suspectCode);
    }

    public Images[] getPhotos(Integer idSuspect) {
        Images[] suspectBeenModifiedPhotos=Query.showImg(idSuspect);
        return suspectBeenModifiedPhotos;
    }
    
    public Suspect[] getNextTen(){
        for (Suspect suspect : Query.showNext()) {
            if(suspect.getName()!=null){
                            System.out.println(suspect.getName());
            }
        }
        return Query.showNext();
    }

    public static void deleteSuspect(Integer id) {
        Query.deleteSuspect(id);
    }

}

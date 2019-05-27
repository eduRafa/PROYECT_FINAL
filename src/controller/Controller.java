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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
public class Controller implements ActionListener {

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

    /**
     * Metodo encargado de modificar el color primario en el archivo colors.xml
     *
     * @param c Nuevo color primario
     */
    public void setPrimaryColor(Color c) {
        Communication.setPrimaryColor(c);
        PrintComponents.printAllComponents(myUI, c);
        PrintComponents.printAllComponents(myUI.getAddSuspectImageManager(), c);
        PrintComponents.printAllComponents(UI.getModifySuspectImageManager(), c);
    }

    /**
     * Metodo sobreescrito que recoge las acciones de los botones relacionados
     * con la base de datos
     *
     * @param e Accion del elemento que la invoco
     */
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
                HashMap<Integer, ArrayList<String>> coincidences = Query.search(myUI.getSearchSuspect());

                for (Map.Entry<Integer, ArrayList<String>> entry : coincidences.entrySet()) {
                    System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue().size());

                }
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
        Images[] suspectBeenModifiedPhotos = Query.showImg(idSuspect);
        return suspectBeenModifiedPhotos;
    }

    public Suspect[] getNextTen() {
        return Query.showNext();
    }

    public static void deleteSuspect(Integer id) {
        Query.deleteSuspect(id);
    }

    public Suspect[] getPreviousTen() {
        return Query.showPrevious();
    }

}

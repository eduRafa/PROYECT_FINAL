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
import model.Communication;
import model.Images;
import model.Suspect;
import view.Tables.CreateAndFillMainTable;
import static view.Tables.CreateAndFillMainTable.fillMainTable;
import view.PrintComponents;
import view.Tables.CreateAndFillSearchTable;
import view.UI;
import view.imageManagers.ImageManager;

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
        PrintComponents.printAllComponents(myUI.getModifySuspectImageManager(), c);
    }

    /**
     * Metodo sobreescrito que recoge las acciones de los botones relacionadas
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
                CreateAndFillMainTable.fillMainTable(null);
                break;
            case "modify":
                Query.Update(myUI.getModifySuspect());
                CreateAndFillMainTable.fillMainTable(null);
                break;
            case "search":
                CreateAndFillSearchTable.setSearchPage(Query.searchRelations(myUI.getSearchSuspect()));
                CreateAndFillSearchTable.fillSearchTable();
                break;
            case "searchSpecificSuspect":
                myUI.setSearchFields(Query.findSuspect(CreateAndFillMainTable.getLastCodeOfSuspectClicked()));
                myUI.hideLayouts();
                myUI.showPnlSearch();
                myUI.pnlSearchSearch.setVisible(true);
                break;
            case "profileOfSearchedSuspect":
                Suspect profileOfSearchedSuspect = Query.findSuspect(CreateAndFillSearchTable.getLastCodeOfSuspectClicked());
                if (profileOfSearchedSuspect != null) {
                    myUI.setSuspectBeenModified(profileOfSearchedSuspect);
                    myUI.setModifySuspectFields(profileOfSearchedSuspect);
                    myUI.hideLayouts();
                    myUI.showSuspectLayouts();
                    myUI.hiddePnlSearch();
                }
                break;
            case "profileOfMainSuspect":
                Suspect profileOfMainSuspect = Query.findSuspect(CreateAndFillMainTable.getLastCodeOfSuspectClicked());
                if (profileOfMainSuspect != null) {
                    myUI.setSuspectBeenModified(profileOfMainSuspect);
                    myUI.setModifySuspectFields(profileOfMainSuspect);
                    myUI.hideLayouts();
                    myUI.showSuspectLayouts();
                    myUI.hiddePnlSearch();
                }
                break;
            case "remove":
                Query.deleteSuspect(CreateAndFillMainTable.getLastCodeOfSuspectClicked());
                CreateAndFillMainTable.fillMainTable(null);
                break;
        }
    }

    public Suspect[] getSuspects() {
        return Query.showTen();
    }

    public int getSuspectNPhotos(Integer id) {
        return Query.getSuspectNPhotos(id);
    }

    public Images[] getPhotos(Integer idSuspect) {
        Images[] suspectBeenModifiedPhotos = Query.showImg(idSuspect);
        return suspectBeenModifiedPhotos;
    }

    public Suspect[] getNextTen() {
        return Query.showNext();
    }

    public Suspect[] getPreviousTen() {
        return Query.showPrevious();
    }

    public Suspect[] getTenSpecificSuspects(Integer[] tenSuspectsCodes) {
        return Query.getTenSpecificSuspects(tenSuspectsCodes);
    }
    
    public void saveImages(Integer codeSuspect, Images[] imgs){
        Query.updateImages(codeSuspect,imgs);
    }
}

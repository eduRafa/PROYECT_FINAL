/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author rafa0
 */
public class UiUtils {

    public static ArrayList<Component> getAllComponents(Container c) {
        Component[] comps = c.getComponents();
        ArrayList<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    public static ArrayList<?> transformTextToArrayList(String allTheValues) {
        String[] eachValue = allTheValues.split(".+\\n");
        ArrayList<String> myValues;
        myValues = new ArrayList<String>(Arrays.asList(eachValue));
        return myValues;
    }
    
    /*public static String transformTextToString(){
        
    }*/
    
    public static String rgbFormatted(Color rgb) {
        StringBuilder stringRGB = new StringBuilder();

        stringRGB.append(rgb.getRed() + "," + rgb.getGreen() + "," + rgb.getBlue());

        return stringRGB.toString();

    }

}

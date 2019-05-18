/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Address;
import model.Car_Registration;
import model.Email;
import model.Phone;

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

    public static ArrayList<?> transformStringToArrayList(String allTheValues) {
        String[] eachValue = allTheValues.split("\\n");

        for (String string : eachValue) {
            string = string.trim();
        }

        ArrayList<String> myValues;
        myValues = new ArrayList<String>(Arrays.asList(eachValue));

        return myValues;
    }

    public static String transformArrayListToString(ArrayList<Object> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            if (multipleText.get(0) instanceof Phone) {
                for (int i = 0; i < multipleText.size(); i++) {
                    Phone p = (Phone) multipleText.get(i);
                    miString.append(p.getPhoneNumber());
                }
            }
            if (multipleText.get(0) instanceof Email) {
                for (int i = 0; i < multipleText.size(); i++) {
                    Email e = (Email) multipleText.get(i);
                    miString.append(e.getEmail());
                }
            }
            if (multipleText.get(0) instanceof Address) {
                for (int i = 0; i < multipleText.size(); i++) {
                    Address a = (Address) multipleText.get(i);
                    miString.append(a.getAddress());
                }
            }
            if (multipleText.get(0) instanceof Car_Registration) {
                for (int i = 0; i < multipleText.size(); i++) {
                    Car_Registration cr = (Car_Registration) multipleText.get(i);
                    miString.append(cr.getRegistration());
                }
            }
        }

        return miString.toString();
    }

    public static String rgbFormatted(Color rgb) {
        StringBuilder stringRGB = new StringBuilder();

        stringRGB.append(rgb.getRed() + "," + rgb.getGreen() + "," + rgb.getBlue());

        return stringRGB.toString();

    }

}

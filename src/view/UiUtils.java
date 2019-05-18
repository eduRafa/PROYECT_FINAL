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

    public static ArrayList<Phone> transformStringToArrayListPhone(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Phone> myPhones = new ArrayList<>();

        for (String string : eachValue) {
            myPhones.add(new Phone(Integer.valueOf(string),suspetCode));
        }

        return myPhones;
    }

    public static ArrayList<Email> transformStringToArrayListEmail(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Email> myEmails = new ArrayList<>();

        for (String string : eachValue) {
            myEmails.add(new Email(string,suspetCode));
        }

        return myEmails;
    }

    public static ArrayList<Address> transformStringToArrayListAddress(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Address> myAddresses = new ArrayList<>();

        for (String string : eachValue) {
            myAddresses.add(new Address(string,suspetCode));
        }

        return myAddresses;
    }

    public static ArrayList<Car_Registration> transformStringToArrayListCar_Registration(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Car_Registration> myAddresses = new ArrayList<>();

        for (String string : eachValue) {
            myAddresses.add(new Car_Registration(string,suspetCode));
        }

        return myAddresses;
    }

    public static String transformArrayListPhoneToString(ArrayList<Phone> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Phone p = (Phone) multipleText.get(i);
                miString.append(p.getPhoneNumber());
            }
        }
        return miString.toString();

    }

    public static String transformArrayListEmailToString(ArrayList<Email> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Email e = (Email) multipleText.get(i);
                miString.append(e.getEmail());
            }
        }
        return miString.toString();
    }

    public static String transformArrayListAddressToString(ArrayList<Address> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Address a = (Address) multipleText.get(i);
                miString.append(a.getAddress());
            }
        }
        return miString.toString();
    }

    public static String transformArrayListCarRegToString(ArrayList<Car_Registration> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Car_Registration cr = (Car_Registration) multipleText.get(i);
                miString.append(cr.getRegistration());
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

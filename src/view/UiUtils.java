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
import model.Address;
import model.Car_Registration;
import model.Email;
import model.Phone;

/**
 *
 * @author rafa0
 */
public class UiUtils {

    /**
     * Metodo el cual Obtiene toda la rama de componentes hijos del contenedor
     * pasado por referencia.
     * @param c Contenedor a buscar sus componentes
     * @return Decuelve un ArrayList de Componentes.
     */
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

    public static ArrayList<Phone> transformStringToArrayListPhone(String allTheValues, Integer suspetCode) throws Exception {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Phone> myPhones = new ArrayList<>();

        if (eachValue.length > 0) {
            for (String string : eachValue) {
                if (string != null) {
                    if (string.length() > 0) {
                        if (string.matches("\\d*")) {
                            myPhones.add(new Phone(Integer.valueOf(string), suspetCode));
                        } else {
                            throw new Exception("Telefono no valido");
                        }
                    }
                }
            }
        }

        return myPhones;
    }

    public static ArrayList<Email> transformStringToArrayListEmail(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Email> myEmails = new ArrayList<>();

        if (eachValue.length > 0) {
            for (String string : eachValue) {
                if (string != null) {
                    myEmails.add(new Email(string, suspetCode));
                }
            }
        }

        return myEmails;
    }

    public static ArrayList<Address> transformStringToArrayListAddress(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Address> myAddresses = new ArrayList<>();

        if (eachValue.length > 0) {
            for (String string : eachValue) {
                if (string != null) {
                    myAddresses.add(new Address(string, suspetCode));
                }
            }
        }

        return myAddresses;
    }

    public static ArrayList<Car_Registration> transformStringToArrayListCar_Registration(String allTheValues, Integer suspetCode) {
        String[] eachValue = allTheValues.split("\\n");
        ArrayList<Car_Registration> myAddresses = new ArrayList<>();

        if (eachValue.length > 0) {
            for (String string : eachValue) {
                if (string != null) {
                    myAddresses.add(new Car_Registration(string, suspetCode));
                }
            }
        }

        return myAddresses;
    }

    public static String transformArrayListPhoneToString(ArrayList<Phone> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Phone p = (Phone) multipleText.get(i);
                if (p.getPhoneNumber() == null) {
                    miString.append("");
                } else {
                    if (i == multipleText.size() - 1) {
                        miString.append(p.getPhoneNumber());
                    } else {
                        miString.append(p.getPhoneNumber() + "\n");
                    }
                }
            }
        }
        return miString.toString();

    }

    public static String transformArrayListEmailToString(ArrayList<Email> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Email e = (Email) multipleText.get(i);
                if (e.getEmail() == null) {
                    miString.append("");
                } else {
                    if (i == multipleText.size() - 1) {
                        miString.append(e.getEmail());
                    } else {
                        miString.append(e.getEmail() + "\n");
                    }

                }
            }
        }
        return miString.toString();
    }

    public static String transformArrayListAddressToString(ArrayList<Address> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Address a = (Address) multipleText.get(i);
                if (a.getAddress() != null) {
                    if (i == multipleText.size() - 1) {
                        miString.append(a.getAddress());
                    } else {
                        miString.append(a.getAddress() + "\n");
                    }
                } else {
                    miString.append("");
                }
            }
        }
        return miString.toString();
    }

    public static String transformArrayListCarRegToString(ArrayList<Car_Registration> multipleText) {
        StringBuilder miString = new StringBuilder();

        if (!multipleText.isEmpty()) {
            for (int i = 0; i < multipleText.size(); i++) {
                Car_Registration cr = (Car_Registration) multipleText.get(i);
                if (cr.getRegistration() != null) {
                    if (i == multipleText.size() - 1) {
                        miString.append(cr.getRegistration());
                    } else {
                        miString.append(cr.getRegistration() + "\n");
                    }
                } else {
                    miString.append(" ");
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

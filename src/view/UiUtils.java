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
     *
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

    public static <T> ArrayList<T> transformStringToArrayList(String allTheValues, Integer suspetCode, String field) throws Exception {
        ArrayList<T> x = null;

        if (allTheValues != null) {
            x = new ArrayList<>();
            String[] eachValue = allTheValues.split("\\n");

            for (String string : eachValue) {
                if (string != null) {
                    if (string.length() > 0) {
                        if (field != null) {
                            switch (field) {
                                case "Phone":
                                    try {
                                        x.add((T) new Phone(Integer.valueOf(string.trim()), suspetCode));
                                    } catch (NumberFormatException nfe) {
                                        throw new Exception("Introduzca un numero válido");
                                    }
                                    break;
                                case "Email":
                                    x.add((T) new Email(string.trim(), suspetCode));
                                    break;
                                case "Address":
                                    x.add((T) new Address(string.trim(), suspetCode));
                                    break;
                                case "Car_Registration":
                                    x.add((T) new Car_Registration(string.trim(), suspetCode));
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return x;
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
        StringBuilder stringRGB = null;
        if (rgb != null) {
            stringRGB = new StringBuilder();
            stringRGB.append(rgb.getRed() + "," + rgb.getGreen() + "," + rgb.getBlue());
        }

        return stringRGB.toString();

    }
}

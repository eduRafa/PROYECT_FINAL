/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static view.UiUtils.getAllComponents;

/**
 *
 * @author rafa0
 */
public class EnteredExited {

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Metodo que agrupa el evento entered de algunos botones de la interfaz.
     *
     * @param evt Evento, en este caso Entered.
     */
    public static void mouseComponentEffect(java.awt.event.MouseEvent evt) {
        Component callingComponent = evt.getComponent();
        String[] values = callingComponent.getAccessibleContext().getAccessibleName().split("\\$");

        if (evt.getComponent() instanceof JButton) {
            applyBackgroundColorEffect(callingComponent, values[0]);
            applyButtonBorderColorEffect((JButton) callingComponent, values[1]);
            applyForegroundColorEffect(callingComponent, values[2]);
            callingComponent.getAccessibleContext().setAccessibleName(changeValues(values));

        } else if (evt.getComponent() instanceof JPanel) {
            jPanelEffects((JPanel) callingComponent, values);
        }
    }

    public static void jPanelEffects(JPanel pnl, String[] values) {

        if (pnl instanceof Container) {
            ArrayList<Component> innerPanelComponents = getAllComponents(pnl);
            for (Component innerPanelComponent : innerPanelComponents) {
                if (innerPanelComponent instanceof JLabel && innerPanelComponent.getAccessibleContext().getAccessibleName().contains("$")) {////aqui
                    JLabel tmpLabel = (JLabel) innerPanelComponent;

                    if (tmpLabel.getIcon() != null) {
                        setIcon(tmpLabel);
                    } else {
                        String[] innerPanelComponentValues = innerPanelComponent.getAccessibleContext().getAccessibleName().split("\\$");
                        applyForegroundColorEffect(innerPanelComponent, innerPanelComponentValues[2]);
                        innerPanelComponent.getAccessibleContext().setAccessibleName(changeValues(innerPanelComponentValues));
                    }
                }
            }
        }
        applyBackgroundColorEffect(pnl, values[0]);
        applyPanelBorderColorEffect(pnl, values[1]);
        applyForegroundColorEffect(pnl, values[2]);
        pnl.getAccessibleContext().setAccessibleName(changeValues(values));
    }

    private static void applyBackgroundColorEffect(Component c, String backgroundColor) {
        switch (backgroundColor) {
            case "0":
                c.setBackground(UI.getPrimaryColor());
                break;
            case "1":
                c.setBackground(UI.getSecundaryColor());
                break;
        }
    }

    private static void applyButtonBorderColorEffect(JButton btn, String borderColor) {
        switch (borderColor) {
            case "1":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getSecundaryColor()));
                break;
            case "0":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getPrimaryColor()));
                break;
        }
    }

    private static void applyPanelBorderColorEffect(JPanel pnl, String borderColor) {
        switch (borderColor) {
            case "1":
                pnl.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getSecundaryColor()));
                break;
            case "0":
                pnl.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getPrimaryColor()));
                break;
        }
    }

    private static void applyForegroundColorEffect(Component c, String foregroundColor) {
        switch (foregroundColor) {
            case "1":
                c.setForeground(UI.getSecundaryColor());
                break;
            case "0":
                c.setForeground(UI.getPrimaryColor());
                break;
        }
    }

    private static String changeValues(String[] values) {
        StringBuilder accesibleName = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("0")) {
                accesibleName.append("1");
            } else if (values[i].equals("1")) {
                accesibleName.append("0");
            } else if (values[i].equals("-")) {
                accesibleName.append("-");
            }

            if (i != values.length) {
                accesibleName.append("$");
            }
        }
        return accesibleName.toString();
    }

    private static void setIcon(JLabel tmpLabel) {

        String oldPath = tmpLabel.getAccessibleContext().getAccessibleDescription();
        //System.out.println(oldPath);
        if (oldPath != null && oldPath.contains("$")) {
            String oldColor[] = oldPath.split("\\$");

            Icon x = null;
            String newPath = null;
            if (rgbFormatted(UI.getPrimaryColor()).equals(oldColor[1])) {
                //System.out.println(newPath);
                newPath = oldPath.replaceAll("\\$\\d{3},\\d{3},\\d{3}\\$", "\\$" + rgbFormatted(UI.getSecundaryColor()) + "\\$");
                //System.out.println(newPath);
                Image myImage = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                        getSystemResource("view/images/" + newPath));
                tmpLabel.setIcon(new ImageIcon(myImage));
                tmpLabel.getAccessibleContext().setAccessibleDescription(newPath);
            } else if (rgbFormatted(UI.getSecundaryColor()).equals(oldColor[1])) {
                newPath = oldPath.replaceAll("\\$\\d{3},\\d{3},\\d{3}\\$", "\\$" + rgbFormatted(UI.getPrimaryColor()) + "\\$");
                //System.out.println(newPath);

                Image myImage = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                        getSystemResource("view/images/" + newPath));
                tmpLabel.setIcon(new ImageIcon(myImage));
                tmpLabel.getAccessibleContext().setAccessibleDescription(newPath);
            }
        }
    }

    private static String rgbFormatted(Color rgb) {
        StringBuilder stringRGB = new StringBuilder();

        stringRGB.append(rgb.getRed() + "," + rgb.getGreen() + "," + rgb.getBlue());

        return stringRGB.toString();

    }

}

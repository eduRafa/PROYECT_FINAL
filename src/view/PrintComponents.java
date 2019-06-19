/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.Dialogs.WarningDialog;
import controller.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 *
 * @author rafa0
 */
public class PrintComponents {

    private static JLabel[] menuConfIcons = new JLabel[3];
    private static Color secundaryColor = Color.white;
    private static Color oldColor;
    private static Color primaryColor;
    private static Color[] themeColor;

    /**
     * Metodo sobreescrito encargado de cambiar el principal color de la
     * aplicación por uno de los temas de la misma.
     *
     * @param e Accion desde la clase UI la cuál corresponde con el cambio de un
     * tema de la aplicación. La cantidad de temas es 6 por eso, el accesible
     * name de la acción, deberá de corresponder con un valor dentro de ese
     * límite.
     */
    public static void changeTheme(ActionEvent e) {
        try {
            int theme = Integer.valueOf(e.getActionCommand());
            if (theme >= 0 && theme < themeColor.length) {
                Controller.getInstance().setPrimaryColor(getThemeColor(Integer.valueOf(e.getActionCommand())));
                primaryColor = themeColor[Integer.valueOf(e.getActionCommand())];
            }
        } catch (NumberFormatException nfe) {
            String message = "Ocurrio algo inesperado: PrintComponents 49";
            new WarningDialog(UI.getInstance(), true, message);
        }
    }

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static Color getSecundaryColor() {
        return secundaryColor;
    }

    /**
     * Obtiene los colores de la aplicación e inicializa los temas.
     */
    public static void setColors() {
        primaryColor = Controller.getInstance().getPrimaryColor();
        oldColor = PrintComponents.primaryColor;
        setThemeColors();
    }

    private static void setThemeColors() {
        themeColor = new Color[6];
        themeColor[0] = new Color(255, 190, 113);
        themeColor[1] = new Color(133, 234, 130);
        themeColor[2] = new Color(158, 180, 230);
        themeColor[3] = new Color(222, 169, 218);
        themeColor[4] = new Color(225, 157, 156);
        themeColor[5] = new Color(248, 239, 92);
    }

    public static Color getThemeColor(int index) {
        if (index >= 0 && index < themeColor.length) {
            return themeColor[index];
        } else {
            return null;
        }
    }

    /**
     * Encargado de pintar todos los componentes en los que su propiedad
     * accesible name contenga el formato 0|1|-$0|1|-$0|1|-
     * (Background$Border$Foreground)
     *
     * @param con El objeto contenedor de los demás componentes.
     * @param primaryColor El nuevo color primario del objeto a pintar.
     */
    public static void printAllComponents(Container con, Color primaryColor) {
        Component[] comps = con.getComponents();
        for (Component comp : comps) {
            if (comp.getAccessibleContext().getAccessibleName() != null && comp.
                    getAccessibleContext().getAccessibleName().contains("$")) {
                String[] value = comp.getAccessibleContext().getAccessibleName().split("\\$");
                if (comp.getAccessibleContext().getAccessibleName().equals("$$$")) {
                    printConfMenuIcons(comp, primaryColor);
                } else {
                    if (comp instanceof JTextComponent) {
                        JScrollPane tmp = (JScrollPane) comp.getParent().getParent();
                        tmp.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
                    } else if (comp instanceof JButton) {
                        JButton tmpButton = (JButton) comp;
                        applyBackgroundColor(comp, value[0], primaryColor);
                        applyButtonBorderColor(tmpButton, value[1], primaryColor);
                        applyForegroundColor(comp, value[2], primaryColor);

                    } else if (comp instanceof JLabel) {
                        applyBackgroundColor(comp, value[0], primaryColor);
                        applyForegroundColor(comp, value[2], primaryColor);
                    } else if (comp instanceof JTable) {
                        applyBackgroundTableHeaderColor((JTable) comp, value[0], primaryColor);
                        applyBorderTableHeaderColor((JTable) comp, value[1], primaryColor);
                        applyForegroundColor((JTable) comp, value[2], primaryColor);
                        ((JTable) comp).setSelectionBackground(primaryColor);
                    } else if (comp instanceof JPanel) {
                        applyBackgroundPanelColor((JPanel) comp, value[0], primaryColor);
                        applyBorderPanelColor((JPanel) comp, value[1], primaryColor);
                        applyForegroundColor(comp, value[2], primaryColor);
                    } else if (comp instanceof JSeparator) {
                        JSeparator tmpSeparator = (JSeparator) comp;
                        tmpSeparator.setForeground(primaryColor);
                    }
                }
            }
            if (comp instanceof Container) {
                printAllComponents((Container) comp, primaryColor);
            }
        }
    }

    private static void applyBackgroundColor(Component c, String value, Color newPrimaryColor) {
        switch (value) {
            /*case "0"://///////////////APLICADO CUANDO SE OBTENGA COLOR SECUNDARIO
                c.setBackground(UI.getPrimaryColor());
                break;*/
            case "1":
                c.setBackground(newPrimaryColor);
                break;
        }
    }

    private static void applyButtonBorderColor(JButton btn, String borderColor, Color newPrimaryColor) {
        switch (borderColor) {
            /*case "0":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getSecundaryColor()));
                break;*/
            case "1":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(newPrimaryColor));
                break;
        }
    }

    private static void applyForegroundColor(Component c, String foregroundColor, Color newPrimaryColor) {
        switch (foregroundColor) {
            /*case "0":
                c.setForeground(UI.getSecundaryColor());
                break;*/
            case "1":
                c.setForeground(newPrimaryColor);
                break;
        }
    }

    private static void applyBackgroundTableHeaderColor(JTable t, String value, Color newPrimaryColor) {
        switch (value) {
            /*case "0"://///////////////APLICADO CUANDO SE OBTENGA COLOR SECUNDARIO
                c.setBackground(UI.getPrimaryColor());
                break;*/
            case "1":
                t.getTableHeader().setBackground(newPrimaryColor);
                break;
        }
    }

    private static void applyBorderTableHeaderColor(JTable t, String borderColor, Color newPrimaryColor) {
        switch (borderColor) {
            /*case "0":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getSecundaryColor()));
                break;*/
            case "1":
                t.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(newPrimaryColor));
                break;
        }
    }

    private static void applyBackgroundPanelColor(JPanel pnl, String value, Color newPrimaryColor) {
        switch (value) {
            /*case "0"://///////////////APLICADO CUANDO SE OBTENGA COLOR SECUNDARIO
                c.setBackground(UI.getPrimaryColor());
                break;*/
            case "1":
                pnl.setBackground(newPrimaryColor);
                break;
        }
    }

    private static void applyBorderPanelColor(JPanel pnl, String borderColor, Color newPrimaryColor) {
        switch (borderColor) {
            /*case "0":
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(UI.getSecundaryColor()));
                break;*/
            case "1":
                pnl.setBorder(javax.swing.BorderFactory.createLineBorder(newPrimaryColor));
                break;
        }
    }

    private static void printConfMenuIcons(Component tmpComp, Color newPrimaryColor) {
        String oldPath = tmpComp.getAccessibleContext().getAccessibleDescription();

        if (oldPath != null) {
            String newPath = oldPath.replaceAll("\\$\\d{3},\\d{3},\\d{3}\\$", "\\$" + UiUtils.rgbFormatted(newPrimaryColor) + "\\$");
            Image myImage = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                    getSystemResource("view/images/" + newPath));
            if (tmpComp instanceof JLabel) {
                JLabel tmpLabel = (JLabel) tmpComp;
                tmpLabel.setIcon(new ImageIcon(myImage));
                tmpLabel.getAccessibleContext().setAccessibleDescription(newPath);
            } else if (tmpComp instanceof JButton) {
                JButton tmpButton = (JButton) tmpComp;
                tmpButton.setIcon(new ImageIcon(myImage));
                tmpButton.getAccessibleContext().setAccessibleDescription(newPath);
            }
        }
    }
}

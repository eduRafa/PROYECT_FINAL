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
    
    private static JLabel[] menuConfIcons=new JLabel[3];

    /**
     * Encargado de pintar todos los componentes en los que su propiedad accesible
     * name contenga el formato 0|1|-$0|1|-$0|1|- 
     * @param con El objeto contenedor de los demás componentes.
     * @param primaryColor El nuevo color primario del objeto a pintar.
     */
    public static void printAllComponents(Container con, Color primaryColor) {
        Component[] comps = con.getComponents();
        for (Component comp : comps) {
            if (comp.getAccessibleContext().getAccessibleName() != null && comp.
                    getAccessibleContext().getAccessibleName().contains("$")) {
                String[] value = comp.getAccessibleContext().getAccessibleName().split("\\$");
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
                }else if(comp instanceof JSeparator){
                    JSeparator tmpSeparator=(JSeparator) comp;
                    tmpSeparator.setForeground(primaryColor);
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
}

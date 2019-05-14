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
import static javafx.scene.input.KeyCode.T;
import javax.lang.model.type.ArrayType;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

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

    public static void printAllComponents(Container con, Color col) {
        Component[] comps = con.getComponents();
        for (Component comp : comps) {
            if (comp.getAccessibleContext().getAccessibleName() != null && comp.
                    getAccessibleContext().getAccessibleName().contains("$")) {
                String[] value = comp.getAccessibleContext().getAccessibleName().split("\\$");
                if (comp instanceof JTextComponent) {
                    JTextComponent tmp = (JTextComponent) comp;
                    tmp.setBorder(javax.swing.BorderFactory.createLineBorder(col));
                } else if (comp instanceof JButton) {
                    JButton tmpButton = (JButton) comp;
                    applyBackgroundColor(comp, value[0], col);
                    applyButtonBorderColor(tmpButton, value[1], col);
                    applyForegroundColor(comp, value[2], col);
                } else if (comp instanceof JLabel) {
                    JLabel tmpLabel = (JLabel) comp;

                    applyBackgroundColor(comp, value[0], col);
                    //applyButtonBorderColor((JButton)comp, value[1], col);
                    applyForegroundColor(comp, value[2], col);
                } else if (comp instanceof JTable) {
                    applyBackgroundTableHeaderColor((JTable) comp, value[0], col);
                    applyBorderTableHeaderColor((JTable) comp, value[1], col);
                    applyForegroundColor((JTable) comp, value[2], col);
                    ((JTable) comp).setSelectionBackground(col);
                } else if (comp instanceof JPanel) {
                    applyBackgroundPanelColor((JPanel) comp, value[0], col);
                    applyBorderPanelColor((JPanel) comp, value[1], col);
                    applyForegroundColor(comp, value[2], col);
                }
            }
            if (comp instanceof Container) {
                printAllComponents((Container) comp, col);
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

    public static ArrayList<?> transformTextToArrayList(String allTheValues){
        String[] eachValue=allTheValues.split(".+\\n");
        ArrayList<String> myValues;
        myValues =new ArrayList<String>(Arrays.asList(eachValue));
        return myValues;
    }
    
}

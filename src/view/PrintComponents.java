/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 *
 * @author rafa0
 */
public class PrintComponents {

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
                    applyForegroundColor(comp, value[2], col);
                } else if (comp instanceof JTable) {
                    applyBackgroundTableHeaderColor((JTable) comp, value[0], col);
                    applyBorderTableHeaderColor((JTable) comp, value[1], col);
                    applyForegroundColor((JTable) comp, value[2], col);
                    ((JTable) comp).setSelectionBackground(col);
                }else if (comp instanceof JPanel) {
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
}

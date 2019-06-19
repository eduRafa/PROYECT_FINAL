/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Tables;

import controller.Controller;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Suspect;
import view.UI;
import view.UiUtils;

/**
 *
 * @author rafa0
 */
public class CreateAndFillSearchTable {

    private static ArrayList<Integer> coincidences;
    private static int currentPosition = 0;
    private static int nPages = 1;
    private static int nPage = 1;
    private static final int SUSPECTSPERPAGE = 10;
    private static final int PROFILE_COLUMN = 10;
    private static Integer lastCodeOfSuspectClicked;

    public static Integer getLastCodeOfSuspectClicked() {
        return lastCodeOfSuspectClicked;
    }

    public static void setSearchPage(ArrayList<Integer> coincidences) {
        CreateAndFillSearchTable.coincidences = coincidences;
        nPages = (int) (Math.ceil((double) coincidences.size() / SUSPECTSPERPAGE));
    }

    public static final void setSearchRelationTable(JTable tblSearchRelation) {
        DefaultTableModel modelo = (DefaultTableModel) tblSearchRelation.getModel();
        Image profile = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-usuario-de-genero-neutro-20.png"));

        JButton profileButton = new JButton(new ImageIcon(profile));

        profileButton.setActionCommand("profileOfSearchedSuspect");
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Controller.getInstance().actionPerformed(evt);
            }
        });

        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);

        Object[][] registros = new Object[modelo.getRowCount()][modelo.getColumnCount()];

        String[] head = {"Nombre", "Apellido 1", "Apellido 2", "Sospechosos",
            "Antecedentes", "Hechos", "Telefono", "E-mail", "Direcciones", "Matriculas",
            "Perfil"
        };

        for (int i = 0; i < registros.length; i++) {
            for (int j = 0; j < registros[i].length - 1; j++) {
                registros[i][j] = "";
            }

            registros[i][registros[i].length - 1] = profileButton;
        }

        final Class[] tiposColumnas = new Class[modelo.getColumnCount()];
        for (int i = 0; i < tiposColumnas.length - 1; i++) {
            tiposColumnas[i] = java.lang.String.class;
        }

        tiposColumnas[tiposColumnas.length - 1] = JButton.class;

        DefaultTableModel model = new DefaultTableModel(registros, head) {
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                // Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                // observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Sobrescribimos este método para evitar que la columna que contiene los botones sea editada.
                Class[] myClasses = new Class[2];
                myClasses[0] = String.class;
                myClasses[1] = JButton.class;
                return false;
            }
        };

        //En esta parte cambiamos de tamaño las columnas menos las dos últimas
        tblSearchRelation.setModel(model);
        TableColumnModel defModel = tblSearchRelation.getColumnModel();
        for (int i = 0; i < defModel.getColumnCount() - 1; i++) {
            defModel.getColumn(i).setMinWidth(150);

        }
        tblSearchRelation.setDefaultRenderer(JButton.class,
                new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                /**
                 * Observen que todo lo que hacemos en éste método es retornar
                 * el objeto que se va a dibujar en la celda. Esto significa que
                 * se dibujará en la celda el objeto que devuelva el TableModel.
                 * También significa que este renderer nos permitiría dibujar
                 * cualquier objeto gráfico en la grilla, pues retorna el objeto
                 * tal y como lo recibe.
                 */
                return (Component) objeto;
            }
        });

        tblSearchRelation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (coincidences != null) {
                    if (!coincidences.isEmpty()) {

                        int fila = tblSearchRelation.rowAtPoint(e.getPoint());
                        int columna = tblSearchRelation.columnAtPoint(e.getPoint());

                        if (columna == PROFILE_COLUMN) {

                            if ((((nPage - 1) * SUSPECTSPERPAGE) + fila) < coincidences.size()) {
                                lastCodeOfSuspectClicked = coincidences.get((((nPage - 1) * SUSPECTSPERPAGE)) + fila);
                                profileButton.doClick();
                            }
                        }
                    }
                }
            }
        });
    }

    private static Integer[] getTenSuspectsCodes() {
        Integer[] tenCodesOfSuspects = null;

        if (!coincidences.isEmpty()) {
            tenCodesOfSuspects = new Integer[SUSPECTSPERPAGE];

            List<Integer> parcialList;
            if (coincidences.size() >= currentPosition + SUSPECTSPERPAGE) {
                parcialList = coincidences.subList(currentPosition, currentPosition + SUSPECTSPERPAGE);
            } else {
                parcialList = coincidences.subList(currentPosition, coincidences.size());
            }

            for (int i = 0; i < parcialList.size(); i++) {
                tenCodesOfSuspects[i] = parcialList.get(i);
            }
        }

        return tenCodesOfSuspects;
    }

    public static void nextPage() {
        if (nPage < nPages) {
            currentPosition += SUSPECTSPERPAGE;
            nPage++;
            fillSearchTable();
        }

    }

    public static void previousPage() {
        if (nPage > 1) {
            currentPosition = currentPosition - SUSPECTSPERPAGE;
            nPage--;
            fillSearchTable();
        }
    }

    public static void fillSearchTable() {
        Suspect[] relatedSuspects = Controller.getInstance().getTenSpecificSuspects(getTenSuspectsCodes());
        removeSearchDataTable();

        if (relatedSuspects != null) {
            JTable tblSearch = UI.getSearchTable();

            DefaultTableModel myModel = (DefaultTableModel) tblSearch.getModel();
            int col = myModel.getColumnCount();
            int row = myModel.getRowCount();
            int k = 0;
            boolean lastOne = false;

            for (int i = 0; i < col - 1 && !lastOne; i++) {
                for (int j = 0; j < row && !lastOne; j++) {
                    if (relatedSuspects[i] != null) {
                        switch (k) {
                            case 0:
                                myModel.setValueAt(relatedSuspects[i].getName(), i, j);
                                break;
                            case 1:
                                myModel.setValueAt(relatedSuspects[i].getLastname1(), i, j);
                                break;
                            case 2:
                                myModel.setValueAt(relatedSuspects[i].getLastname2(), i, j);
                                break;
                            case 3:
                                if (relatedSuspects[i].getCompanions() != null) {
                                    if (!relatedSuspects[i].getCompanions().isEmpty()) {
                                        myModel.setValueAt(relatedSuspects[i].getCompanions(), i, j);
                                    } else {
                                        myModel.setValueAt(" ", i, j);
                                    }
                                } else {
                                    myModel.setValueAt(" ", i, j);
                                }
                                break;
                            case 4:
                                if (relatedSuspects[i].getRecord() != null) {
                                    if (!relatedSuspects[i].getRecord().isEmpty()) {
                                        myModel.setValueAt(relatedSuspects[i].getRecord(), i, j);
                                    }
                                }
                                break;
                            case 5:
                                if (relatedSuspects[i].getFacts() != null) {

                                    if (!relatedSuspects[i].getFacts().isEmpty()) {
                                        myModel.setValueAt(relatedSuspects[i].getFacts(), i, j);
                                    }
                                }
                                break;
                            case 6:
                                if (relatedSuspects[i].getPhone() != null) {
                                    if (!relatedSuspects[i].getPhone().isEmpty()) {
                                        if (relatedSuspects[i].getPhone().size() < 2) {
                                            myModel.setValueAt(UiUtils.transformArrayListPhoneToString(relatedSuspects[i].getPhone()), i, j);
                                        } else {
                                            myModel.setValueAt("Ver en Perfil", i, j);
                                        }
                                    }
                                } else {
                                    myModel.setValueAt(" ", i, j);
                                }
                                break;
                            case 7:
                                if (relatedSuspects[i].getEmail() != null) {
                                    if (!relatedSuspects[i].getEmail().isEmpty()) {
                                        if (relatedSuspects[i].getEmail().size() < 2) {
                                            myModel.setValueAt(UiUtils.transformArrayListEmailToString(relatedSuspects[i].getEmail()), i, j);
                                        } else {
                                            myModel.setValueAt("Ver en Perfil", i, j);
                                        }
                                    }
                                }
                                break;
                            case 8:
                                if (relatedSuspects[i].getAddress() != null) {
                                    if (!relatedSuspects[i].getAddress().isEmpty()) {
                                        if (relatedSuspects[i].getAddress().size() < 2) {
                                            myModel.setValueAt(UiUtils.transformArrayListAddressToString(relatedSuspects[i].getAddress()), i, j);
                                        } else {
                                            myModel.setValueAt("Ver en Perfil", i, j);
                                        }
                                    }
                                }
                                break;
                            case 9:
                                if (relatedSuspects[i].getCar_registration() != null) {
                                    if (!relatedSuspects[i].getCar_registration().isEmpty()) {
                                        if (relatedSuspects[i].getAddress().size() < 2) {
                                            myModel.setValueAt(UiUtils.transformArrayListCarRegToString(relatedSuspects[i].getCar_registration()), i, j);
                                        } else {
                                            myModel.setValueAt("Ver en Perfil", i, j);
                                        }
                                    }
                                }
                                break;
                        }
                    } else {
                        lastOne = true;
                    }
                    k++;
                }
                k = 0;
            }
        }
    }

    public static void removeSearchDataTable() {
        JTable tblSearch = UI.getSearchTable();

        DefaultTableModel myModel = (DefaultTableModel) tblSearch.getModel();
        int col = myModel.getColumnCount();
        int row = myModel.getRowCount();

        for (int i = 0; i < col - 1; i++) {
            for (int j = 0; j < row; j++) {
                myModel.setValueAt(" ", i, j);
            }
        }
    }

    public static int getNpages() {
        return nPages;
    }

    public static int getActualPage() {
        return nPage;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Suspect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.ImageIcon;

/**
 *
 * @author rafa0
 */
public class CreateAndFillTables {

    private static HashMap<Integer, Integer> mySuspects = new HashMap<>();

    public static void setMainTable(JTable tblMain) {
        DefaultTableModel modelo = (DefaultTableModel) tblMain.getModel();
        Image profile = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-usuario-de-genero-neutro-20.png"));
        Image trash = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-papelera-vacia-20.png"));

        JButton profileButton = new JButton(new ImageIcon(profile));
        JButton trashButton = new JButton(new ImageIcon(trash));

        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        trashButton.setContentAreaFilled(false);
        trashButton.setBorderPainted(false);

        Object[][] registros = new Object[modelo.getRowCount()][modelo.getColumnCount()];

        String[] head = {"Nombre", "Apellido 1", "Apellido 2", "Sospechosos",
            "Antecedentes", "Hechos", "Telefono", "E-mail", "Direcciones", "Matriculas",
            "Perfil", "Eliminar"
        };

        for (int i = 0; i < registros.length; i++) {
            for (int j = 0; j < registros[i].length - 2; j++) {
                registros[i][j] = "";
            }

            registros[i][registros[i].length - 2] = profileButton;
            registros[i][registros[i].length - 1] = trashButton;
        }

        final Class[] tiposColumnas = new Class[modelo.getColumnCount()];
        for (int i = 0; i < tiposColumnas.length - 2; i++) {
            tiposColumnas[i] = java.lang.String.class;
        }

        tiposColumnas[tiposColumnas.length - 2] = JButton.class;
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
        tblMain.setModel(model);
        TableColumnModel defModel = tblMain.getColumnModel();
        for (int i = 0; i < defModel.getColumnCount() - 2; i++) {
            defModel.getColumn(i).setMinWidth(150);

        }

        tblMain.setDefaultRenderer(JButton.class,
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

        tblMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblMain.rowAtPoint(e.getPoint());
                int columna = tblMain.columnAtPoint(e.getPoint());

                /**
                 * Preguntamos si hicimos clic sobre la celda que contiene el
                 * botón, si tuviéramos más de un botón por fila tendríamos que
                 * además preguntar por el contenido del botón o el nombre de la
                 * columna
                 */         
                if (columna==11) {
                    Controller.deleteSuspect(getValue(tblMain.getSelectedRow() + 1));
                    fillMainTable();

                } else if (columna==10) {
                    if (getValue(tblMain.getSelectedRow() + 1) != null) {
                        Suspect suspectToUpdate = Controller.getInstance().findSuspect(getValue(tblMain.getSelectedRow() + 1));

                        if (suspectToUpdate != null) {
                            UI myUI = UI.getInstance();
                            myUI.setSuspectBeenModified(suspectToUpdate);
                            myUI.setModifySuspectFields(suspectToUpdate);
                            myUI.hideLayouts();
                            myUI.showSuspectLayouts();
                            myUI.hiddePnlSearch();
                        }
                    }
                }
            }
        });

    }

    /**
     * Metodo que rellena la tabla principaln con los datos de los sospechosos,
     * y modifica los valores de Hashmap de esta clase cambiar el valor del
     * mismo a el codigo de los sospechosos.
     */
    public static void fillMainTable() {
        removeMainDataTable();
        Suspect[] s = Controller.getInstance().getSuspects();
        setHashMap(s);
        JTable tblMain = UI.getMainTable();

        DefaultTableModel myModel = (DefaultTableModel) tblMain.getModel();
        int col = myModel.getColumnCount();
        int row = myModel.getRowCount();
        int k = 0;

        for (int i = 0; i < col - 3; i++) {
            for (int j = 0; j < row; j++) {
                if (s[i] != null) {
                    switch (k) {
                        case 0:
                            myModel.setValueAt(s[i].getName(), i, j);
                            ;
                            break;
                        case 1:
                            myModel.setValueAt(s[i].getLastname1(), i, j);
                            ;
                            break;
                        case 2:
                            myModel.setValueAt(s[i].getLastname2(), i, j);
                            ;
                            break;
                        case 3:
                            if (s[i].getSuspect() != null) {
                                if (!s[i].getSuspect().isEmpty()) {
                                    myModel.setValueAt(s[i].getSuspect(), i, j);
                                } else {
                                    myModel.setValueAt(" ", i, j);
                                }
                            } else {
                                myModel.setValueAt(" ", i, j);
                            }
                            ;
                            break;
                        case 4:
                            if (!s[i].getRecord().isEmpty()) {
                                myModel.setValueAt(s[i].getRecord(), i, j);
                            }
                            ;
                            break;
                        case 5:
                            if (!s[i].getFacts().isEmpty()) {
                                myModel.setValueAt(s[i].getFacts(), i, j);
                            }
                            ;
                            break;
                        case 6:
                            if (s[i].getPhone() != null) {
                                if (!s[i].getPhone().isEmpty()) {
                                    if (s[i].getPhone().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListPhoneToString(s[i].getPhone()), i, j);
                                    }else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                }
                            } else {
                                myModel.setValueAt(" ", i, j);
                            }
                            ;
                            break;
                        case 7:
                            if (s[i].getEmail() != null) {
                                if (!s[i].getEmail().isEmpty()) {
                                    if (s[i].getEmail().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListEmailToString(s[i].getEmail()), i, j);
                                    }else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                }
                            }
                            ;
                            break;
                        case 8:
                            if (s[i].getAddress() != null) {
                                if (!s[i].getAddress().isEmpty()) {
                                    if (s[i].getAddress().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListAddressToString(s[i].getAddress()), i, j);
                                    } else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                }
                            }
                            ;
                            break;
                        case 9:
                            if (s[i].getCar_registration() != null) {
                                if (!s[i].getCar_registration().isEmpty()) {
                                    if (s[i].getAddress().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListCarRegToString(s[i].getCar_registration()), i, j);
                                    } else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                }
                            }
                            ;
                            break;
                    }
                }
                k++;
            }
            k = 0;
        }
    }

    public static void removeMainDataTable() {
        JTable tblMain = UI.getMainTable();

        DefaultTableModel myModel = (DefaultTableModel) tblMain.getModel();
        int col = myModel.getColumnCount();
        int row = myModel.getRowCount();

        for (int i = 0; i < col - 3; i++) {
            for (int j = 0; j < row; j++) {
                myModel.setValueAt(" ", i, j);
            }
        }
    }

    private static void setHashMap(Suspect[] mySuspects) {
        for (int i = 0; i < mySuspects.length; i++) {
            if (mySuspects[i] != null) {
                CreateAndFillTables.mySuspects.put(i + 1, mySuspects[i].getCodeSuspect());
            } else {
                CreateAndFillTables.mySuspects.put(i + 1, null);

            }
        }
        for (Suspect mySuspect : mySuspects) {
            if (mySuspect != null) {
                System.out.println(mySuspect.getCodeSuspect());
            }
        }
    }

    private static Integer getValue(Integer key) {
        Iterator<Entry<Integer, Integer>> it = mySuspects.entrySet().iterator();
        Integer value = null;

        while (it.hasNext()) {
            Entry<Integer, Integer> e = it.next();
            if (e.getKey() == key) {
                value = e.getValue();
            }
        }

        return value;
    }

}

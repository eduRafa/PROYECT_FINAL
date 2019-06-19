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
import view.UI;
import view.UiUtils;

/**
 * @author rafa0
 */
public final class CreateAndFillMainTable {

    private static HashMap<Integer, Integer> mySuspects = new HashMap<>();
    private static Integer lastCodeOfSuspectClicked;
    private static final int PROFILE_COLUMN = 10;
    private static final int TRASH_COLUMN = 11;
    private static final int SEARCH_COLUMN = 12;
    private static JButton profileButton;
    private static JButton trashButton;
    private static JButton searchSuspectButton;

    private static void setButtons() {
        Image profile = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-usuario-de-genero-neutro-20.png"));
        Image trash = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-papelera-vacia-20.png"));
        Image search = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-busqueda-20.png"));

        profileButton = new JButton(new ImageIcon(profile));
        trashButton = new JButton(new ImageIcon(trash));
        searchSuspectButton = new JButton(new ImageIcon(search));

        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        trashButton.setContentAreaFilled(false);
        trashButton.setBorderPainted(false);
        searchSuspectButton.setBorderPainted(false);
        searchSuspectButton.setContentAreaFilled(false);

        profileButton.setActionCommand("profileOfMainSuspect");
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Controller.getInstance().actionPerformed(evt);
            }
        });

        trashButton.setActionCommand("remove");
        trashButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Controller.getInstance().actionPerformed(evt);
            }
        });

        searchSuspectButton.setActionCommand("searchSpecificSuspect");
        searchSuspectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Controller.getInstance().actionPerformed(evt);
            }
        });
    }

    public static void setMainTable(JTable tblMain) {
        DefaultTableModel modelo = (DefaultTableModel) tblMain.getModel();
        setButtons();

        Object[][] registros = new Object[modelo.getRowCount()][modelo.getColumnCount()];

        String[] head = {"Nombre", "Apellido 1", "Apellido 2", "Sospechosos",
            "Antecedentes", "Hechos", "Telefono", "E-mail", "Direcciones", "Matriculas",
            "Perfil", "Eliminar", "Buscar"
        };

        for (int i = 0; i < registros.length; i++) {
            for (int j = 0; j < registros[i].length - 3; j++) {
                registros[i][j] = "";
            }

            registros[i][registros[i].length - 3] = profileButton;
            registros[i][registros[i].length - 2] = trashButton;
            registros[i][registros[i].length - 1] = searchSuspectButton;
        }

        final Class[] tiposColumnas = new Class[modelo.getColumnCount()];
        for (int i = 0; i < tiposColumnas.length - 3; i++) {
            tiposColumnas[i] = java.lang.String.class;
        }

        tiposColumnas[tiposColumnas.length - 3] = JButton.class;
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
        for (int i = 0; i < defModel.getColumnCount() - 3; i++) {
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
                lastCodeOfSuspectClicked = getValue(tblMain.getSelectedRow() + 1);

                /**
                 * Preguntamos si hicimos clic sobre la celda que contiene el
                 * botón, si tuviéramos más de un botón por fila tendríamos que
                 * además preguntar por el contenido del botón o el nombre de la
                 * columna
                 */
                if (lastCodeOfSuspectClicked != null) {
                    int columna = tblMain.columnAtPoint(e.getPoint());
                    switch (columna) {
                        case PROFILE_COLUMN:
                            profileButton.doClick();
                            break;
                        case TRASH_COLUMN:
                            trashButton.doClick();
                            break;
                        case SEARCH_COLUMN:
                            searchSuspectButton.doClick();
                            break;
                    }
                }
            }
        });

    }

    public static Integer getLastCodeOfSuspectClicked() {
        return lastCodeOfSuspectClicked;
    }

    /**
     * Metodo que rellena la tabla principal con los datos de los sospechosos, y
     * modifica los valores de Hashmap de esta clase cambiar el valor del mismo
     * a el codigo de los sospechosos.
     */
    public static void fillMainTable(Suspect[] s) {
        removeMainDataTable();

        if (s == null) {
            s = Controller.getInstance().getSuspects();
        }

        setHashMap(s);
        JTable tblMain = UI.getMainTable();

        DefaultTableModel myModel = (DefaultTableModel) tblMain.getModel();
        int col = myModel.getColumnCount();
        int row = myModel.getRowCount();
        int k = 0;
        boolean lastOne = false;

        for (int i = 0; i < col - 3 && !lastOne; i++) {
            for (int j = 0; j < row && !lastOne; j++) {
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
                            if (s[i].getCompanions() != null) {
                                if (!s[i].getCompanions().isEmpty()) {
                                    myModel.setValueAt(s[i].getCompanions(), i, j);
                                } else {
                                    myModel.setValueAt("", i, j);
                                }
                            } else {
                                myModel.setValueAt("", i, j);
                            }
                            ;
                            break;
                        case 4:
                            if (s[i].getRecord() != null) {
                                if (!s[i].getRecord().isEmpty()) {
                                    myModel.setValueAt(s[i].getRecord(), i, j);
                                }
                            } else {
                                myModel.setValueAt("", i, j);
                            }
                            ;
                            break;
                        case 5:
                            if (s[i].getFacts() != null) {
                                if (!s[i].getFacts().isEmpty()) {
                                    myModel.setValueAt(s[i].getFacts(), i, j);
                                }
                            } else {
                                myModel.setValueAt("", i, j);
                            }
                            ;
                            break;
                        case 6:
                            if (s[i].getPhone() != null) {
                                if (!s[i].getPhone().isEmpty()) {
                                    if (s[i].getPhone().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListPhoneToString(s[i].getPhone()), i, j);
                                    } else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                }
                            } else {
                                myModel.setValueAt("", i, j);
                            }
                            ;
                            break;
                        case 7:
                            if (s[i].getEmail() != null) {
                                if (!s[i].getEmail().isEmpty()) {
                                    if (s[i].getEmail().size() < 2) {
                                        myModel.setValueAt(UiUtils.transformArrayListEmailToString(s[i].getEmail()), i, j);
                                    } else {
                                        myModel.setValueAt("Ver en Perfil", i, j);
                                    }
                                } else {
                                    myModel.setValueAt("", i, j);
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
                            } else {
                                myModel.setValueAt("", i, j);
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
                } else {
                    lastOne = true;
                }
                k++;
            }
            k = 0;
        }
    }

    /**
     * Modifica el contenido a blanco en la tabla principal
     */
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

    /**
     * Metodo encargado de relacionar posicion de la fila de la tabla con el id
     * al que pertenece
     *
     * @param mySuspects Array de sospechosos a relacionar
     */
    private static void setHashMap(Suspect[] mySuspects) {
        CreateAndFillMainTable.mySuspects.clear();

        for (int i = 0; i < mySuspects.length; i++) {
            if (mySuspects[i] != null) {
                CreateAndFillMainTable.mySuspects.put(i + 1, mySuspects[i].getCodeSuspect());
            } else {
                CreateAndFillMainTable.mySuspects.put(i + 1, null);

            }
        }
    }

    /**
     * @param row fila de la tabla que ha sido clicada
     * @return Clave que representa al codigo del sospechoso
     */
    private static Integer getValue(Integer row) {
        Iterator<Entry<Integer, Integer>> it = mySuspects.entrySet().iterator();
        Integer value = null;

        while (it.hasNext()) {
            Entry<Integer, Integer> e = it.next();
            if (e.getKey().intValue() == row.intValue()) {
                value = e.getValue();
            }
        }

        return value;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author rafa0
 */
public class CreateDefaultTableModel {

    private static JButton myButton1;
    private static JButton myButton2;
    private static JButton myButton3;

    private static void setButtons() {
        Image trash = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-papelera-vacia-20.png"));
        myButton1 = new JButton(new ImageIcon(trash));
        myButton2 = new JButton(new ImageIcon(trash));
        myButton3 = new JButton(new ImageIcon(trash));
        
        myButton1.setContentAreaFilled(false);
        myButton2.setContentAreaFilled(false);
        myButton3.setContentAreaFilled(false);
        
        myButton1.setBorderPainted(false);
        myButton2.setBorderPainted(false);
        myButton3.setBorderPainted(false);
    }
    
    

    public static void setMainTable(JTable tblMain) {
        setButtons();

        DefaultTableModel modelo = (DefaultTableModel) tblMain.getModel();

        Object[][] registros = new Object[modelo.getRowCount()][modelo.getColumnCount()];
        
        String[] head={"Nombre", "Apellido 1", "Apellido 2", "Sospechosos",
        "Antecedentes", "Hechos","Telefono", "E-mail", "Direcciones", "Matriculas",
        "Fotos", "", ""
        };

        for (int i = 0; i < registros.length; i++) {
            for (int j = 0; j < registros[i].length - 3; j++) {
                registros[i][j] = "";
            }
            registros[i][registros[i].length - 3] = myButton3;
            registros[i][registros[i].length - 2] = myButton2;
            registros[i][registros[i].length - 1] = myButton1;
        }

        final Class[] tiposColumnas = new Class[modelo.getColumnCount()];
        for (int i = 0; i < tiposColumnas.length - 3; i++) {
            tiposColumnas[i] = java.lang.String.class;
        }
        for (int i = 1; i < 4; i++) {
            tiposColumnas[tiposColumnas.length - i] = JButton.class;
        }

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
                return !(this.getColumnClass(column).equals(JButton.class));
            }
        };

        tblMain.setModel(model);

        tblMain.setDefaultRenderer(JButton.class, new TableCellRenderer() {
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
                if (tblMain.getModel().getColumnClass(columna).equals(JButton.class)) {

                    /**
                     * Aquí pueden poner lo que quieran, para efectos de este
                     * ejemplo, voy a mostrar en un cuadro de dialogo todos los
                     * campos de la fila que no sean un botón.
                     */
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tblMain.getModel().getColumnCount(); i++) {
                        if (!tblMain.getModel().getColumnClass(i).equals(JButton.class)) {
                            sb.append("\n").append(tblMain.getModel().getColumnName(i)).append(": ").append(tblMain.getModel().getValueAt(fila, i));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila + sb.toString());
                }
            }
        });

    }
}

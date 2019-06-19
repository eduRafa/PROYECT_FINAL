/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.Dialogs.WarningDialog;
import view.Dialogs.TextDialog;
import view.Tables.CreateAndFillMainTable;
import view.imageManagers.ImageManager;
import controller.Controller;
import database.Query;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import model.Suspect;
import view.Tables.CreateAndFillSearchTable;
import view.imageManagers.AddSuspectImageManager;
import view.imageManagers.ModifySuspectImageManager;

/**
 *
 * @author rafa0
 */
public class UI extends javax.swing.JFrame {

    /**
     * Creates new form UI
     */
    private UI() {
        me = this;
        myController = Controller.getInstance();
        myController.setUi(me);
        PrintComponents.setColors();
        initComponents();
        myInitComponents();
    }

    public static UI getInstance() {
        if (me == null) {
            return new UI();
        } else {
            return me;
        }
    }

    public static void setController(Controller c) {
        myController = c;
    }

    @Override
    public Image getIconImage() {
        Image myImage = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-huella-dactilar-64.png"));
        return myImage;
    }

    /**
     * Metodo encargado de ocultar los paneles de la aplicación
     */
    public void hideLayouts() {
        pnlModifySuspect.setVisible(false);
        pnlConf.setVisible(false);
        pnlMain.setVisible(false);
        pnlSearch.setVisible(false);
        hideSearchLayouts();
        pnlAdd.setVisible(false);
    }

    /**
     * Metodo encargado de ocultar los paneles del menu Configuracion
     */
    private void hideConfLayouts() {
        pnlConfMain.setVisible(false);
        pnlConfTheme.setVisible(false);
        pnlConfStadistics.setVisible(false);
    }

    /**
     * Metodo encargado de ocultar los paneles del menu Busqueda
     */
    private void hideSearchLayouts() {
        pnlSearchSearch.setVisible(false);
        pnlSearchRelation.setVisible(false);
    }

    /**
     * Metodo encargado de mostrar los paneles del menu del sospechoso
     */
    public void showSuspectLayouts() {
        pnlSearch.setVisible(true);
        pnlModifySuspect.setVisible(true);
    }

    /**
     * Metodo encargado de ocultar el panel search
     */
    public void hiddePnlSearch() {
        pnlSearch.setVisible(false);
    }

    /**
     * Metodo encargado de mostrar el panel search
     */
    public void showPnlSearch() {
        pnlSearch.setVisible(true);
    }

    /**
     * Metodo encargado de mostrar el panel Main
     */
    public void showMainPanel() {
        pnlMain.setVisible(true);
    }

    public static JTable getMainTable() {
        return tblMain;
    }

    public static JTable getSearchTable() {
        return tblSearchRelation;
    }

    /**
     * Metodo encargado de coger los valores del sospechoso a añadir y
     * convertirlos en sospechoso.
     *
     * @return el sospechoso a añadir, los valores en los campos vacios se
     * representan como "" no como nulos.
     */
    public Suspect getAddSuspect() {
        Suspect mySuspect = null;
        String values[] = new String[10];
        boolean empty = true;

        for (int i = 0; i < addSuspectFields.length; i++) {
            if (addSuspectFields[i] != null) {
                if (addSuspectFields[i].getText().equals("")) {
                    values[i] = null;
                } else {
                    values[i] = addSuspectFields[i].getText();
                    values[i].trim();
                    empty = false;
                }
            } else {
                values[i] = null;
            }
        }

        if (!empty) {
            try {
                mySuspect = new Suspect(null, values[0], values[1], values[2], null/*companions*/,
                        values[4], values[5], UiUtils.transformStringToArrayList(values[6], null, "Phone"),
                        UiUtils.transformStringToArrayList(values[7], null, "Email"),
                        UiUtils.transformStringToArrayList(values[8], null, "Address"),
                        UiUtils.transformStringToArrayList(values[9], null, "Car_Registration"),
                        addSuspectImageManager.getPhotos());
            } catch (Exception ex) {
                new WarningDialog(me, true, ex.getMessage()).setVisible(true);
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return mySuspect;
    }

    /**
     * Metodo encargado de coger los valores del sospechoso a añadir y
     * convertirlos en sospechoso.
     *
     * @return el sospechoso a añadir, los valores en los campos vacios se
     * representan como "" no como nulos.
     */
    public HashMap<String, ArrayList<String>> getSearchSuspect() {
        String values[] = new String[10];

        for (int i = 0; i < searchSuspectFields.length; i++) {
            if (searchSuspectFields[i] != null) {
                if (searchSuspectFields[i].getText().equals("")) {
                    values[i] = null;
                } else {
                    values[i] = searchSuspectFields[i].getText();
                }
            } else {
                values[i] = null;
            }
        }

        HashMap<String, ArrayList<String>> fieldAndValues = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                switch (i) {
                    case 1:
                        fieldAndValues.put("LASTNAME1", new ArrayList<String>(Arrays.asList(values[1])));
                        break;
                    case 2:
                        fieldAndValues.put("LASTNAME2", new ArrayList<String>(Arrays.asList(values[2])));
                        break;
                    case 4:
                        fieldAndValues.put("RECORD", new ArrayList<String>(Arrays.asList(values[4])));
                        break;
                    case 5:
                        fieldAndValues.put("FACTS", new ArrayList<String>(Arrays.asList(values[5])));
                        break;
                    case 6:
                        fieldAndValues.put("PHONE", new ArrayList<String>(Arrays.asList(values[6])));
                        break;
                    case 7:
                        fieldAndValues.put("E_MAIL", new ArrayList<String>(Arrays.asList(values[7])));
                        break;
                    case 8:
                        fieldAndValues.put("ADDRESS", new ArrayList<String>(Arrays.asList(values[8])));
                        break;
                    case 9:
                        fieldAndValues.put("CAR_REGISTRATION", new ArrayList<String>(Arrays.asList(values[9])));
                        break;
                }
            }
        }

        return fieldAndValues;
    }

    public void setSearchFields(Suspect s) {
        if (s != null) {
            if (!s.isEmpty()) {
                if (searchSuspectFields[1] != null) {
                    searchSuspectFields[1].setText(s.getLastname1() != null ? s.getLastname1() : "");

                }
                if (searchSuspectFields[2] != null) {
                    searchSuspectFields[2].setText(s.getLastname2() != null ? s.getLastname2() : "");

                }

                if (searchSuspectFields[4] != null) {
                    searchSuspectFields[4].setText(s.getRecord() != null ? s.getRecord() : "");

                }
                if (searchSuspectFields[5] != null) {
                    searchSuspectFields[5].setText(s.getFacts() != null ? s.getFacts() : "");

                }
                if (searchSuspectFields[6] != null) {
                    searchSuspectFields[6].setText(s.getPhone() != null ? UiUtils.transformArrayListPhoneToString(s.getPhone()) : "");

                }
                if (searchSuspectFields[7] != null) {
                    searchSuspectFields[7].setText(s.getEmail() != null ? UiUtils.transformArrayListEmailToString(s.getEmail()) : "");

                }
                if (searchSuspectFields[8] != null) {
                    searchSuspectFields[8].setText(s.getAddress() != null ? UiUtils.transformArrayListAddressToString(s.getAddress()) : "");

                }
                if (searchSuspectFields[9] != null) {
                    searchSuspectFields[9].setText(s.getCar_registration() != null ? UiUtils.transformArrayListCarRegToString(s.getCar_registration()) : "");

                }

            }
        }
    }

    /**
     * Metodo encargado de coger los valores del sospechoso a añadir y
     * convertirlos en sospechoso.
     *
     * @return el sospechoso a añadir, los valores en los campos vacios se
     * representan como "" no como nulos.
     */
    public Suspect getModifySuspect() {
        boolean empty = true;
        String values[] = new String[10];

        for (int i = 0; i < modifySuspectFields.length; i++) {

            if (modifySuspectFields[i] != null) {
                if (modifySuspectFields[i].equals("")) {
                    values[i] = null;
                } else {
                    empty = false;
                    values[i] = modifySuspectFields[i].getText();
                }
            } else {
                values[i] = null;
            }
        }

        Suspect mySuspect = null;

        try {
            mySuspect = new Suspect(suspectBeenModified.getCodeSuspect(), values[0],
                    values[1], values[2], null/*companions*/, values[4], values[5],
                    UiUtils.transformStringToArrayList(values[6], suspectBeenModified.getCodeSuspect(), "Phone"),
                    UiUtils.transformStringToArrayList(values[7], suspectBeenModified.getCodeSuspect(), "Email"),
                    UiUtils.transformStringToArrayList(values[8], suspectBeenModified.getCodeSuspect(), "Address"),
                    UiUtils.transformStringToArrayList(values[9], suspectBeenModified.getCodeSuspect(), "Car_Registration"),
                    null);
        } catch (Exception ex) {
            new WarningDialog(me, true, ex.getMessage()).setVisible(true);
        }

        return mySuspect;
    }

    public Suspect getSupectBennModified() {
        return suspectBeenModified;
    }

    public void setSuspectBeenModified(Suspect s) {
        suspectBeenModified = s;
    }

    public AddSuspectImageManager getAddSuspectImageManager() {
        return addSuspectImageManager;
    }

    public ModifySuspectImageManager getModifySuspectImageManager() {
        return modifySuspectImageManager;
    }

    /**
     * Encargada de abrir un dialogo el para que el usuario escriba.
     *
     * @param evt accion de boton
     * @param x jTextArea el cual una vez cambiado el texto en el dialogo, se
     * setteara en el componente.
     */
    public void compCallingMouseClicked(java.awt.event.ActionEvent evt, JTextComponent x) {
        new TextDialog(this, true, x);
    }

    /**
     * Elimina los campos del panel AddSuspect
     */
    public void removeAddSuspectsFields() {
        for (JTextComponent suspectField : addSuspectFields) {
            if (suspectField != null) {
                suspectField.setText("");
            }
        }
    }

    /**
     * Elimina los campos del panel ModifySuspect
     */
    public void removeModifySuspectsFields() {
        for (JTextComponent suspectField : modifySuspectFields) {
            if (suspectField != null) {
                suspectField.setText("");
            }
        }
    }

    /**
     * Cambia los jTextComponent del panel pnlModifySuspect con los datos del
     * sospechoso.
     *
     * @param s Sospechoso a cambiar.
     */
    public void setModifySuspectFields(Suspect s) {
        for (int i = 0; i < modifySuspectFields.length; i++) {
            switch (i) {
                case 0:
                    if (s.getName() != null) {
                        modifySuspectFields[i].setText(s.getName());
                    } else {
                        modifySuspectFields[i].setText("");
                    }

                    break;
                case 1:
                    if (s.getLastname1() != null) {
                        modifySuspectFields[i].setText(s.getLastname1());
                    } else {
                        modifySuspectFields[i].setText("");
                    }

                    break;
                case 2:
                    if (s.getLastname2() != null) {
                        modifySuspectFields[i].setText(s.getLastname2());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 4:
                    if (s.getRecord() != null) {
                        modifySuspectFields[i].setText(s.getRecord().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 5:
                    if (s.getFacts() != null) {
                        modifySuspectFields[i].setText(s.getFacts().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 6:
                    if (s.getPhone() != null) {
                        modifySuspectFields[i].setText(UiUtils.transformArrayListPhoneToString(s.getPhone()));
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 7:
                    if (s.getEmail() != null) {
                        modifySuspectFields[i].setText(UiUtils.transformArrayListEmailToString(s.getEmail()));
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 8:
                    if (s.getAddress() != null) {
                        modifySuspectFields[i].setText(UiUtils.transformArrayListAddressToString(s.getAddress()));
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 9:
                    if (s.getCar_registration() != null) {
                        modifySuspectFields[i].setText(UiUtils.transformArrayListCarRegToString(s.getCar_registration()));
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
            }
        }

        jLabel51.setText(myController.getSuspectNPhotos(s.getCodeSuspect()) + "/" + ImageManager.NPHOTOS);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl1Background = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        pnlMenu2 = new javax.swing.JPanel();
        lblMenu22 = new javax.swing.JLabel();
        lblMenu2 = new javax.swing.JLabel();
        pnlMenu1 = new javax.swing.JPanel();
        lblMenu11 = new javax.swing.JLabel();
        lblMenu1 = new javax.swing.JLabel();
        pnlMenu3 = new javax.swing.JPanel();
        lblMenu33 = new javax.swing.JLabel();
        lblMenu3 = new javax.swing.JLabel();
        pnlMenu4 = new javax.swing.JPanel();
        lblMenu44 = new javax.swing.JLabel();
        lblMenu4 = new javax.swing.JLabel();
        pnlFormDecorated = new javax.swing.JPanel();
        pnlFormDecoratedCloseWindow = new javax.swing.JButton();
        pnlFormDecoratedMinimizeWindow = new javax.swing.JButton();
        layeredConfMain = new javax.swing.JLayeredPane();
        pnlModifySuspect = new javax.swing.JPanel();
        lblNameSuspectBeenModified = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextArea21 = new javax.swing.JTextArea();
        lblPhoneSuspectBeenModified = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTextArea22 = new javax.swing.JTextArea();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTextArea23 = new javax.swing.JTextArea();
        lblEmailSuspectBeenModified = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea24 = new javax.swing.JTextArea();
        lblSecondName1SuspectBeenModified = new javax.swing.JLabel();
        lblSecondName2SuspectBeenModified = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();
        jButton27 = new javax.swing.JButton();
        lblAddressSuspectBeenModified = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jScrollPane29 = new javax.swing.JScrollPane();
        jTextArea28 = new javax.swing.JTextArea();
        lblRecordSuspectBeenModified = new javax.swing.JLabel();
        jScrollPane30 = new javax.swing.JScrollPane();
        jTextArea29 = new javax.swing.JTextArea();
        lblCarRegistrationSuspectBeenModified = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane31 = new javax.swing.JScrollPane();
        jTextArea30 = new javax.swing.JTextArea();
        lbFactsSuspectBeenModified = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        pnlMenuSearch = new javax.swing.JPanel();
        jButton24 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlSearchSearch = new javax.swing.JPanel();
        lblAddressAddSuspect = new javax.swing.JLabel();
        bttnSearchSuspect = new javax.swing.JButton();
        lblEmailAddSuspect = new javax.swing.JLabel();
        lblCarRegistrationAddSuspect = new javax.swing.JLabel();
        lblNameSecondName1Suspect = new javax.swing.JLabel();
        lblSecondName2AddSuspect = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea11 = new javax.swing.JTextArea();
        lblPhoneAddSuspect = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea12 = new javax.swing.JTextArea();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextArea18 = new javax.swing.JTextArea();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextArea19 = new javax.swing.JTextArea();
        lblRecordAddSuspect = new javax.swing.JLabel();
        lblFactsAddSuspect = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea20 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        jButton23 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea14 = new javax.swing.JTextArea();
        jButton20 = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea15 = new javax.swing.JTextArea();
        pnlSearchRelation = new javax.swing.JPanel();
        jScrollPane33 = new javax.swing.JScrollPane();
        tblSearchRelation = new javax.swing.JTable();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        pnlConf = new javax.swing.JPanel();
        btnConfTheme = new javax.swing.JButton();
        btnConfStadistics = new javax.swing.JButton();
        layeredConf = new javax.swing.JLayeredPane();
        pnlConfMain = new javax.swing.JPanel();
        pnlConfMainThemes = new javax.swing.JPanel();
        lblConfMainThemesTitle = new javax.swing.JLabel();
        lblConfMainThemesDesc = new javax.swing.JLabel();
        lblConfMainThemesIcon = new javax.swing.JLabel();
        pnlConfMainStadistics = new javax.swing.JPanel();
        lbllConfMainStadisticsTitle = new javax.swing.JLabel();
        lbllConfMainStadisticsDesc = new javax.swing.JLabel();
        lbllConfMainStadisticsIcon = new javax.swing.JLabel();
        pnlConfStadistics = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        pnlConfTheme = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton4 = new javax.swing.JToggleButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jToggleButton3 = new javax.swing.JToggleButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jToggleButton5 = new javax.swing.JToggleButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlAdd = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        pnl1Background.setBackground(new java.awt.Color(255, 255, 255));
        pnl1Background.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        pnl1Background.setForeground(new java.awt.Color(255, 255, 255));

        pnlMenu.setBackground(PrintComponents.getPrimaryColor());
        pnlMenu.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));

        pnlMenu2.setBackground(PrintComponents.getPrimaryColor());
        pnlMenu2.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor(), 0));
        pnlMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu2MouseClicked(evt);
            }
        });

        lblMenu22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu22.setForeground(PrintComponents.getSecundaryColor());
        lblMenu22.setText("Búsqueda");

        lblMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-busqueda-30-$255,255,255$.png"))); // NOI18N

        javax.swing.GroupLayout pnlMenu2Layout = new javax.swing.GroupLayout(pnlMenu2);
        pnlMenu2.setLayout(pnlMenu2Layout);
        pnlMenu2Layout.setHorizontalGroup(
            pnlMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenu2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lblMenu22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenu2Layout.setVerticalGroup(
            pnlMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenu2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenu22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)))
        );

        lblMenu22.getAccessibleContext().setAccessibleName("0$-$0");
        lblMenu2.getAccessibleContext().setAccessibleName("-$-$-");
        lblMenu2.getAccessibleContext().setAccessibleDescription("icons8-busqueda-30-$255,255,255$.png");

        pnlMenu1.setBackground(PrintComponents.getPrimaryColor());
        pnlMenu1.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor(), 0));
        pnlMenu1.setPreferredSize(new java.awt.Dimension(192, 46));
        pnlMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu1MouseClicked(evt);
            }
        });

        lblMenu11.setBackground(PrintComponents.getSecundaryColor());
        lblMenu11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu11.setForeground(PrintComponents.getSecundaryColor());
        lblMenu11.setText("Menu Principal");

        lblMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-casa-30-$255,255,255$.png"))); // NOI18N

        javax.swing.GroupLayout pnlMenu1Layout = new javax.swing.GroupLayout(pnlMenu1);
        pnlMenu1.setLayout(pnlMenu1Layout);
        pnlMenu1Layout.setHorizontalGroup(
            pnlMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenu1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMenu11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        pnlMenu1Layout.setVerticalGroup(
            pnlMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenu1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenu11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
        );

        lblMenu11.getAccessibleContext().setAccessibleName("0$-$0");
        lblMenu1.getAccessibleContext().setAccessibleName("-$-$-");
        lblMenu1.getAccessibleContext().setAccessibleDescription("icons8-casa-30-$255,255,255$.png");

        pnlMenu3.setBackground(PrintComponents.getPrimaryColor()
        );
        pnlMenu3.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor(), 0));
        pnlMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu3MouseClicked(evt);
            }
        });

        lblMenu33.setBackground(new java.awt.Color(255, 255, 255));
        lblMenu33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu33.setForeground(PrintComponents.getSecundaryColor());
        lblMenu33.setText("Añadir Sospechoso");

        lblMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-anadir-usuario-masculino-30-$255,255,255$.png"))); // NOI18N

        javax.swing.GroupLayout pnlMenu3Layout = new javax.swing.GroupLayout(pnlMenu3);
        pnlMenu3.setLayout(pnlMenu3Layout);
        pnlMenu3Layout.setHorizontalGroup(
            pnlMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenu3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblMenu33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenu3Layout.setVerticalGroup(
            pnlMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenu3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenu33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMenu3, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)))
        );

        lblMenu33.getAccessibleContext().setAccessibleName("0$-$0");
        lblMenu3.getAccessibleContext().setAccessibleName("-$-$-");
        lblMenu3.getAccessibleContext().setAccessibleDescription("icons8-anadir-usuario-masculino-30-$255,255,255$.png");

        pnlMenu4.setBackground(PrintComponents.getPrimaryColor());
        pnlMenu4.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor(), 0));
        pnlMenu4.setPreferredSize(new java.awt.Dimension(192, 46));
        pnlMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu4MouseClicked(evt);
            }
        });

        lblMenu44.setBackground(PrintComponents.getSecundaryColor());
        lblMenu44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu44.setForeground(PrintComponents.getSecundaryColor());
        lblMenu44.setText("Configuración");

        lblMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-servicios-filled-30-$255,255,255$.png"))); // NOI18N

        javax.swing.GroupLayout pnlMenu4Layout = new javax.swing.GroupLayout(pnlMenu4);
        pnlMenu4.setLayout(pnlMenu4Layout);
        pnlMenu4Layout.setHorizontalGroup(
            pnlMenu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenu4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblMenu4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMenu44, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenu4Layout.setVerticalGroup(
            pnlMenu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenu4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlMenu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenu44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMenu4, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
        );

        lblMenu44.getAccessibleContext().setAccessibleName("0$-$0");
        lblMenu4.getAccessibleContext().setAccessibleName("-$-$-");
        lblMenu4.getAccessibleContext().setAccessibleDescription("icons8-servicios-filled-30-$255,255,255$.png");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
            .addComponent(pnlMenu3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMenu4, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
            .addComponent(pnlMenu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(pnlMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMenu4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu2.getAccessibleContext().setAccessibleName("1$-$-");
        pnlMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        pnlMenu1.getAccessibleContext().setAccessibleName("1$-$-");
        pnlMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        pnlMenu1.getAccessibleContext().setAccessibleDescription("");
        pnlMenu3.getAccessibleContext().setAccessibleName("1$-$-");
        pnlMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        pnlMenu4.getAccessibleContext().setAccessibleName("1$-$-");
        pnlMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);

            }
        });

        pnlFormDecorated.setBackground(new java.awt.Color(255, 255, 255));

        pnlFormDecoratedCloseWindow.setBackground(new java.awt.Color(161, 0, 0));
        pnlFormDecoratedCloseWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-eliminar-20-negro.png"))); // NOI18N
        pnlFormDecoratedCloseWindow.setBorderPainted(false);
        pnlFormDecoratedCloseWindow.setContentAreaFilled(false);
        pnlFormDecoratedCloseWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlFormDecoratedCloseWindowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlFormDecoratedCloseWindowMouseExited(evt);
            }
        });
        pnlFormDecoratedCloseWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnlFormDecoratedCloseWindowActionPerformed(evt);
            }
        });

        pnlFormDecoratedMinimizeWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-línea-horizontal-18.png"))); // NOI18N
        pnlFormDecoratedMinimizeWindow.setToolTipText(null);
        pnlFormDecoratedMinimizeWindow.setBorderPainted(false);
        pnlFormDecoratedMinimizeWindow.setContentAreaFilled(false);
        pnlFormDecoratedMinimizeWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnlFormDecoratedMinimizeWindowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFormDecoratedLayout = new javax.swing.GroupLayout(pnlFormDecorated);
        pnlFormDecorated.setLayout(pnlFormDecoratedLayout);
        pnlFormDecoratedLayout.setHorizontalGroup(
            pnlFormDecoratedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormDecoratedLayout.createSequentialGroup()
                .addGap(671, 671, 671)
                .addComponent(pnlFormDecoratedMinimizeWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlFormDecoratedCloseWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlFormDecoratedLayout.setVerticalGroup(
            pnlFormDecoratedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormDecoratedLayout.createSequentialGroup()
                .addGroup(pnlFormDecoratedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFormDecoratedCloseWindow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFormDecoratedMinimizeWindow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pnlFormDecoratedCloseWindow.setFocusable(false);
        pnlFormDecoratedMinimizeWindow.setFocusable(false);

        pnlModifySuspect.setBackground(new java.awt.Color(255, 255, 255));

        lblNameSuspectBeenModified.setText("Nombre");

        jScrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane22.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane22.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea21.setColumns(20);
        jTextArea21.setRows(5);
        jTextArea21.setBorder(null);
        jScrollPane22.setViewportView(jTextArea21);
        modifySuspectFields[0]=jTextArea21;
        jTextArea21.getAccessibleContext().setAccessibleName("$");
        jTextArea21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lblPhoneSuspectBeenModified.setText("Telefonos (varios)");

        jScrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane23.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane23.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea22.setColumns(20);
        jTextArea22.setRows(5);
        jTextArea22.setBorder(null);
        jScrollPane23.setViewportView(jTextArea22);
        modifySuspectFields[6]=jTextArea22;
        jTextArea22.getAccessibleContext().setAccessibleName("$");
        jTextArea22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton25.setBackground(PrintComponents.getPrimaryColor()    );
        jButton25.setForeground(PrintComponents.getSecundaryColor());
        jButton25.setText("...");
        jButton25.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()
        ));
        jButton25.setFocusable(false);

        jButton26.setBackground(PrintComponents.getPrimaryColor());
        jButton26.setForeground(PrintComponents.getSecundaryColor());
        jButton26.setText("...");
        jButton26.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton26.setFocusable(false);

        jScrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane24.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane24.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea23.setColumns(20);
        jTextArea23.setRows(5);
        jTextArea23.setBorder(null);
        jScrollPane24.setViewportView(jTextArea23);
        modifySuspectFields[7]=jTextArea23;
        jTextArea23.getAccessibleContext().setAccessibleName("$");
        jTextArea23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lblEmailSuspectBeenModified.setText("Email (varios)");

        jScrollPane25.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane25.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane25.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea24.setColumns(20);
        jTextArea24.setRows(5);
        jTextArea24.setBorder(null);
        jScrollPane25.setViewportView(jTextArea24);
        modifySuspectFields[1]=jTextArea24;
        jTextArea24.getAccessibleContext().setAccessibleName("$");
        jTextArea25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lblSecondName1SuspectBeenModified.setText("Apellido 1");

        lblSecondName2SuspectBeenModified.setText("Apellido 2");

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane26.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane26.setToolTipText("");
        jScrollPane26.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea25.setColumns(20);
        jTextArea25.setRows(5);
        jTextArea25.setBorder(null);
        jScrollPane26.setViewportView(jTextArea25);
        modifySuspectFields[2]=jTextArea25;
        jTextArea25.getAccessibleContext().setAccessibleName("$");
        jTextArea26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jScrollPane27.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane27.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane27.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea26.setColumns(20);
        jTextArea26.setRows(5);
        jTextArea26.setBorder(null);
        jScrollPane27.setViewportView(jTextArea26);
        modifySuspectFields[8]=jTextArea26;
        jTextArea26.getAccessibleContext().setAccessibleName("$");
        jTextArea26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton27.setBackground(PrintComponents.getPrimaryColor());
        jButton27.setForeground(PrintComponents.getSecundaryColor());
        jButton27.setText("...");
        jButton27.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton27.setFocusable(false);

        lblAddressSuspectBeenModified.setText("Direcciones (Varios)");

        jButton28.setBackground(PrintComponents.getPrimaryColor());
        jButton28.setForeground(PrintComponents.getSecundaryColor()    );
        jButton28.setText("...");
        jButton28.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton28.setFocusable(false);

        jScrollPane29.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane29.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane29.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea28.setColumns(20);
        jTextArea28.setRows(5);
        jTextArea28.setBorder(null);
        jScrollPane29.setViewportView(jTextArea28);
        modifySuspectFields[4]=jTextArea28;
        jTextArea28.getAccessibleContext().setAccessibleName("$");
        jTextArea28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lblRecordSuspectBeenModified.setText("Antecedentes");

        jScrollPane30.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane30.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane30.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea29.setColumns(20);
        jTextArea29.setRows(5);
        jTextArea29.setBorder(null);
        jScrollPane30.setViewportView(jTextArea29);
        modifySuspectFields[9]=jTextArea29;
        jTextArea29.getAccessibleContext().setAccessibleName("$");
        jTextArea29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lblCarRegistrationSuspectBeenModified.setText("Matriculas (Varios)");

        jLabel50.setText("Fotos");

        jLabel51.setText("0/5");

        jButton2.setBackground(PrintComponents.getPrimaryColor());
        jButton2.setForeground(PrintComponents.getSecundaryColor());
        jButton2.setText("Abrir gestor de imágenes");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane31.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane31.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane31.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea30.setColumns(20);
        jTextArea30.setRows(5);
        jTextArea30.setBorder(null);
        jScrollPane31.setViewportView(jTextArea30);
        modifySuspectFields[5]=jTextArea30;
        jTextArea30.getAccessibleContext().setAccessibleName("$");
        jTextArea30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        lbFactsSuspectBeenModified.setText("Hechos");

        jButton29.setBackground(PrintComponents.getPrimaryColor());
        jButton29.setForeground(PrintComponents.getSecundaryColor());
        jButton29.setText("...");
        jButton29.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton29.setFocusable(false);

        jButton30.setBackground(PrintComponents.getPrimaryColor());
        jButton30.setForeground(PrintComponents.getSecundaryColor());
        jButton30.setText("Modificar sospechoso");
        jButton30.setActionCommand("modify");
        jButton30.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton30.setFocusable(false);

        jButton31.setBackground(PrintComponents.getPrimaryColor());
        jButton31.setForeground(PrintComponents.getSecundaryColor());
        jButton31.setText("...");
        jButton31.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton31.setFocusable(false);

        javax.swing.GroupLayout pnlModifySuspectLayout = new javax.swing.GroupLayout(pnlModifySuspect);
        pnlModifySuspect.setLayout(pnlModifySuspectLayout);
        pnlModifySuspectLayout.setHorizontalGroup(
            pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNameSuspectBeenModified, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSecondName1SuspectBeenModified, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblSecondName2SuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbFactsSuspectBeenModified)
                            .addComponent(lblRecordSuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane29, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(162, 162, 162)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCarRegistrationSuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblPhoneSuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblEmailSuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAddressSuspectBeenModified)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        pnlModifySuspectLayout.setVerticalGroup(
            pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(lblNameSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblSecondName1SuspectBeenModified)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblSecondName2SuspectBeenModified)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblRecordSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(lbFactsSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifySuspectLayout.createSequentialGroup()
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(lblPhoneSuspectBeenModified)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlModifySuspectLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblEmailSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblAddressSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblCarRegistrationSuspectBeenModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(186, 186, 186)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea22);
            }
        });
        jButton25.getAccessibleContext().setAccessibleName("1$0$0");
        jButton25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea23);
            }
        });
        jButton26.getAccessibleContext().setAccessibleName("1$0$0");
        jButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jScrollPane24.getAccessibleContext().setAccessibleName("$");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea26);
            }
        });
        jButton27.getAccessibleContext().setAccessibleName("1$0$0");
        jButton27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea28);
            }
        });
        jButton28.getAccessibleContext().setAccessibleName("1$0$0");
        jButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton2.getAccessibleContext().setAccessibleName("1$0$0");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea30);
            }
        });
        jButton29.getAccessibleContext().setAccessibleName("1$0$0");
        jButton29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myController.actionPerformed(evt);
            }
        });
        jButton30.getAccessibleContext().setAccessibleName("1$0$0");
        jButton30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea29);
            }
        });
        jButton31.getAccessibleContext().setAccessibleName("1$0$0");
        jButton31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMain.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMain.setAutoscrolls(false);
        tblMain.setRowHeight(30);
        tblMain.setSelectionBackground(PrintComponents.getPrimaryColor());
        tblMain.getTableHeader().setResizingAllowed(false);
        tblMain.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(tblMain);
        tblMain.getTableHeader().setBackground(PrintComponents.getPrimaryColor());
        tblMain.getTableHeader().setForeground(PrintComponents.getSecundaryColor());
        tblMain.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        TableColumnModel tblMainColumnModel = tblMain.getColumnModel();

        for (int i = 0; i < tblMainColumnModel.getColumnCount(); i++) {
            tblMainColumnModel.getColumn(i).setMinWidth(200);
        }
        tblMain.getAccessibleContext().setAccessibleName("1$1$0");

        jButton4.setBackground(PrintComponents.getPrimaryColor());
        jButton4.setForeground(PrintComponents.getSecundaryColor());
        jButton4.setText("Siguiente");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton4.setFocusable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(PrintComponents.getPrimaryColor());
        jButton5.setForeground(PrintComponents.getSecundaryColor());
        jButton5.setText("Anterior");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton5.setFocusable(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addContainerGap())
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jButton4.getAccessibleContext().setAccessibleName("1$0$0");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton5.getAccessibleContext().setAccessibleName("1$0$0");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        pnlSearch.setBackground(new java.awt.Color(255, 255, 255));

        pnlMenuSearch.setBackground(new java.awt.Color(255, 255, 255));

        jButton24.setBackground(PrintComponents.getPrimaryColor());
        jButton24.setForeground(PrintComponents.getSecundaryColor());
        jButton24.setText("Relaciones");
        jButton24.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton24.setFocusable(false);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton7.setBackground(PrintComponents.getPrimaryColor());
        jButton7.setForeground(PrintComponents.getSecundaryColor());
        jButton7.setText("Búsqueda");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton7.setFocusable(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuSearchLayout = new javax.swing.GroupLayout(pnlMenuSearch);
        pnlMenuSearch.setLayout(pnlMenuSearchLayout);
        pnlMenuSearchLayout.setHorizontalGroup(
            pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenuSearchLayout.setVerticalGroup(
            pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton24.getAccessibleContext().setAccessibleName("1$0$0");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton7.getAccessibleContext().setAccessibleName("1$0$0");

        pnlSearchSearch.setBackground(new java.awt.Color(255, 255, 255));

        lblAddressAddSuspect.setText("Direcciones (Varios)");

        bttnSearchSuspect.setBackground(PrintComponents.getPrimaryColor());
        bttnSearchSuspect.setForeground(PrintComponents.getSecundaryColor());
        bttnSearchSuspect.setText("Buscar sospechoso");
        bttnSearchSuspect.setActionCommand("search");
        bttnSearchSuspect.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        bttnSearchSuspect.setFocusable(false);

        lblEmailAddSuspect.setText("Email (varios)");

        lblCarRegistrationAddSuspect.setText("Matriculas (Varios)");

        lblNameSecondName1Suspect.setText("Apellido 1");

        lblSecondName2AddSuspect.setText("Apellido 2");

        jScrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea11.setColumns(20);
        jTextArea11.setRows(5);
        jTextArea11.setBorder(null);
        jScrollPane12.setViewportView(jTextArea11);
        jTextArea11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[1]=jTextArea11;
        jTextArea11.getAccessibleContext().setAccessibleName("$");

        lblPhoneAddSuspect.setText("Telefonos (varios)");

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea12.setColumns(20);
        jTextArea12.setRows(5);
        jTextArea12.setBorder(null);
        jScrollPane13.setViewportView(jTextArea12);
        jTextArea12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[2]=jTextArea12;
        jTextArea12.getAccessibleContext().setAccessibleName("$");

        jScrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane19.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane19.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea18.setColumns(20);
        jTextArea18.setRows(5);
        jTextArea18.setBorder(null);
        jScrollPane19.setViewportView(jTextArea18);
        jTextArea18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[4]=jTextArea18;
        jTextArea18.getAccessibleContext().setAccessibleName("$");

        jButton21.setBackground(PrintComponents.getPrimaryColor());
        jButton21.setForeground(PrintComponents.getSecundaryColor());
        jButton21.setText("...");
        jButton21.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton21.setFocusable(false);

        jButton22.setBackground(PrintComponents.getPrimaryColor());
        jButton22.setForeground(PrintComponents.getSecundaryColor());
        jButton22.setText("...");
        jButton22.setActionCommand("");
        jButton22.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton22.setFocusable(false);

        jScrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane20.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane20.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea19.setColumns(20);
        jTextArea19.setRows(5);
        jTextArea19.setBorder(null);
        jScrollPane20.setViewportView(jTextArea19);
        jTextArea19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[6]=jTextArea19;
        jTextArea19.getAccessibleContext().setAccessibleName("$");

        lblRecordAddSuspect.setText("Antecedentes");

        lblFactsAddSuspect.setText("Hechos");

        jButton18.setBackground(PrintComponents.getPrimaryColor());
        jButton18.setForeground(PrintComponents.getSecundaryColor());
        jButton18.setText("...");
        jButton18.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton18.setFocusable(false);

        jScrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane21.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane21.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea20.setColumns(20);
        jTextArea20.setRows(5);
        jTextArea20.setBorder(null);
        jScrollPane21.setViewportView(jTextArea20);
        jTextArea20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[7]=jTextArea20;
        jTextArea20.getAccessibleContext().setAccessibleName("$");

        jScrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea13.setColumns(20);
        jTextArea13.setRows(5);
        jTextArea13.setBorder(null);
        jScrollPane14.setViewportView(jTextArea13);
        jTextArea13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[8]=jTextArea13;
        jTextArea13.getAccessibleContext().setAccessibleName("$");

        jButton23.setBackground(PrintComponents.getPrimaryColor());
        jButton23.setForeground(PrintComponents.getSecundaryColor());
        jButton23.setText("...");
        jButton23.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton23.setFocusable(false);

        jButton19.setBackground(PrintComponents.getPrimaryColor());
        jButton19.setForeground(PrintComponents.getSecundaryColor());
        jButton19.setText("...");
        jButton19.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton19.setFocusable(false);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea14.setColumns(20);
        jTextArea14.setRows(5);
        jTextArea14.setBorder(null);
        jScrollPane15.setViewportView(jTextArea14);
        jTextArea14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[5]=jTextArea14;
        jTextArea14.getAccessibleContext().setAccessibleName("$");

        jButton20.setBackground(PrintComponents.getPrimaryColor());
        jButton20.setForeground(PrintComponents.getSecundaryColor());
        jButton20.setText("...");
        jButton20.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton20.setFocusable(false);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea15.setColumns(20);
        jTextArea15.setRows(5);
        jTextArea15.setBorder(null);
        jScrollPane16.setViewportView(jTextArea15);
        jTextArea15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        searchSuspectFields[9]=jTextArea15;
        jTextArea15.getAccessibleContext().setAccessibleName("$");

        javax.swing.GroupLayout pnlSearchSearchLayout = new javax.swing.GroupLayout(pnlSearchSearch);
        pnlSearchSearch.setLayout(pnlSearchSearchLayout);
        pnlSearchSearchLayout.setHorizontalGroup(
            pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchSearchLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSecondName2AddSuspect)
                    .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRecordAddSuspect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNameSecondName1Suspect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFactsAddSuspect)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane14)
                    .addComponent(lblPhoneAddSuspect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEmailAddSuspect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAddressAddSuspect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCarRegistrationAddSuspect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane21)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
            .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                .addGap(311, 311, 311)
                .addComponent(bttnSearchSuspect, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        pnlSearchSearchLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane16, lblAddressAddSuspect, lblCarRegistrationAddSuspect, lblEmailAddSuspect, lblPhoneAddSuspect});

        pnlSearchSearchLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane13, jScrollPane15, lblFactsAddSuspect, lblNameSecondName1Suspect, lblRecordAddSuspect, lblSecondName2AddSuspect});

        pnlSearchSearchLayout.setVerticalGroup(
            pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                        .addComponent(lblPhoneAddSuspect, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton22))
                        .addGap(18, 18, 18)
                        .addComponent(lblEmailAddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton23)
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblAddressAddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18))
                        .addGap(18, 18, 18)
                        .addComponent(lblCarRegistrationAddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton20)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlSearchSearchLayout.createSequentialGroup()
                        .addComponent(lblNameSecondName1Suspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblSecondName2AddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblRecordAddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21))
                        .addGap(18, 18, 18)
                        .addComponent(lblFactsAddSuspect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19))))
                .addGap(242, 242, 242)
                .addComponent(bttnSearchSuspect, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bttnSearchSuspect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myController.actionPerformed(evt);
            }
        });

        bttnSearchSuspect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        bttnSearchSuspect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        bttnSearchSuspect.getAccessibleContext().setAccessibleName("1$0$0");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea18);
            }
        });
        jButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton21.getAccessibleContext().setAccessibleName("1$0$0");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea19);
            }
        });
        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton22.getAccessibleContext().setAccessibleName("1$0$0");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea13);
            }
        });

        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton18.getAccessibleContext().setAccessibleName("1$0$0");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea20);
            }
        });

        jButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton23.getAccessibleContext().setAccessibleName("1$0$0");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea14);
            }
        });

        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton19.getAccessibleContext().setAccessibleName("1$0$0");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea15);
            }
        });

        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton20.getAccessibleContext().setAccessibleName("1$0$0");

        pnlSearchRelation.setBackground(new java.awt.Color(255, 255, 255));

        tblSearchRelation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido 1", "Apellido 2", "Sospechosos", "Antecedentes", "Hechos", "Telefono", "E-mail", "Direcciones", "Matriculas", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSearchRelation.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblSearchRelation.setAutoscrolls(false);
        tblSearchRelation.setRowHeight(30);
        tblSearchRelation.setSelectionBackground(PrintComponents.getPrimaryColor());
        tblSearchRelation.getTableHeader().setResizingAllowed(false);
        tblSearchRelation.getTableHeader().setReorderingAllowed(false);
        jScrollPane33.setViewportView(tblSearchRelation);
        tblSearchRelation.getTableHeader().setBackground(PrintComponents.getPrimaryColor());
        tblSearchRelation.getTableHeader().setForeground(PrintComponents.getSecundaryColor());
        tblSearchRelation.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        TableColumnModel tblSearchRelationColumnModel = tblSearchRelation.getColumnModel();

        for (int i = 0; i < tblSearchRelationColumnModel.getColumnCount(); i++) {
            tblSearchRelationColumnModel.getColumn(i).setMinWidth(200);
        }
        tblSearchRelation.getAccessibleContext().setAccessibleName("1$1$0");

        jButton32.setBackground(PrintComponents.getPrimaryColor());
        jButton32.setForeground(PrintComponents.getSecundaryColor());
        jButton32.setText("Anterior");
        jButton32.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton32.setFocusable(false);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setBackground(PrintComponents.getPrimaryColor());
        jButton33.setForeground(PrintComponents.getSecundaryColor());
        jButton33.setText("Siguiente");
        jButton33.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton33.setFocusable(false);
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchRelationLayout = new javax.swing.GroupLayout(pnlSearchRelation);
        pnlSearchRelation.setLayout(pnlSearchRelationLayout);
        pnlSearchRelationLayout.setHorizontalGroup(
            pnlSearchRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchRelationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane33)
                .addContainerGap())
            .addGroup(pnlSearchRelationLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );
        pnlSearchRelationLayout.setVerticalGroup(
            pnlSearchRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchRelationLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane33, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(pnlSearchRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );

        jButton32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton32.getAccessibleContext().setAccessibleName("1$0$0");
        jButton33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton33.getAccessibleContext().setAccessibleName("1$0$0");

        jLayeredPane1.setLayer(pnlSearchSearch, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pnlSearchRelation, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSearchSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlSearchRelation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(pnlSearchSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlSearchRelation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenuSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane1)
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addComponent(pnlMenuSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1))
        );

        pnlConf.setBackground(new java.awt.Color(255, 255, 255));

        btnConfTheme.setBackground(PrintComponents.getPrimaryColor());
        btnConfTheme.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfTheme.setForeground(PrintComponents.getSecundaryColor());
        btnConfTheme.setText("Temas");
        btnConfTheme.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        btnConfTheme.setFocusable(false);
        btnConfTheme.setPreferredSize(new java.awt.Dimension(161, 25));
        btnConfTheme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfThemeMouseClicked(evt);
            }
        });
        btnConfTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfThemeActionPerformed(evt);
            }
        });
        btnConfTheme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        btnConfStadistics.setBackground(PrintComponents.getPrimaryColor());
        btnConfStadistics.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfStadistics.setForeground(PrintComponents.getSecundaryColor());
        btnConfStadistics.setText("Estadísticas");
        btnConfStadistics.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        btnConfStadistics.setFocusable(false);
        btnConfStadistics.setPreferredSize(new java.awt.Dimension(161, 25));
        btnConfStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfStadisticsMouseClicked(evt);
            }
        });
        btnConfStadistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfStadisticsActionPerformed(evt);
            }
        });

        pnlConfMain.setBackground(new java.awt.Color(255, 255, 255));

        pnlConfMainThemes.setBackground(PrintComponents.getSecundaryColor());
        pnlConfMainThemes.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        pnlConfMainThemes.setPreferredSize(new java.awt.Dimension(209, 412));
        pnlConfMainThemes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfMainThemesMouseClicked(evt);
            }
        });

        lblConfMainThemesTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblConfMainThemesTitle.setForeground(PrintComponents.getPrimaryColor());
        lblConfMainThemesTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfMainThemesTitle.setText("Temas");

        lblConfMainThemesDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConfMainThemesDesc.setForeground(PrintComponents.getPrimaryColor());
        lblConfMainThemesDesc.setText("<html>\n\t<p>Echa un vistazo a nuestros temas y cambia la apariencia visual de la aplicación</p>\n<html>");

        javax.swing.GroupLayout pnlConfMainThemesLayout = new javax.swing.GroupLayout(pnlConfMainThemes);
        pnlConfMainThemes.setLayout(pnlConfMainThemesLayout);
        pnlConfMainThemesLayout.setHorizontalGroup(
            pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainThemesLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfMainThemesIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfMainThemesTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainThemesLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(lblConfMainThemesDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        pnlConfMainThemesLayout.setVerticalGroup(
            pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainThemesLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lblConfMainThemesTitle)
                .addGap(18, 18, 18)
                .addComponent(lblConfMainThemesIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblConfMainThemesDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        lblConfMainThemesTitle.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainThemesDesc.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainThemesIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-64-$"+UiUtils.rgbFormatted(PrintComponents.getPrimaryColor())+"$.png"))); // NOI18N
        lblConfMainThemesIcon.getAccessibleContext().setAccessibleName("$$$");
        lblConfMainThemesIcon.getAccessibleContext().setAccessibleDescription("icons8-brocha-64-$"+UiUtils.rgbFormatted(PrintComponents.getPrimaryColor())+"$.png");

        pnlConfMainStadistics.setBackground(PrintComponents.getSecundaryColor());
        pnlConfMainStadistics.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        pnlConfMainStadistics.setPreferredSize(new java.awt.Dimension(209, 412));
        pnlConfMainStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfMainStadisticsMouseClicked(evt);
            }
        });

        lbllConfMainStadisticsTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbllConfMainStadisticsTitle.setForeground(PrintComponents.getPrimaryColor());
        lbllConfMainStadisticsTitle.setText("Estadísticas");

        lbllConfMainStadisticsDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbllConfMainStadisticsDesc.setForeground(PrintComponents.getPrimaryColor());
        lbllConfMainStadisticsDesc.setText("<html>\n<p>Visita estadísticas relacionadas con los sospechosos(visibilidad de los sospechosos, peligrosidad... etc) y con tu frecuencia en la app</p>\n<html>");

        javax.swing.GroupLayout pnlConfMainStadisticsLayout = new javax.swing.GroupLayout(pnlConfMainStadistics);
        pnlConfMainStadistics.setLayout(pnlConfMainStadisticsLayout);
        pnlConfMainStadisticsLayout.setHorizontalGroup(
            pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                .addGroup(pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lbllConfMainStadisticsDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                        .addGroup(pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(lbllConfMainStadisticsTitle))
                            .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(lbllConfMainStadisticsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 69, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlConfMainStadisticsLayout.setVerticalGroup(
            pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(lbllConfMainStadisticsTitle)
                .addGap(18, 18, 18)
                .addComponent(lbllConfMainStadisticsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbllConfMainStadisticsDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbllConfMainStadisticsTitle.getAccessibleContext().setAccessibleName("-$-$1");
        lbllConfMainStadisticsDesc.getAccessibleContext().setAccessibleName("-$-$1");
        lbllConfMainStadisticsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-gráfico-de-barras-64-$"+UiUtils.rgbFormatted(PrintComponents.getPrimaryColor())+"$.png"))); // NOI18N
        lbllConfMainStadisticsIcon.getAccessibleContext().setAccessibleName("$$$");
        lbllConfMainStadisticsIcon.getAccessibleContext().setAccessibleDescription("icons8-gráfico-de-barras-64-$"+UiUtils.rgbFormatted(PrintComponents.getPrimaryColor())+"$.png");

        javax.swing.GroupLayout pnlConfMainLayout = new javax.swing.GroupLayout(pnlConfMain);
        pnlConfMain.setLayout(pnlConfMainLayout);
        pnlConfMainLayout.setHorizontalGroup(
            pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(pnlConfMainThemes, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(pnlConfMainStadistics, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        pnlConfMainLayout.setVerticalGroup(
            pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlConfMainThemes, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addComponent(pnlConfMainStadistics, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)))
        );

        pnlConfMainThemes.getAccessibleContext().setAccessibleName("0$1$-");
        pnlConfMainThemes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        pnlConfMainStadistics.getAccessibleContext().setAccessibleName("0$1$-");
        pnlConfMainStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        pnlConfMainStadistics.getAccessibleContext().setAccessibleDescription("");

        pnlConfStadistics.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Visibilidad de los sospechosos:");

        jLabel12.setText("Sospechoso más escurridizo:");

        jLabel13.setText("Peligrosidad:");

        jSeparator1.setForeground(PrintComponents.getPrimaryColor());

        jSeparator2.setForeground(PrintComponents.getPrimaryColor());

        jLabel14.setText("Sospechosos");

        jLabel15.setText("App");

        jLabel16.setText("Visitas en el mes actual:");

        jLabel17.setText("Última insercción de sospechoso:");

        jLabel18.setText("Última visita:");

        jLabel19.setText("jLabel19");

        jLabel20.setText("jLabel20");

        jLabel21.setText("jLabel21");

        jLabel22.setText("jLabel22");

        jLabel23.setText("jLabel23");

        jLabel24.setText("jLabel24");

        javax.swing.GroupLayout pnlConfStadisticsLayout = new javax.swing.GroupLayout(pnlConfStadistics);
        pnlConfStadistics.setLayout(pnlConfStadisticsLayout);
        pnlConfStadisticsLayout.setHorizontalGroup(
            pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfStadisticsLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlConfStadisticsLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)))
                    .addGroup(pnlConfStadisticsLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                            .addComponent(jSeparator2)))
                    .addGroup(pnlConfStadisticsLayout.createSequentialGroup()
                        .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        pnlConfStadisticsLayout.setVerticalGroup(
            pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfStadisticsLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel21))
                .addGap(50, 50, 50)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(pnlConfStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jSeparator1.getAccessibleContext().setAccessibleName("-$-$1");
        jSeparator2.getAccessibleContext().setAccessibleName("-$-$1");

        pnlConfTheme.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getThemeColor(0)
        ));

        jPanel7.setBackground(PrintComponents.getThemeColor(0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );

        jToggleButton1.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30.png"))); // NOI18N
        jToggleButton1.setActionCommand("0");
        jToggleButton1.setBorderPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jToggleButton1.setFocusable(false);

        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintComponents.changeTheme(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getThemeColor(3)));

        jToggleButton4.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-lila.png"))); // NOI18N
        jToggleButton4.setActionCommand("3");
        jToggleButton4.setBorderPainted(false);
        jToggleButton4.setFocusable(false);

        jPanel10.setBackground(PrintComponents.getThemeColor(3));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintComponents.changeTheme(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getThemeColor(1)
        ));

        jToggleButton2.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-verde.png"))); // NOI18N
        jToggleButton2.setActionCommand("1");
        jToggleButton2.setBorderPainted(false);
        jToggleButton2.setFocusable(false);

        jPanel8.setBackground(PrintComponents.getThemeColor(1));
        jPanel8.setToolTipText("");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintComponents.changeTheme(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getThemeColor(2)));

        jToggleButton3.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-azul.png"))); // NOI18N
        jToggleButton3.setActionCommand("2");
        jToggleButton3.setBorderPainted(false);

        jPanel9.setBackground(PrintComponents.getThemeColor(2));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToggleButton3.setFocusable(false);
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintComponents.changeTheme(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getThemeColor(4)));

        jToggleButton5.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-rojo.png"))); // NOI18N
        jToggleButton5.setActionCommand("4");
        jToggleButton5.setBorderPainted(false);
        jToggleButton5.setFocusable(false);

        jPanel11.setBackground(PrintComponents.getThemeColor(4));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintComponents.changeTheme(evt);
            }
        });

        jLabel1.setText("Clique sobre alguno de los pinceles para cambiar el tema.");

        javax.swing.GroupLayout pnlConfThemeLayout = new javax.swing.GroupLayout(pnlConfTheme);
        pnlConfTheme.setLayout(pnlConfThemeLayout);
        pnlConfThemeLayout.setHorizontalGroup(
            pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfThemeLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfThemeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(239, 239, 239))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfThemeLayout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(163, 163, 163))
        );
        pnlConfThemeLayout.setVerticalGroup(
            pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfThemeLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        layeredConf.setLayer(pnlConfMain, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConf.setLayer(pnlConfStadistics, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConf.setLayer(pnlConfTheme, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layeredConfLayout = new javax.swing.GroupLayout(layeredConf);
        layeredConf.setLayout(layeredConfLayout);
        layeredConfLayout.setHorizontalGroup(
            layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlConfTheme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlConfMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlConfStadistics, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layeredConfLayout.setVerticalGroup(
            layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlConfTheme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfLayout.createSequentialGroup()
                    .addComponent(pnlConfMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layeredConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredConfLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlConfStadistics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlConfLayout = new javax.swing.GroupLayout(pnlConf);
        pnlConf.setLayout(pnlConfLayout);
        pnlConfLayout.setHorizontalGroup(
            pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredConf, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlConfLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(btnConfTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnConfStadistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlConfLayout.setVerticalGroup(
            pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfTheme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfStadistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(layeredConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnConfTheme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        btnConfTheme.setVisible(false);
        btnConfTheme.getAccessibleContext().setAccessibleName("1$0$0");
        btnConfStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        btnConfStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        btnConfStadistics.setVisible(false);
        btnConfStadistics.getAccessibleContext().setAccessibleName("1$0$0"); // NOI18N

        pnlAdd.setBackground(new java.awt.Color(255, 255, 255));

        jButton9.setBackground(PrintComponents.getPrimaryColor());
        jButton9.setForeground(PrintComponents.getSecundaryColor());
        jButton9.setText("Añadir sospechoso");
        jButton9.setActionCommand("add");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton9.setFocusable(false);

        jLabel4.setText("Nombre");

        jLabel5.setText("Apellido 1");

        jLabel6.setText("Apellido 2");

        jLabel8.setText("Telefonos (varios)");

        jLabel9.setText("Hechos");

        jLabel26.setText("Antecedentes");

        jLabel27.setText("Email (varios)");

        jLabel28.setText("Fotos");

        jButton1.setBackground(PrintComponents.getPrimaryColor());
        jButton1.setForeground(PrintComponents.getSecundaryColor());
        jButton1.setText("Abrir gestor de imágenes");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel30.setText("0/5");

        jLabel31.setText("Direcciones (Varios)");

        jLabel32.setText("Matriculas (Varios)");

        jButton11.setBackground(PrintComponents.getPrimaryColor());
        jButton11.setForeground(PrintComponents.getSecundaryColor());
        jButton11.setText("...");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton11.setFocusable(false);

        jButton12.setBackground(PrintComponents.getPrimaryColor()
        );
        jButton12.setForeground(PrintComponents.getSecundaryColor());
        jButton12.setText("...");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton12.setFocusable(false);

        jButton13.setBackground(PrintComponents.getPrimaryColor());
        jButton13.setForeground(PrintComponents.getSecundaryColor());
        jButton13.setText("...");
        jButton13.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton13.setFocusable(false);

        jButton14.setBackground(PrintComponents.getPrimaryColor());
        jButton14.setForeground(PrintComponents.getSecundaryColor());
        jButton14.setText("...");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton14.setFocusable(false);

        jButton15.setBackground(PrintComponents.getPrimaryColor());
        jButton15.setForeground(PrintComponents.getSecundaryColor());
        jButton15.setText("...");
        jButton15.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()));
        jButton15.setFocusable(false);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jTextArea1.setMinimumSize(new java.awt.Dimension(211, 23));
        jTextArea1.setPreferredSize(new java.awt.Dimension(211, 23));
        jScrollPane1.setViewportView(jTextArea1);
        addSuspectFields[0]=jTextArea1;
        jTextArea1.getAccessibleContext().setAccessibleName("$");
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(null);
        jScrollPane2.setViewportView(jTextArea2);
        addSuspectFields[1]=jTextArea2;

        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea2.getAccessibleContext().setAccessibleName("$");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setBorder(null);
        jScrollPane3.setViewportView(jTextArea3);
        jTextArea3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea3.getAccessibleContext().setAccessibleName("$");
        addSuspectFields[4]=jTextArea3;

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setToolTipText("");
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setBorder(null);
        jScrollPane4.setViewportView(jTextArea4);
        addSuspectFields[2]=jTextArea4;

        jTextArea4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea4.getAccessibleContext().setAccessibleName("$");

        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jTextArea6.setBorder(null);
        jScrollPane6.setViewportView(jTextArea6);
        addSuspectFields[5]=jTextArea6;

        jTextArea6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea6.getAccessibleContext().setAccessibleName("$");

        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea7.setColumns(20);
        jTextArea7.setRows(5);
        jTextArea7.setBorder(null);
        jScrollPane7.setViewportView(jTextArea7);
        addSuspectFields[7]=jTextArea7;

        jTextArea7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea7.getAccessibleContext().setAccessibleName("$");

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea8.setColumns(20);
        jTextArea8.setRows(5);
        jTextArea8.setBorder(null);
        jScrollPane8.setViewportView(jTextArea8);
        addSuspectFields[6]=jTextArea8;

        jTextArea8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea8.getAccessibleContext().setAccessibleName("$");

        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea9.setColumns(20);
        jTextArea9.setRows(5);
        jTextArea9.setBorder(null);
        jScrollPane9.setViewportView(jTextArea9);
        addSuspectFields[9]=jTextArea9;

        jTextArea9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea9.getAccessibleContext().setAccessibleName("$");

        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getPrimaryColor()));
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea10.setColumns(20);
        jTextArea10.setRows(5);
        jTextArea10.setBorder(null);
        jScrollPane10.setViewportView(jTextArea10);
        addSuspectFields[8]=jTextArea10;

        jTextArea10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jTextArea10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jTextArea10.getAccessibleContext().setAccessibleName("$");

        jButton16.setBackground(PrintComponents.getPrimaryColor());
        jButton16.setForeground(PrintComponents.getSecundaryColor()

        );
        jButton16.setText("...");
        jButton16.setBorder(javax.swing.BorderFactory.createLineBorder(PrintComponents.getSecundaryColor()
        ));
        jButton16.setFocusable(false);

        jLabel2.setText("<html> <p>* No se podrá insertar a un sospechoso que solo</br> contenga imágenes, debe de tener algún otro campo relleno.</html>");

        javax.swing.GroupLayout pnlAddLayout = new javax.swing.GroupLayout(pnlAdd);
        pnlAdd.setLayout(pnlAddLayout);
        pnlAddLayout.setHorizontalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(311, 311, 311)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addGap(278, 278, 278))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel26)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel32)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        pnlAddLayout.setVerticalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12)))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jButton13)
                                .addGap(44, 44, 44)
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton14))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton15))
                                .addGap(18, 18, 18)
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel31)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jButton11)
                                .addGap(0, 6, Short.MAX_VALUE)))
                        .addGap(181, 181, 181)))
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton9.getAccessibleContext().setAccessibleName("1$0$0");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myController.actionPerformed(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton1.getAccessibleContext().setAccessibleName("1$0$0");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea6);
            }
        });
        jButton11.getAccessibleContext().setAccessibleName("1$0$0");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea8);
            }
        });
        jButton12.getAccessibleContext().setAccessibleName("1$0$0");
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea7);
            }
        });
        jButton13.getAccessibleContext().setAccessibleName("1$0$0");
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea10);
            }
        });
        jButton14.getAccessibleContext().setAccessibleName("1$0$0");
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea9);
            }
        });
        jButton15.getAccessibleContext().setAccessibleName("1$0$0");
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jScrollPane1.getAccessibleContext().setAccessibleName("");
        jScrollPane2.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane3.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane4.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane6.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane7.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane8.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane9.getAccessibleContext().setAccessibleName("-$-$-");
        jScrollPane10.getAccessibleContext().setAccessibleName("-$-$-");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea3);
            }
        });
        jButton16.getAccessibleContext().setAccessibleName("1$0$0");
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        layeredConfMain.setLayer(pnlModifySuspect, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConfMain.setLayer(pnlMain, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConfMain.setLayer(pnlSearch, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConfMain.setLayer(pnlConf, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConfMain.setLayer(pnlAdd, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layeredConfMainLayout = new javax.swing.GroupLayout(layeredConfMain);
        layeredConfMain.setLayout(layeredConfMainLayout);
        layeredConfMainLayout.setHorizontalGroup(
            layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlConf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlModifySuspect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layeredConfMainLayout.setVerticalGroup(
            layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfMainLayout.createSequentialGroup()
                    .addComponent(pnlConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 99, Short.MAX_VALUE)))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredConfMainLayout.createSequentialGroup()
                    .addGap(0, 56, Short.MAX_VALUE)
                    .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredConfMainLayout.createSequentialGroup()
                    .addGap(0, 56, Short.MAX_VALUE)
                    .addComponent(pnlModifySuspect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlModifySuspect.getAccessibleContext().setAccessibleName("-$-$-");

        javax.swing.GroupLayout pnl1BackgroundLayout = new javax.swing.GroupLayout(pnl1Background);
        pnl1Background.setLayout(pnl1BackgroundLayout);
        pnl1BackgroundLayout.setHorizontalGroup(
            pnl1BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1BackgroundLayout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl1BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFormDecorated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(layeredConfMain)))
        );
        pnl1BackgroundLayout.setVerticalGroup(
            pnl1BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl1BackgroundLayout.createSequentialGroup()
                .addComponent(pnlFormDecorated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layeredConfMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlMenu.getAccessibleContext().setAccessibleName("1$1$-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl1Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl1Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl1Background.getAccessibleContext().setAccessibleName("-$1$-");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void myInitComponents() {
        CreateAndFillMainTable.setMainTable(tblMain);
        CreateAndFillMainTable.fillMainTable(null);
        CreateAndFillSearchTable.setSearchRelationTable(tblSearchRelation);
        modifySuspectImageManager = new ModifySuspectImageManager(me, true);
        addSuspectImageManager = new AddSuspectImageManager(me, true);
        setLocationRelativeTo(null);
        hideLayouts();
        pnlMain.setVisible(true);
    }

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMousePosition = evt.getX();
        yMousePosition = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - xMousePosition, point.y - yMousePosition);
    }//GEN-LAST:event_formMouseDragged

    private void pnlConfMainThemesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfMainThemesMouseClicked
        pnlConfTheme.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        pnlConfMain.setVisible(false);
    }//GEN-LAST:event_pnlConfMainThemesMouseClicked

    private void btnConfThemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfThemeMouseClicked
        hideConfLayouts();
        pnlConfTheme.setVisible(true);
    }//GEN-LAST:event_btnConfThemeMouseClicked

    private void pnlFormDecoratedMinimizeWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnlFormDecoratedMinimizeWindowActionPerformed
        setExtendedState(ICONIFIED);
    }//GEN-LAST:event_pnlFormDecoratedMinimizeWindowActionPerformed

    private void pnlFormDecoratedCloseWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnlFormDecoratedCloseWindowActionPerformed
        System.exit(0);
    }//GEN-LAST:event_pnlFormDecoratedCloseWindowActionPerformed

    private void pnlFormDecoratedCloseWindowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFormDecoratedCloseWindowMouseExited
        pnlFormDecoratedCloseWindow.setContentAreaFilled(false);
        pnlFormDecoratedCloseWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-eliminar-20-negro.png")));
    }//GEN-LAST:event_pnlFormDecoratedCloseWindowMouseExited

    private void pnlFormDecoratedCloseWindowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFormDecoratedCloseWindowMouseEntered
        pnlFormDecoratedCloseWindow.setContentAreaFilled(true);
        pnlFormDecoratedCloseWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-eliminar-20-blanco.png")));
    }//GEN-LAST:event_pnlFormDecoratedCloseWindowMouseEntered

    private void pnlMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenu4MouseClicked
        hideLayouts();
        hideConfLayouts();
        btnConfTheme.setVisible(false);
        btnConfStadistics.setVisible(false);
        pnlConf.setVisible(true);
        pnlConfMain.setVisible(true);
        pnlMain.setVisible(false);
    }//GEN-LAST:event_pnlMenu4MouseClicked

    private void pnlMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenu1MouseClicked
        hideLayouts();
        pnlConf.setVisible(false);
        pnlMain.setVisible(true);

    }//GEN-LAST:event_pnlMenu1MouseClicked

    private void pnlMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenu2MouseClicked
        hideLayouts();
        pnlSearch.setVisible(true);
        pnlMenuSearch.setVisible(true);
        pnlSearchSearch.setVisible(true);
    }//GEN-LAST:event_pnlMenu2MouseClicked

    private void pnlConfMainStadisticsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfMainStadisticsMouseClicked
        /*hideConfLayouts();
        pnlConfStadistics.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);*/
    }//GEN-LAST:event_pnlConfMainStadisticsMouseClicked

    private void btnConfStadisticsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfStadisticsMouseClicked
        /*hideConfLayouts();
        pnlConfStadistics.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);*/
    }//GEN-LAST:event_btnConfStadisticsMouseClicked

    private void pnlMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenu3MouseClicked
        hideLayouts();
        pnlAdd.setVisible(true);
    }//GEN-LAST:event_pnlMenu3MouseClicked

    private void btnConfThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfThemeActionPerformed

    }//GEN-LAST:event_btnConfThemeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addSuspectImageManager.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        modifySuspectImageManager.setImages(suspectBeenModified.getCodeSuspect());
        modifySuspectImageManager.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        CreateAndFillMainTable.fillMainTable(myController.getNextTen());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (Query.getCurrentPosition() != 0) {
            CreateAndFillMainTable.fillMainTable(myController.getPreviousTen());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        CreateAndFillSearchTable.nextPage();
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        CreateAndFillSearchTable.previousPage();
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        pnlSearchRelation.setVisible(false);
        pnlSearchSearch.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        pnlSearchRelation.setVisible(true);
        pnlSearchSearch.setVisible(false);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void btnConfStadisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfStadisticsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfStadisticsActionPerformed

    public static void start() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UI.getInstance().setVisible(true);

                } catch (Exception ex) {
                    Logger.getLogger(UI.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfStadistics;
    private javax.swing.JButton btnConfTheme;
    private javax.swing.JButton bttnSearchSuspect;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    public javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    public javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea11;
    private javax.swing.JTextArea jTextArea12;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea14;
    private javax.swing.JTextArea jTextArea15;
    private javax.swing.JTextArea jTextArea18;
    private javax.swing.JTextArea jTextArea19;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea20;
    private javax.swing.JTextArea jTextArea21;
    private javax.swing.JTextArea jTextArea22;
    private javax.swing.JTextArea jTextArea23;
    private javax.swing.JTextArea jTextArea24;
    private javax.swing.JTextArea jTextArea25;
    private javax.swing.JTextArea jTextArea26;
    private javax.swing.JTextArea jTextArea28;
    private javax.swing.JTextArea jTextArea29;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea30;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JLayeredPane layeredConf;
    private javax.swing.JLayeredPane layeredConfMain;
    private javax.swing.JLabel lbFactsSuspectBeenModified;
    private javax.swing.JLabel lblAddressAddSuspect;
    private javax.swing.JLabel lblAddressSuspectBeenModified;
    private javax.swing.JLabel lblCarRegistrationAddSuspect;
    private javax.swing.JLabel lblCarRegistrationSuspectBeenModified;
    private javax.swing.JLabel lblConfMainThemesDesc;
    private javax.swing.JLabel lblConfMainThemesIcon;
    private javax.swing.JLabel lblConfMainThemesTitle;
    private javax.swing.JLabel lblEmailAddSuspect;
    private javax.swing.JLabel lblEmailSuspectBeenModified;
    private javax.swing.JLabel lblFactsAddSuspect;
    private javax.swing.JLabel lblMenu1;
    private javax.swing.JLabel lblMenu11;
    private javax.swing.JLabel lblMenu2;
    private javax.swing.JLabel lblMenu22;
    private javax.swing.JLabel lblMenu3;
    private javax.swing.JLabel lblMenu33;
    private javax.swing.JLabel lblMenu4;
    private javax.swing.JLabel lblMenu44;
    private javax.swing.JLabel lblNameSecondName1Suspect;
    private javax.swing.JLabel lblNameSuspectBeenModified;
    private javax.swing.JLabel lblPhoneAddSuspect;
    private javax.swing.JLabel lblPhoneSuspectBeenModified;
    private javax.swing.JLabel lblRecordAddSuspect;
    private javax.swing.JLabel lblRecordSuspectBeenModified;
    private javax.swing.JLabel lblSecondName1SuspectBeenModified;
    private javax.swing.JLabel lblSecondName2AddSuspect;
    private javax.swing.JLabel lblSecondName2SuspectBeenModified;
    private javax.swing.JLabel lbllConfMainStadisticsDesc;
    private javax.swing.JLabel lbllConfMainStadisticsIcon;
    private javax.swing.JLabel lbllConfMainStadisticsTitle;
    private javax.swing.JPanel pnl1Background;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlConf;
    private javax.swing.JPanel pnlConfMain;
    private javax.swing.JPanel pnlConfMainStadistics;
    private javax.swing.JPanel pnlConfMainThemes;
    private javax.swing.JPanel pnlConfStadistics;
    private javax.swing.JPanel pnlConfTheme;
    private javax.swing.JPanel pnlFormDecorated;
    private javax.swing.JButton pnlFormDecoratedCloseWindow;
    private javax.swing.JButton pnlFormDecoratedMinimizeWindow;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenu1;
    private javax.swing.JPanel pnlMenu2;
    private javax.swing.JPanel pnlMenu3;
    private javax.swing.JPanel pnlMenu4;
    private javax.swing.JPanel pnlMenuSearch;
    private javax.swing.JPanel pnlModifySuspect;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlSearchRelation;
    public javax.swing.JPanel pnlSearchSearch;
    private static javax.swing.JTable tblMain;
    private static javax.swing.JTable tblSearchRelation;
    // End of variables declaration//GEN-END:variables
    private int xMousePosition;
    private int yMousePosition;
    private static Controller myController;
    private static UI me;
    private static AddSuspectImageManager addSuspectImageManager;
    private static ModifySuspectImageManager modifySuspectImageManager;
    private static JTextComponent[] addSuspectFields = new JTextComponent[10];
    private JTextComponent[] modifySuspectFields = new JTextComponent[10];
    private JTextComponent[] searchSuspectFields = new JTextComponent[10];
    private Suspect suspectBeenModified;
}

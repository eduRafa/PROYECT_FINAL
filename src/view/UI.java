/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import model.Communication;
import model.Images;
import model.Suspect;

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
        primaryColor = myController.getPrimaryColor();
        oldColor = primaryColor;
        setThemeColors();
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

    public void hideLayouts() {
        pnlModifySuspect.setVisible(false);
        pnlConf.setVisible(false);
        pnlMain.setVisible(false);
        pnlSearch.setVisible(false);
        pnlAdd.setVisible(false);
        this.getAccessibleContext().getAccessibleName();
    }

    private void hideConfLayouts() {
        pnlConfMain.setVisible(false);
        pnlConfTheme.setVisible(false);
        pnlConfSuspectView.setVisible(false);
        pnlConfStadistics.setVisible(false);
    }

    public void showSuspectLayouts() {
        pnlSearch.setVisible(true);
        pnlModifySuspect.setVisible(true);
    }

    public void hiddePnlSearch() {
        pnlSearch.setVisible(false);
    }

    public void showPnlSearch() {
        pnlSearch.setVisible(true);
    }

    private void setThemeColors() {
        themeColor = new Color[6];
        themeColor[0] = new Color(255, 190, 113);
        themeColor[1] = new Color(133, 234, 130);
        themeColor[2] = new Color(158, 180, 230);
        themeColor[3] = new Color(222, 169, 218);
        themeColor[4] = new Color(225, 157, 156);
        themeColor[5] = new Color(248, 239, 92);
    }

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static Color getSecundaryColor() {
        return secundaryColor;
    }

    public static JTable getMainTable() {
        return tblMain;
    }

    public Suspect getAddSuspect() throws SQLException {
        String values[] = new String[10];

        for (int i = 0; i < addSuspectFields.length; i++) {

            if (addSuspectFields[i] != null) {
                values[i] = addSuspectFields[i].getText();
            } else {
                values[i] = null;
            }
        }

        Suspect mySuspect = new Suspect(null, values[0].trim(), values[1].trim(), values[2].trim(), null/*companions*/,
                new SerialBlob(values[4].getBytes()), new SerialBlob(values[5].getBytes()),
                UiUtils.transformStringToArrayList(values[6]), UiUtils.transformStringToArrayList(values[7]),
                UiUtils.transformStringToArrayList(values[8]), UiUtils.transformStringToArrayList(values[9]),
                addSuspectImageManager.getPhotos());
        return mySuspect;
    }

    public Suspect getModifySuspect() throws SQLException {
        String values[] = new String[10];

        for (int i = 0; i < modifySuspectFields.length; i++) {

            if (modifySuspectFields[i] != null) {
                values[i] = modifySuspectFields[i].getText();
            } else {
                values[i] = null;
            }
        }

        Suspect mySuspect = new Suspect(suspectToUpdate.getCodeSuspect(), values[0].trim(), values[1].trim(), values[2].trim(), null/*companions*/,
                new SerialBlob(values[4].getBytes()), new SerialBlob(values[5].getBytes()),
                UiUtils.transformStringToArrayList(values[6]), UiUtils.transformStringToArrayList(values[7]),
                UiUtils.transformStringToArrayList(values[8]), UiUtils.transformStringToArrayList(values[9]),
                modifySuspectImageManager.getPhotos());
        return mySuspect;
    }

    public ImageManager getAddSuspectImageManager() {
        return addSuspectImageManager;
    }

    public static ImageManager getModifySuspectImageManager() {
        return modifySuspectImageManager;
    }

    public void setPhotos(Images[] newPhotos) {
        photos = newPhotos;
    }

    public Images[] getPhotos() {
        return photos;
    }

    public void compCallingMouseClicked(java.awt.event.ActionEvent evt, JTextComponent x) {
        new TextDialog(this, true, x);
    }

    public void removeAddSuspectsFields() {
        for (JTextComponent suspectField : addSuspectFields) {
            if (suspectField != null) {
                suspectField.setText("");
            }
        }
    }

    public void removeModifySuspectsFields() {
        for (JTextComponent suspectField : modifySuspectFields) {
            if (suspectField != null) {
                suspectField.setText("");
            }
        }
    }

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
                case 3:
                    if (s.getSuspect() != null) {
                        modifySuspectFields[i].setText(s.getSuspect().toString());
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
                        modifySuspectFields[i].setText(s.getPhone().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 7:
                    if (s.getEmail() != null) {
                        modifySuspectFields[i].setText(s.getEmail().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 8:
                    if (s.getAddress() != null) {
                        modifySuspectFields[i].setText(s.getAddress().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
                case 9:
                    if (s.getCar_Resgistration() != null) {
                        modifySuspectFields[i].setText(s.getCar_Resgistration().toString());
                    } else {
                        modifySuspectFields[i].setText("");
                    }
                    break;
            }
        }
    }
    
    public void setSuspectToUpdate(Suspect s){
        suspectToUpdate=s;
    }

    public JPanel getPanelModifySuspect() {
        return pnlModifySuspect;
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
        jLabel25 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextArea21 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTextArea22 = new javax.swing.JTextArea();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTextArea23 = new javax.swing.JTextArea();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea24 = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();
        jButton27 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTextArea27 = new javax.swing.JTextArea();
        jLabel47 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jScrollPane29 = new javax.swing.JScrollPane();
        jTextArea28 = new javax.swing.JTextArea();
        jLabel48 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jScrollPane30 = new javax.swing.JScrollPane();
        jTextArea29 = new javax.swing.JTextArea();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane31 = new javax.swing.JScrollPane();
        jTextArea30 = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        pnlSearch = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea11 = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea12 = new javax.swing.JTextArea();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        jButton19 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea14 = new javax.swing.JTextArea();
        jButton20 = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea15 = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextArea16 = new javax.swing.JTextArea();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTextArea17 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextArea18 = new javax.swing.JTextArea();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextArea19 = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea20 = new javax.swing.JTextArea();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        pnlConf = new javax.swing.JPanel();
        btnConfTheme = new javax.swing.JButton();
        btnConfStadistics = new javax.swing.JButton();
        btnConfSuspctView = new javax.swing.JButton();
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
        pnlConfMainSuspectView = new javax.swing.JPanel();
        lblConfMainSuspectViewTitle = new javax.swing.JLabel();
        lblConfMainSuspectViewDesc = new javax.swing.JLabel();
        lblConfMainSuspectViewIcon = new javax.swing.JLabel();
        pnlConfSuspectView = new javax.swing.JPanel();
        scrollTblConfSuspectView = new javax.swing.JScrollPane();
        tblConfSuspectView = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
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
        jPanel6 = new javax.swing.JPanel();
        jToggleButton6 = new javax.swing.JToggleButton();
        jPanel12 = new javax.swing.JPanel();
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
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
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
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
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
        pnl1Background.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        pnl1Background.setForeground(new java.awt.Color(255, 255, 255));

        pnlMenu.setBackground(primaryColor);
        pnlMenu.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));

        pnlMenu2.setBackground(primaryColor);
        pnlMenu2.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 0));
        pnlMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu2MouseClicked(evt);
            }
        });

        lblMenu22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu22.setForeground(secundaryColor);
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

        pnlMenu1.setBackground(primaryColor);
        pnlMenu1.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 0));
        pnlMenu1.setPreferredSize(new java.awt.Dimension(192, 46));
        pnlMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu1MouseClicked(evt);
            }
        });

        lblMenu11.setBackground(secundaryColor);
        lblMenu11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu11.setForeground(secundaryColor);
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

        pnlMenu3.setBackground(primaryColor);
        pnlMenu3.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 0));
        pnlMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu3MouseClicked(evt);
            }
        });

        lblMenu33.setBackground(new java.awt.Color(255, 255, 255));
        lblMenu33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu33.setForeground(secundaryColor);
        lblMenu33.setText("Sospechosos");

        lblMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-anadir-usuario-masculino-30-$255-255-255$.png"))); // NOI18N

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

        pnlMenu4.setBackground(primaryColor);
        pnlMenu4.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 0));
        pnlMenu4.setPreferredSize(new java.awt.Dimension(192, 46));
        pnlMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenu4MouseClicked(evt);
            }
        });

        lblMenu44.setBackground(secundaryColor);
        lblMenu44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMenu44.setForeground(secundaryColor);
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

        jLabel25.setText("Nombre");

        jScrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane22.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane22.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea21.setColumns(20);
        jTextArea21.setRows(5);
        jTextArea21.setBorder(null);
        jScrollPane22.setViewportView(jTextArea21);
        modifySuspectFields[0]=jTextArea21;
        jTextArea21.getAccessibleContext().setAccessibleName("$");

        jLabel37.setText("Telefonos (varios)");

        jScrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane23.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane23.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea22.setColumns(20);
        jTextArea22.setRows(5);
        jTextArea22.setBorder(null);
        jScrollPane23.setViewportView(jTextArea22);
        modifySuspectFields[6]=jTextArea22;
        jTextArea22.getAccessibleContext().setAccessibleName("$");

        jButton25.setBackground(primaryColor
        );
        jButton25.setForeground(secundaryColor);
        jButton25.setText("...");
        jButton25.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton25.setFocusable(false);

        jButton26.setBackground(primaryColor);
        jButton26.setForeground(secundaryColor);
        jButton26.setText("...");
        jButton26.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton26.setFocusable(false);

        jScrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane24.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane24.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea23.setColumns(20);
        jTextArea23.setRows(5);
        jTextArea23.setBorder(null);
        jScrollPane24.setViewportView(jTextArea23);
        modifySuspectFields[7]=jTextArea23;
        jTextArea23.getAccessibleContext().setAccessibleName("$");

        jLabel43.setText("Email (varios)");

        jScrollPane25.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane25.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane25.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea24.setColumns(20);
        jTextArea24.setRows(5);
        jTextArea24.setBorder(null);
        jScrollPane25.setViewportView(jTextArea24);
        modifySuspectFields[1]=jTextArea24;
        jTextArea24.getAccessibleContext().setAccessibleName("$");

        jLabel44.setText("Apellido 1");

        jLabel45.setText("Apellido 2");

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane26.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane26.setToolTipText("");
        jScrollPane26.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea25.setColumns(20);
        jTextArea25.setRows(5);
        jTextArea25.setBorder(null);
        jScrollPane26.setViewportView(jTextArea25);
        modifySuspectFields[2]=jTextArea25;
        jTextArea25.getAccessibleContext().setAccessibleName("$");

        jScrollPane27.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane27.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane27.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea26.setColumns(20);
        jTextArea26.setRows(5);
        jTextArea26.setBorder(null);
        jScrollPane27.setViewportView(jTextArea26);
        modifySuspectFields[8]=jTextArea26;
        jTextArea26.getAccessibleContext().setAccessibleName("$");

        jButton27.setBackground(primaryColor);
        jButton27.setForeground(secundaryColor);
        jButton27.setText("...");
        jButton27.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton27.setFocusable(false);

        jLabel46.setText("Direcciones (Varios)");

        jScrollPane28.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane28.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane28.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea27.setColumns(20);
        jTextArea27.setRows(5);
        jTextArea27.setBorder(null);
        jScrollPane28.setViewportView(jTextArea27);
        modifySuspectFields[3]=jTextArea27;
        jTextArea27.getAccessibleContext().setAccessibleName("$");

        jLabel47.setText("Acompañantes (Varios)");

        jButton28.setBackground(primaryColor);
        jButton28.setForeground(secundaryColor
        );
        jButton28.setText("...");
        jButton28.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton28.setFocusable(false);

        jScrollPane29.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane29.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane29.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea28.setColumns(20);
        jTextArea28.setRows(5);
        jTextArea28.setBorder(null);
        jScrollPane29.setViewportView(jTextArea28);
        modifySuspectFields[4]=jTextArea28;
        jTextArea28.getAccessibleContext().setAccessibleName("$");

        jLabel48.setText("Antecedentes (Varios)");

        jButton10.setBackground(primaryColor);
        jButton10.setForeground(secundaryColor);
        jButton10.setText("...");
        jButton10.setActionCommand("");
        jButton10.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton10.setFocusable(false);

        jScrollPane30.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane30.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane30.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea29.setColumns(20);
        jTextArea29.setRows(5);
        jTextArea29.setBorder(null);
        jScrollPane30.setViewportView(jTextArea29);
        modifySuspectFields[9]=jTextArea29;
        jTextArea29.getAccessibleContext().setAccessibleName("$");

        jLabel49.setText("Matriculas (Varios)");

        jLabel50.setText("Fotos");

        jLabel51.setText("0/5");

        jButton2.setBackground(primaryColor);
        jButton2.setForeground(secundaryColor);
        jButton2.setText("Abrir gestor de imágenes");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane31.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane31.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane31.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea30.setColumns(20);
        jTextArea30.setRows(5);
        jTextArea30.setBorder(null);
        jScrollPane31.setViewportView(jTextArea30);
        modifySuspectFields[5]=jTextArea30;
        jTextArea30.getAccessibleContext().setAccessibleName("$");

        jLabel52.setText("Hechos (varios)");

        jButton29.setBackground(primaryColor);
        jButton29.setForeground(secundaryColor);
        jButton29.setText("...");
        jButton29.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton29.setFocusable(false);

        jButton30.setBackground(primaryColor);
        jButton30.setForeground(secundaryColor);
        jButton30.setText("Modificar sospechoso");
        jButton30.setActionCommand("modify");
        jButton30.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton30.setFocusable(false);

        jButton31.setBackground(primaryColor);
        jButton31.setForeground(secundaryColor);
        jButton31.setText("...");
        jButton31.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton31.setFocusable(false);

        javax.swing.GroupLayout pnlModifySuspectLayout = new javax.swing.GroupLayout(pnlModifySuspect);
        pnlModifySuspect.setLayout(pnlModifySuspectLayout);
        pnlModifySuspectLayout.setHorizontalGroup(
            pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addComponent(jScrollPane22))
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel45)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addGap(127, 127, 127)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel37)
                                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel49)
                                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel50)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel43)
                                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                        .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(85, 85, 85))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifySuspectLayout.createSequentialGroup()
                                    .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane24)
                                        .addComponent(jScrollPane27))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(82, 82, 82))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifySuspectLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addGap(222, 222, 222)))))
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlModifySuspectLayout.setVerticalGroup(
            pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel48)
                                        .addGap(7, 7, 7)
                                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                                        .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel50)
                                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel52)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlModifySuspectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlModifySuspectLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addContainerGap())))
        );

        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea22);
            }
        });
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea23);
            }
        });
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea26);
            }
        });
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea27);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea28);
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

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null, null, null, null, null, null, null, null},
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
                "Nombre", "Apellido 1", "Apellido 2", "Sospechosos", "Antecedentes", "Hechos", "Telefono", "E-mail", "Direcciones", "Matriculas", "Fotos", "", ""
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
        tblMain.setSelectionBackground(primaryColor);
        tblMain.getTableHeader().setResizingAllowed(false);
        tblMain.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(tblMain);
        tblMain.getTableHeader().setBackground(primaryColor);
        tblMain.getTableHeader().setForeground(secundaryColor);
        tblMain.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        TableColumnModel tblMainColumnModel = tblMain.getColumnModel();

        for (int i = 0; i < tblMainColumnModel.getColumnCount(); i++) {
            tblMainColumnModel.getColumn(i).setMinWidth(200);
        }
        tblMain.getAccessibleContext().setAccessibleName("1$1$0");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );

        pnlSearch.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Nombre");

        jScrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea11.setColumns(20);
        jTextArea11.setRows(5);
        jTextArea11.setBorder(null);
        jScrollPane12.setViewportView(jTextArea11);

        jLabel33.setText("Telefonos (varios)");

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea12.setColumns(20);
        jTextArea12.setRows(5);
        jTextArea12.setBorder(null);
        jScrollPane13.setViewportView(jTextArea12);

        jButton17.setBackground(primaryColor
        );
        jButton17.setForeground(secundaryColor);
        jButton17.setText("...");
        jButton17.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton17.setFocusable(false);

        jButton18.setBackground(primaryColor);
        jButton18.setForeground(secundaryColor);
        jButton18.setText("...");
        jButton18.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton18.setFocusable(false);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea13.setColumns(20);
        jTextArea13.setRows(5);
        jTextArea13.setBorder(null);
        jScrollPane14.setViewportView(jTextArea13);

        jButton19.setBackground(primaryColor);
        jButton19.setForeground(secundaryColor);
        jButton19.setText("...");
        jButton19.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton19.setFocusable(false);

        jScrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea14.setColumns(20);
        jTextArea14.setRows(5);
        jTextArea14.setBorder(null);
        jScrollPane15.setViewportView(jTextArea14);

        jButton20.setBackground(primaryColor);
        jButton20.setForeground(secundaryColor);
        jButton20.setText("...");
        jButton20.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton20.setFocusable(false);

        jScrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea15.setColumns(20);
        jTextArea15.setRows(5);
        jTextArea15.setBorder(null);
        jScrollPane16.setViewportView(jTextArea15);

        jLabel34.setText("Direcciones (Varios)");

        jLabel35.setText("Email (varios)");

        jLabel36.setText("Matriculas (Varios)");

        jScrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane17.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea16.setColumns(20);
        jTextArea16.setRows(5);
        jTextArea16.setBorder(null);
        jScrollPane17.setViewportView(jTextArea16);

        jLabel38.setText("Apellido 1");

        jLabel39.setText("Apellido 2");

        jScrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane18.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane18.setToolTipText("");
        jScrollPane18.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea17.setColumns(20);
        jTextArea17.setRows(5);
        jTextArea17.setBorder(null);
        jScrollPane18.setViewportView(jTextArea17);

        jLabel40.setText("Acompañantes (Varios)");

        jScrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane19.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane19.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea18.setColumns(20);
        jTextArea18.setRows(5);
        jTextArea18.setBorder(null);
        jScrollPane19.setViewportView(jTextArea18);

        jButton21.setBackground(primaryColor);
        jButton21.setForeground(secundaryColor
        );
        jButton21.setText("...");
        jButton21.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton21.setFocusable(false);

        jButton22.setBackground(primaryColor);
        jButton22.setForeground(secundaryColor);
        jButton22.setText("...");
        jButton22.setActionCommand("");
        jButton22.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton22.setFocusable(false);

        jScrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane20.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane20.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea19.setColumns(20);
        jTextArea19.setRows(5);
        jTextArea19.setBorder(null);
        jScrollPane20.setViewportView(jTextArea19);

        jLabel41.setText("Antecedentes (Varios)");

        jLabel42.setText("Hechos (varios)");

        jScrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane21.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane21.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea20.setColumns(20);
        jTextArea20.setRows(5);
        jTextArea20.setBorder(null);
        jScrollPane21.setViewportView(jTextArea20);

        jButton23.setBackground(primaryColor);
        jButton23.setForeground(secundaryColor);
        jButton23.setText("...");
        jButton23.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton23.setFocusable(false);

        jButton24.setBackground(primaryColor);
        jButton24.setForeground(secundaryColor);
        jButton24.setText("Buscar sospechoso");
        jButton24.setActionCommand("search");
        jButton24.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton24.setFocusable(false);

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSearchLayout.createSequentialGroup()
                                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlSearchLayout.createSequentialGroup()
                                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(284, 284, 284))
                                    .addGroup(pnlSearchLayout.createSequentialGroup()
                                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                                .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane18)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6))
                            .addGroup(pnlSearchLayout.createSequentialGroup()
                                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlSearchLayout.createSequentialGroup()
                                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40))
                                .addGap(131, 131, 131)))
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35)
                            .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlSearchLayout.createSequentialGroup()
                                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlSearchLayout.createSequentialGroup()
                                            .addComponent(jLabel34)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlSearchLayout.createSequentialGroup()
                                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                        .addComponent(jScrollPane13)
                                        .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(82, Short.MAX_VALUE))))
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlSearchLayout.createSequentialGroup()
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel40)))
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel41))
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea12);
            }
        });

        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jButton17.getAccessibleContext().setAccessibleName("1$0$0");
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
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea19);
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
                compCallingMouseClicked(evt, jTextArea20);
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
        jButton24.getAccessibleContext().setAccessibleName("1$0$0");
        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        pnlConf.setBackground(new java.awt.Color(255, 255, 255));

        btnConfTheme.setBackground(primaryColor);
        btnConfTheme.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfTheme.setForeground(secundaryColor);
        btnConfTheme.setText("Temas");
        btnConfTheme.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
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

        btnConfStadistics.setBackground(primaryColor);
        btnConfStadistics.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfStadistics.setForeground(secundaryColor);
        btnConfStadistics.setText("Estadísticas");
        btnConfStadistics.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        btnConfStadistics.setFocusable(false);
        btnConfStadistics.setPreferredSize(new java.awt.Dimension(161, 25));
        btnConfStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfStadisticsMouseClicked(evt);
            }
        });

        btnConfSuspctView.setBackground(primaryColor);
        btnConfSuspctView.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfSuspctView.setForeground(secundaryColor);
        btnConfSuspctView.setText("Vista de sospechosos");
        btnConfSuspctView.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        btnConfSuspctView.setFocusable(false);
        btnConfSuspctView.setPreferredSize(new java.awt.Dimension(161, 25));
        btnConfSuspctView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfSuspctViewMouseClicked(evt);
            }
        });
        btnConfSuspctView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfSuspctViewActionPerformed(evt);
            }
        });
        btnConfSuspctView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        pnlConfMain.setBackground(new java.awt.Color(255, 255, 255));

        pnlConfMainThemes.setBackground(secundaryColor);
        pnlConfMainThemes.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        pnlConfMainThemes.setPreferredSize(new java.awt.Dimension(209, 412));
        pnlConfMainThemes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfMainThemesMouseClicked(evt);
            }
        });

        lblConfMainThemesTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblConfMainThemesTitle.setForeground(primaryColor);
        lblConfMainThemesTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfMainThemesTitle.setText("Temas");

        lblConfMainThemesDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConfMainThemesDesc.setForeground(primaryColor);
        lblConfMainThemesDesc.setText("<html>\n\t<p>Echa un vistazo a nuestros temas y cambia la apariencia visual de la aplicación</p>\n<html>");

        javax.swing.GroupLayout pnlConfMainThemesLayout = new javax.swing.GroupLayout(pnlConfMainThemes);
        pnlConfMainThemes.setLayout(pnlConfMainThemesLayout);
        pnlConfMainThemesLayout.setHorizontalGroup(
            pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainThemesLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainThemesLayout.createSequentialGroup()
                        .addComponent(lblConfMainThemesDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainThemesLayout.createSequentialGroup()
                        .addGroup(pnlConfMainThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblConfMainThemesIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfMainThemesTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                        .addGap(73, 73, 73))))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblConfMainThemesTitle.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainThemesDesc.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainThemesIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png"))); // NOI18N
        lblConfMainThemesIcon.getAccessibleContext().setAccessibleName("-$-$-");
        lblConfMainThemesIcon.getAccessibleContext().setAccessibleDescription("icons8-brocha-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png");

        pnlConfMainStadistics.setBackground(secundaryColor);
        pnlConfMainStadistics.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        pnlConfMainStadistics.setPreferredSize(new java.awt.Dimension(209, 412));
        pnlConfMainStadistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfMainStadisticsMouseClicked(evt);
            }
        });

        lbllConfMainStadisticsTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbllConfMainStadisticsTitle.setForeground(primaryColor);
        lbllConfMainStadisticsTitle.setText("Estadísticas");

        lbllConfMainStadisticsDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbllConfMainStadisticsDesc.setForeground(primaryColor);
        lbllConfMainStadisticsDesc.setText("<html>\n<p>Visita estadísticas relacionadas con los sospechosos(visibilidad de los sospechosos, peligrosidad... etc) y con tu frecuencia en la app</p>\n<html>");

        javax.swing.GroupLayout pnlConfMainStadisticsLayout = new javax.swing.GroupLayout(pnlConfMainStadistics);
        pnlConfMainStadistics.setLayout(pnlConfMainStadisticsLayout);
        pnlConfMainStadisticsLayout.setHorizontalGroup(
            pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainStadisticsLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lbllConfMainStadisticsDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainStadisticsLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(pnlConfMainStadisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainStadisticsLayout.createSequentialGroup()
                        .addComponent(lbllConfMainStadisticsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainStadisticsLayout.createSequentialGroup()
                        .addComponent(lbllConfMainStadisticsTitle)
                        .addGap(60, 60, 60))))
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
                .addContainerGap(176, Short.MAX_VALUE))
        );

        lbllConfMainStadisticsTitle.getAccessibleContext().setAccessibleName("-$-$1");
        lbllConfMainStadisticsDesc.getAccessibleContext().setAccessibleName("-$-$1");
        lbllConfMainStadisticsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-gráfico-de-barras-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png"))); // NOI18N
        lbllConfMainStadisticsIcon.getAccessibleContext().setAccessibleName("-$-$-");
        lbllConfMainStadisticsIcon.getAccessibleContext().setAccessibleDescription("icons8-gráfico-de-barras-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png");

        pnlConfMainSuspectView.setBackground(secundaryColor);
        pnlConfMainSuspectView.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        pnlConfMainSuspectView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfMainSuspectViewMouseClicked(evt);
            }
        });

        lblConfMainSuspectViewTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblConfMainSuspectViewTitle.setForeground(primaryColor);
        lblConfMainSuspectViewTitle.setText("Vista de sospechosos");

        lblConfMainSuspectViewDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConfMainSuspectViewDesc.setForeground(primaryColor);
        lblConfMainSuspectViewDesc.setText("<html> \t \t<p>Cambia la forma en la que ves los sospechosos, introduciendo o eliminando sus campos. \t</p> </html> ");

        javax.swing.GroupLayout pnlConfMainSuspectViewLayout = new javax.swing.GroupLayout(pnlConfMainSuspectView);
        pnlConfMainSuspectView.setLayout(pnlConfMainSuspectViewLayout);
        pnlConfMainSuspectViewLayout.setHorizontalGroup(
            pnlConfMainSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainSuspectViewLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlConfMainSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfMainSuspectViewDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlConfMainSuspectViewLayout.createSequentialGroup()
                        .addComponent(lblConfMainSuspectViewTitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainSuspectViewLayout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(lblConfMainSuspectViewIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        pnlConfMainSuspectViewLayout.setVerticalGroup(
            pnlConfMainSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfMainSuspectViewLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(lblConfMainSuspectViewTitle)
                .addGap(31, 31, 31)
                .addComponent(lblConfMainSuspectViewIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblConfMainSuspectViewDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblConfMainSuspectViewTitle.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainSuspectViewDesc.getAccessibleContext().setAccessibleName("-$-$1");
        lblConfMainSuspectViewIcon.getAccessibleContext().setAccessibleName("-$-$-");
        lblConfMainSuspectViewIcon.getAccessibleContext().setAccessibleDescription("icons8-visible-filled-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png");
        lblConfMainSuspectViewIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-visible-filled-64-$"+UiUtils.rgbFormatted(primaryColor)+"$.png"))); // NOI18N

        javax.swing.GroupLayout pnlConfMainLayout = new javax.swing.GroupLayout(pnlConfMain);
        pnlConfMain.setLayout(pnlConfMainLayout);
        pnlConfMainLayout.setHorizontalGroup(
            pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(pnlConfMainSuspectView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlConfMainThemes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlConfMainStadistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        pnlConfMainLayout.setVerticalGroup(
            pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlConfMainSuspectView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlConfMainThemes, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(pnlConfMainStadistics, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
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
        pnlConfMainSuspectView.getAccessibleContext().setAccessibleName("0$1$-");
        pnlConfMainSuspectView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        pnlConfSuspectView.setBackground(new java.awt.Color(255, 255, 255));

        tblConfSuspectView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Nombre", null, null},
                {"Apellido 1", null, null},
                {"Apellido 2", null, null},
                {"Teléfono", null, null},
                {"Correo", null, null},
                {"Direcciones", null, null},
                {"Acompañantes", null, null},
                {"Matriculas", null, null}
            },
            new String [] {
                "Campo", "% Rellenado sobre el total", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblConfSuspectView.setGridColor(new java.awt.Color(198, 198, 198));
        tblConfSuspectView.setSelectionBackground(primaryColor);
        tblConfSuspectView.getTableHeader().setResizingAllowed(false);
        tblConfSuspectView.getTableHeader().setReorderingAllowed(false);
        scrollTblConfSuspectView.setViewportView(tblConfSuspectView);
        tblConfSuspectView.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        tblConfSuspectView.getTableHeader().setBackground(primaryColor);
        tblConfSuspectView.getTableHeader().setForeground(secundaryColor);
        tblConfSuspectView.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        tblConfSuspectView.getAccessibleContext().setAccessibleName("1$1$0");

        jButton3.setBackground(primaryColor);
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setForeground(secundaryColor);
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-más-25.png"))); // NOI18N
        jButton3.setText("Insertar campo");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setBackground(secundaryColor);
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(primaryColor);
        jLabel10.setText("0/10");

        javax.swing.GroupLayout pnlConfSuspectViewLayout = new javax.swing.GroupLayout(pnlConfSuspectView);
        pnlConfSuspectView.setLayout(pnlConfSuspectViewLayout);
        pnlConfSuspectViewLayout.setHorizontalGroup(
            pnlConfSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfSuspectViewLayout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addGroup(pnlConfSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTblConfSuspectView, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlConfSuspectViewLayout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addGap(99, 99, 99))
        );
        pnlConfSuspectViewLayout.setVerticalGroup(
            pnlConfSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfSuspectViewLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(scrollTblConfSuspectView, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pnlConfSuspectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jButton3.getAccessibleContext().setAccessibleName("1$0$0");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
        jLabel10.getAccessibleContext().setAccessibleName("0$-$1");

        pnlConfStadistics.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Visibilidad de los sospechosos:");

        jLabel12.setText("Sospechoso más escurridizo:");

        jLabel13.setText("Peligrosidad:");

        jSeparator1.setForeground(primaryColor);

        jSeparator2.setForeground(primaryColor);

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
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[0]));

        jPanel7.setBackground(themeColor[0]);

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
        jToggleButton1.setBorderPainted(false);
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[3]));

        jToggleButton4.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-lila.png"))); // NOI18N
        jToggleButton4.setBorderPainted(false);
        jToggleButton4.setFocusable(false);
        jToggleButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton4MouseClicked(evt);
            }
        });

        jPanel10.setBackground(themeColor[3]);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[1]));

        jToggleButton2.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-verde.png"))); // NOI18N
        jToggleButton2.setBorderPainted(false);
        jToggleButton2.setFocusable(false);
        jToggleButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton2MouseClicked(evt);
            }
        });

        jPanel8.setBackground(themeColor[1]);
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[2]));

        jToggleButton3.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-azul.png"))); // NOI18N
        jToggleButton3.setBorderPainted(false);
        jToggleButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton3MouseClicked(evt);
            }
        });

        jPanel9.setBackground(themeColor[2]);

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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[4]));

        jToggleButton5.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-rojo.png"))); // NOI18N
        jToggleButton5.setBorderPainted(false);
        jToggleButton5.setFocusable(false);
        jToggleButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton5MouseClicked(evt);
            }
        });

        jPanel11.setBackground(themeColor[4]);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(themeColor[5]));

        jToggleButton6.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/icons8-brocha-30-amarillo.png"))); // NOI18N
        jToggleButton6.setBorderPainted(false);
        jToggleButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton6MouseClicked(evt);
            }
        });

        jPanel12.setBackground(themeColor[5]);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToggleButton6.setFocusable(false);

        javax.swing.GroupLayout pnlConfThemeLayout = new javax.swing.GroupLayout(pnlConfTheme);
        pnlConfTheme.setLayout(pnlConfThemeLayout);
        pnlConfThemeLayout.setHorizontalGroup(
            pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfThemeLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );
        pnlConfThemeLayout.setVerticalGroup(
            pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfThemeLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlConfThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );

        layeredConf.setLayer(pnlConfMain, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredConf.setLayer(pnlConfSuspectView, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
                .addComponent(pnlConfSuspectView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(pnlConfSuspectView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(142, 142, 142)
                .addComponent(btnConfSuspctView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnConfTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnConfStadistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
        );
        pnlConfLayout.setVerticalGroup(
            pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfTheme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConfStadistics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConfSuspctView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
        btnConfSuspctView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                //mouseBttHover(evt);
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        btnConfSuspctView.setVisible(false);
        btnConfSuspctView.getAccessibleContext().setAccessibleName("1$0$0");

        pnlAdd.setBackground(new java.awt.Color(255, 255, 255));

        jButton9.setBackground(primaryColor);
        jButton9.setForeground(secundaryColor);
        jButton9.setText("Añadir sospechoso");
        jButton9.setActionCommand("add");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton9.setFocusable(false);

        jLabel4.setText("Nombre");

        jLabel5.setText("Apellido 1");

        jLabel6.setText("Apellido 2");

        jLabel8.setText("Telefonos (varios)");

        jLabel9.setText("Hechos (varios)");

        jLabel26.setText("Acompañantes (Varios)");

        jLabel27.setText("Email (varios)");

        jLabel28.setText("Fotos");

        jButton1.setBackground(primaryColor);
        jButton1.setForeground(secundaryColor);
        jButton1.setText("Abrir gestor de imágenes");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel30.setText("0/5");

        jLabel29.setText("Antecedentes (Varios)");

        jLabel31.setText("Direcciones (Varios)");

        jLabel32.setText("Matriculas (Varios)");

        jButton8.setBackground(primaryColor);
        jButton8.setForeground(secundaryColor);
        jButton8.setText("...");
        jButton8.setActionCommand("");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton8.setFocusable(false);

        jButton11.setBackground(primaryColor);
        jButton11.setForeground(secundaryColor);
        jButton11.setText("...");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton11.setFocusable(false);

        jButton12.setBackground(primaryColor
        );
        jButton12.setForeground(secundaryColor);
        jButton12.setText("...");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton12.setFocusable(false);

        jButton13.setBackground(primaryColor);
        jButton13.setForeground(secundaryColor);
        jButton13.setText("...");
        jButton13.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton13.setFocusable(false);

        jButton14.setBackground(primaryColor);
        jButton14.setForeground(secundaryColor);
        jButton14.setText("...");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton14.setFocusable(false);

        jButton15.setBackground(primaryColor);
        jButton15.setForeground(secundaryColor);
        jButton15.setText("...");
        jButton15.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton15.setFocusable(false);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);
        addSuspectFields[0]=jTextArea1;
        jTextArea1.getAccessibleContext().setAccessibleName("");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(null);
        jScrollPane2.setViewportView(jTextArea2);
        addSuspectFields[1]=jTextArea2;

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setBorder(null);
        jScrollPane3.setViewportView(jTextArea3);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setToolTipText("");
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setBorder(null);
        jScrollPane4.setViewportView(jTextArea4);
        addSuspectFields[2]=jTextArea4;

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jTextArea5.setBorder(null);
        jScrollPane5.setViewportView(jTextArea5);
        addSuspectFields[4]=jTextArea5;

        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jTextArea6.setBorder(null);
        jScrollPane6.setViewportView(jTextArea6);
        addSuspectFields[5]=jTextArea6;

        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea7.setColumns(20);
        jTextArea7.setRows(5);
        jTextArea7.setBorder(null);
        jScrollPane7.setViewportView(jTextArea7);
        addSuspectFields[7]=jTextArea7;

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea8.setColumns(20);
        jTextArea8.setRows(5);
        jTextArea8.setBorder(null);
        jScrollPane8.setViewportView(jTextArea8);
        addSuspectFields[6]=jTextArea8;

        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea9.setColumns(20);
        jTextArea9.setRows(5);
        jTextArea9.setBorder(null);
        jScrollPane9.setViewportView(jTextArea9);
        addSuspectFields[9]=jTextArea9;

        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor));
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea10.setColumns(20);
        jTextArea10.setRows(5);
        jTextArea10.setBorder(null);
        jScrollPane10.setViewportView(jTextArea10);
        addSuspectFields[8]=jTextArea10;

        jButton16.setBackground(primaryColor);
        jButton16.setForeground(secundaryColor
        );
        jButton16.setText("...");
        jButton16.setBorder(javax.swing.BorderFactory.createLineBorder(secundaryColor));
        jButton16.setFocusable(false);

        javax.swing.GroupLayout pnlAddLayout = new javax.swing.GroupLayout(pnlAdd);
        pnlAdd.setLayout(pnlAddLayout);
        pnlAddLayout.setHorizontalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(177, 177, 177))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel29)
                            .addComponent(jLabel6)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9)
                            .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                                .addComponent(jButton13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel32)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddLayout.setVerticalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29))
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddLayout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compCallingMouseClicked(evt, jTextArea5);
            }
        });
        jButton8.getAccessibleContext().setAccessibleName("1$0$0");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });

        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                view.EnteredExited.mouseComponentEffect(evt);
            }
        });
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
        jScrollPane5.getAccessibleContext().setAccessibleName("-$-$-");
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
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfMainLayout.createSequentialGroup()
                    .addComponent(pnlConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 13, Short.MAX_VALUE)))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfMainLayout.createSequentialGroup()
                    .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 29, Short.MAX_VALUE)))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfMainLayout.createSequentialGroup()
                    .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 23, Short.MAX_VALUE)))
            .addGroup(layeredConfMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredConfMainLayout.createSequentialGroup()
                    .addComponent(pnlModifySuspect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 28, Short.MAX_VALUE)))
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
        CreateAndFillTables.setMainTable(tblMain);
        CreateAndFillTables.fillMainTable();
        modifySuspectImageManager = new ImageManager(me, true);
        addSuspectImageManager = new ImageManager(me, true);
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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        Communication.setPrimaryColor(themeColor[0]);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void pnlConfMainThemesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfMainThemesMouseClicked
        pnlConfTheme.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        btnConfSuspctView.setVisible(true);
        pnlConfMain.setVisible(false);
    }//GEN-LAST:event_pnlConfMainThemesMouseClicked

    private void btnConfSuspctViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfSuspctViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfSuspctViewActionPerformed

    private void btnConfSuspctViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfSuspctViewMouseClicked
        hideConfLayouts();
        pnlConfSuspectView.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        btnConfSuspctView.setVisible(true);
    }//GEN-LAST:event_btnConfSuspctViewMouseClicked

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
        btnConfSuspctView.setVisible(false);
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
    }//GEN-LAST:event_pnlMenu2MouseClicked

    private void pnlConfMainSuspectViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfMainSuspectViewMouseClicked
        hideConfLayouts();
        pnlConfSuspectView.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        btnConfSuspctView.setVisible(true);
    }//GEN-LAST:event_pnlConfMainSuspectViewMouseClicked

    private void pnlConfMainStadisticsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfMainStadisticsMouseClicked
        hideConfLayouts();
        pnlConfStadistics.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        btnConfSuspctView.setVisible(true);
    }//GEN-LAST:event_pnlConfMainStadisticsMouseClicked

    private void btnConfStadisticsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfStadisticsMouseClicked
        hideConfLayouts();
        pnlConfStadistics.setVisible(true);
        btnConfTheme.setVisible(true);
        btnConfStadistics.setVisible(true);
        btnConfSuspctView.setVisible(true);
    }//GEN-LAST:event_btnConfStadisticsMouseClicked

    private void pnlMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenu3MouseClicked
        hideLayouts();
        pnlAdd.setVisible(true);
    }//GEN-LAST:event_pnlMenu3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        myController.setPrimaryColor(themeColor[0]);
        primaryColor = themeColor[0];
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void jToggleButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton2MouseClicked
        myController.setPrimaryColor(themeColor[1]);
        primaryColor = themeColor[1];
    }//GEN-LAST:event_jToggleButton2MouseClicked

    private void jToggleButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton3MouseClicked
        myController.setPrimaryColor(themeColor[2]);
        primaryColor = themeColor[2];
    }//GEN-LAST:event_jToggleButton3MouseClicked

    private void jToggleButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton4MouseClicked
        myController.setPrimaryColor(themeColor[3]);
        primaryColor = themeColor[3];
    }//GEN-LAST:event_jToggleButton4MouseClicked

    private void jToggleButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton5MouseClicked
        myController.setPrimaryColor(themeColor[4]);
        primaryColor = themeColor[4];
    }//GEN-LAST:event_jToggleButton5MouseClicked

    private void jToggleButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton6MouseClicked
        myController.setPrimaryColor(themeColor[5]);
        primaryColor = themeColor[5];
    }//GEN-LAST:event_jToggleButton6MouseClicked

    private void btnConfThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfThemeActionPerformed

    }//GEN-LAST:event_btnConfThemeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addSuspectImageManager.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton btnConfSuspctView;
    private javax.swing.JButton btnConfTheme;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    public javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
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
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JTextArea jTextArea16;
    private javax.swing.JTextArea jTextArea17;
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
    private javax.swing.JTextArea jTextArea27;
    private javax.swing.JTextArea jTextArea28;
    private javax.swing.JTextArea jTextArea29;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea30;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JLayeredPane layeredConf;
    private javax.swing.JLayeredPane layeredConfMain;
    private javax.swing.JLabel lblConfMainSuspectViewDesc;
    private javax.swing.JLabel lblConfMainSuspectViewIcon;
    private javax.swing.JLabel lblConfMainSuspectViewTitle;
    private javax.swing.JLabel lblConfMainThemesDesc;
    private javax.swing.JLabel lblConfMainThemesIcon;
    private javax.swing.JLabel lblConfMainThemesTitle;
    private javax.swing.JLabel lblMenu1;
    private javax.swing.JLabel lblMenu11;
    private javax.swing.JLabel lblMenu2;
    private javax.swing.JLabel lblMenu22;
    private javax.swing.JLabel lblMenu3;
    private javax.swing.JLabel lblMenu33;
    private javax.swing.JLabel lblMenu4;
    private javax.swing.JLabel lblMenu44;
    private javax.swing.JLabel lbllConfMainStadisticsDesc;
    private javax.swing.JLabel lbllConfMainStadisticsIcon;
    private javax.swing.JLabel lbllConfMainStadisticsTitle;
    private javax.swing.JPanel pnl1Background;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlConf;
    private javax.swing.JPanel pnlConfMain;
    private javax.swing.JPanel pnlConfMainStadistics;
    private javax.swing.JPanel pnlConfMainSuspectView;
    private javax.swing.JPanel pnlConfMainThemes;
    private javax.swing.JPanel pnlConfStadistics;
    private javax.swing.JPanel pnlConfSuspectView;
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
    private javax.swing.JPanel pnlModifySuspect;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JScrollPane scrollTblConfSuspectView;
    private javax.swing.JTable tblConfSuspectView;
    private static javax.swing.JTable tblMain;
    // End of variables declaration//GEN-END:variables
    private int xMousePosition;
    private int yMousePosition;
    private static Color primaryColor;
    private static Color secundaryColor = Color.white;
    public static Color oldColor;
    private static Color[] themeColor;
    private static Controller myController;
    private static UI me;
    private Images[] photos;
    private static ImageManager addSuspectImageManager;
    private static ImageManager modifySuspectImageManager;
    private static JTextComponent[] addSuspectFields = new JTextComponent[10];
    private JTextComponent[] modifySuspectFields = new JTextComponent[10];
    private Suspect suspectToUpdate;
}

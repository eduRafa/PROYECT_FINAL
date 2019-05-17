/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author rafa0
 */
public class PhotoJButton extends javax.swing.JButton{
    Image images = Toolkit.getDefaultToolkit().getImage(ClassLoader.
                getSystemResource("view/images/icons8-galeria-20.png"));

    public PhotoJButton() {
        setBorderPainted(false);
        setContentAreaFilled(false);
        setImageIcon();
    }

    public void setImageIcon() {
        super.setIcon(new ImageIcon(images));

    }
}

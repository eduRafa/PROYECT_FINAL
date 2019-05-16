/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.ImageIcon;


/**
 *
 * @author rafa0
 */
public class TrashJButton extends javax.swing.JButton{

    private int suspectCode;
    
    public TrashJButton(ImageIcon x) {
        super(x);
    }

    public int getSuspectCode() {
        return suspectCode;
    }

    public void setSuspectCode(int suspectCode) {
        this.suspectCode = suspectCode;
    }
    

    
    
    
}

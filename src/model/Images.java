/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Antonio Jose Adamuz Sereno
 */
public class Images {

    private ImageIcon image;
    private Integer codeImage;
    private String description;
    public FileInputStream fis;
    private Integer codeSuspect;

    /*Constructor para cuando obtengas info de la bd*/
    public Images(Integer CodeImage, String Description,
            Integer CodeSuspect) {

        this.codeImage = CodeImage;
        this.description = Description;
        this.codeSuspect = CodeSuspect;
    }

    public Images(Image image) {
        transformImage(image);
        setFileInputStream();
    }

    public void transformImage(Image image) {
        this.image = new ImageIcon(image);
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(Image Image) {
        transformImage(Image);
    }

    public Integer getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(Integer CodeImage) {
        this.codeSuspect = CodeImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public Integer getCodeSuspect() {
        return codeSuspect;
    }

    public void setCodeSuspect(Integer CodeSuspect) {
        this.codeSuspect = CodeSuspect;
    }

    private void setFileInputStream() {
        String pathWrongFormed = image.toString();
        String pathWellFormed = pathWrongFormed.substring(6, pathWrongFormed.length());

        try {
            FileInputStream fis = null;
            File file = new File(pathWellFormed);
            fis = new FileInputStream(file);
            //ps.setBinaryStream(1, fis, (int) file.length());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.fis=fis;
    }

}

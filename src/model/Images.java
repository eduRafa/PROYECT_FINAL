/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Antonio Jose Adamuz Sereno
 */
public class Images {

    private ImageIcon image;
    private Integer codeImage;
    private String description;
    private Integer codeSuspect;
    private File f;

    /*Constructor para cuando obtengas info de la bd*/
    public Images(Integer CodeImage, String Description,
            Integer CodeSuspect) {

        this.codeImage = CodeImage;
        this.description = Description;
        this.codeSuspect = CodeSuspect;
    }

    public Images(Image image, String path) {
        transformImage(image);
        if (path != null) {
            this.f = new File(path);
        }
    }

    public File getFile() {
        return f;
    }

    public void transformImage(Image image) {
        this.image = new ImageIcon(image);
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(Image Image, String path) {
        if (path != null) {
            f = new File(path);
        }
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
}

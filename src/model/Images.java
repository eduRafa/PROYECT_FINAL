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

    private Image myImage;
    private ImageIcon image;
    private Integer codeImage;
    private String description;
    private Integer codeSuspect;
    private File f;
    String path;

    /*Constructor para cuando obtengas info de la bd*/
    public Images(Integer CodeImage, String Description,
            Integer CodeSuspect, ImageIcon img) {
        this.codeImage = CodeImage;
        this.description = Description;
        this.codeSuspect = CodeSuspect;
        this.image = img;
    }

    public Images(Image image, String path) {
        myImage=image;
        this.image=new ImageIcon(image);
        if (path != null) {
            f = new File(path);
        }
        this.path=path;
    }

    public File getFile() {
        return f;
    }
    
    public String getPath(){
        return path;
    }
    
    public void setFile(File f){
        this.f=f;
    }

    public ImageIcon getImageIcon() {
        return image;
    }
    
    public Image getImage(){
        return myImage;
    }

    public void setImage(Image Image, String path) {
        if (path != null) {
            f = new File(path);
        }
        this.path=path;
    }

    public void setImageIcon(ImageIcon imag) {
        image = imag;
    }

    public Integer getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(Integer CodeImage) {
        this.codeImage = CodeImage;
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

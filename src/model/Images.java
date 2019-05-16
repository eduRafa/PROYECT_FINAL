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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

    /*Constructor para cuando obtengas info de la bd*/
    public Images(Integer CodeImage, String Description,
            Integer CodeSuspect) {

        this.codeImage = CodeImage;
        this.description = Description;
        this.codeSuspect = CodeSuspect;
    }

    public Images(Image image) {
        transformImage(image);
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

    public static BufferedImage getBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public byte[] getBytes() {
        byte[] byteArray = null;

        if (image != null) {
            try {
                BufferedImage bi = getBufferedImage(image.getImage());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", baos);

                byteArray = baos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return byteArray;
    }

}

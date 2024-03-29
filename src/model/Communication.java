/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author rafa0
 */
public class Communication {

    public static final String COLORFILE = "./colors.xml";
    public static final String CONNECTIONFILE = "./connection.xml";

    /**
     * Metodo encargado de obtener un documento en el sistema a traves de su
     * path
     *
     * @param fileName path del archivo a obtener
     * @return Devuelve un objeto Document con el archivo
     */
    static Document getDocumentXML(String fileName) {
        Document docParsed = null;

        try {
            DocumentBuilder doc = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            docParsed = doc.parse(new File(fileName));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return docParsed;
    }

    /**
     * Metodo encargado de recibir un color y de guardarlo en el archivo el cual
     * tiene como path el "COLORFILE" (variable almacenada como variable global
     * de esta clase)
     *
     * @param c Nuevo color
     */
    public static void setPrimaryColor(Color c) {
        if (c != null) {
            boolean changed = false;
            Document doc = getDocumentXML(COLORFILE);
            NodeList object = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < object.getLength(); i++) {
                Node tempNode = object.item(i);
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (tempNode.getNodeName().equals("primaryColor")) {
                        tempNode.setTextContent(String.valueOf(c.getRed()) + ","
                                + String.valueOf(c.getGreen()) + "," + (String.valueOf(
                                c.getBlue())));
                        saveDocument(doc, COLORFILE);
                    }
                }
            }
        }
    }

    /**
     * Metodo encargado de modificar un documento.
     *
     * @param doc Documento con el nuevo contenido
     * @param fileName Path del documento con el contenido antiguo
     */
    static void saveDocument(Document doc, String fileName) {
        File color = new File(fileName);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(color);
            Source input = new DOMSource(doc);
            try {
                transformer.transform(input, output);
            } catch (TransformerException ex) {
                Logger.getLogger(Communication.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Communication.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo encargado de imprimir el contenido del archivo pasado por
     * parametro
     *
     * @param xml Documento xml a imprimir
     * @return Un String con el contenido del archivo
     */
    public static String xmlToString(Document xml) {
        String xmlString = null;

        if (xml != null) {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;

            try {
                transformer = tf.newTransformer();

                // Uncomment if you do not require XML declaration
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                //A character stream that collects its output in a string buffer,
                //which can then be used to construct a string.
                StringWriter writer = new StringWriter();

                //transform document to string
                transformer.transform(new DOMSource(xml), new StreamResult(writer));

                xmlString = writer.getBuffer().toString();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return xmlString;
    }

    /**
     * Metodo encargado de obtener el color principal del xml guardado en la
     * variable global COLORFILE
     *
     * @return Devuelve un objeto Color del archivo guardado en la variable
     * global COLORFILE
     */
    public static Color getPrimaryColor() {
        Color c = null;
        Document doc = getDocumentXML(COLORFILE);
        NodeList object = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < object.getLength(); i++) {
            Node tempNode = object.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.getNodeName().equals("primaryColor")) {
                    int[] rgb = new int[3];
                    String[] rgbString = tempNode.getTextContent().split(",");
                    for (int z = 0; z < rgbString.length; z++) {
                        rgb[z] = Integer.valueOf(rgbString[z]);
                    }
                    c = new Color(rgb[0], rgb[1], rgb[2]);
                }
            }
        }
        return c;
    }

    /**
     * Metodo encargado de obtener
     *
     * @return Devuelve un array de String el cual representa los valores
     * database localhost, user y password
     */
    public static String[] getDatabaseAccess() {
        String[] dbValues = new String[4];
        Document doc = getDocumentXML(CONNECTIONFILE);
        NodeList object = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < object.getLength(); i++) {
            Node tempNode = object.item(i);
            if (tempNode.getNodeName().equals("driver")) {
                NodeList x = tempNode.getChildNodes();
                for (int j = 0; j < x.getLength(); j++) {
                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                        dbValues[0] = x.item(j).getTextContent();
                    }
                }
            } else if (tempNode.getNodeName().equals("url")) {
                NodeList x = tempNode.getChildNodes();
                for (int j = 0; j < x.getLength(); j++) {
                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                        dbValues[1] = x.item(j).getTextContent();
                    }
                }
            } else if (tempNode.getNodeName().equals("user")) {
                NodeList x = tempNode.getChildNodes();
                for (int j = 0; j < x.getLength(); j++) {
                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                        dbValues[2] = x.item(j).getTextContent();
                    }
                }
            } else if (tempNode.getNodeName().equals("password")) {
                NodeList x = tempNode.getChildNodes();
                for (int j = 0; j < x.getLength(); j++) {
                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                        dbValues[3] = x.item(j).getTextContent();
                    }
                }
            }
        }

        return dbValues;
    }

}

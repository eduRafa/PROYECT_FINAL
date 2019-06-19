/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.*;

/**
 *
 * @author amgal
 */
public class Query {

    static Connect c;
    static Connection connection;
    static ResultSet rs;
    static int maxPosition = 10;
    static int currentPosition = 0;
    static int numberOfSuspects = 10;
    static Suspect[] lastTen = new Suspect[numberOfSuspects];
    static ArrayList<Integer> coincidences = new ArrayList<>();

    public static void setConnect(Connect c) {
        Query.c = c;
        connection = c.getMyConnection();
    }

    /*
    *@return last: es un String que contiene el codigo de sospechoso del ultimo 
    registro introducido en la base de datos
    *Este metodo nos permite optener el codigo de sospechosos del utimo sospechosos 
    añadido a la base de datos 
     */
    private static String findLast() {
        String last = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("SELECT CodeSuspect from SUSPECT");
            if (rs.last()) {
                last = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return last;
    }

    public static int getCurrentPosition() {
        return currentPosition;
    }

    /*
    *Este metodo borrar un sospechoso
    *@param sus: Es el sospechoso que se desea eliminar
     */
    public static boolean deleteSuspect(Integer sus) {
        boolean deleted = false;
        try {
            if (sus != null) {
                ResultSet rs2;
                Statement s2 = connection.createStatement();
                Statement s = connection.createStatement();
                s.executeUpdate("Delete from Suspect where CodeSuspect = " + sus.toString());
                for (int i = 0; i < lastTen.length; i++) {
                    if (lastTen[i] != null) {
                        if (lastTen[i].getCodeSuspect() == sus) {
                            lastTen[i] = null;

                        }
                    }
                }

                rs2 = s2.executeQuery("Select * from SUSPECT limit " + currentPosition + ", 10");
                if (!rs2.next()) {
                    currentPosition -= 10;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleted;
    }

    /*
    Este metodo se ocupa de actualizar toda la infomracion de las tablas de sospecoso con los nuevos datos 
    @param type: Es el tipo de cato que se desea actualizar
    @param code: Es el codigo de sospechoso del sospechoso del que se desea actualizar la informacion
    @param value: Es el valor de la nueva informacion 
    @param table: Es el nombre de la tabla donde se encuentran los valores que se desean cambiar
    @param 
     */
    private static boolean updateAttribute(String type, String code, String value,
            String table, String key) {
        boolean updated = false;
        try {
            Statement s = connection.createStatement();
            if (!type.equals("PhoneNumber")) {
                s.executeUpdate("Update " + table + " set " + type + "='" + value + "' where " + key + "=" + code);
                updated = true;
            } else {
                s.executeUpdate("Update " + table + " set " + type + "=" + value + " where " + key + "=" + code);
                updated = true;
            }
            s.close();
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }

    /*
    Este metodo actualiza la infomracion de un sospechoso
    *@param sus: Es el sospechoso que se desea actualizar el la base de datos cuyos parametros ya tiene los nuevos valores
    (Los codigos deben de ser los mismos en todos los parametros, asi que lo qu ese debe hacer es usar los setter para cambiar los valores antiguos por los nuevos)
     */
    public static boolean Update(Suspect sus) {

        boolean updated = false;

        if (sus.isEmpty()) {
            System.out.println("hola");
            deleteSuspect(sus.getCodeSuspect());
        } else {
            if (sus != null) {
                Suspect preUpdate = Query.findSuspect(sus.getCodeSuspect());

                if (!sus.getName().equals(preUpdate.getName())) {
                    updated = updateAttribute("Name", sus.getCodeSuspect().toString(), sus.getName(), "Suspect", "CodeSuspect");
                }
                if (!sus.equals(preUpdate.getLastname1())) {
                    updated = updateAttribute("Lastname1", sus.getCodeSuspect().toString(), sus.getLastname1(), "Suspect", "CodeSuspect");
                }
                if (!sus.equals(preUpdate.getLastname2())) {
                    updated = updateAttribute("Lastname2", sus.getCodeSuspect().toString(), sus.getLastname2(), "Suspect", "CodeSuspect");
                }
                if (!sus.getRecord().equals(preUpdate.getRecord())) {
                    updated = updateAttribute("Record", sus.getCodeSuspect().toString(), sus.getRecord().toString(), "Suspect", "CodeSuspect");
                }
                if (!sus.getFacts().equals(preUpdate.getFacts())) {
                    updated = updateAttribute("Facts", sus.getCodeSuspect().toString(), sus.getFacts().toString(), "Suspect", "CodeSuspect");
                }
                if (sus.getCompanions() != null) {
                    for (int i = 0; i < sus.getCompanions().size(); i++) {
                        if (sus.getCompanions().get(i) != null) {
                            updated = updateAttribute("CodeSuspect2", sus.getCodeSuspect().toString(), sus.getCompanions().get(i).toString(), "COMPANIONS", "CodeSuspect");
                        }
                    }
                }
                updateMultipleFields(sus);
            }
        }

        return updated;
    }

    /*
    *Este metodo se encarga de hacer un update a una imagen de la base de datos
    *@param code: Ese el codigo del registro de la imagen que se dsea actualizar
    *@param img: Es la nueva imagen que se desea introducir
     */
    private static boolean updateImage(String code, Images img) {
        boolean added = false;
        String update = "Update Images set Image = ? , Description = ? where CodeImage=" + code;
        FileInputStream fis = null;

        try {
            PreparedStatement ps = null;
            if (img != null) {
                if (img.getFile() != null) {
                    fis = new FileInputStream(img.getFile());
                    ps = connection.prepareStatement(update);
                    ps.setBinaryStream(1, fis, img.getFile().length());
                    ps.setString(2, img.getDescription());
                    ps.execute();
                }
            } else {
                Statement s = connection.createStatement();
                s.execute("Delete from IMAGES where CodeImage=" + code.toString());
                s.close();
            }

            if (ps != null) {
                ps.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    public static void updateImages(Integer suspectCode, Images[] imgs) {

        Images[] preUpdateImgs = showImg(suspectCode);
        Images[] modified = new Images[5];

        if (preUpdateImgs != null) {
            for (int i = 0; i < preUpdateImgs.length; i++) {
                boolean updated = false;
                if (preUpdateImgs[i] != null) {
                    if (imgs != null) {
                        for (int j = 0; j < imgs.length && !updated; j++) {
                            if (imgs[j] != null) {
                                if (imgs[j].getCodeImage() != null) {
                                    if (preUpdateImgs[i].getCodeImage().intValue() == imgs[j].getCodeImage().intValue()) {
                                        if (imgs[j].getFile() != null) {
                                            FileInputStream fis = null;
                                            try {
                                                fis = new FileInputStream(imgs[j].getFile());
                                                PreparedStatement ps = null;
                                                String insert = ("update Images set Image=?,Description=?,CodeSuspect=?"
                                                        + " where codeImage=" + imgs[j].getCodeImage());
                                                ps = connection.prepareStatement(insert);
                                                ps.setBinaryStream(1, fis, (int) imgs[j].getFile().length());
                                                ps.setString(2, imgs[j].getDescription());
                                                ps.setString(3, suspectCode.toString());
                                                ps.execute();
                                            } catch (FileNotFoundException ex) {
                                                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (SQLException ex) {
                                                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                                            } finally {
                                                try {
                                                    fis.close();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        } else {
                                            if (imgs[j].getDescription() != null) {
                                                try {
                                                    Statement s2 = connection.createStatement();
                                                    s2.executeUpdate("Update Images set Description" + "='" + imgs[j].getDescription()
                                                            + "' where CodeImage =" + imgs[j].getCodeImage());
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                        updated = true;
                                        boolean added = false;
                                        for (int k = 0; k < modified.length && !added; k++) {
                                            if (modified[k] == null) {
                                                modified[k] = imgs[j];
                                                added = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (updated == false) {
                            try {
                                Statement s3 = connection.createStatement();
                                s3.executeUpdate("Delete from IMAGES where CodeImage = " + preUpdateImgs[i].getCodeImage());
                            } catch (SQLException ex) {
                                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }

        for (Images images : imgs) {
            if (images != null) {
                boolean updated = false;
                for (Images imgModified : modified) {
                    if (images == imgModified) {
                        updated = true;
                    }
                }
                if (!updated) {
                    if (images.getFile() != null) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(images.getFile());
                            PreparedStatement ps = null;
                            String insert = ("insert into Images (Image,Description,CodeSuspect)"
                                    + "values(?,?,?)");
                            ps = connection.prepareStatement(insert);
                            ps.setBinaryStream(1, fis, (int) images.getFile().length());
                            ps.setString(2, images.getDescription());
                            ps.setString(3, suspectCode.toString());
                            ps.execute();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                fis.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }

    }


    /*
    *Este metodo se encarga de almacenar en la base de datos una informacion dada de un atributo dado para un sospechosos en concreto
    *@param code: Es el codigo del sospechosos al que se le desean añadir los atributos
    *@param al:Es el arraylist con los valores que se desean añadir
     */
    public static <T> boolean addAtrivute(String code, ArrayList<T> al, String type) {
        boolean added = false;
        if (al != null) {
            try {
                Statement s = connection.createStatement();
                FileInputStream fis = null;
                PreparedStatement ps = null;
                if (type != null) {
                    switch (type) {
                        case "Phone":
                            for (int i = 0; i < al.size(); i++) {
                                Phone phone = (Phone) al.get(i);
                                s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                                        + "values (" + code + "," + phone.getPhoneNumber() + ")");
                            }
                            break;
                        case "Email":
                            for (int i = 0; i < al.size(); i++) {
                                Email email = (Email) al.get(i);
                                s.executeUpdate("INSERT into E_MAIL (CodeSuspect,Email) "
                                        + "values (" + code + ",'" + email.getEmail() + "')");
                            }
                            break;
                        case "Address":
                            for (int i = 0; i < al.size(); i++) {
                                Address address = (Address) al.get(i);
                                s.executeUpdate("INSERT into ADDRESS (CodeSuspect,Address) "
                                        + "values (" + code + ",'" + address.getAddress() + "')");
                            }
                            break;
                        case "Suspect":
                            for (int i = 0; i < al.size(); i++) {
                                Suspect suspect = (Suspect) al.get(i);
                                s.executeUpdate("INSERT into COMPANIONS (CodeSuspect,CodeSuspect2) "
                                        + "values (" + code + "," + suspect.getCodeSuspect() + ")");
                            }
                            break;
                        case "Car_Registration":
                            for (int i = 0; i < al.size(); i++) {
                                Car_Registration cr = (Car_Registration) al.get(i);
                                s.executeUpdate("INSERT into CAR_REGISTRATION (CodeSuspect,Registration_number) "
                                        + "values (" + code + ",'" + cr.getRegistration() + "')");
                            }
                            break;
                    }
                }

                added = true;
                s.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return added;
    }

    /*
    *Este metodo introduce las imagenes de un sospechoso en la base de datos
    *@param code: Es el codigo del sospechoso al cual pertenece la imagen 
    *@param al: Es el arraylist de images que pertecen al sospechoso y que se desean añadir
     */
    private static boolean addImage(String code, ArrayList<Images> al) {
        boolean added = false;
        String ruta = null;
        FileInputStream fis = null;
        try {
            PreparedStatement ps = null;
            for (int i = 0; i < al.size(); i++) {
                try {
                    if (al.get(i) != null) {
                        if (al.get(i).getFile() != null) {
                            fis = new FileInputStream(al.get(i).getFile());
                            String insert = ("insert into Images (Image,Description,CodeSuspect)"
                                    + "values(?,?,?)");
                            added = true;
                            ps = connection.prepareStatement(insert);
                            ps.setBinaryStream(1, fis, (int) al.get(i).getFile().length());
                            ps.setString(2, al.get(i).getDescription());
                            Image img;
                            ps.setString(3, code);
                            ps.execute();

                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Query.class
                            .getName()).log(Level.SEVERE, null, ex);
                    added = false;
                }
            }
            if (fis != null) {
                fis.close();
            }
            if (ps != null) {
                ps.close();

            }
        } catch (Exception ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    /*
    *Este metodo permite añadir un sospechoso desde cero, pudiendo recibir campos nulos en aquellos que puedan serlo en la base de datos
    *@param suspect: Es un sospechosos el cual se desea añadir
     */
    public static boolean addSuspect(Suspect suspect) {
        boolean correct = false;

        if (suspect != null) {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("INSERT INTO SUSPECT (" + setInsertQuery(suspect));

                String last = findLast();

                correct = Query.<Phone>addAtrivute(last, suspect.getPhone(), "Phone");
                correct = Query.<Email>addAtrivute(last, suspect.getEmail(), "Email");
                correct = Query.<Address>addAtrivute(last, suspect.getAddress(), "Address");
                correct = Query.<Suspect>addAtrivute(last, suspect.getCompanions(), "Suspect");
                correct = Query.<Car_Registration>addAtrivute(last, suspect.getCar_registration(), "Car_Registration");
                correct = Query.addImage(last, new ArrayList<>(Arrays.asList(suspect.getImages())));
                s.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName() + "--").log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return correct;
    }

    private static String setInsertQuery(Suspect suspect) {
        StringBuilder query = new StringBuilder();
        query.append(suspect.getName() != null ? "name," : "");
        query.append(suspect.getLastname1() != null ? "lastname1," : "");
        query.append(suspect.getLastname2() != null ? "lastname2," : "");
        query.append(suspect.getRecord() != null ? "record," : "");
        query.append(suspect.getFacts() != null ? "facts," : "");
        if (query.length() > 0) {
            query.deleteCharAt(query.length() - 1);//Elimina la coma
            query.append(") values (");
            query.append(suspect.getName() != null ? "'" + suspect.getName() + "'," : "");
            query.append(suspect.getLastname1() != null ? "'" + suspect.getLastname1() + "'," : "");
            query.append(suspect.getLastname2() != null ? "'" + suspect.getLastname2() + "'," : "");
            query.append(suspect.getRecord() != null ? "'" + suspect.getRecord() + "'," : "");
            query.append(suspect.getFacts() != null ? "'" + suspect.getFacts() + "'," : "");
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        } else {
            query.append("name, lastname1, lastname2, record, facts) values (null,null,null,null,null)");
        }

        return query.toString();
    }

    /*
    *Este metodo busca un sospechoso en la base de datos a partir de su codigo y 
    devuleve el resultado en forma de Sospechoso 
     */
    public static Suspect findSuspect(Integer code) {
        Suspect sus = null;
        try {
            String name = null;
            String lastname1 = null;
            String lastname2 = null;
            String Record = null;
            String Facts = null;
            ArrayList<Phone> ph = new ArrayList<>();
            Phone p;
            ArrayList<Suspect> as = new ArrayList<>();
            Suspect suspect;
            ArrayList<Email> em = new ArrayList<>();
            Email email;
            ArrayList<Address> ad = new ArrayList<>();
            Address address;
            ArrayList<Car_Registration> cr = new ArrayList<>();
            Car_Registration cregistration;
            Images[] img = new Images[5];
            Images images;

            Statement s = connection.createStatement();
            rs = s.executeQuery("Select name,lastname1,lastname2,Record,Facts "
                    + "from Suspect where CodeSuspect=" + code);
            Integer codeSuspect = code;
            if (rs.last()) {
                name = rs.getString(1);
                lastname1 = rs.getString(2);
                lastname2 = rs.getString(3);
                Record = rs.getString(4);
                Facts = rs.getString(5);
            }
            rs = s.executeQuery("Select CodePhone,PhoneNumber from PHONE "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                p = new Phone(Integer.valueOf(code), rs.getInt(1), rs.getInt(2));
                ph.add(p);
            }
            rs = s.executeQuery("Select CodeSuspect2 from COMPANIONS "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                suspect = new Suspect(rs.getInt(1), null, null, null, null, null, null, null, null, null, null, null);
                as.add(suspect);
            }
            rs = s.executeQuery("Select CodeE_mail,Email from E_MAIL "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                email = new Email(rs.getInt(1), Integer.valueOf(code), rs.getString(2));
                em.add(email);
            }
            rs = s.executeQuery("Select CodeAddress,Address from ADDRESS "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                address = new Address(rs.getInt(1), Integer.valueOf(code), rs.getString(2));
                ad.add(address);
            }
            rs = s.executeQuery("Select Registration_number, CodeRegistration from CAR_REGISTRATION "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                cregistration = new Car_Registration(rs.getString(1), rs.getInt(2));
                cr.add(cregistration);
            }

            s.close();
            rs.close();

            sus = new Suspect(code, name, lastname1, lastname2, as, Record, Facts, ph, em, ad, cr, null);

        } catch (Exception ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return sus;
    }

    public static int getSuspectNPhotos(Integer id) {
        int nPhotos = 0;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("SELECT count(*) from Images where codeSuspect = " + id);
            if (rs.next()) {
                nPhotos = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return nPhotos;
    }

    public static Suspect[] getTenSpecificSuspects(Integer[] tenSuspectCodes) {
        Suspect[] relatedSuspects = null;
        boolean endOfList = false;

        if (tenSuspectCodes != null) {
            relatedSuspects = new Suspect[numberOfSuspects];
            for (int i = 0; i < numberOfSuspects && !endOfList; i++) {
                if (tenSuspectCodes[i] != null) {
                    relatedSuspects[i] = findSuspect(tenSuspectCodes[i]);
                    relatedSuspects[i].setImages(showImg(tenSuspectCodes[i]));
                } else {
                    endOfList = true;
                }
            }
        }

        return relatedSuspects;
    }

    /*
    *Este metodo muestra 10 sospechos de la base de datos
    *@return show: Son los 10 sospechosos mostrados, en caso de que no haya doce en el grupo que se esta
    mirando habra valores nulos
     */
    public static Suspect[] showTen() {
        Suspect[] show = new Suspect[numberOfSuspects];
        try {
            ResultSet rs2;
            Statement s = connection.createStatement();
            rs2 = s.executeQuery("Select CodeSuspect from SUSPECT limit " + currentPosition + ", 10");
            int j = 0;
            if (rs2 != null) {
                for (int i = currentPosition; i < maxPosition && rs2.next(); i++) {
                    show[j] = findSuspect(rs2.getInt(1));
                    j++;
                }
            }
            s.close();
            rs2.close();
            if (show[0] != null) {
                lastTen = show;
            } else {
                show = lastTen;
                currentPosition -= 10;

            }

        } catch (Exception ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return show;
    }

    /*
    *Este metodo se usa para, una vez mostrando 10 sospechos pasar a los 10 siguientes de la base de datos
    *@return show:Son los 10 sospechosos mostrados, en caso de que no haya doce en el grupo que se esta
    mirando habra valores nulos
     */
    public static Suspect[] showNext() {
        Suspect[] show = new Suspect[numberOfSuspects];
        currentPosition += numberOfSuspects;
        maxPosition += numberOfSuspects;
        show = showTen();
        return show;
    }

    /*
    *Este metodo se usa para, una vez mostrando 10 sospechos pasar a los 10 anteriores de la base de datos
    *@return show:Son los 10 sospechosos mostrados, en caso de que no haya doce en el grupo que se esta
    mirando habra valores nulos
     */
    public static Suspect[] showPrevious() {
        Suspect[] show = new Suspect[numberOfSuspects];
        currentPosition -= numberOfSuspects;
        maxPosition -= numberOfSuspects;
        show = showTen();
        return show;
    }

    /*
    *@param sus: El sospechoso del que se desean las fotos
    *@return rs: es el resulset el cual contiene las fotografias del sospechosos junto a su descripcion
     */
    public static Images[] showImg(Integer sus) {
        Images[] imgs = new Images[5];
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("SELECT Image,CodeImage, Description,"
                    + "CodeSuspect FROM IMAGES "
                    + "where CodeSuspect=" + sus.toString());
            if (rs != null) {
                int j = 0;
                for (int i = 0; i < maxPosition && rs.next(); i++, j++) {
                    Blob blob = rs.getBlob("Image");
                    InputStream in = blob.getBinaryStream();
                    BufferedImage image = ImageIO.read(in);
                    imgs[i] = new Images((Image) image, null);
                    imgs[i].setCodeImage(rs.getInt(2));
                    imgs[i].setDescription(rs.getString(3));
                    imgs[i].setCodeSuspect(rs.getInt(4));
                }
            }
            s.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return imgs;
    }

    public static ArrayList<Integer> searchRelations(HashMap<String, ArrayList<String>> fieldAndValues) {
        coincidences = new ArrayList<>();

        try {
            if (fieldAndValues != null) {
                if (!fieldAndValues.isEmpty()) {
                    ResultSet rs = null;
                    Statement s = connection.createStatement();
                    String query = createQuery(fieldAndValues);
                    if (query != null) {
                        rs = s.executeQuery(createQuery(fieldAndValues));
                        while (rs.next()) {
                            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                                coincidences.add(rs.getInt(1));
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return coincidences;
    }

    private static String createQuery(HashMap<String, ArrayList<String>> fieldAndValues) {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, ArrayList<String>> entry : fieldAndValues.entrySet()) {
            query.append("Select codeSuspect from ");
            switch (entry.getKey()) {
                case "LASTNAME1":
                    query.append("suspect where LASTNAME1 like (");
                    break;
                case "LASTNAME2":
                    query.append("suspect where LASTNAME2 like (");
                    break;
                case "FACTS":
                    query.append("suspect where FACTS like (");
                    break;
                case "RECORD":
                    query.append("suspect where RECORD like (");
                    break;
                case "PHONE":
                    query.append("PHONE where PHONENUMBER in (");
                    break;
                case "E_MAIL":
                    query.append("E_MAIL where EMAIL like (");
                    break;
                case "ADDRESS":
                    query.append("ADDRESS where ADDRESS like (");
                    break;
            }

            for (String value : entry.getValue()) {
                if (!entry.getKey().equals("PHONE")) {
                    query.append("'%" + value + "%'");
                } else {
                    query.append(value);
                }
                query.append(",");
            }
            query.deleteCharAt(query.length() - 1);//Elimino la coma del último campo
            query.append(")");
            query.append("UNION ");
        }

        if (query.length() > 0) {
            query.delete((query.length() - 1) - ("UNION ".length() - 1), query.length());
        }

        return query.toString();
    }
    
    private static void updateMultipleFields(Suspect sus) {

        if (sus.getPhone() != null) {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("Delete from Phone where CodeSuspect = " + sus.getCodeSuspect());
                for (Phone phone : sus.getPhone()) {
                    s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                            + "values (" + sus.getCodeSuspect() + "," + phone.getPhoneNumber() + ")");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sus.getEmail() != null) {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("Delete from E_mail where CodeSuspect = " + sus.getCodeSuspect());
                for (Email email : sus.getEmail()) {
                    s.executeUpdate("INSERT into E_mail (CodeSuspect,Email) "
                            + "values ('" + sus.getCodeSuspect() + "','" + email.getEmail() + "')");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sus.getAddress() != null) {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("Delete from Address where CodeSuspect = " + sus.getCodeSuspect());
                for (Address address : sus.getAddress()) {
                    s.executeUpdate("INSERT into Address (CodeSuspect,Address) "
                            + "values ('" + sus.getCodeSuspect() + "','" + address.getAddress() + "')");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sus.getCar_registration() != null) {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("Delete from Car_Registration where CodeSuspect = " + sus.getCodeSuspect());
                for (Car_Registration cr : sus.getCar_registration()) {
                    s.executeUpdate("INSERT into Car_Registration (CodeSuspect,Registration_Number) "
                            + "values ('" + sus.getCodeSuspect() + "','" + cr.getRegistration() + "')");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Query.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String clobStringConversion(Clob clb) {
        if (clb == null) {
            return "";
        }

        StringBuffer str = new StringBuffer();
        String strng;

        BufferedReader bufferRead;
        try {
            bufferRead = new BufferedReader(clb.getCharacterStream());
            while ((strng = bufferRead.readLine()) != null) {
                str.append(strng);

            }
        } catch (IOException ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Query.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return str.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.*;

/**
 *
 * @author amgal
 */
public class Query {

    static Connection c = Connect.getMyConnection();
    static ResultSet rs;
    static int maxPosition = 11;
    static int currentPosition = 1;
    static int numberOfSuspects = 10;

    /*
    *@return last: es un String que contiene el codigo de sospechoso del ultimo 
    registro introducido en la base de datos
    *Este metodo nos permite optener el codigo de sospechosos del utimo sospechosos 
    añadido a la base de datos 
     */
    private static String findLast() {
        String last = null;
        try {
            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
            rs = s.executeQuery("SELECT CodeSuspect from SUSPECT");
            if (rs.last()) {
                last = rs.getString(1);
            }
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return last;
    }

    /*
    *Este metodo borrar un sospechoso
    *@param sus: Es el sospechoso que se desea eliminar
     */
    public static boolean deleteSuspect(Integer sus) {
        boolean deleted = false;
        try {
            if (sus != null) {
                Connect.startConnection();
                c = Connect.getMyConnection();
                Statement s = c.createStatement();
                s.executeUpdate("Delete from Suspect where CodeSuspect = " + sus.toString());
                Connect.closeConnection();
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
            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
            if (!type.equals("PhoneNumber")) {
                s.executeUpdate("Update " + table + " set " + type + "='" + value + "' where " + key + "=" + code);
                updated = true;
            } else {
                s.executeUpdate("Update " + table + " set " + type + "=" + value + " where " + key + "=" + code);
                updated = true;
            }
            s.close();
            Connect.closeConnection();
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
            if (sus.getSuspect() != null) {
                for (int i = 0; i < sus.getSuspect().size(); i++) {
                    if (sus.getSuspect().get(i) != null) {
                        updated = updateAttribute("CodeSuspect2", sus.getCodeSuspect().toString(), sus.getSuspect().get(i).toString(), "COMPANIONS", "CodeSuspect");
                    }
                }
            }
            if (sus.getPhone() != null) {
                try {
                    Connect.startConnection();
                    c = Connect.getMyConnection();
                    Statement s = c.createStatement();
                    for (int i = 0; i < sus.getPhone().size(); i++) {
                        if (i < preUpdate.getPhone().size()) {
                            System.out.println(preUpdate.getPhone().get(i).getCodePhone());
                            s.executeUpdate("Update PHONE set PhoneNumber = " + sus.getPhone().get(i).getPhoneNumber() + " where "
                                    + "CodePhone=" + preUpdate.getPhone().get(i).getCodePhone());
                        } else {
                            ArrayList<Phone> phones = new ArrayList<>();
                            phones.add(sus.getPhone().get(i));
                            Query.<Phone>addAtrivute(preUpdate.getCodeSuspect().toString(), phones, "Phone");
                        }
                    }
                    s.close();
                    rs.close();
                    Connect.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sus.getEmail() != null) {
                try {
                    Connect.startConnection();
                    c = Connect.getMyConnection();
                    Statement s = c.createStatement();
                    for (int i = 0; i < sus.getEmail().size(); i++) {
                        if (i < preUpdate.getEmail().size()) {
                            if (sus.getEmail().get(i).getEmail().equals("")) {
                                s.executeUpdate("Update E_MAIL set Email = null where "
                                        + "CodeE_mail=" + preUpdate.getEmail().get(i).getCodeEmail());
                            } else {
                                s.executeUpdate("Update E_MAIL set Email = '" + sus.getEmail().get(i).getEmail() + "' where "
                                        + "CodeE_mail=" + preUpdate.getEmail().get(i).getCodeEmail());
                            }
                        } else {
                            ArrayList<Email> emails = new ArrayList<>();
                            emails.add(sus.getEmail().get(i));
                            Query.<Email>addAtrivute(preUpdate.getCodeSuspect().toString(), emails, "Email");
                        }
                    }
                    s.close();
                    rs.close();
                    Connect.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sus.getAddress() != null) {
                try {
                    Connect.startConnection();
                    c = Connect.getMyConnection();
                    Statement s = c.createStatement();
                    for (int i = 0; i < sus.getAddress().size(); i++) {
                        if (i < preUpdate.getAddress().size()) {
                            if (sus.getAddress().get(i).getAddress().equals("")) {
                                s.executeUpdate("Update ADDRESS set Address = null where "
                                        + "CodeAddress=" + preUpdate.getAddress().get(i).getCodeAddress());
                            } else {
                                s.executeUpdate("Update ADDRESS set Address = '" + sus.getAddress().get(i).getAddress() + "' where "
                                        + "CodeAddress=" + preUpdate.getAddress().get(i).getCodeAddress());
                            }
                        } else {
                            ArrayList<Address> address = new ArrayList<>();
                            address.add(sus.getAddress().get(i));
                            Query.<Address>addAtrivute(preUpdate.getCodeSuspect().toString(), address, "Address");
                        }
                    }
                    s.close();
                    rs.close();
                    Connect.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sus.getCar_registration() != null) {
                try {
                    Connect.startConnection();
                    c = Connect.getMyConnection();
                    Statement s = c.createStatement();
                    for (int i = 0; i < sus.getCar_registration().size(); i++) {
                        if (i < preUpdate.getCar_registration().size()) {
                            if (sus.getCar_registration().get(i).getRegistration().equals("")) {
                                s.executeUpdate("Update CAR_REGISTRATION set Registration_number = null where "
                                        + "CodeRegistration=" + sus.getCar_registration().get(i).getCodeRegistration());
                            } else {
                                s.executeUpdate("Update CAR_REGISTRATION set Registration_number = '"
                                        + sus.getCar_registration().get(i).getRegistration() + "' where "
                                        + "CodeRegistration=" + sus.getCar_registration().get(i).getCodeRegistration());
                            }
                        } else {
                            ArrayList<Car_Registration> cr = new ArrayList<>();
                            cr.add(sus.getCar_registration().get(i));
                            Query.<Car_Registration>addAtrivute(preUpdate.getCodeSuspect().toString(), cr, "Car_Registration");
                        }
                    }
                    s.close();
                    rs.close();
                    Connect.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sus.getImages() != null) {
                for (int i = 0; i < sus.getImages().size(); i++) {
                    if (sus.getImages().get(i) != null) {
                        if (i < preUpdate.getImages().size()) {
                            updateImage(preUpdate.getImages().get(i).getCodeImage().toString(), sus.getImages().get(i));
                        } else {
                            ArrayList<Images> img = new ArrayList<>();
                            img.add(sus.getImages().get(i));
                            Query.addImage(preUpdate.getCodeSuspect().toString(), img);
                        }
                    }
                }
            }

        }
        return updated;
    }

    private static boolean updateImage(String code, Images img) {
        boolean added = false;
        String update = "Update Images set ? = ? where CodeImage=" + code;
        FileInputStream fis = null;
        try {
            Connect.startConnection();
            c = Connect.getMyConnection();
            PreparedStatement ps = null;
            if (img.getFile() != null) {
                fis = new FileInputStream(img.getFile());
                ps = c.prepareStatement(update);
                ps.setString(1, "Image");
                ps.setString(2, "null");
                ps.execute();
                ps.setString(1, "Image");
                ps.setBinaryStream(2, fis, img.getFile().length());
                ps.execute();
                ps.setString(1, "Description");
                ps.setString(2, img.getDescription());
                ps.execute();
                c.close();
            } else {
                Statement s = c.createStatement();
                s.execute("Delete from IMAGES where CodeImage=" + code.toString());
                s.close();
                c.close();
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

    /*
    *Este metodo se encarga de almacenar en la base de datos una informacion dada de un atributo dado para un sospechosos en concreto
    *@param code: Es el codigo del sospechosos al que se le desean añadir los atributos
    *@param al:Es el arraylist con los valores que se desean añadir
     */
    public static <T> boolean addAtrivute(String code, ArrayList<T> al, String type) {
        boolean added = false;
        if (al != null) {
            try {
                Connect.startConnection();
                c = Connect.getMyConnection();
                Statement s = c.createStatement();
                FileInputStream fis = null;
                PreparedStatement ps = null;
                if (type != null) {
                    switch (type) {
                        case "Phone":
                            for (int i = 0; i < al.size(); i++) {
                                Phone phone = (Phone) al.get(i);
                                if (al.get(i).equals("")) {
                                    s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                                            + "values (" + code + ",null)");
                                } else {
                                    s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                                            + "values (" + code + "," + phone.getPhoneNumber() + ")");
                                }

                                rs = s.executeQuery("SELECT CodePhone from PHONE");
                                if (rs.last()) {
                                    phone.setCodePhone(rs.getInt(1));
                                    al.set(i, (T) phone);
                                }
                            }
                            break;
                        case "Email":
                            for (int i = 0; i < al.size(); i++) {
                                Email email = (Email) al.get(i);
                                s.executeUpdate("INSERT into E_MAIL (CodeSuspect,Email) "
                                        + "values (" + code + ",'" + email.getEmail() + "')");
                                rs = s.executeQuery("SELECT CodeE_mail from E_MAIL");
                                if (rs.last()) {
                                    email.setCodeEmail(rs.getInt(1));
                                    al.set(i, (T) email);
                                }
                            }
                            break;
                        case "Address":
                            for (int i = 0; i < al.size(); i++) {
                                Address address = (Address) al.get(i);
                                s.executeUpdate("INSERT into ADDRESS (CodeSuspect,Address) "
                                        + "values (" + code + ",'" + address.getAddress() + "')");
                                rs = s.executeQuery("SELECT CodeAddress from ADDRESS");
                                if (rs.last()) {
                                    address.setCodeAddress(rs.getInt(1));
                                    al.set(i, (T) address);
                                }
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
                                rs = s.executeQuery("SELECT CodeRegistration from CAR_REGISTRATION");
                                if (rs.last()) {
                                    cr.setCodeRegistration(rs.getInt(1));
                                    al.set(i, (T) cr);
                                }
                            }
                            break;
                    }
                }

                added = true;
                s.close();
                rs.close();
                Connect.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return added;
    }

    private static boolean addImage(String code, ArrayList<Images> al) {
        boolean added = true;
        String ruta = null;
        FileInputStream fis = null;
        try {
            Connect.startConnection();
            c = Connect.getMyConnection();
            PreparedStatement ps = null;
            for (int i = 0; i < al.size(); i++) {
                try {
                    if (al.get(i).getFile() != null) {
                        fis = new FileInputStream(al.get(i).getFile());
                        String insert = ("insert into Images (Image,Description,CodeSuspect)"
                                + "values(?,?,?)");
                        ps = c.prepareStatement(insert);
                        ps.setBinaryStream(1, fis, (int) al.get(i).getFile().length());
                        ps.setString(2, al.get(i).getDescription());
                        Image img;
                        ps.setString(3, code);
                        ps.execute();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                    added = false;
                }
            }
            if (fis != null) {
                fis.close();
            }
            if (ps != null) {
                ps.close();
            }
            c.close();
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return added;
    }

    /*
    *Este metodo permite añadir un sospechoso desde cero, pudiendo recibir campos nulos en aquellos que puedan serlo en la base de datos
    *@param attributes: Es un array con los atributos del sospechosos, puede tener campos null que seran guardados asi en la base de datos, 
    ademas debe de estar guardado en el orden (nombre,primer apellido, segundo apellido,numero(s) de telefono,direcion(es) de correo elctronico,
    direcion(es),compañero(s),matricula(s),imagen(s)
     */
    public static boolean addSuspect(Suspect suspect) {
        boolean correct = false;
        try {
            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO SUSPECT (name,lastname1, lastname2, Record,Facts) "
                    + "values ('" + suspect.getName() + "','" + suspect.getLastname1() + "','"
                    + suspect.getLastname2() + "','" + suspect.getRecord() + "','" + suspect.getFacts() + "')");

            String last = findLast();
            correct = Query.<Phone>addAtrivute(last, suspect.getPhone(), "Phone");
            correct = Query.<Email>addAtrivute(last, suspect.getEmail(), "Email");
            correct = Query.<Address>addAtrivute(last, suspect.getAddress(), "Address");
            correct = Query.<Suspect>addAtrivute(last, suspect.getSuspect(), "Suspect");
            correct = Query.<Car_Registration>addAtrivute(last, suspect.getCar_registration(), "Car_Registration");
            correct = Query.addImage(last, suspect.getImages());
            s.close();
            rs.close();
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName() + "--").log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correct;
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
            ArrayList<Images> img = new ArrayList<>();
            Images images;

            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
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
            rs = s.executeQuery("Select Image,CodeImage,Description from IMAGES "
                    + "where CodeSuspect=" + code);
            while (rs.next()) {
                Blob blob = rs.getBlob(1);
                byte[] data = blob.getBytes(1, (int) blob.length());
                BufferedImage image = null;
                image = ImageIO.read(new ByteArrayInputStream(data));
                ImageIcon imageICON = new ImageIcon(image);
                images = new Images(rs.getInt(2), rs.getString(3), code, imageICON);
                img.add(images);
            }
            s.close();
            rs.close();
            Connect.closeConnection();

            sus = new Suspect(code, name, lastname1, lastname2, as, Record, Facts, ph, em, ad, cr, img);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sus;
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
            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
            rs2 = s.executeQuery("Select CodeSuspect from SUSPECT");
            if (rs2 != null) {
                int j = 0;
                for (int i = currentPosition; i < maxPosition && rs2.next(); i++, j++) {
                    show[j] = findSuspect(rs2.getInt(1));
                }
            }
            s.close();
            rs2.close();
            c.close();
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
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
    public static ResultSet showImg(Integer sus) {
        Images[] imgs = new Images[5];
        try {
            Connect.startConnection();
            c = Connect.getMyConnection();
            Statement s = c.createStatement();
            rs = s.executeQuery("SELECT Image, Description FROM IMAGES "
                    + "where CodeSuspect=" + sus.toString());
            if (rs != null) {
                int j = 0;
                for (int i = 0; i < maxPosition && rs.next(); i++, j++) {
                    Blob blob = rs.getBlob("Image");
                    InputStream in = blob.getBinaryStream();
                    BufferedImage image = ImageIO.read(in);
                    imgs[i].setImage(image, null);
                    imgs[i].setCodeImage(rs.getInt(2));
                    imgs[i].setDescription(rs.getString(3));//ES un blob
                    imgs[i].setCodeSuspect(rs.getInt(4));
                }
            }
            s.close();
            rs.close();
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /*
    *Este metode permite realizar una consulta en la base de datos buscando con por un valor dado de un paramatro concreto
    *@param key: Es tipo de campo por el cual se esta buscando (name,lastname1,lastname2,Phonenumber,Email,Registration_number,
    Address,Companions
    *@param value: Es el valor por el que se realiza la busqueda
    *@return sus: Es el arraylist de los sospechosos  resultado de la consulta
     */
    public static ArrayList<Suspect> searchBy(String key, String value) {
        ArrayList<Suspect> sus = new ArrayList<>();
        try {
            if(value.equals("")){
                Connect.startConnection();
                c = Connect.getMyConnection();
                Statement s=c.createStatement();
                switch (key) {

                    case "name":
                    case "lastname1":
                    case "lastname2":
                        rs = s.executeQuery("Select CodeSuspect from Suspect "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                    case "PhoneNumber":
                        System.out.println("entra");
                        rs = s.executeQuery("Select CodeSuspect from PHONE "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                    case "Email":
                        rs = s.executeQuery("Select CodeSuspect from E_MAIL "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                    case "Registration_number":
                        rs = s.executeQuery("Select CodeSuspect from CAR_REGISTRATION "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                    case "Address":
                        rs = s.executeQuery("Select CodeSuspect from ADDRESS "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                    case "Companions":
                        rs = s.executeQuery("Select CodeSuspect from COMPANIONS "
                                + "where " + key + "='" + value + "'");
                        while (rs.next()) {
                            sus.add(Query.findSuspect(rs.getInt(1)));
                        }
                        break;
                }
                s.close();
                rs.close();
                Connect.closeConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sus;
    }

    public static HashMap<Suspect, ArrayList<String>> findCoincidences(Suspect sus) {
        HashMap<Suspect, ArrayList<String>> coincidences = new HashMap<>();
        ArrayList<Suspect> suspects = new ArrayList<>();
        ArrayList<String> atributtes = new ArrayList<>();
        //String code = sus.getCodeSuspect().toString();
        //lastname1,lastname2,phone,email,address,registration,suspect
        ArrayList<Suspect> fln = Query.searchBy("lastname1", sus.getName());
        for (int i = 0; i < fln.size(); i++) {
            coincidences.put(fln.get(i), atributtes);
            coincidences.get(fln.get(i)).add("lastname1");
        }
        ArrayList<Suspect> sln = Query.searchBy("lastname2", sus.getLastname2());

        Query.add(sln, suspects);
        for (int i = 0; i < suspects.size(); i++) {
            if (coincidences.containsKey(suspects.get(i))) {
                coincidences.get(fln.get(i)).add("lastname2");
            } else {
                coincidences.put(suspects.get(i), atributtes);
                coincidences.get(fln.get(i)).add("lastname2");
            }
        }
        for (int i = 0; i < sus.getPhone().size(); i++) {
            ///////////////////////////////
            ArrayList<Suspect> ph = Query.searchBy("PhoneNumber", sus.getPhone().get(i).getPhoneNumber().toString());
            Query.add(ph, suspects);
            for (int j = 0; j < suspects.size(); j++) {
                if (coincidences.containsKey(suspects.get(j))) {
                    coincidences.get(fln.get(j)).add("PhoneNumber");
                } else {
                    coincidences.put(suspects.get(j), atributtes);
                    coincidences.get(fln.get(j)).add("PhoneNumber");
                }
            }
        }
        for (int i = 0; i < sus.getEmail().size(); i++) {
            ArrayList<Suspect> em = Query.searchBy("Email", sus.getEmail().get(i).getEmail());
            Query.add(em, suspects);
            for (int j = 0; j < suspects.size(); j++) {
                if (coincidences.containsKey(suspects.get(j))) {
                    coincidences.get(fln.get(j)).add("Email");
                } else {
                    coincidences.put(suspects.get(j), atributtes);
                    coincidences.get(fln.get(j)).add("Email");
                }
            }
        }
        for (int i = 0; i < sus.getCar_registration().size(); i++) {
            ArrayList<Suspect> cr = Query.searchBy("Registration_number", sus.getCar_registration().get(i).getRegistration());
            Query.add(cr, suspects);
            for (int j = 0; j < suspects.size(); j++) {
                if (coincidences.containsKey(suspects.get(j))) {
                    coincidences.get(fln.get(j)).add("Registration_number");
                } else {
                    coincidences.put(suspects.get(j), atributtes);
                    coincidences.get(fln.get(j)).add("Registration_number");
                }
            }
        }
        for (int i = 0; i < sus.getAddress().size(); i++) {
            ArrayList<Suspect> ad = Query.searchBy("Address", sus.getAddress().get(i).getAddress());
            Query.add(ad, suspects);
            for (int j = 0; j < suspects.size(); j++) {
                if (coincidences.containsKey(suspects.get(j))) {
                    coincidences.get(fln.get(j)).add("Address");
                } else {
                    coincidences.put(suspects.get(j), atributtes);
                    coincidences.get(fln.get(j)).add("Address");
                }
            }
        }
        for (int i = 0; i < sus.getSuspect().size(); i++) {
            ArrayList<Suspect> com = Query.searchBy("Companions", sus.getSuspect().get(i).getCodeSuspect().toString());
            Query.add(com, suspects);
            for (int j = 0; j < suspects.size(); j++) {
                if (coincidences.containsKey(suspects.get(j))) {
                    coincidences.get(fln.get(j)).add("Companions");
                } else {
                    coincidences.put(suspects.get(j), atributtes);
                    coincidences.get(fln.get(j)).add("Companions");
                }
            }
        }
        return coincidences;
    }

    private static void add(ArrayList<Suspect> toCheck, ArrayList<Suspect> saved) {
        Boolean added = false;
        for (int i = 0; i < toCheck.size(); i++) {
            for (int j = 0; j < saved.size() && !added; j++) {
                if (toCheck.get(i).getCodeSuspect() == saved.get(j).getCodeSuspect()) {
                    added = true;
                }
            }
            if (!added) {
                toCheck.add(saved.get(i));
                added = true;
            }
        }
    }
}

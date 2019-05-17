/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.sql.Blob;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author amgal
 */
public class Query {
    
    static Connection c=Connect.getMyConnection();
    static  ResultSet rs;
    static int maxPosition=11;
    static int currentPosition=1;
    static int numberOfSuspects=10;
    
    /*
    *@return last: es un String que contiene el codigo de sospechoso del ultimo 
    registro introducido en la base de datos
    *Este metodo nos permite optener el codigo de sospechosos del utimo sospechosos 
    añadido a la base de datos 
    */
    private static String findLast(){
        String last=null;
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            rs=s.executeQuery("SELECT CodeSuspect from SUSPECT");
            if(rs.last()){
                last=rs.getString(1);
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
    public static boolean deleteSuspect(Integer sus){
        boolean deleted=false;
        try {
            if(sus!=null){
                Connect.startConnection();
                c=Connect.getMyConnection();
                Statement s=c.createStatement();
                s.executeUpdate("Delete from Suspect where CodeSuspect = "+sus.toString());
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
    private static boolean updateAttribute(String type,String code,String value,
    String table, String key){
        boolean updated=false;
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            s.executeUpdate("Update "+table+" set "+type+"='"+value+"' where "+key+"='"+code+"'");
            updated=true;
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
    public static boolean Update(Suspect sus){
        boolean updated=false;
        Suspect preUpdate=Query.find(sus.getCodeSuspect().toString());
                if(sus!=null){
                    if(!sus.getName().equals(preUpdate.getName())){
                        updated=updateAttribute("Name",sus.getCodeSuspect().toString(),sus.getName(),"Suspect","CodeSuspect");
                    }
                    if(!sus.equals(preUpdate.getLastname1())){
                        updated=updateAttribute("Lastname1",sus.getCodeSuspect().toString(),sus.getLastname1(),"Suspect","CodeSuspect");
                    }
                    if(!sus.equals(preUpdate.getLastname2())){
                        updated=updateAttribute("Lastname2",sus.getCodeSuspect().toString(),sus.getLastname2(),"Suspect","CodeSuspect");
                    }
                    if(!sus.getRecord().equals(preUpdate.getRecord())){
                        updated=updateAttribute("Record",sus.getCodeSuspect().toString(),sus.getRecord().toString(),"Suspect","CodeSuspect");
                    }
                    if(!sus.getFacts().equals(preUpdate.getFacts())){
                        updated=updateAttribute("Facts",sus.getCodeSuspect().toString(),sus.getFacts().toString(),"Suspect","CodeSuspect");
                    }
                    if(sus.getSuspect()!=null){
                        for(int i=0;i<sus.getSuspect().size();i++){
                            if(sus.getSuspect().get(i)!=null){
                                updated=updateAttribute("CodeSuspect2",sus.getCodeSuspect().toString(),sus.getSuspect().get(i).toString(),"COMPANIONS","CodeSuspect");
                            }
                        }
                    }
                    if(sus.getPhone()!=null){
                        for(int i=0;i<sus.getPhone().size();i++){
                            if(sus.getPhone().get(i)!=null){
                                Phone phone=(Phone) sus.getSuspect().get(i);
                                updated=updateAttribute("PhoneNumber",phone.getCodePhone().toString(),phone.getPhoneNumber().toString(),"PHONE","CodePhone");
                            }
                        }
                    }
                    if(sus.getEmail()!=null){
                        for(int i=0;i<sus.getSuspect().size();i++){
                            if(sus.getEmail().get(i)!=null){
                                Email email=(Email) sus.getAddress().get(i);
                                updated=updateAttribute("Email", email.getCodeEmail().toString(), email.getEmail(), "E_MAIL", "CodeE_mail");
                            }
                        }
                    }
                    if(sus.getAddress()!=null){
                        for(int i=0;i<sus.getAddress().size();i++){
                            if(sus.getAddress().get(i)!=null){
                                Address address=(Address) sus.getAddress().get(i);
                                updated=updateAttribute("Address", address.getCodeAddress().toString(), address.getAddress(), "ADDRESS", "CodeAddress");
                            }
                        }
                    }
                    if(sus.getCar_Resgistration()!=null){
                        for(int i=0;i<sus.getCar_Resgistration().size();i++){
                            if(sus.getCar_Resgistration().get(i)!=null){
                                Car_Registration cRegistration=(Car_Registration) sus.getCar_Resgistration().get(i);
                                updated=updateAttribute("Registration_number", cRegistration.getCodeRegistration().toString(), cRegistration.getRegistration(), "CAR_REGISTRATION", "CodeRegistration");
                            }
                        }
                    }
                    if(sus.getImages()!=null){
                        for(int i=0;i<sus.getImages().size();i++){
                            if(sus.getImages().get(i)!=null){
                                Images img=(Images) sus.getImages().get(i);
                                updated=updateAttribute("Image", img.getCodeImage().toString(), img.getImageEncoded().toString(), "IMAGES", "CodeImage");
                                updated=updateAttribute("Description", img.getCodeImage().toString(), img.getDescription(), "IMAGES", "CodeImage");
                            }
                        }
                    }
                
                }
        
        return updated;
    }
    /*
    *Este metodo se encarga de almacenar en la base de datos una informacion dada de un atributo dado para un sospechosos en concreto
    *@param code: Es el codigo del sospechosos al que se le desean añadir los atributos
    *@param al:Es el arraylist con los valores que se desean añadir
    */
    public static boolean addAtrivute(String code,ArrayList<Object> al,String type){
        boolean added=false;
        if(al!=null){
            try {
                Connect.startConnection();
                c=Connect.getMyConnection();   
                Statement s=c.createStatement();
                if(type!=null){
                    switch(type){
                    case "Phone":
                        for(int i=0;i<al.size();i++){
                            if(al.get(i).equals("")){
                                s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                                + "values ("+code+",null)");
                            }else{
                                s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber) "
                                + "values ("+code+","+al.get(i)+")");
                            }
                        }
                        break;
                    case "Email":
                        for(int i=0;i<al.size();i++){
                            System.out.println(al.get(i));
                                s.executeUpdate("INSERT into E_MAIL (CodeSuspect,Email) "
                                + "values ("+code+",'"+al.get(i)+"')");
                        }
                        break;
                    case "Address":
                        for(int i=0;i<al.size();i++){
                                s.executeUpdate("INSERT into ADDRESS (CodeSuspect,Address) "
                                + "values ("+code+",'"+al.get(i)+"')");
                        }
                        break;
                    case "Suspect":
                        for(int i=0;i<al.size();i++){
                                s.executeUpdate("INSERT into COMPANIONS (CodeSuspect,CodeSuspect2) "
                                + "values ("+code+","+al.get(i)+")");
                        }
                        break;
                    case "Car_Registration":
                        for(int i=0;i<al.size();i++){
                                s.executeUpdate("INSERT into CAR_REGISTRATION (CodeSuspect,Registration_number) "
                                + "values ("+code+",'"+al.get(i)+"')");
                        }
                        break;
                    case "Images":
                        for(int i=0;i<al.size();i++){
                            if(al.get(i) instanceof Images){
                                Images img=(Images) al.get(i);
                                s.executeUpdate("INSERT into Images (CodeSuspect,image,description) "
                                + "values ('"+code+"','"+img.getImageEncoded()+"','"+img.getDescription()+"')");
                            }
                        }
                    }
                }
                                
                added=true;
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
  
    /*
    *Este metodo permite añadir un sospechoso desde cero, pudiendo recibir campos nulos en aquellos que puedan serlo en la base de datos
    *@param attributes: Es un array con los atributos del sospechosos, puede tener campos null que seran guardados asi en la base de datos, 
    ademas debe de estar guardado en el orden (nombre,primer apellido, segundo apellido,numero(s) de telefono,direcion(es) de correo elctronico,
    direcion(es),compañero(s),matricula(s),imagen(s)
    */
    public static boolean addSuspect(Suspect suspect){
        boolean correct=false;
            try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            s.executeUpdate("INSERT INTO SUSPECT (name,lastname1, lastname2, Record,Facts) "
            + "values ('"+suspect.getName()+"','"+suspect.getLastname1()+"','"+suspect.getLastname2()+"','"+suspect.getRecord()+"','"+suspect.getFacts()+"')");

            String last=findLast();
            correct=addAtrivute(last,suspect.getPhone(),"Phone");
            correct=addAtrivute(last,suspect.getEmail(),"Email");
            correct=addAtrivute(last,suspect.getAddress(),"Address");
            correct=addAtrivute(last,suspect.getSuspect(),"Suspect");
            correct=addAtrivute(last,suspect.getCar_Resgistration(),"Car_Registration");
            correct=addAtrivute(last, (ArrayList<Object>) suspect.getImages(),"Images");
            s.close();
            rs.close();
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()+"--").log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correct;
    }
   /*
    *Este metodo busca un sospechoso en la base de datos a partir de su codigo y 
    devuleve el resultado en forma de Sospechoso 
    */
    public static Suspect find(String code){
       Suspect sus=null;
        try {
            String name=null;
            String lastname1=null;
            String lastname2=null;
            Blob Record=null;
            Blob Facts=null;
            ArrayList<Phone> ph=new ArrayList<>();
            Phone p;
            ArrayList<Suspect> as=new ArrayList<>();
            Suspect suspect;
            ArrayList<Email> em=new ArrayList<>();
            Email email;
            ArrayList<Address> ad=new ArrayList<>();
            Address address;
            ArrayList<Car_Registration> cr=new ArrayList<>();
            Car_Registration cregistration;
            ArrayList<Images> img=new ArrayList<>();
            Images images;
            
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            rs=s.executeQuery("Select name,lastname1,lastname2,Record,Facts "
                    + "from Suspect where CodeSuspect="+code);
            String codeSuspect=code;
            if(rs.last()){
                name=rs.getString(1);
                lastname1=rs.getString(2);
                lastname2=rs.getString(3);
                Record=rs.getBlob(4);
                Facts=rs.getBlob(5);
            }
            rs=s.executeQuery("Select CodePhone,PhoneNumber from PHONE "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                p=new Phone(Integer.valueOf(code),rs.getInt(1),rs.getInt(2));
                ph.add(p);
            }
            rs=s.executeQuery("Select CodeSuspect2 from COMPANIONS "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                suspect=new Suspect(rs.getInt(1), null, null, null, null, null, null, null, null, null, null, null);
                as.add(suspect);
            }
            rs=s.executeQuery("Select CodeE_mail,Email from E_MAIL "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                email=new Email(rs.getInt(1),Integer.valueOf(code),rs.getString(2));
                em.add(email);
            }
            rs=s.executeQuery("Select CodeAddress,Address from ADDRESS "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                address=new Address(rs.getInt(1),Integer.valueOf(code),rs.getString(2));
                ad.add(address);
            }
            rs=s.executeQuery("Select Registration_number, CodeRegistration from CAR_REGISTRATION "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                cregistration=new Car_Registration(rs.getString(1),rs.getInt(2));
                cr.add(cregistration);
            }
            rs=s.executeQuery("Select Image,CodeImage,Description from IMAGES "
                    + "where CodeSuspect="+code);
            while(rs.next()){
                images=new Images(rs.getBlob(1),rs.getInt(2), rs.getString(3),Integer.valueOf(code));
                img.add(images);
            }
            s.close();
            rs.close();
            Connect.closeConnection();
            sus=new Suspect(Integer.valueOf(code), name, lastname1, lastname2, as, Record, Facts, ph, em, ad, cr, img);
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
    public static Suspect[] showTen(){
        Suspect[] show=new Suspect[numberOfSuspects];
        try {
            ResultSet rs2;
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            rs2=s.executeQuery("Select CodeSuspect from SUSPECT");
            if(rs2!=null){
                int j=0;
                for(int i=currentPosition;i<maxPosition&&rs2.next();i++,j++){
                    show[j]=find(rs2.getString(1));
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
    public static Suspect[] showNext(){
        Suspect[] show=new Suspect[numberOfSuspects];
        currentPosition+=numberOfSuspects;
        maxPosition+=numberOfSuspects;
        show=showTen();
        return show;
    }
    
    /*
    *Este metodo se usa para, una vez mostrando 10 sospechos pasar a los 10 anteriores de la base de datos
    *@return show:Son los 10 sospechosos mostrados, en caso de que no haya doce en el grupo que se esta
    mirando habra valores nulos
    */
    public static Suspect[] showPrevious(){
        Suspect[] show=new Suspect[numberOfSuspects];
        currentPosition-=numberOfSuspects;
        maxPosition-=numberOfSuspects;
        show=showTen();
        return show;
    }
    /*
    *@param sus: El sospechoso del que se desean las fotos
    *@return rs: es el resulset el cual contiene las fotografias del sospechosos junto a su descripcion
    */
    public static ResultSet showImg(Integer sus){
        Images[] imgs=new Images[5];
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();   
            Statement s=c.createStatement();
            rs=s.executeQuery("SELECT Image, Description FROM IMAGES "
                    + "where CodeSuspect="+sus.toString());
            if(rs!=null){
                int j=0;
                for(int i=0;i<maxPosition&&rs.next();i++,j++){
                    show[j]=find(rs.getString(1));
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
    Address,CodeSuspect2
    *@param value: Es el valor por el que se realiza la busqueda
    *@return sus: Es el arraylist de los sospechosos  resultado de la consulta
    */
    public static ArrayList<Suspect> searchBy(String key,String value){
        ArrayList<Suspect> sus=new ArrayList<>();
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            switch(key){
                
                case "name":
                case "lastname1":
                case "lastname2":
                    rs =s.executeQuery("Select CodeSuspect from Suspect "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
                case "PhoneNumber":
                    
                    rs =s.executeQuery("Select CodeSuspect from PHONE "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
                case "Email":
                    rs=s.executeQuery("Select CodeSuspect from E_MAIL "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
                case "Registration_number":
                    rs=s.executeQuery("Select CodeSuspect from CAR_REGISTRATION "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
                case "Address":
                    rs=s.executeQuery("Select CodeSuspect from ADDRESS "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
                case "CodeSuspect2":
                    rs=s.executeQuery("Select CodeSuspect from COMPANIONS "
                            + "where "+key+"='"+value+"'");
                    while(rs.next()){
                        sus.add(Query.find(rs.getString(1)));
                    }
                    break;
            }
            s.close();
            rs.close();
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sus;
    }
    
   public HashMap<Suspect,HashMap<String,Boolean>> findCoincidences(Suspect sus){
        HashMap<Suspect,HashMap<String,Boolean>> coincidences=null;
        Boolean[] matchs=new Boolean[8];
        String code=sus.getCodeSuspect().toString();
        ArrayList<Suspect> als=new ArrayList<>();
        //name,lastname1,lastname2,phone,email,address,registration,suspect
        als=searchBy("name",sus.getName());
        
    return coincidences;
    }
}

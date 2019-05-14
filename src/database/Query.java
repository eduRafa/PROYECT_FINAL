/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    *@param sus: Es el sospecgoso que se desea eliminar
    */
    public static boolean deleteSuspect(Suspect sus){
        boolean deleted=false;
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            s.executeQuery("Delete from Suspect where CodeSuspect="+sus.getCodeSuspect());      
            Connect.closeConnection();
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
            s.executeQuery("Update "+table+" set "+type+"='"+value+"' where "+key+"='"+code+"'");
            updated=true;
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
        ResultSet preUpdate=Query.searchBy("CodeSuspect",sus.getCodeSuspect().toString());
        try {
            if(rs.last()){
                if(sus!=null){
                    
                    if(sus.getName()!=rs.getString(1)){
                        updated=updateAttribute("Name",sus.getCodeSuspect().toString(),sus.getName(),"Suspect","CodeSuspect");
                    }
                    if(sus.getLastname1()!=rs.getString(2)){
                        updated=updateAttribute("Lastname1",sus.getCodeSuspect().toString(),sus.getLastname1(),"Suspect","CodeSuspect");
                    }
                    if(sus.getLastname2()!=rs.getString(3)){
                        updated=updateAttribute("Lastname2",sus.getCodeSuspect().toString(),sus.getLastname2(),"Suspect","CodeSuspect");
                    }
                    if(sus.getRecord()!=rs.getBlob(4)){
                        updated=updateAttribute("Record",sus.getCodeSuspect().toString(),sus.getRecord().toString(),"Suspect","CodeSuspect");
                    }
                    if(sus.getFacts()!=rs.getBlob(5)){
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
                        
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    /*
    *Este metodo se encarga de almacenar en la base de datos una informacion dada de un atributo dado para un sospechosos en concreto
    *@param code: Es el codigo del sospechosos al que se le desean añadir los atributos
    *@param al:Es el arraylist con los valores que se desean añadir
    */
    public static boolean addAtrivute(String code,ArrayList<Object> al){
        boolean added=false;
        if(al!=null){
            try {
                Connect.startConnection();
                c=Connect.getMyConnection();   
                boolean find=false;
                String type=null;
                for(int j=0;j<al.size()&&!find;j++){
                    if(al.get(j)!=null){
                        type=al.get(j).getClass().getSimpleName();
                        find=true;
                    }
                }
                
                Statement s=c.createStatement();
                if(type!=null){
                    switch(type){
                    case "Phone":
                        for(int i=0;i<al.size();i++){
                            Phone ph=(Phone) al.get(i);
                            s.executeUpdate("INSERT into PHONE (CodeSuspect,PhoneNumber)"
                            + "values ('"+code+"','"+ph.getPhoneNumber()+"')");
                        }
                        break;
                    case "Email":
                        for(int i=0;i<al.size();i++){
                            Email em=(Email) al.get(i);
                            s.executeUpdate("INSERT into E-MAIL (CodeSuspect,Email)"
                            + "values ('"+code+"','"+em.getEmail()+"')");
                        }
                        break;
                    case "Address":
                        for(int i=0;i<al.size();i++){
                            Address ad=(Address) al.get(i);
                            s.executeUpdate("INSERT into ADDRESS (CodeSuspect,Address)"
                            + "values ('"+code+"','"+ad.getAddress()+"')");
                        }
                        break;
                    case "Suspect":
                        for(int i=0;i<al.size();i++){
                            Suspect su=(Suspect) al.get(i);
                            s.executeUpdate("INSERT into COMPANIONS (CodeSuspect,CodeSuspect2)"
                            + "values ('"+code+"','"+su.getCodeSuspect()+"')");
                        }
                        break;
                    case "Car_Registration":
                        for(int i=0;i<al.size();i++){
                            Car_Registration cr=(Car_Registration) al.get(i);
                            s.executeUpdate("INSERT into CAR_REGISTRATION (CodeSuspect,Registration_number)"
                            + "values ('"+code+"','"+cr.getRegistration()+"')");
                        }
                        break;
                    case "Images":
                        for(int i=0;i<al.size();i++){
                            Images img=(Images) al.get(i);
                            s.executeUpdate("INSERT into Images (CodeSuspect,image,description)"
                            + "values ('"+code+"','"+img.getImageEncoded()+"','"+img.getDescription()+"')");
                        }
                        //getbinarystream
                    }
                }
                                
                added=true;
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
            s.executeUpdate("INSERT INTO SUSPECT (name,lastname1, lastname2, Record,Facts)"
            + "values ('"+suspect.getName()+"','"+suspect.getLastname1()+"','"+suspect.getLastname2()+"','"+suspect.getRecord()+"','"+suspect.getFacts()+"')");

            String last=findLast();
            correct=addAtrivute(last,suspect.getPhone());
            correct=addAtrivute(last,suspect.getEmail());
            correct=addAtrivute(last,suspect.getAddress());
            correct=addAtrivute(last,suspect.getSuspect());
            correct=addAtrivute(last,suspect.getCar_Resgistration());
            correct=addAtrivute(last, (ArrayList<Object>) suspect.getImages());
            Connect.closeConnection();
         
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correct;
    }
   
    /*
    * Este metodo realiza una consulta a la base de datos para mostrar todos los sospechosos guardados
    *@return rs: Es el resultset con la resuesta de la consulta al servidor, el cual contiene el registro co todos los sospechosos
    */
    public static ResultSet showAll(){
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            rs=s.executeQuery("Select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr");
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
    /*
    *@param code: El codigo de sospechoso del que se desean las fotos
    *@return rs: es el resulset el cual contiene las fotografias del sospechosos junto a su descripcion
    */
    public static ResultSet showImg(Suspect sus){
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();   
            Statement s=c.createStatement();
            rs=s.executeQuery("SELECT Image, Description FROM IMAGES"
                    + "where CodeSuspect="+sus.getCodeSuspect());
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
    *@param key: Es tipo de campo por el cual se esta buscando 
    *@param value: Es el valor por el que se realiza la busqueda
    *@return rs: Es el resultset que guarda el resultado de la consulta
    */
    public static ResultSet searchBy(String key,String value){
        try {
            Connect.startConnection();
            c=Connect.getMyConnection();
            Statement s=c.createStatement();
            switch(key){
                
                case "name":
                case "lastname1":
                case "lastname2":
                    rs =s.executeQuery("select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr"
                            + "where sus."+key+"="+value);
                    break;
                case "PhoneNumber":
                    
                    rs =s.executeQuery("select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr"
                            + "where sus.CodeSuspect=(select CodeSuspect from PHONE where "+key+"="+value+")");
                    break;
                case "Email":
                    rs=s.executeQuery("select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr"
                            + "where sus.CodeSuspect=(select CodeSuspect from EXISTS_ADDRESS where "+key+"="+value+")");
                    break;
                case "Registration_number":
                    rs=s.executeQuery("select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr"
                            + "where sus.CodeSuspect=(select CodeSuspect from CAR_REGISTRATION where "+key+"="+value+")");
                    break;
                case "CodeSuspect":
                    rs=s.executeQuery("select sus.name,sus.lastname1,sus.lastname2,sus.Record,sus.Facts,"
                            + "p.PhoneNumber, em.Email,ad.Address,cr.Registration_number"
                            + "from Suspect sus, PHONE p, E_Mail em,ADDRESS ad,CAR_REGISTRATION cr"
                            + "where sus.CodeSuspect="+value);
                    break;
            }
            Connect.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;
import java.util.ArrayList;



/**
 *
 * @author Antonio Jose Adamuz Sereno
 */
public class Suspect {
    
    private Integer CodeSuspect;
    private String name;
    private String lastname1;
    private String lastname2;
    private Blob Record;
    private Blob Facts;
    private ArrayList<Phone> Phone = new ArrayList<>();
    private ArrayList<Email> Email= new ArrayList<>();
    private ArrayList<Address> Address= new ArrayList<>();
    private ArrayList<Car_Registration> Car_registration=
            new ArrayList<>();
    private ArrayList<Images> Images= new ArrayList<>();
    private ArrayList<Suspect> Suspect= new ArrayList<>();
    
   
    
    public Suspect(Integer CodeSuspect, String name, String lastname1,
            String lastname2, ArrayList Suspect, Blob Record, Blob Facts,
            ArrayList Phone, ArrayList Email, ArrayList Address,
            ArrayList Car_registration, ArrayList Images){
            this.CodeSuspect=CodeSuspect;
            this.name=name;
            this.lastname1=lastname1;
            this.lastname2=lastname2;
            this.Record=Record;
            this.Facts=Facts;
            this.Phone=Phone;
            this.Email=Email;
            this.Address=Address;
            this.Car_registration=Car_registration;
            this.Images=Images;
            this.Suspect=Suspect;
            
           
            
    }
    
    public Integer getCodeSuspect(){
        return CodeSuspect;
    }
    
    public void setCodeSuspect(Integer CodeSuspect){
        this.CodeSuspect=CodeSuspect;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
     public String getLastname1(){
        return lastname1;
    }
    
    public void setLastname1(String lastname1){
        this.lastname1=lastname1;
    }
    
    public String getLastname2(){
        return lastname2;
    }
    
    public void setLastname2(String lastname2){
        this.lastname2=lastname2;
    }
    
    public Blob getRecord(){
        return Record;
    }
    
    public void setRecord(Blob Record){
        this.Record=Record;
    }
    
    public Blob getFacts(){
        return Facts;
    }
    
    public void setFacts(Blob Facts){
        this.Facts=Facts;
    }
    
    public ArrayList getPhone(){
        return Phone;
    }
    
    public void setPhone(ArrayList Phone){
        this.Phone=Phone;
    }
    
    public ArrayList getEmail(){
        return Email;
    } 
    
    public void setEmail(ArrayList Email){
        this.Email=Email;
    }
    
    public ArrayList getAddress(){
        return Address;
    }
    
    public void setAddress(ArrayList Address){
        this.Address=Address;
    }
    
    public ArrayList getCar_Resgistration(){
        return Car_registration;
    }
    
    public void setCar_Registration(ArrayList Car_registration){
        this.Car_registration=Car_registration;
    }
   
      public ArrayList<?> getImages(){
        return Images;
    }
    
    public void setImages(ArrayList Images){
        this.Images=Images;
    }
    
      public ArrayList getSuspect(){
        return Suspect;
    }
    
    public void setSuspect(ArrayList Suspect){
        this.Suspect=Suspect;
    }
    
}

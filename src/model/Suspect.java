/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    private String Record;
    private String Facts;
    private ArrayList<Phone> Phone = new ArrayList<>();
    private ArrayList<Email> Email= new ArrayList<>();
    private ArrayList<Address> Address= new ArrayList<>();
    private ArrayList<Car_Registration> Car_registration=
            new ArrayList<>();
    private ArrayList<Images> Images= new ArrayList<>();
    private ArrayList<Suspect> Suspect= new ArrayList<>();
    
    public Suspect(Integer CodeSuspect, String name, String lastname1,
            String lastname2, ArrayList Suspect, String Record, String Facts,
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

    public Integer getCodeSuspect() {
        return CodeSuspect;
    }

    public String getName() {
        return name;
    }

    public String getLastname1() {
        return lastname1;
    }

    public String getLastname2() {
        return lastname2;
    }

    public String getRecord() {
        return Record;
    }

    public String getFacts() {
        return Facts;
    }
    
    public void setFacts(String Facts){
        this.Facts=Facts;
    }
    
    public ArrayList<Phone> getPhone(){
        return Phone;
    }
    
    public ArrayList<Email> getEmail(){
        return Email;
    } 
    
    public void setEmail(ArrayList<Email> Email){
        this.Email=Email;
    }
    

    public ArrayList<Address> getAddress() {
        return Address;
    }
    
    public void setAddress(ArrayList<Address> address){
        this.Address=Address;
    }
    

    public ArrayList<Car_Registration> getCar_registration() {
        return Car_registration;
    }
    
    public void setCar_Registration(ArrayList<Car_Registration> car_Registration){
        this.Car_registration=Car_registration;
    }
   


    public ArrayList<Images> getImages() {
        return Images;
    }
    
    public void setImages(ArrayList<Images> img){
        this.Images=img;
    }
    
    public ArrayList<Suspect> getSuspect() {
        return Suspect;
    }
    
    public void setSuspect(ArrayList<Suspect> Suspect){
        this.Suspect=Suspect;


    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public void setRecord(String Record) {
        this.Record = Record;
    }


    public void setPhone(ArrayList<Phone> Phone) {
        this.Phone = Phone;
    }

    public void setCar_registration(ArrayList<Car_Registration> Car_registration) {
        this.Car_registration = Car_registration;
    }

}

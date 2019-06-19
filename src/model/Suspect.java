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

    private Integer codeSuspect;
    private String name;
    private String lastname1;
    private String lastname2;
    private String record;
    private String facts;
    private ArrayList<Phone> phones;
    private ArrayList<Email> emails;
    private ArrayList<Address> addresses;
    private ArrayList<Car_Registration> carRegistrations;
    private Images[] images;
    private ArrayList<Suspect> companions;

    public Suspect(Integer CodeSuspect, String name, String lastname1,
            String lastname2, ArrayList<Suspect> companions, String record, String facts,
            ArrayList<Phone> phones, ArrayList<Email> emails, ArrayList<Address> adresses,
            ArrayList<Car_Registration> carRegistrations, Images[] images) {
        this.codeSuspect = CodeSuspect;
        this.name = name;
        this.lastname1 = lastname1;
        this.lastname2 = lastname2;
        this.record = record;
        this.facts = facts;
        this.phones = phones;
        this.emails = emails;
        this.addresses = adresses;
        this.carRegistrations = carRegistrations;
        setImages(images);
        this.companions = companions;
    }

    public Integer getCodeSuspect() {
        return codeSuspect;
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
        return record;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String Facts) {
        this.facts = Facts;
    }

    public ArrayList<Phone> getPhone() {
        return phones;
    }

    public ArrayList<Email> getEmail() {
        return emails;
    }

    public void setEmail(ArrayList<Email> emails) {
        this.emails = emails;
    }

    public ArrayList<Address> getAddress() {
        return addresses;
    }

    public void setAddress(ArrayList<Address> address) {
        this.addresses = address;
    }

    public ArrayList<Car_Registration> getCar_registration() {
        return carRegistrations;
    }

    public void setCar_Registration(ArrayList<Car_Registration> car_Registration) {
        this.carRegistrations = car_Registration;
    }

    public Images[] getImages() {
        return images;
    }

    public void setImages(Images[] img) {
        if (img != null) {
            if (img.length >= 5) {
                this.images = new Images[5];
                this.images = img;
            } else {
                this.images = null;
            }
        } else {
            this.images = null;
        }

    }

    public ArrayList<Suspect> getCompanions() {
        return companions;
    }

    public void setSuspect(ArrayList<Suspect> companions) {
        this.companions = companions;

    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setPhone(ArrayList<Phone> Phones) {
        this.phones = phones;
    }

    public void setCar_registration(ArrayList<Car_Registration> carRegistrations) {
        this.carRegistrations = carRegistrations;
    }

    public boolean isEmpty() {
        boolean empty = true;

        if (empty && name != null) {
            empty = false;
        }
        if (empty && lastname1 != null) {
            empty = false;
        }
        if (empty && lastname2 != null) {
            empty = false;
        }
        if (empty && record != null) {
            empty = false;
        }
        if (empty && facts != null) {
            empty = false;
        }
        if (empty && phones != null) {
            empty = false;
        }
        if (empty && emails != null) {
            empty = false;
        }
        if (empty && addresses != null) {
            empty = false;
        }
        if (empty && carRegistrations != null) {
            empty = false;
        }
        if (empty && images != null) {
            empty = false;
        }
        if (empty && companions != null) {
            empty = false;
        }

        return empty;
    }

}

package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jurad0
 */
public class Car_Registration {  
    private String registration;
    
    
public Car_Registration(String registration) {
    this.registration=registration;
    
}


public boolean validate (String registration){
    boolean validate=false;    
    Pattern p = Pattern.compile("^[0-9]{4}[a-zA-Z]{3}$");
    Matcher m = p.matcher(this.registration);
    if (m.find()){}
    
        return true;
}


public String getRegistration(){
        return registration;
    }
    
    public void setRegistration(String registration){
        this.registration=registration;
    }
    
    

   
}

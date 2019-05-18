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
    private Integer codeRegistration;
    
    public Car_Registration(String registration,int codeRegistration) {
        this.registration=registration;
        this.codeRegistration=codeRegistration;
}
    
    public Car_Registration(String registration){
        this.registration=registration;
    }

    public void setCodeRegistration(Integer codeRegistration) {
        this.codeRegistration = codeRegistration;
    }

    public Integer getCodeRegistration() {
        return codeRegistration;
    }
    public String getRegistration(){
        return registration;
    }
    
    public void setRegistration(String registration){
        this.registration=registration;
    }
    
    

   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Antonio Jose Adamuz Sereno
 */
public class Phone {
    private Integer CodeSuspect;
    private Integer CodePhone;
    private Integer PhoneNumber;
    
    public Phone(Integer CodeSuspect, Integer CodePhone, Integer PhoneNumber){
        super();
        this.CodeSuspect=CodeSuspect;
        this.CodePhone=CodePhone;
        this.PhoneNumber=PhoneNumber;
    }
    
    public Integer getCodeSuspect(){
        return CodeSuspect;
    }
    
    public void setCodeSuspect(Integer CodeSuspect){
        this.CodeSuspect=CodeSuspect;
    }
    
    public Integer getCodePhone(){
        return CodePhone;
    }
    
    public void setCodePhone(Integer CodePhone){
        this.CodePhone=CodePhone;
    }
    
    public Integer getPhoneNumber(){
        return PhoneNumber;
    }
    
    public void setPhoneNumber(Integer PhoneNumber){
        this.PhoneNumber=PhoneNumber;
    }
}

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
public class Address {
    private Integer CodeAddress;
    private Integer CodeSuspect;
    private String Address;
    
    public Address(Integer CodeAddress, Integer CodeSuspect, String Address){
        
        this.CodeAddress=CodeAddress;
        this.CodeSuspect=CodeSuspect;
        this.Address=Address;
        
    }
    
    public Integer getCodeAddress(){
        return CodeAddress;
    }
    
    public void setCodeAddress(Integer CodeAddress){
        this.CodeAddress=CodeAddress;
    }
    
    public Integer getCodeSuspect(){
        return CodeSuspect;
    }
    
    public void setCodeSuspect(Integer CodeSuspect){
        this.CodeSuspect=CodeSuspect;
    }
    
    public String getAddress(){
        return Address;
    }
    
    public void setAddress(String Address){
        this.Address= Address;
    }
}

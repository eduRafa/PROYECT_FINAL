/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author usuario
 */
public class Email {
    String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
    Pattern pattern = Pattern.compile(emailPattern);
    private Integer CodeEmail;
    private Integer CodeSuspect;
    private String Email;
    
    public Email(Integer CodeEmail, Integer CodeSuspect, String Email){
        
        this.CodeEmail=CodeEmail;
        this.CodeSuspect=CodeSuspect;
        if (Email != null) {
            Matcher matcher = pattern.matcher(Email);
            if (matcher.matches()) {
                this.Email=Email;
            }
        }
    }
     public Integer getCodeEmail(){
        return CodeEmail;
    }
    
    public void setCodeEmail(Integer CodeEmail){
        this.CodeEmail=CodeEmail;
    }
    
     public Integer getCodeSuspect(){
        return CodeSuspect;
    }
    
    public void setCodeSuspect(Integer CodeSuspect){
        this.CodeSuspect=CodeSuspect;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        if (Email != null) {
            Matcher matcher = pattern.matcher(Email);
            if (matcher.matches()) {
                this.Email=Email;
            }
        }
    }
    
}

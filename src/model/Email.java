/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author usuario
 */
public class Email {

    private Integer CodeEmail;
    private Integer CodeSuspect;
    private String Email;

    public Email(Integer CodeEmail, Integer CodeSuspect, String Email) {
        this.CodeEmail = CodeEmail;
        this.CodeSuspect = CodeSuspect;
        this.Email = Email;
    }

    public Email(String Email, Integer CodeSuspect) {
        this.Email = Email;
        this.CodeSuspect = CodeSuspect;

    }

    public Integer getCodeEmail() {
        return CodeEmail;
    }

    public void setCodeEmail(Integer CodeEmail) {
        this.CodeEmail = CodeEmail;
    }

    public Integer getCodeSuspect() {
        return CodeSuspect;
    }

    public void setCodeSuspect(Integer CodeSuspect) {
        this.CodeSuspect = CodeSuspect;
    }

    public String getEmail() {
        return Email;
    }
}

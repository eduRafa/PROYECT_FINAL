/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import database.Connect;
import view.UI;

/**
 *
 * @author rafa0
 */
public class Application {

    public static void main(String[] args) {
        Connect c = Connect.getInstance();
        UI.start();
    }
}

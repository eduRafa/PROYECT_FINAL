/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author rafa0
 */
public class CommunicationTest {
    
    public CommunicationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetPrimaryColor() {
        System.out.println("setPrimaryColor");
        Color c = null;
        Communication.setPrimaryColor(c);
    }

    @Test
    public void testXmlToString() {
        System.out.println("xmlToString");
        Document xml = null;
        String expResult = null;
        String result = Communication.xmlToString(xml);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPrimaryColor() {
        
        System.out.println("getPrimaryColor");
        Color expResult = null;
        Color result = Communication.getPrimaryColor();
        assertEquals(expResult, result);
    }
    
}

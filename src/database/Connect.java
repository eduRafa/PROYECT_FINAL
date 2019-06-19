/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import model.Communication;

/**
 *
 * @author amgal
 */
public class Connect {

    //Atributo que gestiona la conexión con la BBDD
    static private Connection myConnection;
    static private boolean state = false;//Estado de la conexión
    static private boolean CREATED = false;
    static private Connect me;

    private Connect() {

    }

    public static Connect getInstance() {
        if (me == null) {
            me = new Connect();
        }
        return me;
    }

    /*Método: startConnection()
	Funcionalidad: Realiza la conexión a la base de datos y si se realiza con exito
        pone el indicador de estado a true
     */
    public void startConnection() throws ClassNotFoundException, SQLException {
        if (myConnection == null) {
            String[] parameters = Communication.getDatabaseAccess();

            Class.forName(parameters[0]);
            // Setup the connection with the DB

            //miConexion= DriverManager.getConnection("jdbc:mysql://"+HOST_DE+"/"+BBDD_DE+"?user="+LOGIN_DE+"&password="+PASSWORD_DE);
            //conexión completa para evitar errores de sincronizacion con el servidor
            myConnection = DriverManager.getConnection(parameters[1], parameters[2], parameters[3]);

            if (CREATED == false) {
                generateStructure();
            }

            Query.setConnect(me);
        }
    }

    /*Método: getState()
	Tipo: boolean
	Parámetros: ninguno
	Devuelve: boolean
	Funcionalidad: Devuelve si la conexión está establecida o no
     */
    public boolean getState() {
        return state;
    }

    /*Método: getMyConnection()
	Tipo: Connection
	Parámetros: ninguno
	Devuelve: Connection
	Funcionalidad: Devuelve la conexion
     */
    public Connection getMyConnection() {
        return myConnection;
    }

    /*Método: closeConnection()
	Tipo: boolean
	Parámetros: ninguno
	Devuelve: boolean
	Funcionalidad: Devuelve true si ha cerrado la conexión a la BBDD y false en caso contrario
     */
    public boolean closeConnection() throws Exception {
        boolean closed = false;
        try {
            Connect.myConnection.close();
            closed = true;

        } catch (SQLException se) {
            se.printStackTrace();
        }
        return closed;
    }

    private void generateStructure() {
        boolean generated = true;

        String lineSQL;

        Statement sentence;
        try {
            lineSQL = "CREATE TABLE IF NOT EXISTS SUSPECT"
                    + "(CodeSuspect         int unsigned PRIMARY KEY auto_increment,"
                    + "name                 varchar(30) DEFAULT null,"
                    + "lastname1            varchar(30) DEFAULT null,"
                    + "lastname2            varchar(30) DEFAULT null,"
                    + "Record               text ,"
                    + "Facts                text"
                    + ")ENGINE=INNODB";

            //conectamos la sentencia a la base de datos
            sentence = myConnection.createStatement();
            //ejecutamos la sentencia;
            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS COMPANIONS"
                    + "(CodeSuspect        int unsigned,"
                    + "CodeSuspect2         int unsigned,"
                    + "PRIMARY KEY (CodeSuspect,CodeSuspect2),"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (CodeSuspect2) REFERENCES SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();

            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS PHONE"
                    + "(CodeSuspect          int unsigned,"
                    + "CodePhone             int auto_increment PRIMARY KEY,"
                    + "PhoneNumber           int unsigned,"
                    + "FOREIGN KEY (CodeSuspect) references SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();
            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS E_MAIL"
                    + "(CodeE_mail          int auto_increment PRIMARY KEY,"
                    + "CodeSuspect          int unsigned,"
                    + "Email                varchar(50) DEFAULT 'desconocido',"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();
            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS ADDRESS"
                    + "(CodeAddress          int auto_increment PRIMARY KEY,"
                    + "CodeSuspect           int unsigned,"
                    + "Address               varchar(100) DEFAULT null,"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();
            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS CAR_REGISTRATION"
                    + "(Registration_number varchar (11) DEFAULT null,"
                    + "CodeRegistration      int auto_increment PRIMARY KEY,"
                    + "CodeSuspect           int unsigned,"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();
            sentence.executeUpdate(lineSQL);

            lineSQL = "CREATE TABLE IF NOT EXISTS IMAGES"
                    + "(Image                blob,"
                    + "CodeImage             int auto_increment PRIMARY KEY,"
                    + "Description           text,"
                    + "CodeSuspect           int unsigned,"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";

            sentence = myConnection.createStatement();
            sentence.executeUpdate(lineSQL);

            CREATED = true;
        } catch (SQLException se) {
            generated = false;
            se.printStackTrace();
        }
    }
}

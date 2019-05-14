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
	static private boolean state=false;//Estado de la conexión
	static private final String HOST_DE="127.0.0.1"; //Host de la BBDD
       	static private final String BBDD_DE="POLICE_STATION"; //Nombre de la BBDD
        static private final String LOGIN_DE="root"; //Login 
        static private final String PASSWORD_DE=""; //Password
        static private boolean CREATED=false;

	
	
	
	/*Método: startConnection()
	Tipo: boolean
	Parámetros: ninguno
	Devuelve: booleano que indica el estado de la conexión
	Funcionalidad: Realiza la conexión a la base de datos y si se realiza con exito
        pone el indicador de estado a true
	*/
	
	static public boolean startConnection() throws Exception
	{
		try
		{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String[] parameters=Communication.getDatabaseAccess();
                    // Setup the connection with the DB

                    //miConexion= DriverManager.getConnection("jdbc:mysql://"+HOST_DE+"/"+BBDD_DE+"?user="+LOGIN_DE+"&password="+PASSWORD_DE);
                    
                    //conexión completa para evitar errores de sincronizacion con el servidor
                    myConnection= DriverManager.getConnection("jdbc:mysql://"+parameters[0]+"/"+parameters[1]+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user="+parameters[2]+"&password="+parameters[3]);
		
                    if(CREATED==false){
                        generateStructure();             
                    }
		} 
		catch (Exception e) {
			e.printStackTrace();
           }
         
		return Connect.state;
	}
	
	
	
	/*Método: getState()
	Tipo: boolean
	Parámetros: ninguno
	Devuelve: boolean
	Funcionalidad: Devuelve si la conexión está establecida o no
        */
	
	static public boolean getState()
	{
		return state;
		
	}

        /*Método: getMyConnection()
	Tipo: Connection
	Parámetros: ninguno
	Devuelve: Connection
	Funcionalidad: Devuelve la conexion
        */
        public static Connection getMyConnection() {
            return myConnection;
        }
	
	/*Método: closeConnection()
	Tipo: boolean
	Parámetros: ninguno
	Devuelve: boolean
	Funcionalidad: Devuelve true si ha cerrado la conexión a la BBDD y false en caso contrario
        */
	
	static public boolean closeConnection() throws Exception
	{
		boolean closed=false;
		try
		{
			Connect.myConnection.close();
			closed=true;
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		return closed;
		
	}

    private static void generateStructure() {
        boolean generated=true;
		
		String lineSQL;
		
		Statement sentence;
                try{
                    lineSQL="CREATE TABLE IF NOT EXISTS SUSPECT"
                    +"(CodeSuspect         int unsigned zerofill PRIMARY KEY auto_increment,"
                    +"name                 varchar(20) DEFAULT 'desconocido',"
                    +"lastname1            varchar(20) DEFAULT 'desconocido',"
                    +"lastname2            varchar(20) DEFAULT 'desconocido',"
                    +"Record               blob ,"
                    +"Facts                blob"
                    + ")ENGINE=INNODB";
                    
                    //conectamos la sentencia a la base de datos
                    sentence = myConnection.createStatement();
                    //ejecutamos la sentencia;
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS COMPANIONS"
                    + "(CodeSuspect        int unsigned,"
                    + "CodeSuspect2         int unsigned,"
                    + "PRIMARY KEY (CodeSuspect,CodeSuspect2),"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (CodeSuspect2) REFERENCES SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                            
                    sentence = myConnection.createStatement();
                    
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS PHONE"
                    + "(CodeSuspect          int unsigned,"
                    + "CodePhone             int zerofill auto_increment PRIMARY KEY,"
                    + "PhoneNumber           int DEFAULT 00000000,"
                    + "FOREIGN KEY (CodeSuspect) references SUSPECT(CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                    sentence = myConnection.createStatement();
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS E_MAIL"
                    + "(CodeE_mail          int zerofill auto_increment PRIMARY KEY,"
                    + "CodeSuspect          int unsigned,"
                    + "Email                varchar(20) DEFAULT 'desconocido',"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                    sentence = myConnection.createStatement();
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS ADDRESS"
                    + "(CodeAddress          int zerofill auto_increment PRIMARY KEY,"
                    + "CodeSuspect           int unsigned,"
                    + "Address               varchar(100) DEFAULT 'desconocido',"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                    sentence = myConnection.createStatement();
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS CAR_REGISTRATION"
                    + "(Resgistration_number varchar (11) DEFAULT 'desconocido',"
                    + "CodeRegistration      int zerofill auto_increment PRIMARY KEY,"
                    + "CodeSuspect           int unsigned,"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                    sentence = myConnection.createStatement();
                    sentence.executeUpdate(lineSQL);
                    
                    lineSQL="CREATE TABLE IF NOT EXISTS IMAGES"
                    + "(Image                blob,"
                    + "CodeImage             int zerofill auto_increment PRIMARY KEY,"
                    + "Description           blob,"
                    + "CodeSuspect           int unsigned,"
                    + "FOREIGN KEY (CodeSuspect) REFERENCES SUSPECT (CodeSuspect) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")ENGINE=INNODB";
                    
                    sentence = myConnection.createStatement();
                    sentence.executeUpdate(lineSQL);
                    
                    CREATED=true;
                }catch(SQLException se){
			generated=false;
			se.printStackTrace();
		}
    }
	
	

}


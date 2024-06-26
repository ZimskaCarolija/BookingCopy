/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Aleksa
 */
public class Konekcija {
    public static Connection VratiKonekciju()
    {
          try{
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","");

//$con->close();

        return connection;
        }
        catch(Exception ex)
        {
            return null;
        }
         
    }
}

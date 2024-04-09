/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Random;

/**
 *
 * @author Aleksa
 */
public abstract class Security {
    
    private static int securityTokenLength = 15;
     public static String RandomSecurityToken()
     {
           String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
           StringBuilder br = new StringBuilder();
           for (int i = 0; i < securityTokenLength; i++) {
             Random random = new Random();
             int index = random.nextInt(characters.length());
             br.append(characters.charAt(index));

         }
           return br.toString();
     }
     public static String TwoFaToken(int IdUser) throws  Exception//puts 2fa token and date in db and returs token which will be embeded in url for csrf safety
     {
         Connection con = Konekcija.VratiKonekciju();
         PreparedStatement st = con.prepareStatement("update users set secuity_token = ? , security_date = ? where user_id = ?");
         String securT = RandomSecurityToken();
         st.setString(1, securT);
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         st.setTimestamp(2, timestamp);
         st.setInt(3, IdUser);
         int noEffected = st.executeUpdate();
         if(noEffected<1)
         {
             con.close();
             throw new Exception("Zero rows Affected");
         }
         con.close();
         return securT;
         
     }
     public static void CheckSecurityTimeStamp(int idUser,Connection con) throws Exception
     {
         PreparedStatement st = con.prepareStatement("select security_date from users where user_id = ?");
         st.setInt(1, idUser);
         ResultSet rs = st.executeQuery();
         Timestamp timestamp = null;
         if(rs.next())
         {
             timestamp = rs.getTimestamp("security_date");
           
         }
          Timestamp CurrentTime = new Timestamp(System.currentTimeMillis());
             long diff  =  CurrentTime.getTime() -timestamp.getTime();
             //getting minutes 
             long sec = diff/1000;
             long min = sec/60;
             if(min>2)
             {
                 con.close();
                 throw new Exception("Expired");
             }
        
         
     }
     public static void SecurityCheckVerify(int idUser , String token,Connection con) throws Exception
     {
         PreparedStatement st = con.prepareStatement("select 1 from users where user_id = ? and secuity_token = ?");
         st.setInt(1, idUser);
         st.setString(2, token);
         ResultSet rs = st.executeQuery();
         if(rs.next())
         {
             
             return;
         }
         else
         {
             throw new Exception("Not Valid");
         }
     }
     public static void UpdateSQlVerify(int idUser,Connection con) throws Exception
     {
         PreparedStatement st = con.prepareStatement("update users set verified = true where user_id = ?");
         st.setInt(1, idUser);
         int noAffected = st.executeUpdate();
         if(noAffected<1)
             throw new Exception("NO Affected is 0");
     }
     public static void SecurityCode(int IdUser,Connection con) throws  Exception
     {
         PreparedStatement st = con.prepareStatement("update users set security_code = ? where user_id = ?");
         Random random = new Random();
         int code = random.nextInt(1000, 9999);
         st.setInt(1, code);
          st.setInt(2, IdUser);
         int noEffected = st.executeUpdate();
         if(noEffected<1)
         {
             con.close();
             throw new Exception("Zero rows Affected");
         }
         
     }
     
}

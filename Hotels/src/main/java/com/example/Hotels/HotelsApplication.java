package com.example.Hotels;

import Klase.Amenity;
import Klase.Drzava;
import Klase.Grad;
import Klase.Hotel;
import Klase.Konekcija;
import Klase.Korisnik;
import Klase.KorisnikEmail;
import Klase.RoomType;
import Klase.RoomTypeHotel;
import Klase.RoomTypeImage;
import Klase.Sesije;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@RestController
public class HotelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class, args);
	}
 @GetMapping("/")
   public String a()
   {
       return "AAAAAAAAAAa";
   }
   
   
   @PostMapping("/login")
   public boolean Logovanje(@RequestParam("email")String email,@RequestParam("password")String password,HttpServletRequest request)
   {
       Korisnik k = new Korisnik();
        k = Korisnik.LoginProvera(email,password);
        if(k==null || k.getId()<0)
        {
            return false;
        }
        else
        {
            Sesije.Login(k, request);
            return true;
        }
        
   }
   @PostMapping("/AccLogged")
   public Korisnik UzmiUlovanog(HttpServletRequest request)
   {
      Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
      
      
      return k;
   }
   @PostMapping("/Sign")
   public String SIgnovan(@RequestParam("email")String email,@RequestParam("password")String password,HttpServletRequest request)
   {
       String i = Korisnik.NapraviNalog(email, password, request);
       return i;
   }
   @PostMapping("/AccSlika")
   public String UzmiSlikuNaloga(@RequestParam("id") int id ,HttpServletRequest request)
   {
    return Korisnik.VratiSliku(id); 
   }
   @PostMapping("/POstaviSlikuAcc")
   public String POstaviSlikuAcc(@RequestParam("img") MultipartFile file ,HttpServletRequest request)
   {
       if(request.getSession().getAttribute("korisnik")==null)
           return "Not Logged in";
       Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
       Connection con = Konekcija.VratiKonekciju();
       try{
           byte[] fileBytes = file.getBytes();
           PreparedStatement st = con.prepareStatement("Update users set acc_image = ? where user_id = ?;");
           st.setBytes(1, fileBytes);
           st.setInt(2, k.getId());
           st.executeUpdate();
       }
       catch(Exception ex){
       return ex.getMessage();
       }
       return "aaa";
   }
     @PostMapping("/POstaviTelefon")
      public String POstaviTelefon(@RequestParam("phone") String phone ,HttpServletRequest request)
   {
       if(request.getSession().getAttribute("korisnik")==null)
           return "Not Logged in";
       Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
       Connection con = Konekcija.VratiKonekciju();
       try{
           PreparedStatement st = con.prepareStatement("Update users set phone = ? where user_id = ?;");
           st.setString(1, phone);
            st.setInt(2, k.getId());
           st.executeUpdate();
           k.setPhone(phone);
           Sesije.Login(k, request);
           con.close();
       }
       catch(Exception ex){
       return ex.getMessage();
       }
       return "bbb";
   }
      @PostMapping("/DodajAmenity")
      public String DodajAmenity(HttpServletRequest request,@RequestParam("ime")String ime,@RequestParam("opis")String opis,@RequestParam("slika") MultipartFile slika)
      {
          
           if(!Sesije.ProveriOvlascenje(request,3))
           {
               return"0";
           }
                    
          Connection con = Konekcija.VratiKonekciju();
          try
          {
          PreparedStatement st = con.prepareStatement("Insert into Amenities(amenity_name,amenity_desc,amenity_image) values(?,?,?)");
          byte[] fileBytes = slika.getBytes();
          st.setString(1, ime);
          st.setString(2, opis);
          st.setBytes(3, fileBytes);
          int affectedRows = st.executeUpdate();
          con.close();
           if(affectedRows<1)
           {
               return "Error";
           }
           else
          return "Succes " + ime +" "+opis;    
          }
          catch(Exception ex)
          {
              return ex.getMessage();
          }
          finally {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage(); // ili logujte izuzetak na neki drugi način
        }
    }
          
      }
      @PostMapping("/UzmiAMenitije")
      public String UzmiAmenitije()
      {
          try
          {
          ArrayList<Amenity> amenitiji = Amenity.VratiSve();
          ObjectMapper maper = new ObjectMapper();
          return maper.writeValueAsString(amenitiji);
          }
          catch(Exception ex)
          {
              return "Greska";
          }
          
      }
        @GetMapping("/UzmiAMenitiID")
      public String UzmiAmenitije(HttpServletRequest request,@RequestParam("id")int id)
      {
          //if(!Sesije.ProveriOvlascenje(request,3))
           //{
              // return"0";
           //}
          //return"aaaaaaaaaaaa";
          try
          {
          Amenity ameniti = Amenity.UzmiPOId(id);
          ObjectMapper maper = new ObjectMapper();
          return maper.writeValueAsString(ameniti);
         
          }
          catch(Exception ex)
          {
              return ex.getMessage();
          }
          
      }
       @PostMapping("/IzmeniAMeniti")
      public String IzmeniAmeniti(HttpServletRequest request,@RequestParam("ime")String ime,@RequestParam("opis")String opis,
        @RequestParam("slika") MultipartFile slika,@RequestParam("id")int id,@RequestParam("ban")boolean ban)
      {
          
           if(!Sesije.ProveriOvlascenje(request,3))
           {
               return"0";
           }
                    
          Connection con = Konekcija.VratiKonekciju();
          try
          {
          PreparedStatement st = con.prepareStatement("update Amenities set amenity_name = ?  , amenity_desc = ? , banovan = ?  , amenity_image = ? where amenity_id = ?;");
          byte[] fileBytes = slika.getBytes();
          st.setString(1, ime);
          st.setString(2, opis);
          st.setBoolean(3,ban);
          st.setBytes(4, fileBytes);
          st.setInt(5, id);
          int affectedRows = st.executeUpdate();
          con.close();
           if(affectedRows<1)
           {
               return "Error";
           }
           else
          return "Succes " + ime +" "+opis;    
          }
          catch(Exception ex)
          {
              return ex.getMessage();
          }
          finally {
            try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
            } catch (SQLException e) {
            return e.getMessage(); // ili logujte izuzetak na neki drugi način
            }
    }
      }
      @PostMapping("/UzmiKorisnike")
      public ResponseEntity<?> UzmiKorisnike(@RequestParam("pretraga")String pretraga)
      {
          try {
           ArrayList<KorisnikEmail> korisnici  =Korisnik.UzmiKOrisnike(pretraga);
          ObjectMapper maper= new ObjectMapper();
          String a = maper.writeValueAsString(korisnici);
            return ResponseEntity.ok(a);
        } catch (SQLException | JsonProcessingException e) {
            // Vraćanje odgovora sa porukom greške
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
      }
       @PostMapping("/UzmiKorisnikID")
       public ResponseEntity<?>UzmiKorisnikaID(@RequestParam("id") int id)
       {
           try
           {
               Korisnik k = Korisnik.VratiPoId(id);
               ObjectMapper maper = new ObjectMapper();
               String a = maper.writeValueAsString(k);
               return ResponseEntity.ok(a);
           }
           catch(Exception e)
            {
             return ResponseEntity.badRequest().body("Error"+e.getMessage());
            }
           
       }
       @PostMapping("/IZmeniKOrisnika")
       public ResponseEntity<?> IzmeniKorisnika(HttpServletRequest request,@RequestParam("id") int id,@RequestParam("tip") int tip)
       {
           if(!Sesije.ProveriOvlascenje(request,3))
           {
               return ResponseEntity.badRequest().body("0");
           }
           if(tip != 1 || tip != 2 || tip != 3)
           {
               //return ResponseEntity.badRequest().body("Account type does not exists");
           }
           try
           {
               Connection con = Konekcija.VratiKonekciju();
               PreparedStatement st = con.prepareStatement("Update users set acc_type_id = ? Where user_id = ?;");
               st.setInt(1, tip);
               st.setInt(2, id);
               int broj = st.executeUpdate();
              // if(broj<1)
                //return ResponseEntity.badRequest().body("Error");   
               //else
                   return ResponseEntity.ok("USer changed");
           }
           catch(Exception e)
           {
                 return ResponseEntity.badRequest().body("Error"+e.getMessage());
           }
       }
       @PostMapping("/DodajDrzavu")
       public ResponseEntity<?> DodajDrzavu(HttpServletRequest request,@RequestParam("naziv") String naziv)
       {
            if(!Sesije.ProveriOvlascenje(request,3))
           {
               return ResponseEntity.badRequest().body("0");
           }
            try
            {
                Connection con = Konekcija.VratiKonekciju();
                PreparedStatement st = con.prepareStatement("insert into country(country_name) VALUES(?);");
                st.setString(1, naziv);
                int broj = st.executeUpdate();
                return ResponseEntity.ok("USer changed");
            }
            catch(Exception e)
            {
                return ResponseEntity.badRequest().body("Error : " + e.getMessage());
            }
       }
       @PostMapping("/SveDrzave")
       public ResponseEntity<?> SveDrzave()
       {
           try
           {
               ArrayList<Drzava> sve = Drzava.VratiSveDrzave();
               ObjectMapper maper = new ObjectMapper();
               String a = maper.writeValueAsString(sve);
               return ResponseEntity.ok(a);
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : " + ex.getMessage());
           }
       }
       @GetMapping("/DrzavaId")
       public ResponseEntity<?>DrzavaId(@RequestParam("id") int id)
       {
           try
           {
               Drzava dr = Drzava.VratiPoId(id);
               ObjectMapper maper = new ObjectMapper();
               String a = maper.writeValueAsString(dr);
               return ResponseEntity.ok(a);
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : "+ex.getMessage());
           }
       }
       @PostMapping("/DrzavaUpdate")
       public ResponseEntity<?>DrzavaUpdate(HttpServletRequest request,@RequestParam("id") int id,@RequestParam("naziv") String naziv,@RequestParam("ban") boolean ban)
       {
           if(!Sesije.ProveriOvlascenje(request,3))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Connection con = Konekcija.VratiKonekciju();
               PreparedStatement st = con.prepareStatement("Update country set country_name = ?, banovan = ? where county_id = ?;");
               st.setString(1, naziv);
               st.setBoolean(2, ban);
               st.setInt(3, id);
               int broj  = st.executeUpdate();
               con.close();
               return ResponseEntity.ok("broj");
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : "+ex.getMessage());
           }
       }
       @PostMapping("/DodajGrad")
       public ResponseEntity<?>DodajGrad(HttpServletRequest request,@RequestParam("id") int id,@RequestParam("naziv") String naziv)
       {
           if(!Sesije.ProveriOvlascenje(request,3))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Grad.DodajGrad(id,naziv);
               return ResponseEntity.ok("broj");
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : "+ex.getMessage());
           }
       }
       @PostMapping("/SviGradovi")
       public ResponseEntity<?> SviGradovi()
       {
           try
           {
               ArrayList<Grad> gradovi = Grad.SviGradovi();
               ObjectMapper maper = new ObjectMapper();
               String a = maper.writeValueAsString(gradovi);
               return ResponseEntity.ok(a);
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body(ex.getMessage());
           }
       }
       @PostMapping("/GradID")
       public ResponseEntity<?> GradId(@RequestParam("id") int id)
       {
           try
           {
               Grad grad = Grad.UzmiGradId(id);
               ObjectMapper maper = new ObjectMapper();
               String a = maper.writeValueAsString(grad);
               return ResponseEntity.ok(a);
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body(ex.getMessage());
           }
       }
       @PostMapping("/IZmenaGrada")
       public ResponseEntity<?>GradIzmena(HttpServletRequest request,@RequestParam("cityId") int gradId,
       @RequestParam("name") String naziv,@RequestParam("ban") boolean ban,@RequestParam("countryId") int countryId)
       {
            if(!Sesije.ProveriOvlascenje(request,3))
           {
               return ResponseEntity.badRequest().body("0");
           }
            try
            {
                Grad.IzmenaGrada(gradId, countryId, naziv, ban);
                return ResponseEntity.ok("Success");
            }
            catch(Exception ex)
            {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
       }
       @PostMapping("/DodajHotel")
       public ResponseEntity<?>DodajHotel(HttpServletRequest request,@RequestParam("name") String naziv,@RequestParam("cityId") int gradId,
        @RequestParam("address") String adresa,@RequestParam("stars") int zvezde, @RequestParam("image") MultipartFile slika,
       @RequestParam("desc") String opis)
       {
            if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Connection con = Konekcija.VratiKonekciju();
               Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
               int idKorisnik = k.getId();
               PreparedStatement st = con.prepareStatement("insert into hotel(hotel_name,city_id,adress,stars,hotel_main_ing,hotel_desc,userId) value(?,?,?,?,?,?,?)");
               st.setString(1, naziv);
               st.setInt(2, gradId);
               st.setString(3, adresa);
               st.setInt(4, zvezde);
               byte[] img =  slika.getBytes();
               st.setBytes(5, img);
               st.setString(6,opis);
               st.setInt(7, idKorisnik);
               st.executeUpdate();
               con.close();
               return ResponseEntity.ok("Success");
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error"+ex.getMessage());
           }
           
       }
       @PostMapping("/HoteliUSer")
       public ResponseEntity<?> HotelUser(HttpServletRequest request)
       {
            if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
            try
            {
                Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
               int idKorisnik = k.getId();
               ArrayList<Hotel> hoteli = Hotel.HotelMenadzer(idKorisnik);
               ObjectMapper maper = new ObjectMapper();
               String str = maper.writeValueAsString(hoteli);
               return ResponseEntity.ok(str);
               
            }
            catch(Exception e)
            {
                   return ResponseEntity.badRequest().body("Error"+e.getMessage());
            }
       }
      @PostMapping("/HotelId")
        public ResponseEntity<?> HotelId(HttpServletRequest request, @RequestParam("id") int id) {
             if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
            try {
                Hotel h = Hotel.VratiHotelId(id);
                ObjectMapper mapper = new ObjectMapper();
                String st = mapper.writeValueAsString(h);
                return ResponseEntity.ok(st);
            } catch (Exception ex) {
                return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
            }
        }
        @PostMapping("/IzmeniHotel")
       public ResponseEntity<?>IzmeniHotel(HttpServletRequest request,@RequestParam("name") String naziv,@RequestParam("cityId") int gradId,
        @RequestParam("address") String adresa,@RequestParam("stars") int zvezde, @RequestParam("image") MultipartFile slika,
       @RequestParam("desc") String opis ,@RequestParam("id") int id,@RequestParam("showing") boolean showing )
       {
            if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Connection con = Konekcija.VratiKonekciju();
               Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
               
               int idKorisnik = k.getId();
               if(!Hotel.ProveraHotelUser(idKorisnik, id))
               {
                     return ResponseEntity.badRequest().body("You are not owner of room");
               }
               PreparedStatement st = con.prepareStatement("UPDATE hotel SET hotel_name = ?, city_id = ?, adress = ?, stars = ?, hotel_main_ing = ?, hotel_desc = ? , banovan = ? WHERE hotel_id = ?;");
               st.setString(1, naziv);
               st.setInt(2, gradId);
               st.setString(3, adresa);
               st.setInt(4, zvezde);
               byte[] img =  slika.getBytes();
               st.setBytes(5, img);
               st.setString(6,opis);
               st.setBoolean(7, showing);
               st.setInt(8, id);
               st.executeUpdate();
               con.close();
               return ResponseEntity.ok("Success");
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error"+ex.getMessage());
           }
           
       }
       @PostMapping("/DodajRoomType")
      public ResponseEntity<?> DodajRoomType(HttpServletRequest request, @RequestParam("name") String naziv, @RequestParam("hotel") int hotelId, @RequestParam("noBeds") int noBeds, @RequestParam("opis") String opis) 
       {
            if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Korisnik k  = (Korisnik)request.getSession().getAttribute("korisnik");
             if(!RoomType.ProveraVlasnistaSobe(hotelId, k.getId()))
             {
                 return ResponseEntity.badRequest().body("You are not owner of hotel");
             }
            Connection con = Konekcija.VratiKonekciju();
            PreparedStatement st = con.prepareStatement("insert into room_type(no_beds,room_type_name,desribe,hotel_id) Values(?,?,?,?)");
            st.setInt(1, noBeds);
            st.setString(2, naziv);
            st.setString(3, opis);
            st.setInt(4, hotelId);
            st.execute();
            con.close();
            return ResponseEntity.ok("Success");
            
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : " + ex.getMessage());
           }
       }
        @PostMapping("/MyRoomTypes")
        public ResponseEntity<?> MyRoomTypes(HttpServletRequest request)
        {
             if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
            try
            {
                ArrayList<RoomTypeHotel> hoteliSobe = new ArrayList<>(); 
                Korisnik k  = (Korisnik)request.getSession().getAttribute("korisnik");
                Connection con = Konekcija.VratiKonekciju();
                PreparedStatement st = con.prepareStatement("select room_type_id,no_beds,room_type_name,desribe,r.hotel_id,r.banovan,hotel_name from room_type r , hotel h where r.hotel_id  = h.hotel_id and  userId = ?");
                st.setInt(1, k.getId());
                ResultSet rs = st.executeQuery();
                
                while(rs.next())
                {
                    int no_beds = rs.getInt("no_beds");
                    int room_type_id = rs.getInt("room_type_id");
                    String room_type_name = rs.getString("room_type_name");
                    int hotel_id  = rs.getInt("hotel_id");
                    boolean banovan = rs.getBoolean("banovan");
                    String desribe = rs.getString("desribe");
                    String hotel_name = rs.getString("hotel_name");
                    RoomType room = new RoomType(room_type_id,no_beds,room_type_name,desribe,hotel_id,banovan);
                    RoomTypeHotel rh = new RoomTypeHotel(room,hotel_name);
                    hoteliSobe.add(rh);
                }
                con.close();
                ObjectMapper maper = new ObjectMapper();
                String str = maper.writeValueAsString(hoteliSobe);
                return ResponseEntity.ok(str);
            }
            catch(Exception ex)
            {
                return ResponseEntity.badRequest().body("Error : " + ex.getMessage());
            }
        }
        @PostMapping("/RoomTypeID")
        public ResponseEntity<?> RoomTypeID(HttpServletRequest request,@RequestParam("id") int id)
        {
             if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
             Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
          
           try
           {
                if(!RoomType.ProveraSobaUser(k.getId(), id))
                {
                return ResponseEntity.badRequest().body("You are not owner of room");
                }
               RoomType room = RoomType.VratiPoID(id);
               ObjectMapper maper  = new ObjectMapper();
               String str  = maper.writeValueAsString(room);
               return ResponseEntity.ok(str);
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : " + ex.getMessage());
           }
        }
         @PostMapping("/IzmeniRoomType")
      public ResponseEntity<?> IzmeniRoomType(HttpServletRequest request, @RequestParam("name") String naziv, @RequestParam("hotel") int hotelId, 
              @RequestParam("noBeds") int noBeds, @RequestParam("opis") String opis,
               @RequestParam("id") int id,@RequestParam("showing") boolean ban ) 
       {
            if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               Korisnik k  = (Korisnik)request.getSession().getAttribute("korisnik");
             if(!RoomType.ProveraVlasnistaSobe(hotelId, k.getId()))
             {
                 return ResponseEntity.badRequest().body("You are not owner of hotel");
             }
            Connection con = Konekcija.VratiKonekciju();
            PreparedStatement st = con.prepareStatement("UPDATE room_type SET no_beds = ?, room_type_name = ?, desribe = ?, hotel_id = ?, banovan = ? WHERE  room_type_id = ?;");
            st.setInt(1, noBeds);
            st.setString(2, naziv);
            st.setString(3, opis);
            st.setInt(4, hotelId);
            st.setBoolean(5, ban);
            st.setInt(6, id);
            st.execute();
            con.close();
            return ResponseEntity.ok("Success");
            
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body("Error : " + ex.getMessage());
           }
       }
      @PostMapping("/DodajRoomTypeIMG")
      public ResponseEntity<?> DodajRoomTypeIMG(HttpServletRequest request,@RequestParam("roomId") int roomId,@RequestParam("img") String img)
      {
           if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
           {
               byte[] niz = Base64.getDecoder().decode(img);
               Connection con = Konekcija.VratiKonekciju();
               PreparedStatement st = con.prepareStatement("insert into room_type_image(room_type_id,image) values(?,?);");
               st.setInt(1, roomId);
               st.setBytes(2, niz);
               st.executeUpdate();
               con.close();
               return ResponseEntity.ok("Success");
               
           }
           catch(Exception ex)
           {
               return ResponseEntity.badRequest().body(ex.getMessage());
           }
      }
      @PostMapping("/RoomImages")
      public ResponseEntity<?> RoomImages(HttpServletRequest request,@RequestParam("roomId") int roomId)
      {
          if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
          try
          {
              ArrayList<RoomTypeImage> images = RoomTypeImage.LoadImages(roomId);
              ObjectMapper maper = new ObjectMapper();
              String JSON_String = maper.writeValueAsString(images);
              return ResponseEntity.ok(JSON_String);
          }
          catch(Exception ex)
          {
              return ResponseEntity.badRequest().body(ex.getMessage());
          }
      }
       @PostMapping("/DeleteRoomImage")
       public ResponseEntity<?> DeleteRoomImage(HttpServletRequest request,@RequestParam("imageID") int imageID)
       {
           if(!Sesije.ProveriOvlascenje(request,2))
           {
               return ResponseEntity.badRequest().body("0");
           }
           try
          {
              Connection con = Konekcija.VratiKonekciju();
              PreparedStatement st = con.prepareStatement("delete from room_type_image where room_img_id = ?");
              st.setInt(1, imageID);
              int noEfected = st.executeUpdate();
              con.close();
              if(noEfected>0)
                  return  ResponseEntity.ok("Success!");
              else
                  throw new Exception("Not Affected");
          }
          catch(Exception ex)
          {
              return ResponseEntity.badRequest().body(ex.getMessage());
          }
       }
}
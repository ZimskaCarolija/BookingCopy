/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function login()
{
    let email = document.getElementById("mail").value;
    let sifra = document.getElementById("sifra").value;
    $.post("http://localhost:8080/login",{email:email,password:sifra},function(data){
        let poruka = "Wrong!!!";
        if(data == true)
        {
            poruke = "You successfully logged in";
            window.location.href = "Profil.html";
        }
        else
        {
            poruke = "Error";
        }
        alert(poruke);
    });
}

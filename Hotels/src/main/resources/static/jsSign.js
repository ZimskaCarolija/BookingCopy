/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function signUp()
{
    let email = document.getElementById("mail").value;
    let sifra = document.getElementById("sifra").value;
    let sifra2 = document.getElementById("sifra2").value;
    if(sifra != sifra2)
    {
        alert("Passwords do not match!!!");
        return;
    }
    $.post("http://localhost:8080/Sign",{email:email,password:sifra},function(data){
        let poruka = "Wrong!!!";
        console.log(data);
        if(data == 1)
        {
            poruke = "You successfully logged in";
            window.location.href = "Profil.html";
        }
        alert(data);
    });
}

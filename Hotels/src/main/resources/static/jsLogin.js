/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function login()
{
    let email = document.getElementById("mail").value;
    let sifra = document.getElementById("sifra").value;
    $.post("http://localhost:8080/login",{email:email,password:sifra},function(data){
       
            window.location.href = data;
       
    }).fail(function(xhr, status, error) {
                           alert(xhr.responseText);
                            console.log(xhr.responseText);
                });;
    
}

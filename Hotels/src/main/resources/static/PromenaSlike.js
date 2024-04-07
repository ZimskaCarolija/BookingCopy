/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function PromenaSlike()
{
     var fileInput = document.getElementById('slikaOdabir');
    var file = fileInput.files[0];
    if (file) {
        var reader = new FileReader();

        reader.onload = function(e) {
            document.getElementById('slika').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}
function Meni()
{
     var x = document.getElementById("myTopnav");
  if (x.className === "topnav") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}
function Logout()
{
    $.post("http://localhost:8080/Logout",function (data){
        window.location.href="Login.html";
    }).fail(function(xhr, status, error) {
                           alert(xhr.responseText);
                });
}

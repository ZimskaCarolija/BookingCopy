/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

window.onload =() =>{
    
    $.post("http://localhost:8080/AccLogged",function(data){
        console.log(data);
        if(data.id == null || data.id<0)
        {
            window.location.href="Login.html";
        }
        document.getElementById("mail").innerHTML = "E-mail : "+data.email;
        document.getElementById("phone").value = data.phone;
        let phone = 
         $.post("http://localhost:8080/AccSlika",{id:data.id},function(data2){
             document.getElementById("slika").src="data:image/png;base64," + data2;
             document.getElementById("slika").src="data:image/jpeg;base64," + data2;
           
         });
        if(data.verified == true)
        {
           document.getElementById("verifikovan").innerHTML = "";
        }
    });
    
    
}
function Sacuvaj()
{
     let phone = document.getElementById("phone").value;
   $.post("http://localhost:8080/POstaviTelefon",{phone:phone},function(data3){
             alert(data3);
    });
  let slika = document.getElementById("slikaOdabir");

if (slika.files.length > 0) {
    let file = slika.files[0];
    let formData = new FormData();
    formData.append('img', file);
    let phone =  document.getElementById("phone").value;
    $.ajax({
        url: "http://localhost:8080/POstaviSlikuAcc",
        type: "POST",
        data: formData,
        contentType: false, 
        processData: false,
        success: function(data2) {
            alert(data2);
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
            alert("Error.");
        }
    });

}
}
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
function verify()
{
    alert('Verify clicked');
     $.post("http://localhost:8080/Email/Verify", function(data) {                                               
                      alert(data);
                   }).fail(function(xhr, status, error) {
                           alert(xhr.responseText);
                });
}
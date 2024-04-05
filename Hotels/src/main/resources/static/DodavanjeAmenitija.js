/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function Dodaj()
{
    let ime = document.getElementById("ime").value;
    let opis = document.getElementById("opis").value;
    let slika = document.getElementById("slikaOdabir");
    if(ime.length<3 || opis.length<3)
    {
        alert("Name and description needs to be longer");
        return;
    }
    alert(ime + " "+opis)
    if(slika.files.length>0)
    {
        let file = slika.files[0];
        let formData = new FormData();
        formData.append('slika', file);
        formData.append('ime', ime);
        formData.append('opis', opis);
        $.ajax({
        url: "http://localhost:8080/DodajAmenity",
        type: "POST",
        data: formData,
        contentType: false, 
        processData: false,
        success: function(data2) {
            alert(data2);
            if(data2 == "0")
            {
                alert("Not logged in or your account does not have permission to do that.");
            }
        },
        error: function(xhr, status, error) {
            if(xhr.responseText == "0")
                             window.location.href="Login.html";
            console.error("Greška: " + error);
            alert("Error");
        }
    });
    }
}

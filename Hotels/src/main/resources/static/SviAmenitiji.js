/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


window.onload =() =>{
   
    $.post("http://localhost:8080/UzmiAMenitije",function(data){
       let podatci = JSON.parse(data);
       console.log(podatci);
       let karte = document.getElementById("karte");
       karte.innerHTML = "";
       podatci.forEach(podatak=>{
           karte.innerHTML+=` <div class="karta"><a href='IzmenaAmenitija.html?id=${podatak.amenityId}'>
                    <div class="deoSlika"><img src="data:image/png;base64,${podatak.slika}"></div>
                    <div class="Opis">${podatak.ime}</div>
                </a></div>`;
       });
    });
}
/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


window.onload = ()=>
{
    
  Pretraga();
}
function Pretraga()
{
    let pretraga = document.getElementById("pretraga").value;
    let stavke = document.getElementById("stavke");
    stavke.innerHTML = "";
      $.ajax({
        url:"http://localhost:8080/UzmiKorisnike",
        method:"POST",
        data:{pretraga:pretraga},
        success: function(response) {
        response = JSON.parse(response);
        response.forEach(element=>{
            stavke.innerHTML+=`
            <div class="stavka"><div class="Email">${element.mail}</div><button data-id="${element.id}">View</button></div>`;
                
        });
        const handleClick = function(e) {
            if (e.target.tagName === 'BUTTON') {
            const userId = e.target.getAttribute('data-id');
            window.location.href = `KOrisnikIzmena.html?id=${userId}`;
            }
         };
        document.getElementById("stavke").addEventListener('click', handleClick);
        
    },
    error: function(xhr, status, error) {
        if(xhr.responseText == "0")
                             window.location.href="Login.html";
        console.log("Error", xhr.responseText);
        alert("Error " + xhr.responseText);
    }
        
    });
}
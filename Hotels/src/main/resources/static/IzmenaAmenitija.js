/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const params = new URLSearchParams(location.search);
    if(!params.has("id"))
        window.location.href="SviAmenitiji.html";
    const id = params.get("id");
window.onload =() =>{
     
    $.get("http://localhost:8080/UzmiAMenitiID",{id:id},function(data){
         if(data == "0")
       {
           window.location.href="Login.html";
       }
        data  =JSON.parse(data);
        console.log(data);
      
        document.getElementById("ime").value = data.ime;
        document.getElementById("opis").value = data.opis;
        document.getElementById("slika").src="data:image/png;base64,"+data.slika;
        document.getElementById("ban").checked = data.banovan;
        alert(data.banovan);
        
    });
    
}
function sacuvaj()
    {
        let ime =document.getElementById("ime").value;
        let opis = document.getElementById("opis").value;
        let slika = document.getElementById("slika").src;
        let banovan = document.getElementById("ban").checked ;
        alert(banovan);
         if(ime.trim()!="" && opis.trim()!="")
        {
        const mimeType = 'image/png';
        const filename = 'slika.png';
        const fajl = base64ToFile(slika, filename, mimeType);
        let formData = new FormData();
        formData.append('slika', fajl);
        formData.append('ime', ime);
        formData.append('opis', opis);
        formData.append('ban', banovan);
        formData.append('id', id);
        $.ajax({
        url: "http://localhost:8080/IzmeniAMeniti",
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
            console.log( status + " Greška: " + error);
            alert("Error");
        }
        });
        }
    }
  //for learning this function in writen again in every fajl
function base64ToFile(base64String, filename, mimeType) {
    const base64Data = base64String.split(';base64,').pop();
    const binaryData = atob(base64Data);
    const arrayBuffer = new ArrayBuffer(binaryData.length);
    const view = new Uint8Array(arrayBuffer);
    for (let i = 0; i < binaryData.length; i++) {
        view[i] = binaryData.charCodeAt(i);
    }
    const blob = new Blob([arrayBuffer], { type: mimeType });
    const file = new File([blob], filename, { type: mimeType });

    return file;
}
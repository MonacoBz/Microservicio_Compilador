const botonT = document.getElementById("botonT");
const botonC = document.getElementById("botonC");

let textArea = document.getElementById("data");

let contenedor1=document.getElementById("contenedor");
let contenedor2=document.getElementById("contenedor2");    

let textoT="",textoC="";

let size=10;
let page=0;
let totalpage;

botonT.addEventListener("click", async e=>{
    const data = textArea.value;
    if(textoT!==data){
        textoT=data;
    const respuesta = await fetch(`http://localhost:8081/Paginacion?size=${size}&page=${page}`,{
                        method: "POST",        
                        body:data
                            })
    const JSON  =await respuesta.json();
    console.log(JSON);
    const errores=JSON.Errores;
    const tokens=JSON.Tokens.content;
    totalpage=JSON.Tokens.totalPages;
    let tablaE;
    let tablaT=crearTablaTokens(tokens);
    if(errores.length>0)tablaE=crearTablaErrores(errores);
    let contenedor=contenedor1.firstChild&&contenedor1.firstChild.tagName==="TABLE"?contenedor1:
                    contenedor2.firstChild&&contenedor2.firstChild.tagName==="TABLE"?contenedor2:
                    !contenedor1.hasChildNodes()?contenedor1:contenedor2;
    agregaTablas(contenedor,tablaT,tablaE);
    }
})

botonC.addEventListener("click",async e=>{
    const data = textArea.value;
    if(textoC!==data){
        textoC=data;
        const respuesta = await fetch("http://localhost:8081/Compilador",{
            method: "POST",
            body:data
        })
        const json=await respuesta.json()
        console.log(json)
        const JSON =await json.Compilador
        const errores = await json.Errores
        let contenedor=contenedor1.firstChild&&contenedor1.firstChild.tagName==="TEXTAREA"?contenedor1:
                    contenedor2.firstChild&&contenedor2.firstChild.tagName==="TEXTAREA"?contenedor2:
                    !contenedor1.hasChildNodes()?contenedor1:contenedor2;
        agregaTexto(contenedor,JSON)
        
        const divErrores = document.getElementById("errores");
divErrores.innerHTML = "";

if (errores.length > 0) {
    errores.forEach(err => {
        const p = document.createElement("p");
        p.textContent = "⚠️ " + err;
        divErrores.appendChild(p);
    });
} else {
    divErrores.innerHTML = "<p style='color: green;'>✅ Sin errores</p>";
}
    }
})



function agregaTablas(contenedor, tablaT, tablaE) {
    contenedor.replaceChildren(); 
    let boton = document.createElement("button");
    let boton2 = document.createElement("button");
    let divB = document.createElement("div");
    divB.className="pag"
    boton.innerText="<"
    boton2.innerText=">"
    divB.appendChild(boton)
    divB.appendChild(boton2)
    contenedor.appendChild(tablaT);
    contenedor.appendChild(divB);
    botonesH(boton,boton2)
    boton2.addEventListener("click",async e=>{
        page++;
        botonesH(boton,boton2);
        let data = textArea.value;
        const respuesta = await fetch(`http://localhost:8081/Paginacion?size=${size}&page=${page}`,{
            method: "POST",        
            body:data
                })
        const JSON = await respuesta.json();
        const tokens = JSON.Tokens.content;
        let tabla = crearTablaTokens(tokens);
        contenedor.querySelector("table").remove()
        contenedor.prepend(tabla)
     })
     boton.addEventListener("click",async e=>{
        page--;
        botonesH(boton,boton2);
        let data = textArea.value;
        const respuesta = await fetch(`http://localhost:8081/Paginacion?size=${size}&page=${page}`,{
            method: "POST",        
            body:data
                })
        const JSON = await respuesta.json();
        const tokens = JSON.Tokens.content;
        let tabla = crearTablaTokens(tokens);
        contenedor.querySelector("table").remove()
        contenedor.prepend(tabla)
     })

    if (tablaE) {
        
        const dialogoExistente = document.getElementById("dialogoErrores");
        if (dialogoExistente) dialogoExistente.remove();

        
        const dialogo = document.createElement("dialog");
        dialogo.id = "dialogoErrores";

        dialogo.appendChild(tablaE);

        document.body.appendChild(dialogo);
        dialogo.showModal();
    }
}


function crearTablaTokens(Tokens){
    let tabla=document.createElement("table");
    let thead= tabla.createTHead();
    let filaCabeza=document.createElement("tr");
    let lCabeza=document.createElement("td");
    let tCabeza=document.createElement("td");   
    tCabeza.innerText="Token";
    lCabeza.innerText="Lexema";
    filaCabeza.appendChild(lCabeza);
    filaCabeza.appendChild(tCabeza);
    thead.appendChild(filaCabeza);
    Tokens.map(t=>{
        let fila=document.createElement("tr");
        let celdaL=document.createElement("td");
        let celdaT=document.createElement("td");
        celdaL.innerText=t.lexema;
        celdaT.innerText=t.token;
        fila.appendChild(celdaL);
        fila.appendChild(celdaT);
        tabla.appendChild(fila); 
    })
    return tabla;
}

function crearTablaErrores(errores){
    let tablaE = document.createElement("table");
    let thead= tablaE.createTHead();
    let filaCabeza=document.createElement("tr");
    let celdaCabeza=document.createElement("td");
    celdaCabeza.innerText="Errores";
    filaCabeza.appendChild(celdaCabeza);
    thead.appendChild(filaCabeza);
    tablaE.appendChild(thead);
    let tbody=tablaE.createTBody();
    errores.map(e=>{
    let fila=document.createElement("tr");
    let celda=document.createElement("td");
    celda.innerText=e;
    fila.appendChild(celda);
    tbody.appendChild(fila); 
    })
    tablaE.appendChild(tbody);
    return tablaE;
}
function agregaTexto(contenedor, data) {
    contenedor.replaceChildren();
    const textoSinTabulaciones = data.replace(/\t/g, "    ");
    let textA = document.createElement("textarea");
    textA.value = textoSinTabulaciones;
    const lineas = textoSinTabulaciones.split("\n");
    const maxLongitud = Math.max(...lineas.map(linea => linea.length)); 
    textA.rows = Math.max(lineas.length, 5); 
    textA.cols = Math.max(maxLongitud, 20); 

    contenedor.appendChild(textA);
}

function botonesH(boton,boton2){
   if(page==0)boton.hidden=true;
   else if(page+1==3)boton2.hidden=true;
   else{
    boton.hidden=false;
    boton2.hidden=false;
   }
}
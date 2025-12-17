const API_URL = "http://localhost:8080/api/usuarios";

const form = document.getElementById("usuarioForm");
const tbody = document.getElementById("usuariosBody");
const cancelarBtn = document.getElementById("cancelar");


const idInput = document.getElementById("id");
const nombreInput = document.getElementById("nombre");
const apellidoInput = document.getElementById("apellido");
const edadInput = document.getElementById("edad");
const ciudadInput = document.getElementById("ciudad");
const rolInput = document.getElementById("rol");
const puntosInput = document.getElementById("puntos");
const activoInput = document.getElementById("activo");
let usuariosCache = [];

async function cargarUsuarios() {
    const res = await fetch(API_URL);
    const usuarios = await res.json();

    console.log("Respuesta completa del backend:", usuarios);

    usuariosCache = usuarios;
    tbody.innerHTML = "";

    usuarios.forEach(u => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${u.id}</td>
            <td>${u.nombre} ${u.apellido}</td>
            <td>${u.ciudad}</td>
            <td>${u.rol}</td>
            <td>${u.activo ? "Sí" : "No"}</td>
            <td>${u.puntos}</td>
            <td>
                <button class="btn-editar" onclick="editar('${u.id}')">Editar</button>
                <button class="btn-eliminar" onclick="eliminar('${u.id}')">Eliminar</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}


form.addEventListener("submit", async e => {
    e.preventDefault();

    const usuario = {
        id: idInput.value,
        nombre: nombreInput.value,
        apellido: apellidoInput.value,
        edad: Number(edadInput.value),
        ciudad: ciudadInput.value,
        rol: rolInput.value,
        puntos: Number(puntosInput.value),
        activo: activoInput.checked
    };

    await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });

    limpiarFormulario();
    cargarUsuarios();
});


function editar(id) {
    const u = usuariosCache.find(x => x.id === id);
    if (!u) return;

    idInput.value = u.id;
    nombreInput.value = u.nombre;
    apellidoInput.value = u.apellido;
    edadInput.value = u.edad;
    ciudadInput.value = u.ciudad;
    rolInput.value = u.rol;
    puntosInput.value = u.puntos;
    activoInput.checked = u.activo;
}


async function eliminar(id) {
    if (!confirm("¿Eliminar usuario?")) return;

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    cargarUsuarios();
}

function limpiarFormulario() {
    form.reset();
}

cargarUsuarios();

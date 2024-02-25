function borrarUsuario(idUsuario) {
    // Leer el token CSRF y el nombre del encabezado desde las etiquetas meta
    var token = $('meta[name="_csrf"]').attr('content');
    var header = $('meta[name="_csrf_header"]').attr('content');

    $.ajax({
        type: "POST",
        url: "/borrarUsuario",
        beforeSend: function(xhr) {
            // Añadir el encabezado CSRF a la solicitud
            xhr.setRequestHeader(header, token);
        },
        data: { idUsuario: idUsuario },
        success: function(response) {
            // Código para manejar la respuesta exitosa
            location.reload();
        },
        error: function(xhr, status, error) {
            // Código para manejar la respuesta de error
            console.error("Error al borrar usuario:", error);
            alert("Error al borrar usuario. Inténtelo de nuevo.");
        }
    });
}
function mostrarConfirmacionBorrado(idUsuario) {
	console.log("ID de usuario a borrar:", idUsuario);
    $('#confirmacionBorradoModal').modal('show');

    // Primero, desvinculamos cualquier manejador de eventos anterior para evitar duplicados
    $('#confirmarBorradoBtn').off('click').on('click', function() {
        borrarUsuario(idUsuario);       
        $('#confirmacionBorradoModal').modal('hide');
    });
    
    
}

function cargarUsuario(idUsuario) {
    // Realiza una solicitud AJAX para obtener los datos del usuario
    fetch(`/rutaParaObtenerUsuario/${idUsuario}`)
        .then(response => response.json())
        .then(data => {
            // Suponiendo que 'data' es el objeto con los datos del usuario
            // Ahora rellena los campos del modal con estos datos
            document.getElementById('nombreUsuario').value = data.nombre; // Ajusta según tu estructura de datos
            // Rellena los demás campos de forma similar

            // Finalmente, muestra el modal (Bootstrap 4)
            $('#usuarioModal').modal('show');
        })
        .catch(error => console.error('Error al cargar los datos del usuario:', error));
}
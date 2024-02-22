// Función para inicializar el modal de edición con los datos del equipo
function cargarEquipo(idEquipo) {
    // Aquí deberías hacer una solicitud AJAX para obtener los datos del equipo por su ID
    // y luego llenar los campos del formulario en el modal con estos datos.
    // Por simplicidad, se omite la solicitud AJAX y se muestra solo la estructura básica.
    console.log("Cargar datos del equipo con ID:", idEquipo);
    // Ejemplo de cómo establecer valores (deberías reemplazarlos con los datos reales obtenidos)
    $('#equipoId').val(idEquipo);
    $('#equipoNombre').val("Nombre del Equipo");
    $('#equipoDescripcion').val("Descripción del Equipo");
    // Abrir el modal
    $('#equipoModal').modal('show');
}

// Función para mostrar el modal de confirmación de borrado
function mostrarConfirmacionBorrado(idEquipo) {
    // Guardar el ID del equipo en un lugar accesible
    $('#confirmarBorradoBtn').data('idEquipo', idEquipo);
    $('#confirmacionBorradoModal').modal('show');
}

 // Agrega aquí tu JavaScript si es necesario
    function mostrarModalQR(idEquipo) {
        // Lógica para generar y mostrar el código QR
        console.log("Generar y mostrar QR para el equipo con ID:", idEquipo);
        // Mostrar el modal
        $('#codigoQRModal').modal('show');
    }

    function descargarQR() {
        // Lógica para descargar el código QR
        console.log("Descargar el QR mostrado");
    }

$(document).ready(function() {
    // Manejar la confirmación de borrado
    $('#confirmarBorradoBtn').click(function() {
        var idEquipo = $(this).data('idEquipo');
        console.log("Borrar el equipo con ID:", idEquipo);
        // Aquí deberías hacer una solicitud AJAX para borrar el equipo por su ID
        // Por simplicidad, se omite la solicitud AJAX y solo se muestra un mensaje
        alert("Equipo borrado (simulado).");

        // Cerrar el modal de confirmación
        $('#confirmacionBorradoModal').modal('hide');

        // Opcionalmente, recargar la página o actualizar la tabla de equipos para reflejar el cambio
        // location.reload();
    });

    // Función para buscar equipos por nombre en la tabla
    $('#buscarEquipo').keyup(function() {
        var texto = $(this).val().toLowerCase();
        $("#tablaEquipos tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(texto) > -1)
        });
    });
});
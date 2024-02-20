function borrarUsuario(idUsuario) {
    
        $.ajax({
            type: "POST",
            url: "/borrarUsuario",
            data: { idUsuario: idUsuario },
            success: function(response) {
                // Actualizar la página o la tabla de usuarios si es necesario
                location.reload(); // Recargar la página
            },
            error: function(xhr, status, error) {
                console.error("Error al borrar usuario:", error);
                alert("Error al borrar usuario. Inténtelo de nuevo.");
            }
        });
    
}
function mostrarConfirmacionBorrado(idUsuario) {
    $('#confirmacionBorradoModal').modal('show');

    $('#confirmarBorradoBtn').on('click', function() {
        borrarUsuario(idUsuario);
        $('#confirmacionBorradoModal').modal('hide');
    });
}
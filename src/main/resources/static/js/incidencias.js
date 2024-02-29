$(document).ready(function() {
	
	
    // Manjeador para el botón "Guardar Cambios"
    $('#guardarCambiosEstado').click(function() {
		console.log("Guardar Cambios")
        var idIncidencia = $('#incidenciaIdParaCambiarEstado').val(); // Obtiene el ID de la incidencia desde el campo oculto
        var nuevoEstado = $('#estadoNuevo').val(); // Obtiene el nuevo estado seleccionado en el modal
        
        // Llama a la función para procesar el cambio de estado
        procesarCambioEstado(idIncidencia, nuevoEstado);
    });
    
	function procesarCambioEstado(idIncidencia, nuevoEstado) {
		// Obtener el token CSRF y el nombre del header desde los metadatos
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		console.log('Procesando cambio de estado para la incidencia ID ' + idIncidencia + ' a ' + nuevoEstado);

		$.ajax({
			url: '/cambiarEstadoIncidencia',
			type: 'POST',
			data: {
				idIncidencia: idIncidencia,
				estado: nuevoEstado
			},
			// Añadir el token CSRF a las cabeceras de la solicitud
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success: function(response) {
				console.log(response);
			// Cerrar el modal de Bootstrap
            	$('#cambiarEstadoModal').modal('hide');
            // Recargar la página para reflejar los cambios
            	location.reload();
			},
			error: function(xhr, status, error) {
				console.error(error);
				// Manejo de errores
			}
		});
	}
    
    // Código para abrir el modal y establecer el ID y el estado actual de la incidencia
    $('#cambiarEstadoModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget); // Botón que activó el modal
        var idIncidencia = button.data('id'); // Extraer el ID de la incidencia desde el botón
        var estadoActual = button.data('estado'); // Extraer el estado actual
        
        var modal = $(this);
        modal.find('#incidenciaIdParaCambiarEstado').val(idIncidencia); // Establecer el ID en el campo oculto del modal
        modal.find('#estadoNuevo').val(estadoActual); // Establecer el estado actual en el selector
    });
});


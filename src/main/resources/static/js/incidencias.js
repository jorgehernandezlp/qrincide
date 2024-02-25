$(document).ready(function() {
function mostrarDesplegable(elemento) {
    var id = $(elemento).data('id');
    var estadoActual = $(elemento).text();
    $(elemento).hide();
    var select = $(elemento).next('.estado-select');
    select.val(estadoActual);
    select.show();
}

function cambiarEstado(select) {
    var id = $(select).data('id');
    var nuevoEstado = $(select).val();
    // Aquí puedes hacer una llamada AJAX para actualizar el estado en la base de datos
    $(select).hide();
    $(select).prev('.estado-texto').text(nuevoEstado).show();
    // Opcionalmente, recargar la página o actualizar de otra manera la vista
}
// Función para configurar el ID de la incidencia en el modal y preseleccionar el estado actual
function setIdIncidencia(idIncidencia, estadoActual) {
    // Establece el valor del campo oculto con el ID de la incidencia
    document.getElementById('incidenciaIdParaCambiarEstado').value = idIncidencia;

    // Opcional: preselecciona el estado actual en el desplegable
    document.getElementById('estadoNuevo').value = estadoActual;
}

// Función que se llama cuando se guarda el cambio de estado
function realizarCambioEstado() {
    var idIncidencia = document.getElementById('incidenciaIdParaCambiarEstado').value;
    var nuevoEstado = document.getElementById('estadoNuevo').value;

    // Aquí podrías enviar una solicitud AJAX al servidor para actualizar el estado de la incidencia
    console.log('Cambiando estado de incidencia ID ' + idIncidencia + ' a ' + nuevoEstado);

    // Cierra el modal manualmente después de realizar el cambio
    $('#cambiarEstadoModal').modal('hide');

    // Actualiza el estado en la interfaz de usuario, si es necesario
}

$('#cambiarEstadoModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget); // Botón que activó el modal
  var idIncidencia = button.data('id'); // Extraer el ID de la incidencia
  var estadoActual = button.data('estado'); // Extraer el estado actual

  var modal = $(this);
  modal.find('#incidenciaIdParaCambiarEstado').val(idIncidencia); // Pasar el ID al campo oculto
  modal.find('#estadoNuevo').val(estadoActual); // Seleccionar el estado actual en el desplegable
});

function realizarCambioEstado() {
    var idIncidencia = $('#incidenciaIdParaCambiarEstado').val();
    var nuevoEstado = $('#estadoNuevo').val();

    // Aquí deberías enviar una solicitud AJAX al servidor para actualizar el estado de la incidencia
    console.log('Cambiando estado de incidencia ID ' + idIncidencia + ' a ' + nuevoEstado);

    // Actualiza la interfaz de usuario según sea necesario
    // Esto puede incluir cerrar el modal y actualizar el botón del estado en la tabla

    $('#cambiarEstadoModal').modal('hide');
    // Asegúrate de actualizar también el estado en la tabla. Esto puede requerir recargar la tabla o parte de ella.
}
});
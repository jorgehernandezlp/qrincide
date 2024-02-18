$(document).ready(function() {
    $('#editarIncidenciaModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Botón que activó el modal
        var id = button.data('id'); // Extrae el id de los atributos de datos
        
        // Simulación: Aquí cargarías los datos de la incidencia desde tu servidor o directamente si ya están en la página
        var titulo = "Título ejemplo " + id;
        var descripcion = "Descripción ejemplo " + id;
        // Setear los valores en el modal
        var modal = $(this);
        modal.find('.modal-body #incidenciaId').val(id);
        modal.find('.modal-body #editarTitulo').val(titulo);
        modal.find('.modal-body #editarDescripcion').val(descripcion);
        // Aquí también puedes setear los valores de estado y prioridad según los datos de la incidencia
    });

    // Aquí manejarías el evento de clic en "Guardar Cambios" para actualizar la incidencia
    $('#guardarCambios').click(function() {
        // Aquí iría el código para enviar los cambios al servidor
    });
});

$(document).ready(function() {
	
    // Asegúrate de que el contenido principal se muestre al hacer clic en "Inicio"
    $("a.nav-link:contains('Inicio')").click(function(e) {
        e.preventDefault();
        // Cierra el modal de Nueva Incidencia si está abierto
        $('#nuevaIncidenciaModal').modal('hide');
        
    });
     // Inicialización de la fecha al crear una incidencia.
     $('#nuevaIncidenciaModal').on('show.bs.modal', function (e) {
        let fechaActual = new Date();
        let dia = ("0" + fechaActual.getDate()).slice(-2);
        let mes = ("0" + (fechaActual.getMonth() + 1)).slice(-2);
        let año = fechaActual.getFullYear();
        let horas = ("0" + fechaActual.getHours()).slice(-2);
        let minutos = ("0" + fechaActual.getMinutes()).slice(-2);
        let fechaFormateada = horas + ":" + minutos + " - " + dia + "/" + mes + "/" + año;
        $('#incidenciaFechaCreacion').val(fechaFormateada);
    });
    
    
});

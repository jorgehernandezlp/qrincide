
$(document).ready(function() {
	    
    $("a.nav-link:contains('Inicio')").click(function(e) {
        e.preventDefault();
        $('#nuevaIncidenciaModal').modal('hide');
    });

    // Inicialización de la fecha al crear una incidencia.
    $('#nuevaIncidenciaModal').on('show.bs.modal', function(e) {
        let fechaActual = new Date();
        let dia = ("0" + fechaActual.getDate()).slice(-2);
        let mes = ("0" + (fechaActual.getMonth() + 1)).slice(-2);
        let año = fechaActual.getFullYear();
        let horas = ("0" + fechaActual.getHours()).slice(-2);
        let minutos = ("0" + fechaActual.getMinutes()).slice(-2);
        let fechaFormateada = horas + ":" + minutos + " - " + dia + "/" + mes + "/" + año;
        $('#incidenciaFechaCreacion').val(fechaFormateada);
        
        cargarEquipos(); 
    });
	// Función para cargar los equipos en el modal al realizar una incidencia
    function cargarEquipos() {
        fetch(`/buscarEquipos?`)
            .then(response => {
                if (!response.ok) throw new Error('Error en la respuesta del servidor');
                return response.json();
            })
            .then(data => {
                var selectEquipo = document.getElementById('incidenciaEquipo');
                selectEquipo.innerHTML = ''; // Limpia el select antes de agregar nuevas opciones
                
               
                var opcionDefault = document.createElement('option');
                opcionDefault.value = '';
                opcionDefault.textContent = 'Seleccione un equipo';
                selectEquipo.appendChild(opcionDefault);

                // Añade cada equipo como una opción en el select
                data.forEach(equipo => {
                    var opcion = document.createElement('option');
                    opcion.value = equipo.idEquipo; 
                    opcion.textContent = equipo.marca + ' ' + equipo.modelo; // Muestra marca y modelo como texto
                    selectEquipo.appendChild(opcion);
                });
            })
            .catch(error => {
                console.error('Error al cargar los equipos:', error);
                
            });
    }
    
    // Controlador para el envío del formulario
    $('#formNuevaIncidencia').on('submit', function(e) {
        e.preventDefault(); // Evita el envío del formulario de la manera tradicional        
        var datosIncidencia = $(this).serialize();       
        // Obtiene el token CSRF y el nombre del encabezado de los metadatos
        var token = $('meta[name="_csrf"]').attr('content');
    	var header = $('meta[name="_csrf_header"]').attr('content');
		var idUsuario = $('#idUsuario').val();
		console.log(idUsuario);
        // Envía los datos a través de AJAX al controlador
        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: datosIncidencia,
            beforeSend: function(xhr) {
                // Aquí se establece el encabezado CSRF
                xhr.setRequestHeader(header, token);
            },
            success: function(response) {
                console.log('Incidencia guardada:', response);
                $('#nuevaIncidenciaModal').modal('hide');
                 location.reload();
            },
            error: function(error) {
                console.error('Error al guardar la incidencia:', error);
               
            }
        });
    });
});
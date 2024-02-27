

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
		// Leer el token CSRF y el nombre del encabezado desde las etiquetas meta
		var token = $('meta[name="_csrf"]').attr('content');
		var header = $('meta[name="_csrf_header"]').attr('content');

		var idEquipo = $(this).data('idEquipo');
		console.log("Borrar el equipo con ID:", idEquipo);
		$.ajax({
			type: "POST",
			url: "/borrarEquipo",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			data: { idEquipo: idEquipo },
			success: function(response) {
				// Código para manejar la respuesta exitosa
				location.reload();
			},
			error: function(xhr, status, error) {
				// Código para manejar la respuesta de error
				console.error("Error al borrar equipo:", error);
				alert("Error al borrar usuario. Inténtelo de nuevo.");
			}
		});
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


function generarYMostrarQR(idEquipo) {
	var urlDestino = 'http://localhost:8080/registrarIncidencia?idEquipo=' + idEquipo;
	var qrcodeContainer = document.getElementById('qrcode');
	qrcodeContainer.innerHTML = "";

	var qr = new QRCode(qrcodeContainer, {
		text: urlDestino,
		width: 128,
		height: 128,
		colorDark: "#000000",
		colorLight: "#ffffff",
		correctLevel: QRCode.CorrectLevel.H
	});

	// Actualizar el texto con la URL
	document.getElementById('qrUrl').textContent = urlDestino;
	document.getElementById('openLinkBtn').href = urlDestino;
	
	$('#qrModal').modal('show');
}

// Función para imprimir el QR
function imprimirQR() {
    // Obtener el contenido HTML del elemento que contiene el código QR.
    var contenido = document.getElementById('qrcode').innerHTML;

    // Crear una nueva ventana para la impresión.
    var ventanaImpresion = window.open('', '_blank');

    // Esperar a que la ventana de impresión esté cargada antes de añadir el contenido y imprimir.
    ventanaImpresion.document.open();
    ventanaImpresion.document.write('<html><head><title>Imprimir QR</title></head><body>');
    ventanaImpresion.document.write(contenido);
    ventanaImpresion.document.write('</body></html>');
    ventanaImpresion.document.close(); // Importante para ciertos navegadores para que disparen el load.
    
    // Una vez que la ventana está cargada, imprimir y cerrar.
    ventanaImpresion.onload = function() {
        ventanaImpresion.print();
        ventanaImpresion.close();
    };
}

function cerrarModal() {
	// Cerrar el modal
	$('#qrModal').modal('hide');
}

function actualizarHrefDelBoton() {
  var qrUrl = document.getElementById('qrUrl').textContent;
  var botonAbrirEnlace = document.getElementById('openLinkBtn');
  botonAbrirEnlace.href = qrUrl;
}
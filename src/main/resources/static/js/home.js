
$(document).ready(function() {

	// Asegúrate de que el contenido principal se muestre al hacer clic en "Inicio"
	$("a.nav-link:contains('Inicio')").click(function(e) {
		e.preventDefault();
		// Cierra el modal de Nueva Incidencia si está abierto
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
	});

	document.addEventListener("DOMContentLoaded", function() {
		var inputEquipo = document.getElementById('incidenciaEquipo');

		inputEquipo.addEventListener('input', function() {
			var query = this.value;
			if (query.length < 2) { // Para evitar búsquedas con muy pocos caracteres
				document.getElementById('listaEquipos').innerHTML = '';
				return;
			}

			fetch(`/buscarEquipos?query=${query}`)
				.then(response => response.json())
				.then(data => {
					var lista = document.getElementById('listaEquipos');
					lista.innerHTML = '';
					data.forEach(equipo => {
						var div = document.createElement('div');
						div.innerHTML = equipo.nombre; // Asumiendo que 'nombre' es la propiedad que quieres mostrar
						div.classList.add('sugerencia');
						div.addEventListener('click', function() {
							inputEquipo.value = equipo.nombre; // Ajusta si es necesario
							lista.innerHTML = ''; // Limpia la lista una vez seleccionado un equipo
						});
						lista.appendChild(div);
					});
				});
		});
	});


	document.addEventListener("DOMContentLoaded", function() {
		var inputEquipo = document.getElementById('incidenciaEquipo');

		inputEquipo.addEventListener('input', function() {
			var query = this.value;
			if (query.length < 2) { 
				document.getElementById('listaEquipos').innerHTML = '';
				return;
			}

			fetch(`/buscarEquipos?`)
				.then(response => response.json())
				.then(data => {
					var lista = document.getElementById('listaEquipos');
					lista.innerHTML = '';
					data.forEach(incidencia => {
						var div = document.createElement('div');
						// Modifica esta línea para usar marcaEquipo y modeloEquipo
						div.innerHTML = `${incidencia.marcaEquipo} ${incidencia.modeloEquipo}`;
						div.classList.add('sugerencia');
						div.addEventListener('click', function() {
							// Aquí también puedes ajustar lo que se establece como valor del input
							inputEquipo.value = `${incidencia.marcaEquipo} ${incidencia.modeloEquipo}`;
							lista.innerHTML = ''; // Limpia la lista una vez seleccionado un equipo
						});
						lista.appendChild(div);
					});
				});
		});
	});
});

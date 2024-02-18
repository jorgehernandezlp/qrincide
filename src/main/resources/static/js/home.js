//Se sobrecarga el botón toggle
$(function() {
  // Sidebar toggle behavior
  $('#sidebarCollapse').on('click', function() {
    $('#sidebar, #content').toggleClass('active');
  });

});

//Se sobrecarga botón de Guardar de ventana modal de Nueva Película y 
//evento onclick de Listar Películas
$(document).ready(function(){
  
/////////////////////////////////////////////////////////////////////////////  
//Aquí deberías implementar la llamada asincrona al servidor para enviar por
//método POST los datos de la nueva película.  
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
$('#formularioAgregarPelicula').on('submit', function(e) {
        e.preventDefault(); // Evita que el formulario se envíe de la manera tradicional
        
        var formData = $(this).serialize(); // Serializa los datos del formulario para el envío		
		console.log(header, token)
		console.log(formData);
        $.ajax({
            url: '/nuevaPelicula', // Ruta de la controladora POST
            type: 'POST',
            contentType: 'application/json',
             data: {
        titulo: "PruebaTitulo",
        sinopsis: "PruebaSinopsis",
        genero: 2,
        director: "PruebaDirector",
        reparto: "PruebaReparto",
        anio: 1991,
        fechaEstreno: "2024-02-18",
        distribuidor: 11,
        pais: 12
    },
           	beforeSend: function(xhr) {
        	xhr.setRequestHeader(header, token);
    		},
            success: function(response) {				
				                console.log(response)
                $("#NuevaPeliculaCenter").modal('hide'); 
                $('body').removeClass('modal-open'); 
                $('.modal-backdrop').remove(); 

                // Muestra la lista de películas si todo fue exitoso
                $('#ListarPeliculasVisual').modal('show');
            },
            error: function(xhr, status, error) {
                // Manejo del error
                console.error("Error al guardar la película: " + error);
            }
        });
    });


  ///////////////////////////////////////////////////////////////////////
  //Aquí debería implementar la llamada asíncrona para obtener los datos de las películas
  //y cargarlos dentro de la capa #htmlListaPeliculas
  
 $.ajax({
    url: '/getListadoPeliculas',
    type: 'GET',
    dataType: 'json', 
   success: function(peliculas) {    	   
    var html = '';
    peliculas.forEach(function(pelicula) {    
    html += '<div class="contenedor-pelicula">';
    html += '<h3>' + (pelicula.titulo || 'Título no disponible') + '</h3>';
    if (pelicula.sinopsis) {
        html += '<div class="detalle-pelicula"><span>Sinopsis:</span> ' + pelicula.sinopsis + '</div>';
    }
    if (pelicula.genero) {
        html += '<div class="detalle-pelicula"><span>Género:</span> ' + pelicula.genero + '</div>';
    }
    if (pelicula.director) {
        html += '<div class="detalle-pelicula"><span>Director:</span> ' + pelicula.director + '</div>';
    }
    if (pelicula.reparto) {
        html += '<div class="detalle-pelicula"><span>Reparto:</span> ' + pelicula.reparto + '</div>';
    }
    if (pelicula.anio) {
        html += '<div class="detalle-pelicula"><span>Año:</span> ' + pelicula.anio + '</div>';
    }
    if (pelicula.fechaEstreno) {
        html += '<div class="detalle-pelicula"><span>Fecha del Estreno:</span> ' + pelicula.fechaEstreno + '</div>';
    }
    if (pelicula.productor) {
        html += '<div class="detalle-pelicula"><span>Distribuidor:</span> ' + pelicula.productor + '</div>';
    }
    if (pelicula.pais) {
        html += '<div class="detalle-pelicula"><span>País:</span> ' + pelicula.pais + '</div>';
    }
    html += '</div>';

});
    $("#htmlListaPeliculas").html(html);
},
    error: function(xhr, status, error) {
        console.error("Error: " + error);
    }
});
  
  
})
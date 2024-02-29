 function getQueryParam(param) {
        const queryParams = new URLSearchParams(window.location.search);
        return queryParams.get(param);
    }

    function formatCurrentDate() {
        const now = new Date();
        const hours = now.getHours().toString().padStart(2, '0');
        const minutes = now.getMinutes().toString().padStart(2, '0');
        const day = now.getDate().toString().padStart(2, '0');
        const month = (now.getMonth() + 1).toString().padStart(2, '0'); // +1 porque getMonth() devuelve 0-11
        const year = now.getFullYear();
        return `${hours}:${minutes} - ${day}/${month}/${year}`;
    }

    window.onload = function() {
        // Asigna el ID del equipo a partir de la URL
        const equipoId = getQueryParam('idEquipo');        
        document.getElementById('equipo').value = equipoId || 'ID no encontrado';

        // Establece la fecha actual con el formato deseado
        document.getElementById('fechaCreacion').value = formatCurrentDate();
    };
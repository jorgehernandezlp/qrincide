package es.prw.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Incidencia {
	

    private int idIncidencia;
    private int idUsuario;
    private int idEquipo;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private Date fechaHoraAsignacion;
    private Date fechaHoraResolucion;
    private String estado;
    private String prioridad;    
    private String nombreUsuario;
    private String marcaEquipo;
    private String modeloEquipo;

    // Constructor que acepta un ResultSet
    public Incidencia(ResultSet rs) throws SQLException {
        this.idIncidencia = rs.getInt("ID_Incidencia");
        this.idUsuario = rs.getInt("ID_Usuario");
        this.idEquipo = rs.getInt("ID_Equipo");
        this.titulo = rs.getString("Titulo");
        this.descripcion = rs.getString("Descripción");
        this.ubicacion = rs.getString("Ubicación");
        this.fechaHoraAsignacion = rs.getDate("FechaHoraAsignación");
        this.fechaHoraResolucion = rs.getDate("FechaHoraResolución");
        this.estado = rs.getString("Estado");
        this.prioridad = rs.getString("Prioridad");
        // Asumiendo que las columnas de usuario y equipo están presentes en el ResultSet
        this.nombreUsuario = rs.getString("UsuarioNombre");
        this.marcaEquipo = rs.getString("EquipoMarca");
        this.modeloEquipo = rs.getString("EquipoModelo");
    }
}

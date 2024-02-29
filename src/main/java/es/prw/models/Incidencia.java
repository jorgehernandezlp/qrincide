package es.prw.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Incidencia {
	

    public int getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Date getFechaHoraAsignacion() {
		return fechaHoraAsignacion;
	}

	public void setFechaHoraAsignacion(Date fechaHoraAsignacion) {
		this.fechaHoraAsignacion = fechaHoraAsignacion;
	}

	public Date getFechaHoraResolucion() {
		return fechaHoraResolucion;
	}

	public void setFechaHoraResolucion(Date fechaHoraResolucion) {
		this.fechaHoraResolucion = fechaHoraResolucion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMarcaEquipo() {
		return marcaEquipo;
	}

	public void setMarcaEquipo(String marcaEquipo) {
		this.marcaEquipo = marcaEquipo;
	}

	public String getModeloEquipo() {
		return modeloEquipo;
	}

	public void setModeloEquipo(String modeloEquipo) {
		this.modeloEquipo = modeloEquipo;
	}

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
        this.nombreUsuario = rs.getString("UsuarioNombre");
        this.marcaEquipo = rs.getString("EquipoMarca");
        this.modeloEquipo = rs.getString("EquipoModelo");
    }
    
    
}

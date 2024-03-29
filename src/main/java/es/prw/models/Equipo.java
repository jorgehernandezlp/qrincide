package es.prw.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Equipo {
    private int idEquipo;    
    private String tipo;
    private String marca;
    private String modelo;
    private String descripcion;
    private String ubicacion;

    
    public Equipo() {
    }

    // Constructor que acepta un ResultSet
    public Equipo(ResultSet rs) {
        try {
            this.idEquipo = rs.getInt("ID_Equipo");            
            this.tipo = rs.getString("Tipo");
            this.marca = rs.getString("Marca");
            this.modelo = rs.getString("Modelo");
            this.descripcion = rs.getString("Descripción");
            this.ubicacion = rs.getString("Ubicación");
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }

    // Getters y setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

  

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    // Método toString() para representación en cadena de la instancia de Equipo
    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +                
                ", tipo='" + tipo + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
package es.prw.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String rol;
    private String contraseña;

    // Constructor que acepta un ResultSet
    public Usuario(ResultSet rs) throws SQLException {        
        this.idUsuario =  (int) rs.getLong("ID_Usuario");
        this.nombre = rs.getString("Nombre");
        this.email = rs.getString("Email");
        this.rol = rs.getString("Rol");
        this.contraseña = rs.getString("Contraseña");
    }

    // Getters y setters para tus campos

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}

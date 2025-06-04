package modelo;

import java.sql.Date;

public class ClienteOtaku {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private Date fechaRegistro;

    // Constructor para crear nuevo cliente (sin id ni fechaRegistro que es automático)
    public ClienteOtaku(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Constructor completo (para obtener cliente de la base de datos, con id y fechaRegistro)
    public ClienteOtaku(int id, String nombre, String email, String telefono, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

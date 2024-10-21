/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cita;

/**
 *
 * @author edin1
 */
public class CitaModelo {

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the idCita
     */
    public int getIdCita() {
        return idCita;
    }

    /**
     * @param idCita the idCita to set
     */
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    /**
     * @return the dependiente
     */
    public String getDependiente() {
        return dependiente;
    }

    /**
     * @param dependiente the dependiente to set
     */
    public void setDependiente(String dependiente) {
        this.dependiente = dependiente;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the fechahora
     */
    public String getFechahora() {
        return fechahora;
    }

    /**
     * @param fechahora the fechahora to set
     */
    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public CitaModelo() {
    }
    private int idCita;
    private String dependiente;
    private int idUsuario;
    private String fechahora;
    private String comentarios;
    private int estado;
}

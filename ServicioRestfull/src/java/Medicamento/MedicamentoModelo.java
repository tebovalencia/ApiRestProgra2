/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medicamento;

/**
 *
 * @author edin1
 */
public class MedicamentoModelo {

    /**
     * @return the idMedicamento
     */
    public int getIdMedicamento() {
        return idMedicamento;
    }

    /**
     * @param idMedicamento the idMedicamento to set
     */
    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    /**
     * @return the descipcion
     */
    public String getDescipcion() {
        return descipcion;
    }

    /**
     * @param descipcion the descipcion to set
     */
    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    /**
     * @return the unidadMedida
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * @param unidadMedida the unidadMedida to set
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public MedicamentoModelo() {
        
    }

    private int idMedicamento;
    private String descipcion;
    private String unidadMedida;
}

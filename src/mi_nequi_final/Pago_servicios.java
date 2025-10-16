/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mi_nequi_final;



public class Pago_servicios {
    private final String servicio;
    private final double valor;
//constructor
    public Pago_servicios(String servicio, double valor) {
        this.servicio = servicio;
        this.valor = valor;
    }
// metoddos get 
    public String getServicio() {
        return servicio;
    }

    public double getValor() {
        return valor;
    }
}

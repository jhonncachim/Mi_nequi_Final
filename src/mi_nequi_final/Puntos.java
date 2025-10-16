/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mi_nequi_final;

/**
 *
 * @author ASUS VIVOBOOK
 */

public class Puntos {
    private int puntos;

    public Puntos() {
        this.puntos = 0; // Empieza con 0
    }
//metodos
    public void ganarPuntos(int cantidad) {
        puntos += cantidad;
        System.out.println(" Has ganado " + cantidad + " puntos. Total: " + puntos);
    }

    public void mostrarPuntos() {
        System.out.println(" Actualmente tienes " + puntos + " puntos.");
    }

    public int getPuntos() {
        return puntos;
    }
}

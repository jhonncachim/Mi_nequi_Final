/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mi_nequi_final;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class Saldo {
    private double valor; // Dinero disponible en la cuenta

    // Constructor: recibe el saldo inicial
    public Saldo(double valorInicial) {
        this.valor = valorInicial;
    }

    // Retornar el saldo actual
    public double getValor() {
        return valor;
    }

    // Aumentar saldo (cuando se consigna dinero)
    public void consignar(double monto) {
        this.valor += monto;
    }

    // Disminuir saldo (cuando se retira dinero)
    public boolean retirar(double monto) {
        if (monto <= valor) { // Verifica si hay dinero suficiente
            valor -= monto;
            return true; // Retiro exitoso
        }
        return false; // Saldo insuficiente
    }
}
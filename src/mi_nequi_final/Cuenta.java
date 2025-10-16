/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mi_nequi_final;

import java.util.ArrayList;

/**
 *
 * @author ASUS VIVOBOOK
 */

/**
 * Cuenta: usuario, contraseña, saldo, historial y puntos (gamificación).
 */


public class Cuenta {
    private final String usuario;               // Usuario dueño de la cuenta
    private final String password;              // Contraseña de la cuenta
    private final String telefono;              // 📞 Teléfono asociado
    private final Saldo saldo;                  // Composición: cada cuenta tiene un saldo
    private final ArrayList<String> movimientos;// Historial de movimientos
    private final Puntos puntos;                // Sistema de puntos (gamificación)

    // 🔹 Constructor: ahora recibe también el teléfono
    public Cuenta(String usuario, String password, String telefono, double saldoInicial) {
        this.usuario = usuario;
        this.password = password;
        this.telefono = telefono;
        this.saldo = new Saldo(saldoInicial);//composicion
        this.movimientos = new ArrayList<>();//composicion
        this.puntos = new Puntos();//composicion
    }

    // Getters básicos
    public String getUsuario() {
        return usuario;
    }

    public boolean validarPassword(String pass) {
        return this.password.equals(pass);
    }

    public String getTelefono() {
        return telefono;
    }

    public Puntos getPuntos() {
        return puntos;
    }

    public double getSaldo() {
        return saldo.getValor();
    }

    // Operaciones metodos
    public void consignar(double monto) {
        saldo.consignar(monto);
        movimientos.add("Consignación de $" + monto);
        puntos.ganarPuntos(5); //  cada consignación suma puntos
    }

    public boolean retirar(double monto) {
        if (saldo.retirar(monto)) {
            movimientos.add("Retiro de $" + monto);
            puntos.ganarPuntos(2); //  retirar también da puntos
            return true;
        }
        return false;
    }

    // Pagar servicios
    public boolean pagarServicio(Pago_servicios servicio) {
        if (saldo.retirar(servicio.getValor())) {
            movimientos.add("Pago de " + servicio.getServicio() + " por $" + servicio.getValor());
            puntos.ganarPuntos(10); // 🎯 más puntos por pagar servicios
            return true;
        } else {
            return false;
        }
    }

    public void mostrarMovimientos() {
        System.out.println("\n Movimientos de " + usuario + " (Tel: " + telefono + "):");
        for (String m : movimientos) {
            System.out.println("- " + m);
        }
    }
}

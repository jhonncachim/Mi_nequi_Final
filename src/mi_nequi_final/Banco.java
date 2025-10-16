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

public class Banco {
    protected String nombre;               // Nombre del banco
    protected ArrayList<Cuenta> cuentas;   // Lista de cuentas (Agregación)

    // Constructor
    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new ArrayList<>();
    }

    // Método getter para obtener el nombre del banco
    public String getNombre() {
        return nombre;
    }

    // Agregar cuenta a la lista
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    // Buscar cuenta por usuario
    public Cuenta buscarCuenta(String usuario) {
        for (Cuenta c : cuentas) {
            if (c.getUsuario().equals(usuario)) {
                return c;
            }
        }
        return null;
    }
}

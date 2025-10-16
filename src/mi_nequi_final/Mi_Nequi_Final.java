/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package mi_nequi_final;

import java.util.Scanner;
// Importamos clases de iText para trabajar con PDFs
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author ASUS VIVOBOOK
*/

public class Mi_Nequi_Final {

    // Método auxiliar para crear un PDF con el recibo de la transacción
    private static void generarRecibo(String usuario, String banco, String tipo, double monto, double saldo) {
        Document document = new Document();
        try {
            // Nombre único del PDF con usuario y tipo de operación
            String nombreArchivo = "Recibo_" + usuario + "_" + tipo + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();
            // Contenido del recibo
            document.add(new Paragraph("----- RECIBO DE TRANSACCION -----"));
            document.add(new Paragraph("Usuario: " + usuario));
            document.add(new Paragraph("Banco: " + banco));
            document.add(new Paragraph("Tipo de operacion: " + tipo));
            document.add(new Paragraph("Monto: $" + monto));
            document.add(new Paragraph("Saldo final: $" + saldo));
            document.add(new Paragraph("Gracias por usar el Cajero Springel"));
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Error al generar recibo: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public static void main(String[] args) {
        // Usamos Scanner para leer datos desde el teclado
        try (Scanner sc = new Scanner(System.in)) {
            
            // Crear instancias de diferentes bancos disponibles
            Banco nequi = new BancoNequi();
            Banco bancolombia = new BancoBancolombia();
            Banco davivienda = new BancoDavivienda();

            // Guardamos todos los bancos en un arreglo para poder recorrerlos
            Banco[] bancos = new Banco[]{nequi, bancolombia, davivienda};
            
            // Bucle principal del cajero: se repetirá hasta que el usuario decida salir
            while (true) {
                // Menú de opciones del cajero
                System.out.println("\n  CAJERO SPRINGEL   ");
                System.out.println("1. Crear cuenta");
                System.out.println("2. Retiro");
                System.out.println("3. Consignacion");
                System.out.println("4. Consultar saldo");
                System.out.println("5. Ver movimientos");
                System.out.println("6. Ver puntos ");     // opción nueva
                System.out.println("7. Pagar servicios "); // opción nueva
                System.out.println("8. Salir");
                System.out.print("Seleccione opcion (1-8): ");
                
                // Leer la opción como texto y convertirla a número
                String linea = sc.nextLine().trim();
                int opcion;
                try {
                    opcion = Integer.parseInt(linea);
                } catch (NumberFormatException e) {
                    // Si no se puede convertir a número, se muestra mensaje de error
                    System.out.println("Entrada invalida. Ingresa un numero entre 1 y 8.");
                    continue; // vuelve al menú
                }
                
                // Si la opción es 8, cerramos el programa
                if (opcion == 8) {
                    System.out.println("Gracias por usar el cajero. Hasta luego");
                    break;
                }
                
                // Opción 1: Crear cuenta (no requiere login)
                if (opcion == 1) {
                    try {
                        System.out.println("\n   CREAR CUENTA   ");
                        System.out.print("Selecciona banco (1=Nequi 2=Bancolombia 3=Davivienda): ");
                        int bancoSel = Integer.parseInt(sc.nextLine().trim());
                        
                        System.out.print("Nombre de usuario: ");
                        String usuario = sc.nextLine().trim();
                        
                        System.out.print("Passwor: ");
                        String password = sc.nextLine().trim();
                        
                        System.out.print("Número de teléfono: ");
                        String telefono = sc.nextLine().trim();

                        System.out.print("Saldo inicial: ");
                        double saldoInicial = Double.parseDouble(sc.nextLine().trim());
                        
                        // Crear nueva cuenta con los datos ingresados
                        Cuenta nueva = new Cuenta(usuario, password, telefono, saldoInicial);

                        // Agregar la cuenta al banco seleccionado
                        switch (bancoSel) {
                            case 1 -> nequi.agregarCuenta(nueva);
                            case 2 -> bancolombia.agregarCuenta(nueva);
                            case 3 -> davivienda.agregarCuenta(nueva);
                            default -> {
                                System.out.println("Banco invalido. Cuenta no creada.");
                                continue;
                            }
                        }
                        System.out.println(" Cuenta creada con exito para " + usuario);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrada numerica invalida. Intenta de nuevo.");
                    }
                    continue; // vuelve al menú principal
                }
                
                // Opciones 2..7: requieren login con usuario y contraseña
                if (opcion >= 2 && opcion <= 7) {
                    System.out.print("Usuario: ");
                    String usuario = sc.nextLine().trim();
                    System.out.print("Passwor: ");
                    String password = sc.nextLine().trim();
                    
                    // Buscar la cuenta del usuario en los bancos
                    Cuenta cuenta = buscarCuentaEnBancos(usuario, bancos);
                    Banco banco = encontrarBancoDeCuenta(usuario, bancos);
                    
                    // Validar si la cuenta existe
                    if (cuenta == null) {
                        System.out.println("Usuario no encontrado.");
                        continue;
                    }
                    // Validar la contraseña
                    if (!cuenta.validarPassword(password)) {
                        System.out.println("Passwor incorrecta.");
                        continue;
                    }
                    
                    // Si el login es correcto, procesamos la opción elegida
                    switch (opcion) {
                        case 2 -> { // RETIRO
                            try {
                                System.out.print("Monto a retirar: ");
                                double monto = Double.parseDouble(sc.nextLine().trim());
                                if (monto <= 0) {
                                    System.out.println("Monto invalido.");
                                    break;
                                }
                                // Intentar retirar saldo
                                if (cuenta.retirar(monto)) {
                                    System.out.println(" Retiro exitoso en " + banco.getNombre());
                                    // >>> Nuevo: generar PDF de recibo
                                    generarRecibo(usuario, banco.getNombre(), "Retiro", monto, cuenta.getSaldo());
                                } else {
                                    System.out.println(" Fondos insuficientes.");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Monto invalido.");
                            }
                        }
                        case 3 -> { // CONSIGNACIÓN
                            try {
                                System.out.print("Monto a consignar: ");
                                double monto = Double.parseDouble(sc.nextLine().trim());
                                if (monto <= 0) {
                                    System.out.println("Monto invalido.");
                                    break;
                                }
                                // Sumar al saldo
                                cuenta.consignar(monto);
                                System.out.println(" Consignacion exitosa en " + banco.getNombre());
                                // >>> Nuevo: generar PDF de recibo
                                generarRecibo(usuario, banco.getNombre(), "Consignacion", monto, cuenta.getSaldo());
                            } catch (NumberFormatException ex) {
                                System.out.println("Monto invalido.");
                            }
                        }
                        case 4 -> { // CONSULTAR SALDO
                            System.out.println(" Saldo actual en " + banco.getNombre() + ": $" + String.format("%.2f", cuenta.getSaldo()));
                        }
                        case 5 -> { // VER MOVIMIENTOS
                            cuenta.mostrarMovimientos();
                        }
                        case 6 -> { // VER PUNTOS
                            cuenta.getPuntos().mostrarPuntos();
                        }
                        case 7 -> { // PAGAR SERVICIOS
                            try {
                                System.out.println("Elige servicio: 1=Agua  2=Luz  3=Internet  4=Otro");
                                int serv = Integer.parseInt(sc.nextLine().trim());
                                // Asignar nombre al servicio según la selección
                                String nombreServicio = switch (serv) {
                                    case 1 -> "Agua";
                                    case 2 -> "Luz";
                                    case 3 -> "Internet";
                                    default -> "Otro";
                                };
                                System.out.print("Ingresa el valor del servicio: ");
                                double valor = Double.parseDouble(sc.nextLine().trim());
                                if (valor <= 0) {
                                    System.out.println("Valor inválido.");
                                    break;
                                }
                                // Crear objeto de pago y procesar
                                Pago_servicios ps = new Pago_servicios(nombreServicio, valor);
                                boolean ok = cuenta.pagarServicio(ps);
                                if (ok) {
                                    System.out.println(" Pago de " + nombreServicio + " realizado. Se descontó $" + valor);
                                    // >>> Nuevo: generar PDF de recibo
                                    generarRecibo(usuario, banco.getNombre(), "Pago_" + nombreServicio, valor, cuenta.getSaldo());
                                } else System.out.println(" No hay saldo suficiente para pagar " + nombreServicio);
                            } catch (NumberFormatException ex) {
                                System.out.println("Entrada numerica inválida para servicio/valor.");
                            }
                        }
                        default -> System.out.println("Opcion no valida.");
                    } // fin switch autenticado
                } else {
                    // Si el usuario ingresa una opción fuera de 1..8
                    System.out.println("Opcion no válida. Intenta con un número entre 1 y 8.");
                }
            } // fin while
        }
    }

    // Método auxiliar: busca una cuenta en todos los bancos por nombre de usuario
    private static Cuenta buscarCuentaEnBancos(String usuario, Banco[] bancos) {
        for (Banco b : bancos) {
            Cuenta c = b.buscarCuenta(usuario);
            if (c != null) return c;
        }
        return null;
    }

    // Método auxiliar: retorna el banco que contiene la cuenta indicada
    private static Banco encontrarBancoDeCuenta(String usuario, Banco[] bancos) {
        for (Banco b : bancos) {
            if (b.buscarCuenta(usuario) != null) return b;
        }
        return null;
    }
}
/*
package mi_nequi_final;

import java.util.Scanner;
// Importamos clases de iText para trabajar con PDFs
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author ASUS VIVOBOOK
 */
/*
public class Mi_Nequi_Final {

    // ===============================
    // MÉTODO PARA GENERAR EL RECIBO EN PDF
    // ===============================
    private static void generarRecibo(String usuario, String banco, String tipo, double monto, double saldo) {
        Document document = new Document();
        try {
            // Nombre único del PDF con usuario y tipo de operación
            String nombreArchivo = "Recibo_" + usuario + "_" + tipo + ".pdf";

            // Se crea el escritor de PDF y se asocia al archivo
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));

            // Abrimos el documento
            document.open();

            // Contenido del recibo
            document.add(new Paragraph("----- RECIBO DE TRANSACCION -----"));
            document.add(new Paragraph("Usuario: " + usuario));
            document.add(new Paragraph("Banco: " + banco));
            document.add(new Paragraph("Operacion: " + tipo));
            document.add(new Paragraph("Monto: $" + monto));
            document.add(new Paragraph("Saldo final: $" + saldo));
            document.add(new Paragraph("Gracias por usar el Cajero Springel"));

            // Mensaje de confirmación en consola
            System.out.println("Recibo PDF generado: " + nombreArchivo);

        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Error al generar recibo: " + e.getMessage());
        } finally {
            // Cerramos el documento aunque haya error
            document.close();
        }
    }

    public static void main(String[] args) {
        // Usamos Scanner para leer datos desde el teclado
        try (Scanner sc = new Scanner(System.in)) {

            // Crear instancias de diferentes bancos
            Banco nequi = new BancoNequi();
            Banco bancolombia = new BancoBancolombia();
            Banco davivienda = new BancoDavivienda();

            // Guardamos todos los bancos en un arreglo
            Banco[] bancos = new Banco[]{nequi, bancolombia, davivienda};

            // Bucle principal
            while (true) {
                System.out.println("\n  CAJERO SPRINGEL   ");
                System.out.println("1. Crear cuenta");
                System.out.println("2. Retiro");
                System.out.println("3. Consignacion");
                System.out.println("4. Consultar saldo");
                System.out.println("5. Ver movimientos");
                System.out.println("6. Ver puntos");
                System.out.println("7. Pagar servicios");
                System.out.println("8. Salir");
                System.out.print("Seleccione opcion (1-8): ");

                String linea = sc.nextLine().trim();
                int opcion;
                try {
                    opcion = Integer.parseInt(linea);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada invalida. Ingresa un numero entre 1 y 8.");
                    continue;
                }

                if (opcion == 8) {
                    System.out.println("Gracias por usar el cajero. Hasta luego");
                    break;
                }

                // CREAR CUENTA
                if (opcion == 1) {
                    try {
                        System.out.println("\n   CREAR CUENTA   ");
                        System.out.print("Selecciona banco (1=Nequi 2=Bancolombia 3=Davivienda): ");
                        int bancoSel = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Nombre de usuario: ");
                        String usuario = sc.nextLine().trim();

                        System.out.print("Password: ");
                        String password = sc.nextLine().trim();

                        System.out.print("Número de teléfono: ");
                        String telefono = sc.nextLine().trim();

                        System.out.print("Saldo inicial: ");
                        double saldoInicial = Double.parseDouble(sc.nextLine().trim());

                        Cuenta nueva = new Cuenta(usuario, password, telefono, saldoInicial);

                        switch (bancoSel) {
                            case 1 -> nequi.agregarCuenta(nueva);
                            case 2 -> bancolombia.agregarCuenta(nueva);
                            case 3 -> davivienda.agregarCuenta(nueva);
                            default -> {
                                System.out.println("Banco invalido. Cuenta no creada.");
                                continue;
                            }
                        }
                        System.out.println("Cuenta creada con exito para " + usuario);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrada numerica invalida. Intenta de nuevo.");
                    }
                    continue;
                }

                // OPCIONES 2..7 REQUIEREN LOGIN
                if (opcion >= 2 && opcion <= 7) {
                    System.out.print("Usuario: ");
                    String usuario = sc.nextLine().trim();
                    System.out.print("Password: ");
                    String password = sc.nextLine().trim();

                    Cuenta cuenta = buscarCuentaEnBancos(usuario, bancos);
                    Banco banco = encontrarBancoDeCuenta(usuario, bancos);

                    if (cuenta == null) {
                        System.out.println("Usuario no encontrado.");
                        continue;
                    }
                    if (!cuenta.validarPassword(password)) {
                        System.out.println("Password incorrecta.");
                        continue;
                    }

                    switch (opcion) {
                        case 2 -> { // RETIRO
                            try {
                                System.out.print("Monto a retirar: ");
                                double monto = Double.parseDouble(sc.nextLine().trim());
                                if (monto <= 0) {
                                    System.out.println("Monto invalido.");
                                    break;
                                }
                                if (cuenta.retirar(monto)) {
                                    System.out.println("Retiro exitoso en " + banco.getNombre());
                                    // Generar recibo
                                    generarRecibo(usuario, banco.getNombre(), "Retiro", monto, cuenta.getSaldo());
                                } else {
                                    System.out.println("Fondos insuficientes.");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Monto invalido.");
                            }
                        }
                        case 3 -> { // CONSIGNACIÓN
                            try {
                                System.out.print("Monto a consignar: ");
                                double monto = Double.parseDouble(sc.nextLine().trim());
                                if (monto <= 0) {
                                    System.out.println("Monto invalido.");
                                    break;
                                }
                                cuenta.consignar(monto);
                                System.out.println("Consignacion exitosa en " + banco.getNombre());
                                // Generar recibo
                                generarRecibo(usuario, banco.getNombre(), "Consignacion", monto, cuenta.getSaldo());
                            } catch (NumberFormatException ex) {
                                System.out.println("Monto invalido.");
                            }
                        }
                        case 4 -> { // CONSULTAR SALDO
                            System.out.println("Saldo actual en " + banco.getNombre() + ": $" + String.format("%.2f", cuenta.getSaldo()));
                        }
                        case 5 -> { // VER MOVIMIENTOS
                            cuenta.mostrarMovimientos();
                        }
                        case 6 -> { // VER PUNTOS
                            cuenta.getPuntos().mostrarPuntos();
                        }
                        case 7 -> { // PAGAR SERVICIOS
                            try {
                                System.out.println("Elige servicio: 1=Agua  2=Luz  3=Internet  4=Otro");
                                int serv = Integer.parseInt(sc.nextLine().trim());
                                String nombreServicio = switch (serv) {
                                    case 1 -> "Agua";
                                    case 2 -> "Luz";
                                    case 3 -> "Internet";
                                    default -> "Otro";
                                };
                                System.out.print("Ingresa el valor del servicio: ");
                                double valor = Double.parseDouble(sc.nextLine().trim());
                                if (valor <= 0) {
                                    System.out.println("Valor inválido.");
                                    break;
                                }
                                Pago_servicios ps = new Pago_servicios(nombreServicio, valor);
                                boolean ok = cuenta.pagarServicio(ps);
                                if (ok) {
                                    System.out.println("Pago de " + nombreServicio + " realizado. Se descontó $" + valor);
                                    // Generar recibo
                                    generarRecibo(usuario, banco.getNombre(), "Pago_" + nombreServicio, valor, cuenta.getSaldo());
                                } else {
                                    System.out.println("No hay saldo suficiente para pagar " + nombreServicio);
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Entrada numerica inválida para servicio/valor.");
                            }
                        }
                        default -> System.out.println("Opcion no valida.");
                    }
                } else {
                    System.out.println("Opcion no válida. Intenta con un número entre 1 y 8.");
                }
            }
        }
    }

    // Buscar cuenta en los bancos
    private static Cuenta buscarCuentaEnBancos(String usuario, Banco[] bancos) {
        for (Banco b : bancos) {
            Cuenta c = b.buscarCuenta(usuario);
            if (c != null) return c;
        }
        return null;
    }

    // Encontrar banco de la cuenta
    private static Banco encontrarBancoDeCuenta(String usuario, Banco[] bancos) {
        for (Banco b : bancos) {
            if (b.buscarCuenta(usuario) != null) return b;
        }
        return null;
    }
}
 */
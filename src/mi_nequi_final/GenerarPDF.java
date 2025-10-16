/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mi_nequi_final;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

/**
 * Clase para generar recibos PDF de las operaciones
 */
public class GenerarPDF {

    public static void generarRecibo(String usuario, String operacion, double monto, double saldoFinal) {
        try {
            // Nombre del archivo PDF, cada usuario tiene su archivo separado
            String archivo = "recibo_" + usuario + ".pdf";

            // Crear documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(archivo));
            document.open();

            // Contenido del PDF
            document.add(new Paragraph("===== RECIBO DE TRANSACCION ====="));
            document.add(new Paragraph("Usuario: " + usuario));
            document.add(new Paragraph("Operacion: " + operacion));
            document.add(new Paragraph("Monto: $" + monto));
            document.add(new Paragraph("Saldo final: $" + saldoFinal));
            document.add(new Paragraph("Gracias por usar Cajero Springel :)"));

            // Cerrar documento
            document.close();

            System.out.println("Recibo generado en: " + archivo);

        } catch (Exception e) {
            System.out.println("Error al generar el PDF: " + e.getMessage());
        }
    }
}

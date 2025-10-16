Proyecto Nequi Final



# Proyecto Nequi Final

Este proyecto simula la aplicación **Nequi**, permitiendo al usuario realizar operaciones bancarias como **retiros, consignaciones, consultas de saldo, pago de servicios y acumulación de puntos**.  
Además, genera **recibos automáticos en PDF** de cada transacción usando la librería **iTextPDF**.

---

## Funcionalidades principales

- Crear y manejar cuentas (Nequi, Bancolombia, Davivienda, etc.)
- Consultar saldo.
- Realizar **retiros**, **consignaciones** y **pagos de servicios**.
- Sistema de **puntos acumulables**.
- Generación de **recibos PDF automáticos**.
- Uso de **agregación y composición** entre clases.

---

## Clases principales

- `Cuenta` → Clase base de las cuentas.
- `BancoNequi`, `BancoBancolombia`, `BancoDavivienda` → Heredan de `Banco`.
- `Puntos` → Administra el sistema de puntos.
- `Pago_servicios` → Representa los pagos de servicios.
- `Mi_Nequi_Final` → Clase principal con el menú e interacción del usuario.

---

## Relación entre clases

- **Composición:** `Cuenta` contiene un objeto de `Puntos` (si se elimina la cuenta, también se eliminan los puntos).
- **Agregación:** `Banco` tiene varias `Cuenta`, pero las cuentas pueden existir sin el banco.
- **Asociación:** `Pago_servicios` se usa cuando el usuario paga un servicio desde `Cuenta`.

---

##  Integrante

- **Jhonatan Duván Cachimbo Moreno**
- **Materia:**  POO
- **Institución:** Unipacifico
- **Año:** 2025

---



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen;

import javax.swing.JOptionPane;

/**
 *
 * @author juamp
 */
public class Principal {

    public static void main(String[] args) {

        int filas = 5;
        int columnas = 4;

        Asiento[][] avion = new Asiento[filas][columnas];

        precargarAvion(avion, filas, columnas);

        int opcion = 0;

        do {
            JOptionPane.showMessageDialog(null, "Bienvenido el sistema de Aerolinea Juanpa :D");

            String menu = "Menu de Reserva\n"
                    + "1. Ver mapa del avión\n"
                    + "2. Reservar asiento\n"
                    + "3. Reservar con descuento (cliente frecuente)\n"
                    + "4. Consultar pasajero por asiento\n"
                    + "5. Ver resumen del vuelo\n"
                    + "6. Salir\n";
            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:
                    verMapa(avion, filas, columnas);
                    break;
                case 2:
                    reservarAsiento(avion, filas, columnas, false);
                    break;
                case 3:
                    reservarAsiento(avion, filas, columnas, true);
                    break;
                case 4:
                    consultarPasajero(avion, filas, columnas);
                    break;
                case 5:
                    resumenVuelo(avion, filas, columnas);
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 6);

    }

    public static void precargarAvion(Asiento[][] avion, int filas, int columnas) {

        char[] letras = {'A', 'B', 'C', 'D'};

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                Asiento a = new Asiento();

                a.codigo = (i + 1) + "" + letras[j];

                if (i == 0) {
                    a.clase = "Primera";
                    a.precioBase = 500;
                } else if (i == 1) {
                    a.clase = "Business";
                    a.precioBase = 300;
                } else {
                    a.clase = "Economica";
                    a.precioBase = 100;
                }

                a.estado = "Libre";
                a.pasajero = null;

                avion[i][j] = a;
            }
        }
    }

    public static void verMapa(Asiento[][] avion, int filas, int columnas) {

        char[] letras = {'A', 'B', 'C', 'D'};
        String resultado = "MAPA DEL AVIÓN\n\n";

        resultado += "Fila ";
        for (int j = 0; j < columnas; j++) {
            resultado += " " + letras[j] + " ";
        }
        resultado += "\n";

        for (int i = 0; i < filas; i++) {
            resultado += " " + (i + 1) + " ";
            for (int j = 0; j < columnas; j++) {

                Asiento a = avion[i][j];

                if (a.estado.equals("Libre")) {
                    resultado += "[L]";
                } else {
                    resultado += "[O]";
                }

                resultado += " ";
            }
            resultado += "\n";
        }

        resultado += "\n(L=Libre, 0=Ocupado)";

        JOptionPane.showMessageDialog(null, resultado);
    }

    public static void reservarAsiento(Asiento[][] avion, int filas, int columnas, boolean conDescuento) {

        int fila = Integer.parseInt(JOptionPane.showInputDialog("Digite número de fila (1-" + filas + "):"));
        String colTexto = JOptionPane.showInputDialog("Digite letra de asiento (A-D):");

        fila = fila - 1;
        char colChar = colTexto.toUpperCase().charAt(0);

        int col = -1;
        if (colChar == 'A') {
            col = 0;
        } else if (colChar == 'B') {
            col = 1;
        } else if (colChar == 'C') {
            col = 2;
        } else if (colChar == 'D') {
            col = 3;
        }

        if (fila < 0 || fila >= filas || col < 0 || col >= columnas) {
            JOptionPane.showMessageDialog(null, "Asiento inexistente");
            return;
        }

        Asiento a = avion[fila][col];

        if (a.estado.equals("Ocupado")) {
            JOptionPane.showMessageDialog(null, "El asiento ya está ocupado");
            return;
        }

        Pasajero p = new Pasajero();
        p.nombre = JOptionPane.showInputDialog("Nombre completo del pasajero:");
        p.pasaporte = JOptionPane.showInputDialog("Número de pasaporte:");
        p.nacionalidad = JOptionPane.showInputDialog("Nacionalidad:");

        double precioFinal = a.precioBase;

        if (conDescuento) {
            String esCliente = JOptionPane.showInputDialog("¿Es cliente frecuente? (S/N):");
            esCliente = esCliente.toUpperCase();

            if (esCliente.equals("S")) {
                double descuento = 0;

                if (a.clase.equals("Economica")) {
                    descuento = 0.10;
                } else if (a.clase.equals("Business")) {
                    descuento = 0.15;
                } else if (a.clase.equals("Primera")) {
                    descuento = 0.18;
                }

                precioFinal = a.precioBase - (a.precioBase * descuento);
            }
        }

        p.precioPagado = precioFinal;

        a.estado = "Ocupado";
        a.pasajero = p;

        JOptionPane.showMessageDialog(null,
                "Asiento " + a.codigo + " reservado.\n Clase: " + a.clase
                + "\n Precio final:" + precioFinal);
    }

    public static void consultarPasajero(Asiento[][] avion, int filas, int columnas) {

        int fila = Integer.parseInt(JOptionPane.showInputDialog("Fila (1-" + filas + "):")) - 1;
        String letra = JOptionPane.showInputDialog("Letra del asiento (A-D):").toUpperCase();

        int col = letra.charAt(0) - 'A';

        Asiento a = avion[fila][col];

        if (a.estado.equals("Ocupado") && a.pasajero != null) {
            String info = "PASAJERO ASIGNADO\n\n"
                    + "Nombre: " + a.pasajero.nombre + "\n"
                    + "Pasaporte: " + a.pasajero.pasaporte + "\n"
                    + "Nacionalidad: " + a.pasajero.nacionalidad + "\n"
                    + "Pagó: " + a.pasajero.precioPagado;

            JOptionPane.showMessageDialog(null, info);
        } else {
            JOptionPane.showMessageDialog(null, "No hay pasajero asignado.");
        }
    }

    public static void resumenVuelo(Asiento[][] avion, int filas, int columnas) {

        int ocupados = 0;
        int libres = 0;
        double total = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                Asiento a = avion[i][j];

                if (a.estado.equals("Ocupado")) {
                    ocupados++;
                    if (a.pasajero != null) {
                        total += a.pasajero.precioPagado;
                    }
                } else {
                    libres++;
                }
            }
        }
        JOptionPane.showMessageDialog(null,
                "Resumen de vuelo\n"
                + "Asientos ocupados: " + ocupados + "\n"
                + "Asientos libres: " + libres + "\n"
                + "Recaudación total: " + total);
    }
}

 
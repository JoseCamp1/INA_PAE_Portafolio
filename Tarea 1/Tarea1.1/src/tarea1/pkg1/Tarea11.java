/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package tarea1.pkg1;
import java.util.Scanner; // Importamos la clase Scanner para entrada de datos
import javax.swing.JOptionPane; // Importamos la clase JOptionPane para mensajes en Swing

/**
 * @author JoaCa
 * INA
 * Fecha 4 septiembre 2023
 * Alumno Jose Campos Chaves
 * Profesor Luis Alonso Bogantes Rodriguez
 */

public class Tarea11 {    
    
     // Declaración de una constante
    public static final double PI = 3.14159265359;

    // Declaración de un tipo enumerado
    enum DiaSemana {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }
    
    public static void main(String[] args) {
        // Declaración de variables
        boolean esVerdadero = true;
        boolean esFalso = false;

        // Operadores relacionales
        int numero1 = 5;
        int numero2 = 10;
        boolean resultadoRelacional = numero1 < numero2; // El resultado será true

        // Operadores lógicos
        boolean condicion1 = true;
        boolean condicion2 = false;
        boolean resultadoLogico = condicion1 && condicion2; // El resultado será false

        // Prueba de asignación de valores por defecto
        int entero; // El valor por defecto es 0
        double decimal; // El valor por defecto es 0.0
        char caracter; // El valor por defecto es '\u0000' (carácter nulo)
        boolean logico; // El valor por defecto es false

        // Caracteres de escape
        System.out.println("Mensaje con salto de línea\nOtro mensaje en la siguiente línea");
        System.out.println("Mensaje con tabulación\tTexto después de la tabulación");

        // Entrada de datos desde la consola
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Hola, " + nombre);

        // Envío de mensajes con Swing
        JOptionPane.showMessageDialog(null, "Este es un mensaje en Swing", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        // Uso del tipo enumerado
        DiaSemana dia = DiaSemana.MARTES;
        System.out.println("Hoy es " + dia);        
    }
    
}

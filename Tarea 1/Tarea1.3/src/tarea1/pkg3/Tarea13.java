/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea1.pkg3;

/**
 * @author JoaCa
 * INA
 * Fecha 4 septiembre 2023
 * Alumno Jose Campos Chaves
 * Profesor Luis Alonso Bogantes Rodriguez
 */

public class Tarea13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Utilizar métodos de la clase Math para cálculos
        double numeroAleatorio = Math.random(); // Genera un número aleatorio entre 0 (inclusive) y 1 (exclusivo)
        System.out.println("Número aleatorio entre 0 y 1: " + numeroAleatorio);

        double raizCuadrada = Math.sqrt(25); // Calcula la raíz cuadrada de un número
        System.out.println("Raíz cuadrada de 25: " + raizCuadrada);

        double potencia = Math.pow(2, 3); // Calcula 2 elevado a la potencia de 3
        System.out.println("2^3: " + potencia);

        double valorAbsoluto = Math.abs(-5.7); // Devuelve el valor absoluto de un número
        System.out.println("Valor absoluto de -5.7: " + valorAbsoluto);

        double redondeo = Math.round(3.75); // Redondea un número al entero más cercano
        System.out.println("Redondeo de 3.75: " + redondeo);

        // Tipos de datos int y double, conversión de int a double
        int entero = 10;
        double decimal = (double) entero; // Conversión de int a double
        System.out.println("Valor decimal después de conversión: " + decimal);

        // Operadores aritméticos
        int a = 5;
        int b = 3;
        int suma = a + b;
        int resta = a - b;
        int multiplicacion = a * b;
        double division = (double) a / b; // Realizamos una conversión para obtener un resultado decimal
        int modulo = a % b;

        System.out.println("Suma: " + suma);
        System.out.println("Resta: " + resta);
        System.out.println("Multiplicación: " + multiplicacion);
        System.out.println("División: " + division);
        System.out.println("Módulo: " + modulo);

        // Operadores de asignación
        int x = 7;
        int y = 3;

        x += y; // Equivalente a x = x + y
        System.out.println("x += y: " + x);

        x -= y; // Equivalente a x = x - y
        System.out.println("x -= y: " + x);

        x *= y; // Equivalente a x = x * y
        System.out.println("x *= y: " + x);

        x /= y; // Equivalente a x = x / y
        System.out.println("x /= y: " + x);

        x %= y; // Equivalente a x = x % y
        System.out.println("x %= y: " + x);
    }    
}

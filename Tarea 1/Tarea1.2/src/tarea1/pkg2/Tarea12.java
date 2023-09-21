/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea1.pkg2;
import java.util.Scanner;

/**
 * @author JoaCa
 * INA
 * Fecha 4 septiembre 2023
 * Alumno Jose Campos Chaves
 * Profesor Luis Alonso Bogantes Rodriguez
 */
public class Tarea12 {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Tomar un texto
        System.out.print("Ingrese un texto: ");
        String texto = scanner.nextLine();

        // Utilizar métodos de la clase String
        System.out.println("Longitud del texto: " + texto.length());
        System.out.println("Texto en mayúsculas: " + texto.toUpperCase());
        System.out.println("Texto en minúsculas: " + texto.toLowerCase());
        System.out.println("Caracter en la posición 3: " + texto.charAt(2));
        System.out.println("Índice de 'o': " + texto.indexOf('o'));

        // Realizar conversiones de tipo String a int y double
        System.out.print("Ingrese un número entero: ");
        String numeroEnteroTexto = scanner.nextLine();
        int numeroEntero = Integer.parseInt(numeroEnteroTexto);
        System.out.println("Número entero + 10 = " + (numeroEntero + 10));

        System.out.print("Ingrese un número decimal: ");
        String numeroDecimalTexto = scanner.nextLine();
        double numeroDecimal = Double.parseDouble(numeroDecimalTexto);
        System.out.println("Número decimal * 2 = " + (numeroDecimal * 2));

        // Realizar conversiones de tipos numéricos a string
        int numero = 42;
        String numeroTexto = String.valueOf(numero);
        double decimal = 3.14;
        String decimalTexto = String.valueOf(decimal);

        // Comparaciones y concatenaciones entre strings
        String palabra1 = "Hola";
        String palabra2 = "Mundo";

        if (palabra1.equals(palabra2)) {
            System.out.println("Las palabras son iguales.");
        } else {
            System.out.println("Las palabras son diferentes.");
        }

        String concatenacion = palabra1 + ", " + palabra2;
        System.out.println("Concatenación: " + concatenacion);
    }
    
}

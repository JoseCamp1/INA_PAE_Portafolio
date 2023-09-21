/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea2;

/**
 *
 * @author JoaCa
 * INA
 * Fecha 4 septiembre 2023
 * Alumno Jose Campos Chaves
 * Profesor Luis Alonso Bogantes Rodriguez
 */
import java.util.Scanner;
public class Tarea2 {

    /**
     * @param args the command line arguments
     */
    
    // Variable global para almacenar la matriz
    private static int[][] matriz = new int[6][5];
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            opcion = mostrarMenu(scanner);

            switch (opcion) {
                case 1:
                    calcularFactorial(scanner);
                    break;
                case 2:
                    mostrarPrimerosPrimos();
                    break;
                case 3:
                    crearMatriz();
                    break;
                case 4:
                    obtenerMayorMatriz();
                    break;
                case 5:
                    System.out.println("¡Tenga un Buen Dia!");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (opcion != 5);
        
    }
    // Método para mostrar el menú y obtener la opción del usuario
    public static int mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("Menú:");
            System.out.println("1 - Número Factorial");
            System.out.println("2 - Los 10 primeros números Primos");
            System.out.println("3 - Crear una matriz de números al azar");
            System.out.println("4 - Obtener el número mayor de la matriz");
            System.out.println("5 - Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
        } while (opcion < 1 || opcion > 5);

        return opcion;
    }

    // Método para calcular el factorial de un número
    public static void calcularFactorial(Scanner scanner) {
        System.out.print("Ingrese un número entero: ");
        int numero = scanner.nextInt();
        int factorial = 1;

        for (int i = 1; i <= numero; i++) {
            factorial *= i;
        }

        System.out.println("El factorial de " + numero + " es: " + factorial);        
        
    }

    // Método para verificar si un número es primo
    public static boolean esPrimo(int numero) {
    if (numero <= 1) {
        return false;
    }
    if (numero <= 3) {
        return true;
    }
    if (numero % 2 == 0 || numero % 3 == 0) {
        return false;
    }
    
    int i = 5;
    while (i * i <= numero) {
        if (numero % i == 0 || numero % (i + 2) == 0) {
            return false;
        }
        i += 6;
    }
    return true;
}

    // Método para mostrar los primeros 10 números primos
    public static void mostrarPrimerosPrimos() {
        int[] numerosPrimos = new int[10];
        int contador = 0;
        int numeroActual = 2;

        while (contador < 10) {
            if (esPrimo(numeroActual)) {
                numerosPrimos[contador] = numeroActual;
                contador++;
            }
            numeroActual++;
        }

        System.out.println("Los 10 primeros números primos son:");
        for (int numero : numerosPrimos) {
            System.out.print(numero + " ");
        }
        System.out.println();
    }
    
    public static void crearMatriz() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                matriz[i][j] = (int) (Math.random() * 200) + 1;
            }
        }

        System.out.println("Matriz creada.");
        
        System.out.println("Matriz :");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                // alinear cada columna a la derecha usando printf
                System.out.printf("%4d\t", matriz[i][j]);
            }
            System.out.println();
        }
    }

    

    // Método para obtener el número mayor de la matriz
    public static void obtenerMayorMatriz() {
        // Verificar si la matriz ya ha sido llenada
        boolean matrizLlenada = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] != 0) {
                    matrizLlenada = true;
                    break;
                }
            }
            if (matrizLlenada) {
                break;
            }
        }

        if (!matrizLlenada) {
            System.out.println("Primero debe llenar la matriz.");
            return;
        }

        int mayor = matriz[0][0];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] > mayor) {
                    mayor = matriz[i][j];
                }
            }
        }

        System.out.println("El número mayor de la matriz es: " + mayor);
    }    
}

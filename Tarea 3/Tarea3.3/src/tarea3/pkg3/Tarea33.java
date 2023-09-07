/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea3.pkg3;
//Nombre:Jose Campos Chaves
 //Fecha:6/9/2023

import java.util.Random;
import java.util.Scanner;

public class Tarea33 {

    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int n = 0;
        int[][] matriz = null;
        boolean matrizLlena = false;

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la dimensión de la matriz (n x n): ");
                    n = scanner.nextInt();
                    matriz = new int[n][n];
                    llenarMatrizAleatoriamente(matriz, random);
                    matrizLlena = true;
                    System.out.println("Matriz llenada con números aleatorios entre 1 y 200.");
                    break;
                case 2:
                    if (matrizLlena) {
                        mostrarMatriz(matriz);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 3:
                    if (matrizLlena) {
                        System.out.print("Ingrese el número de fila a sumar (0-" + (n - 1) + "): ");
                        int fila = scanner.nextInt();
                        int sumaFila = sumarFila(matriz, fila);
                        System.out.println("La suma de la fila " + fila + " es: " + sumaFila);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 4:
                    if (matrizLlena) {
                        System.out.print("Ingrese el número de columna a sumar (0-" + (n - 1) + "): ");
                        int columna = scanner.nextInt();
                        int sumaColumna = sumarColumna(matriz, columna);
                        System.out.println("La suma de la columna " + columna + " es: " + sumaColumna);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 5:
                    if (matrizLlena) {
                        int sumaDiagonalPrincipal = sumarDiagonalPrincipal(matriz);
                        System.out.println("La suma de la diagonal principal es: " + sumaDiagonalPrincipal);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 6:
                    if (matrizLlena) {
                        int sumaDiagonalInversa = sumarDiagonalInversa(matriz);
                        System.out.println("La suma de la diagonal inversa es: " + sumaDiagonalInversa);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 7:
                    if (matrizLlena) {
                        double promedio = calcularPromedio(matriz);
                        System.out.println("El promedio de los valores de la matriz es: " + promedio);
                    } else {
                        System.out.println("Primero debe llenar la matriz.");
                    }
                    break;
                case 8:
                    System.out.println("¡Adiós!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Introduce un número del 1 al 7.");
                    break;
            }
        }  
       
    }
    
    public static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1) Llenar la matriz con números aleatorios entre 1 y 200.");
        System.out.println("2) Mostrar la matriz.");
        System.out.println("3) Sumar una fila.");
        System.out.println("4) Sumar una columna.");
        System.out.println("5) Sumar la diagonal principal.");
        System.out.println("6) Sumar la diagonal inversa.");
        System.out.println("7) El promedio de los valores de la matriz.");
        System.out.println("8) Salir.");
        System.out.print("Selecciona una opción: ");
    }

    public static void llenarMatrizAleatoriamente(int[][] matriz, Random random) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = random.nextInt(200) + 1;
            }
        }
    }

    public static void mostrarMatriz(int[][] matriz) {
        System.out.println("Matriz:");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static int sumarFila(int[][] matriz, int fila) {
        int suma = 0;
        for (int i = 0; i < matriz[fila].length; i++) {
            suma += matriz[fila][i];
        }
        return suma;
    }

    public static int sumarColumna(int[][] matriz, int columna) {
        int suma = 0;
        for (int i = 0; i < matriz.length; i++) {
            suma += matriz[i][columna];
        }
        return suma;
    }

    public static int sumarDiagonalPrincipal(int[][] matriz) {
        int suma = 0;
        for (int i = 0; i < matriz.length; i++) {
            suma += matriz[i][i];
        }
        return suma;
    }

    public static int sumarDiagonalInversa(int[][] matriz) {
        int suma = 0;
        for (int i = 0; i < matriz.length; i++) {
            suma += matriz[i][matriz.length - 1 - i];
        }
        return suma;
    }

    public static double calcularPromedio(int[][] matriz) {
        int suma = 0;
        int cantidadElementos = matriz.length * matriz[0].length;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                suma += matriz[i][j];
            }
        }
        return (double) suma / cantidadElementos;
    }
    
}

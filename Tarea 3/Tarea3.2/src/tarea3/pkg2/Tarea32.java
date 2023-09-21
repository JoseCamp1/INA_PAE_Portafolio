/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea3.pkg2;

//Nombre:Jose Campos Chaves
//Fecha:6/9/2023
import java.util.Random;

public class Tarea32 {

    
    public static void main(String[] args) {
        // Generar un vector de 15 números enteros aleatorios en el rango del 1 al 20
        int[] vector = generarVectorAleatorio(15, 1, 20);

        // Mostrar el vector 
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
    }
    
    public static int[] generarVectorAleatorio(int cantidad, int minimo, int maximo) {
        // Verificar si la cantidad está dentro del rango válido
        if (cantidad > (maximo - minimo + 1) || cantidad < 1) {
            throw new IllegalArgumentException("Cantidad fuera de rango");
        }

        // Crear un vector para almacenar los números aleatorios
        int[] vector = new int[cantidad];
        Random random = new Random();

<<<<<<< HEAD
        // lleno el vector con numeros aleatorios
=======
        // Iterar para llenar el vector con números aleatorios no repetidos
>>>>>>> d47bd48399ffb3a76754a35514dcaf3687ad3d68
        for (int i = 0; i < cantidad; i++) {
            int numeroAleatorio;
            boolean repetido;

            do {
<<<<<<< HEAD
                // Genero un número aleatorio en el rango
=======
                // Generar un número aleatorio en el rango especificado
>>>>>>> d47bd48399ffb3a76754a35514dcaf3687ad3d68
                numeroAleatorio = random.nextInt(maximo - minimo + 1) + minimo;
                repetido = false;

                // Verificar si el número ya está en el vector
                for (int j = 0; j < i; j++) {
                    if (vector[j] == numeroAleatorio) {
                        repetido = true;
                        break;
                    }
                }
            } while (repetido);

            // Almacenar el número aleatorio en el vector
            vector[i] = numeroAleatorio;
        }

        return vector;
    }
    
}

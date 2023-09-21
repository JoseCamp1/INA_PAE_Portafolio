/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea3;
 //Nombre:Jose Campos Chaves
 //Fecha:6/9/2023

//para recibir entradas del usuario
import java.util.Scanner;

public class Tarea3 {  
   
    
    //creacion de variables estaticas
    private static int [] edades;
    private static  String[] nombres;
    private static double[] salarios;
    private static int cantTrabajadores;
    private static boolean datosIngresados=false;
    private static Scanner scanner=new Scanner(System.in);
    
    public static void main(String[] args) {
        //menu
        while (true) {
            System.out.println("_____________________ Menu _____________________\n"
                    + "1) Indicar la cantidad de trabajadores.\n"
                    + "2) Introducir los datos de los trabajadores.\n"
                    + "3) Ordenar por nombre.\n"
                    + "4) Ordenar por edad.\n"
                    + "5) Ordenar por salario.\n"
                    + "6) Mostrar información de los empleados.\n"
                    + "7) Salir.\n"
                    +"________________________________________________\n" 
                    +"Seleccione alguna opcion: ");            
            

            int opcion = scanner.nextInt();
            scanner.nextLine(); //salto de línea

            switch (opcion) {
                case 1:
                    ingresarCantidadTrabajadores();
                    break;
                case 2:
                    ingresarDatosTrabajadores();
                    break;
                case 3:
                    ordenarPorNombre();
                    break;
                case 4:
                    ordenarPorEdad();
                    break;
                case 5:
                    ordenarPorSalario();
                    break;
                case 6:
                    mostrarInformacionTrabajadores();
                    break;
                case 7:
                    System.out.println("Adios, tenga buen dia!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Se equivoco de opcion!");
                    break;
            }
        }

    }
    
    
    //metodo para ingresar catidad de trabajadores y llenar arreglos de datos
     private static void ingresarCantidadTrabajadores() {
        System.out.print("Ingrese la cantidad de trabajadores: ");
        cantTrabajadores = scanner.nextInt();
        scanner.nextLine(); // salto de línea
        nombres = new String[cantTrabajadores];
        edades = new int[cantTrabajadores];
        salarios = new double[cantTrabajadores];
        datosIngresados = false;
        System.out.println("Se ingresaron los trabajadores a " + cantTrabajadores + ".");
    }
     
     
     //metodo para ingresar nombres edades salarios
      private static void ingresarDatosTrabajadores() {
          //sino se ingresaron datos
        if (cantTrabajadores == 0) {
            System.out.println("Se debe indicar la cantidad de trabajadores.");
            return;
        }

        for (int i = 0; i < cantTrabajadores; i++) {
            System.out.println("Ingrese los datos del trabajador" + (i + 1) + ":");
            System.out.print("Nombre: ");
            nombres[i] = scanner.nextLine();
            System.out.print("Edad: ");
            edades[i] = scanner.nextInt();
            System.out.print("Salario: ");
            salarios[i] = scanner.nextDouble();
            scanner.nextLine(); // salto de línea
        }

        datosIngresados = true;
        System.out.println("Se ingresaron los datos de los trabajadores.");
    }
      
    //metodo para ordenar burbuja  
    private static void ordenarPorNombre() {
         //sino se ingresaron datos
        if (!datosIngresados) {
            System.out.println("Se debe ingresar los datos de los trabajadores.");
            return;
        }
        // burbuja
        boolean intercambiado;
        do {
            intercambiado = false;
            for (int i = 0; i < cantTrabajadores - 1; i++) {
                if (nombres[i].compareTo(nombres[i + 1]) > 0) {
                    // Intercambia los nombres
                    String tempNombre = nombres[i];
                    nombres[i] = nombres[i + 1];
                    nombres[i + 1] = tempNombre;

                    //intercambiar las edades y los salarios
                    int tempEdad = edades[i];
                    edades[i] = edades[i + 1];
                    edades[i + 1] = tempEdad;

                    double tempSalario = salarios[i];
                    salarios[i] = salarios[i + 1];
                    salarios[i + 1] = tempSalario;

                    intercambiado = true;
                }
            }
        } while (intercambiado);

        System.out.println("Trabajadores ordenados por nombre.");
    }
    
    //metodo para ordenar seleccion
    private static void ordenarPorEdad() {
        if (!datosIngresados) {
            System.out.println("Se debe ingresar los datos de los trabajadores.");
            return;
        }

        // seleccion
        for (int i = 0; i < cantTrabajadores - 1; i++) {
            int indiceMenorEdad = i;
            for (int j = i + 1; j < cantTrabajadores; j++) {
                if (edades[j] < edades[indiceMenorEdad]) {
                    indiceMenorEdad = j;
                }
            }

            // Intercambia las edades y los nombres 
            int tempEdad = edades[i];
            edades[i] = edades[indiceMenorEdad];
            edades[indiceMenorEdad] = tempEdad;

            String tempNombre = nombres[i];
            nombres[i] = nombres[indiceMenorEdad];
            nombres[indiceMenorEdad] = tempNombre;

            double tempSalario = salarios[i];
            salarios[i] = salarios[indiceMenorEdad];
            salarios[indiceMenorEdad] = tempSalario;
        }

        System.out.println("Trabajadores ordenados por edad.");
    }
    
    //metodo insercion
    private static void ordenarPorSalario() {
        if (!datosIngresados) {
            System.out.println("Se debe ingresar los datos de los trabajadores.");
            return;
        }

        // insercion
        for (int i = 1; i < cantTrabajadores; i++) {
            double salarioActual = salarios[i];
            String nombreActual = nombres[i];
            int edadActual = edades[i];
            int j = i - 1;

            while (j >= 0 && salarios[j] > salarioActual) {
                salarios[j + 1] = salarios[j];
                nombres[j + 1] = nombres[j];
                edades[j + 1] = edades[j];
                j--;
            }

            salarios[j + 1] = salarioActual;
            nombres[j + 1] = nombreActual;
            edades[j + 1] = edadActual;
        }

        System.out.println("Trabajadores ordenados por salario menor a mayor.");
    }
      
      
      
    //metodo para printear info   
    private static void mostrarInformacionTrabajadores() {
        //sino se ingresaron datos
        if (!datosIngresados) {
            System.out.println("Ingresa los datos de los trabajadores.");
            return;
        }

        System.out.println("___Informacion de los trabajadores___");
        for (int i = 0; i < cantTrabajadores; i++) {
            System.out.println("Trabajador #" + (i + 1) + ":");
            System.out.println("Nombre: " + nombres[i]);
            System.out.println("Edad: " + edades[i]);
            System.out.println("Salario: " + salarios[i]);
        }
    }    
}

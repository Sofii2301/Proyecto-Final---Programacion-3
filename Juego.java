import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Juego {

    private List<Personaje> Jugador1;
    private List<Personaje> Jugador2;
    private List<String> log;

    public Juego() {
        Jugador1 = new ArrayList<>();
        Jugador2 = new ArrayList<>();
        log = new ArrayList<>();
    }

    public void generarPersonajesAleatorios() {
        // Generar los 6 personajes aleatorios
        // Agregarlos a las listas de jugadores
    }

    public void iniciarPartida() {
        // Lógica para iniciar la partida
        // Seleccionar los personajes para cada jugador
        // Realizar las rondas de combate
        // Actualizar los personajes y el log después de cada ronda
    }

    public void ingresarPersonajesAMano() {
        // Lógica para permitir al usuario ingresar los personajes manualmente
    }

    public void leerLogs() {
        // Lógica para leer los logs de todas las partidas jugadas desde un archivo
    }

    public void borrarArchivoLogs() {
        // Lógica para borrar el archivo de logs
    }

    public void guardarLogsEnArchivo() {
        try (FileWriter writer = new FileWriter("logs.txt")) {
            for (String logEntry : log) {
                writer.write(logEntry);
                writer.write(System.lineSeparator());
            }
            System.out.println("Los logs se han guardado en el archivo.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar los logs en el archivo.");
        }
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            System.out.println("----- Menú del Juego de Cartas -----");
            System.out.println("1. Iniciar partida (generar personajes aleatoriamente)");
            System.out.println("2. Iniciar partida (ingresar personajes a mano)");
            System.out.println("3. Leer logs de partidas jugadas");
            System.out.println("4. Borrar archivo de logs");
            System.out.println("5. Salir");
            System.out.print("Ingrese la opción deseada: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después de ingresar la opción

            switch (opcion) {
                case 1:
                    generarPersonajesAleatorios();
                    iniciarPartida();
                    break;
                case 2:
                    ingresarPersonajesAMano();
                    iniciarPartida();
                    break;
                case 3:
                    leerLogs();
                    break;
                case 4:
                    borrarArchivoLogs();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        }

        guardarLogsEnArchivo();
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.mostrarMenu();
    }
}


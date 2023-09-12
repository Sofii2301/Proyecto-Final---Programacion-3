import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        for (int i = 0; i < 6; i++){
            RandomEnumGenerator n = new RandomEnumGenerator(Nombre.class);
            Nombre nombreR = (Nombre) n.randomEnum();
            String nombre = nombreR.name();
            RandomEnumGenerator r = new RandomEnumGenerator(Raza.class);
            Raza raza = (Raza) r.randomEnum();
            RandomEnumGenerator a = new RandomEnumGenerator(Apodo.class);
            Apodo apodoR = (Apodo) a.randomEnum();
            String apodo = apodoR.name();
            LocalDate dt = LocalDate.now();
            int yn = dt.getYear();
            int mes = numAleatorio(1,12);
            int anio = numAleatorio(yn - 300, yn);
            int dia = numAleatorio(1,cantDiasMes(mes,anio));
            LocalDate nacimiento = LocalDate.of(dia, mes, anio);
            int velocidad = numAleatorio(1, 10);
            int destreza = numAleatorio(1, 5);
            int fuerza = numAleatorio(1, 10);
            int nivel = numAleatorio(1, 10);
            int armadura = numAleatorio(1, 10);

            Personaje p = new Personaje(nombre, raza, apodo, nacimiento, velocidad, destreza, fuerza, nivel, armadura);
            if(i % 2 == 0){
                Jugador1.add(p);
            }
            else {
                Jugador2.add(p);
            }
        }
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

    public static int numAleatorio(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int cantDiasMes(int mes, int anio){
        int cant = 0;
        switch (mes){
            case 1,3,5,7,8,10,12:
                cant = 31;
                break;
            case 4,6,9,11:
                cant = 30;
                break;
            case 2:
                if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0))){
                    cant = 29;
                    break;
                }
                else {
                    cant = 28;
                    break;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + mes);
        }
        return cant;
    }

    public void mostrarMenu(){
        boolean salir = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------------------------------------------------");
        System.out.println("-                                                            -");
        System.out.println("-                       JUEGO DE CARTAS                      -");
        System.out.println("-                                                            -");
        System.out.println("--------------------------------------------------------------");
        System.out.println("------------------ Menú del Juego de Cartas ------------------");
        System.out.println("-  1. Iniciar partida (generar personajes aleatoriamente)    -");
        System.out.println("-  2. Iniciar partida (ingresar personajes a mano)           -");
        System.out.println("-  3. Leer logs de partidas jugadas                          -");
        System.out.println("-  4. Borrar archivo de logs                                 -");
        System.out.println("-  5. Salir                                                  -");
        System.out.println("--------------------------------------------------------------");
        System.out.print  ("Ingrese la opción deseada: ");
        while (!salir){
            int opcion = scanner.nextInt();

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
                    System.exit(0);
                default:
                    salir = true;
            }
        }

        guardarLogsEnArchivo();
    }
}


package src;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Juego {

    private List<Personaje> Jugador1;
    private List<Personaje> Jugador2;
    private List<String> log;

    public Juego() {
        Jugador1 = new ArrayList<>();
        Jugador2 = new ArrayList<>();
        log = new ArrayList<>();
    }

    // Define conjuntos para nombres y apodos
    Set<String> nombresUtilizados = new HashSet<>();
    Set<String> apodosUtilizados = new HashSet<>();
    Scanner s = new Scanner(System.in);
    Boolean continuarJugando = true;

    public void generarPersonajesAleatorios() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        for (int i = 0; i < 6; i++){
            //Para que nombres y apodos no se repitan
            String nombre;
            do {
                nombre = generarNombreAleatorio();
            } while (nombresUtilizados.contains(nombre));
            nombresUtilizados.add(nombre);
            String apodo;
            do {
                apodo = generarApodoAleatorio();
            } while (apodosUtilizados.contains(apodo));
            apodosUtilizados.add(apodo);
            RandomEnumGenerator r = new RandomEnumGenerator(Raza.class);
            Raza raza = (Raza) r.randomEnum();
            LocalDate dt = LocalDate.now();
            int yn = dt.getYear();
            int mes = numAleatorio(1,12);
            int anio = numAleatorio(yn - 300, yn);
            int dia = numAleatorio(1,cantDiasMes(mes,anio));
            LocalDate nacimiento = LocalDate.of(anio, mes, dia);
            int velocidad = numAleatorio(1, 10);
            int destreza = numAleatorio(1, 5);
            int fuerza = numAleatorio(1, 10);
            int nivel = numAleatorio(1, 10);
            int armadura = numAleatorio(1, 10);

            Personaje p = new Personaje(nombre, raza, apodo, nacimiento, velocidad, destreza, fuerza, nivel, armadura);
            
            personajes.add(p);
        }
        //System.out.println(personajes);
        
        asignarPersonajes(personajes);
    }

    private String generarNombreAleatorio() {
        RandomEnumGenerator n = new RandomEnumGenerator(Nombre.class);
        Nombre nombreR = (Nombre) n.randomEnum();
        String nombre = nombreR.name();
        return nombre;
    }

    private String generarApodoAleatorio() {
        RandomEnumGenerator a = new RandomEnumGenerator(Apodo.class);
        Apodo apodoR = (Apodo) a.randomEnum();
        String apodo = apodoR.name();
        return apodo;
    }

    public void ingresarPersonajesAMano() {
        //El usuario tiene la opcion de volver al menu principal
        System.out.println("Debe ingresar los datos de 3 personajes para cada jugador, ingrese 's' (salir) para volver al menu o presione enter para ingresar los datos: ");
        String salir = s.nextLine();
        List<Personaje> personajes = new ArrayList<Personaje>();
        if (salir.equalsIgnoreCase("s")){
            mostrarMenu();
        } else {
            for (int i = 0; i < 6; i++){
                int nro = i+1;
                System.out.println(nro+"° personaje");
                Personaje p = leerDatosPersonaje(); 
                personajes.add(p);
            }
        }

        asignarPersonajes(personajes);
    }

    public void asignarPersonajes(List<Personaje> p){
        int indice1 = 0;
        int indice2 = 0;

        for (int i = 0; i < p.size(); i++){
            if (i % 2 == 0){
                Jugador1.add(indice1,p.get(i));
                indice1++;
            }  
            else{
                Jugador2.add(indice2,p.get(i));
                indice2++;
            }    
        }   
    }

    public Personaje leerDatosPersonaje(){
        //Para que no se repitan nombres ni apodos
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = s.nextLine();
            if (nombresUtilizados.contains(nombre)) {
                System.out.println("¡Este nombre ya ha sido utilizado! Por favor, ingrese otro.");
            }
        } while (nombresUtilizados.contains(nombre));
        nombresUtilizados.add(nombre);

        String apodo;
        do {
            System.out.print("Apodo: ");
            apodo = s.nextLine();
            if (apodosUtilizados.contains(apodo)) {
                System.out.println("¡Este apodo ya ha sido utilizado! Por favor, ingrese otro.");
            }
        } while (apodosUtilizados.contains(apodo));
        apodosUtilizados.add(apodo);

        boolean salir = false;
        Raza raza = Raza.HUMANO;
        while(!salir){
            try {
                System.out.print("Raza (Humano (H), Orco (O), Elfo (E)): ");
                String razaInput = s.nextLine();
                    
                switch(razaInput.toUpperCase()){
                    case "H":
                        raza = Raza.HUMANO;
                        salir = true;
                        break;
                    case "O":
                        raza = Raza.ORCO;
                        salir = true;
                        break;
                    case "E":
                        raza = Raza.ELFO;
                        salir = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Opción inválida. Por favor, ingrese una letra (Raza) válida ( H - O - E ).");
                };
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
            
        LocalDate nacimiento = leerFechaNacimiento();
            
        int velocidad = validarInt(1, 10,"Velocidad (1-10): ");
        int destreza = validarInt(1, 5,"Destreza (1-5): ");
        int fuerza = validarInt(1, 10,"Fuerza (1-10): ");
        int nivel = validarInt(1, 10,"Nivel (1-10): ");
        int armadura = validarInt(1, 10,"Armadura (1-10): ");

        Personaje p = new Personaje(nombre, raza, apodo, nacimiento, velocidad, destreza, fuerza, nivel, armadura);
        return p;
    }

    public void mostrarMenu(){
        while (continuarJugando) {
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
            int opcion;
            try {
                opcion = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

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
                    salir();
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese un número del 1 al 5.");
            }
        }
    }

    public void iniciarPartida() {
        if (Jugador1.isEmpty() || Jugador2.isEmpty()) {
            System.out.println("No hay suficientes personajes asignados para iniciar la partida.");
            return;
        }
        //Guarda los personajes en variables
        Personaje J1P1 = Jugador1.get(0);
        Personaje J1P2 = Jugador1.get(1);
        Personaje J1P3 = Jugador1.get(2);
        Personaje J2P1 = Jugador2.get(0);
        Personaje J2P2 = Jugador2.get(1);
        Personaje J2P3 = Jugador2.get(2);

        //Presenta las cartas de cada uno
        System.out.println("\nLos personajes asignados para cada jugador son:");
        System.out.println("\nJUGADOR 1");
        System.out.println(J1P1.toString());
        System.out.println(J1P2.toString());
        System.out.println(J1P3.toString());
        System.out.println("\nJUGADOR 2");
        System.out.println(J2P1.toString());
        System.out.println(J2P2.toString());
        System.out.println(J2P3.toString());

        System.out.println("");
        System.out.println("////////////////////////////////////////////////");
        System.out.println("");

        //Guarda el ganador para luego mostrarlo en el log
        String ganador = enfrentamiento();

        String resultadoPartida = "Partida jugada - " + LocalDate.now();
        resultadoPartida += "\nGanador: " + ganador;
        resultadoPartida += "\nCartas de Jugador 1:";
        resultadoPartida += "\n " + J1P1.toString();
        resultadoPartida += "\n " + J1P2.toString();
        resultadoPartida += "\n " + J1P3.toString();
        resultadoPartida += "\nCartas de Jugador 2:";
        resultadoPartida += "\n " + J2P1.toString();
        resultadoPartida += "\n " + J2P2.toString();
        resultadoPartida += "\n " + J2P3.toString();
        
        log.add(resultadoPartida);

        // Guardar los registros en el archivo de logs
        guardarLogsEnArchivo();

        // Preguntar al usuario si desea jugar otra vez
        System.out.print("¿Desea jugar otra partida? (s): ");
        String respuesta = s.nextLine();
        if (!respuesta.equalsIgnoreCase("s")) {
            salir();
        } else {
            Jugador1.clear();
            Jugador2.clear();
            nombresUtilizados.clear();
            apodosUtilizados.clear();
        }
    }

    public String enfrentamiento(){
        int ronda = 1;
        boolean seguirCombatiendo = true;
        String ganador = "";

        //Bucle dentro de otro para manejar enfrentamientos y rondas
        while (seguirCombatiendo) {
            int atacaPrimero = numAleatorio(1,2);
            int sorteo1, sorteo2;

            sorteo1 = numAleatorio(0,Jugador1.size()-1);
            sorteo2 = numAleatorio(0,Jugador2.size()-1);
            Personaje J1 = Jugador1.get(sorteo1);
            Personaje J2 = Jugador2.get(sorteo2);

            System.out.println("El sistema sorteó al Jugador "+ atacaPrimero +" para iniciar la ronda");
            System.out.println("El sistema eligió al personaje " + J1.getNombre() + " del jugador 1 y al personaje "+J2.getNombre()+" de jugador 2 para que se enfrenten en esta ronda.");
        
            Personaje atacante;
            Personaje defensor;

            if (atacaPrimero == 1){
                atacante = J1;
                defensor = J2;
            } else {
                atacante = J2;
                defensor = J1;
            }
            System.out.println("---------------------------------------------------");
            System.out.println("                  Ronda " + ronda);
            System.out.println("---------------------------------------------------");
            for(int i=0; i<14; i++){
                System.out.println("Atacante: " + atacante.getNombre());
                System.out.println("Defensor: " + defensor.getNombre());

                int danio = ataque(atacante, defensor);
                
                System.out.println(atacante.getNombre()+" ataca a "+defensor.getNombre()+" y le quita "+ danio+" de salud. "+
                                   defensor.getNombre()+" queda con "+defensor.getSalud()+" de salud.");

                if (defensor.getSalud() == 0){
                    System.out.println("");
                    System.out.println("Muere "+defensor.getNombre()+".");
                    int salud = atacante.getSalud() + 10;
                    atacante.setSalud(salud);
                    System.out.println(atacante.getNombre()+" gana 10 de salud como premio, quedando con "+salud+" de salud.\n");
                    if (atacante == J1){
                        Jugador2.remove(defensor);
                    } else {
                        Jugador1.remove(defensor);
                    }
                    break;
                }
                //Se intercambian el atacante y el defensor
                Personaje aux = atacante;
                atacante = defensor;
                defensor = aux;
            }
            ronda++;

            String perdedor;
            List<Personaje> ganadores;

            //Guardar cartas del ganador
            if (Jugador1.size()==0){
                ganador = "Jugador 2";
                perdedor = "Jugador 1";
                ganadores = Jugador2;
                seguirCombatiendo = false;
            }  else if (Jugador2.size()==0){
                ganador = "Jugador 1";
                perdedor = "Jugador 2";
                ganadores = Jugador1;
                seguirCombatiendo = false;
            } else continue;

            System.out.println("El "+perdedor+" ha perdido todas sus cartas.");
            System.out.println("Gana "+ganador+", le quedo/aron vivos los sgtes. personajes:");
            System.out.println(ganadores);
            System.out.println("Felicitaciones "+ganador+", las fuerzas mágicas del universo luz te abrazan!");
            System.out.println("Fin.");
        }
        return ganador;
    }

    public int ataque(Personaje atacante, Personaje defensor){
        int PD = atacante.getDestreza() * atacante.getFuerza() * atacante.getNivel();
        int ED = numAleatorio(1,100);
        //System.out.println(ED);
        DecimalFormat df = new DecimalFormat("#");
        int VA = Integer.parseInt(df.format((PD * ED)/100));
        int PDEF = defensor.getVelocidad() * defensor.getArmadura();

        //Logica calculo del ataque truncando decimales
        int danio = 0;
        switch(atacante.getRaza()){
            case HUMANO:
                danio = Integer.parseInt(df.format((((VA*ED)-PDEF)/500)*5));
                break;
            case ORCO:
                danio = Integer.parseInt(df.format(((((VA*ED)-PDEF)/500)*5 ) * 1.1));
                break;
            case ELFO:
                danio = Integer.parseInt(df.format(((((VA*ED)-PDEF)/500)*5 ) * 1.05));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + atacante.getRaza());
        }

        //Resta el danio en la salud
        int salud = defensor.getSalud();
        if (salud>danio){
            salud = salud-danio;
        } else {
            salud = 0;
        }
        defensor.setSalud(salud);

        return danio;
    }

    private void salir() {
        System.out.println("¡Gracias por jugar! ¡Hasta luego!");
        continuarJugando = false;
        System.exit(0);
    }

    public void leerLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.txt"))) {
            String line;
            System.out.println("Registros de partidas jugadas:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de logs.");
        }
    }

    public void borrarArchivoLogs() {
        File archivo = new File("logs.txt");
        if (archivo.exists()) {
            archivo.delete();
            System.out.println("El archivo de logs ha sido eliminado.");
        } else {
            System.out.println("El archivo de logs no existe.");
        }
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

    private LocalDate leerFechaNacimiento() {
        while (true) {
            try {
                System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
                String fechaStr = s.nextLine();
                String[] fechaParts = fechaStr.split("-");
                if (fechaParts.length != 3) {
                    throw new IllegalArgumentException("Formato de fecha incorrecto. Ingrese la fecha nuevamente.");
                }
                int anio = Integer.parseInt(fechaParts[0]);
                int mes = Integer.parseInt(fechaParts[1]);
                int dia = Integer.parseInt(fechaParts[2]);
                
                // Validar mes
                if (mes < 1 || mes > 12) {
                    throw new IllegalArgumentException("El mes debe estar entre 1 y 12. Ingrese nuevamente.");
                }
    
                // Validar día
                int maxDias = LocalDate.of(anio, mes, 1).lengthOfMonth();
                if (dia < 1 || dia > maxDias) {
                    throw new IllegalArgumentException("El día ingresado no es válido para el mes y año especificados. Ingrese nuevamente.");
                }
                
                //Validar año
                LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
                LocalDate fechaLimite = LocalDate.now().minusYears(300);
    
                if (fechaNacimiento.isBefore(fechaLimite)) {
                    throw new IllegalArgumentException("El personaje no puede tener más de 300 años. Ingrese una fecha de nacimiento más reciente.");
                }
    
                return fechaNacimiento;
            } catch (NumberFormatException e) {
                System.out.println("Formato de fecha incorrecto. Ingrese la fecha nuevamente.");
                // Consumir la entrada inválida del usuario
                s.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    

    public static int numAleatorio(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int cantDiasMes(int mes, int anio){
        //Devuelve la cantidad de dias que puede tener el mes considerando si el anio es bisciesto
        int cant = 0;
        switch (mes){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                cant = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
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

    public int validarInt(int min, int max, String mensaje) {
        //Comprueba que el int ingresado se encuentre entre los valores min y max
        int numero;
        while (true) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(s.nextLine());
                if (numero < min || numero > max) {
                    throw new IllegalArgumentException("Ingrese un número entero entre " + min + " y " + max + ".");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Ingrese un número entero válido. " + e.getMessage());
            }
        }
        return numero;
    }  
}

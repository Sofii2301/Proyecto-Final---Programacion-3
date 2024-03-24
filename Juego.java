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

    public void printPersonajesLista(){
        System.out.println(Jugador1);
        System.out.println(Jugador2);
    }

    Scanner s = new Scanner(System.in);

    public void generarPersonajesAleatorios() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        
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

    public void ingresarPersonajesAMano() {
        System.out.println("Debe ingresar los datos de 3 personajes para cada jugador, ingrese 's' (salir) para volver al menu: ");
        String salir = s.nextLine();
        List<Personaje> personajes = new ArrayList<Personaje>();
        if (salir == "s" || salir == "S"){
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
        System.out.print("Nombre: ");
            String nombre = s.nextLine();
            System.out.print("Apodo: ");
            String apodo = s.nextLine();

            boolean salir = false;
            Raza raza = Raza.HUMANO;
            while(!salir){
                System.out.print("Raza (Humano (H), Orco (O), Elfo (E)): ");
                String razaInput = s.nextLine();
                razaInput.toUpperCase();
                
                switch(razaInput){
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
                }
            }
            LocalDate fechaActual = LocalDate.now();
            System.out.print("Fecha de nacimiento");
            int mes, anio, dia;
            while (true){
                System.out.print("Anio: ");
                anio = s.nextInt();
                if (anio < fechaActual.getYear()-300){
                    System.out.println("El personaje no puede tener mas de 300 anios.");
                    System.out.println("Ingrese un anio mayor.");
                } else
                    break;
            }
            while (true){
                System.out.print("Mes (1-12): ");
                mes = s.nextInt();
                if (mes > 12){
                    System.out.println("Ingrese un mes valido (<12).");
                } else
                    break;
            }
            while (true){
                System.out.print("Dia: ");
                dia = s.nextInt();
                if (dia > cantDiasMes(mes, anio)){
                    System.out.println("Ingrese un dia valido para el mes y anio ingresados.");
                } else
                    break;
            }
            
            LocalDate nacimiento = LocalDate.of(anio, mes, dia);
            
            int velocidad = validarInt(1, 10,"Velocidad (1-10): ");
            int destreza = validarInt(1, 5,"Destreza (1-5): ");
            int fuerza = validarInt(1, 10,"Fuerza (1-10): ");
            int nivel = validarInt(1, 10,"Nivel (1-10): ");
            int armadura = validarInt(1, 10,"Armadura (1-10): ");

            Personaje p = new Personaje(nombre, raza, apodo, nacimiento, velocidad, destreza, fuerza, nivel, armadura);
            return p;
    }

    public void mostrarMenu(){
        boolean salir = false;

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
            int opcion = s.nextInt();

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

    public void iniciarPartida() {
        System.out.println("Los personajes asignados para cada jugador son:");
        System.out.println("JUGADOR 1");
        System.out.println(Jugador1.get(0).toString());
        System.out.println(Jugador1.get(1).toString());
        System.out.println(Jugador1.get(2).toString());
        System.out.println("JUGADOR 2");
        System.out.println(Jugador2.get(0).toString());
        System.out.println(Jugador2.get(1).toString());
        System.out.println(Jugador2.get(2).toString());

        System.out.println("");
        System.out.println("////////////////////////////////////////////////");
        System.out.println("");

        enfrentamiento();
    }

    public void enfrentamiento(){
        int atacaPrimero = numAleatorio(1,2);
        int sorteo1, sorteo2;

        sorteo1 = numAleatorio(0,2);
        sorteo2 = numAleatorio(0,2);
        Personaje J1 = Jugador1.get(sorteo1);
        Personaje J2 = Jugador2.get(sorteo2);

        System.out.println("El sistema sorteó al Jugador "+ atacaPrimero +" para iniciar la ronda");
        System.out.println("El sistema eligió al personaje " + J1.getNombre() + " del jugador 1 y al personaje "+J2.getNombre()+" de jugador 2 para que se enfrenten en esta ronda.");
        
        int ronda = 1;
        boolean seguirCombatiendo = true;

        Personaje atacante;
        Personaje defensor;

        if (atacaPrimero == 1){
            atacante = J1;
            defensor = J2;
        } else {
            atacante = J2;
            defensor = J1;
        }

        while (seguirCombatiendo) {
            System.out.println("Ronda " + ronda);
            for(int i=0; i<14; i++){
                System.out.println("Atacante: " + atacante.getNombre());
                System.out.println("Defensor: " + defensor.getNombre());

                double danio = ataque(atacante, defensor);
                
                System.out.println(atacante.getNombre()+" ataca a "+defensor.getNombre()+" y le quita "+ danio+" de salud. "+
                                   defensor.getNombre()+" queda con "+defensor.getSalud()+" de salud.");

                if (defensor.getSalud() == 0){
                    System.out.println("");
                    System.out.println("Muere "+defensor.getNombre()+".");
                    double salud = atacante.getSalud() + 10;
                    atacante.setSalud(salud);
                    System.out.println(atacante.getNombre()+" gana 10 de salud como premio, quedando con "+salud+" de salud.");
                    if (atacante == J1){
                        Jugador2.remove(defensor);
                    } else {
                        Jugador1.remove(defensor);
                    }
                    break;
                }
                swap(atacante,defensor);
            }
            ronda++;

            String ganador, perdedor;
            List<Personaje> ganadores;

            if (Jugador1.size()==0){
                ganador = "Jugador 2";
                perdedor = "Jugador 1";
                ganadores = Jugador2;
            }  else if (Jugador2.size()==0){
                ganador = "Jugador 1";
                perdedor = "Jugador 2";
                ganadores = Jugador1;
            } else continue;

            System.out.println("El "+perdedor+" ha perdido todas sus cartas.");
            System.out.println("Gana "+ganador+", le quedo/aron vivos los sgtes. personajes:");
            System.out.println(ganadores);
            System.out.println("Felicitaciones "+ganador+", las fuerzas mágicas del universo luz te abrazan!");
            System.out.println("Fin.");
        }
        
    }

    public double ataque(Personaje atacante, Personaje defensor){
        int PD = atacante.getDestreza() * atacante.getFuerza() * atacante.getNivel();
        int ED = numAleatorio(1,100);
        System.out.println(ED);
        double VA = (PD * ED)/100;
        int PDEF = defensor.getVelocidad() * defensor.getArmadura();

        double danio = 0;
        switch(atacante.getRaza()){
            case Raza.HUMANO:
                danio = (((VA*ED)-PDEF)/500)*5;
                break;
            case Raza.ORCO:
                danio = ((((VA*ED)-PDEF)/500)*5 ) * 1.1;
                break;
            case Raza.ELFO:
                danio = ((((VA*ED)-PDEF)/500)*5 ) * 1.05;
                break;
        }

        double salud = defensor.getSalud();
        if (salud>danio){
            salud = salud-danio;
        } else {
            salud = 0;
        }
        defensor.setSalud(salud);

        return danio;
    }

    public void swap(Personaje A, Personaje B){
        Personaje aux = A;
        A = B;
        B = aux;
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

    public int validarInt(int min, int max, String mensajeIngresar){
        int valor;
        while (true){
            System.out.println(mensajeIngresar);
            valor = s.nextInt();
            if (!(valor>=min && valor<=max))
                System.out.println("No es un valor valido, intente de nuevo.");
            else
                break;
        }
        return valor;
    }

    
}


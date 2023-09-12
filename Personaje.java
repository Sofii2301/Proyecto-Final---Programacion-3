import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


class Personaje {
    private String nombre;
    private Raza raza;
    private String apodo;
    private LocalDate nacimiento;
    private long edad; //Consultar si la edad puede ser cualquier entero o tiene q ser calculada con la fecha de nacimiento
    private int velocidad;
    private int destreza;
    private int fuerza;
    private int nivel;
    private int armadura;
    private int salud;

    public Personaje(String nombre, Raza raza, String apodo, LocalDate nacimiento, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        this.nombre = nombre;
        this.raza = raza;
        this.apodo = apodo;
        this.nacimiento = nacimiento;
        this.edad = ChronoUnit.YEARS.between(nacimiento, LocalDate.now());
        this.velocidad = velocidad;
        this.destreza = destreza;
        this.fuerza = fuerza;
        this.nivel = nivel;
        this.armadura = armadura;
        this.salud = 100;
    }

    public boolean estaVivo() {
        return salud > 0;
    }

    // Implementar métodos de ataque y defensa


    @Override
    public String toString() {
        return "Personaje: " + nombre +
                ", Raza: " + raza +
                ", Apodo: " + apodo +
                ", Salud: " + salud;
    }
}
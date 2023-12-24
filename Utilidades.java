import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Description of the class
 * <p>
 * Marcos Cerrada García
 * Mario Blasco Puente
 *
 * @version 1.0
 */
public class Utilidades {
    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites),
     * y si se introduze un String , se vuelve a solicitar un numero
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        boolean aux = false;
        int numero;
        do {
            try {
                System.out.print(mensaje);
                numero = teclado.nextInt();
                if (numero >= minimo && numero <= maximo) {
                    aux = true;
                }
            } catch (InputMismatchException ex) {
                numero = 0;
                teclado.nextLine();
            }
        } while (!aux);
        return numero;
    }

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites),
     * y si se introduze un String , se vuelve a solicitar un numero
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return long numero
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        boolean aux = false;
        long numero;
        do {
            try {
                System.out.println(mensaje);
                numero = teclado.nextLong();
                if (numero >= minimo && numero <= maximo) {
                    aux = true;
                }
            } catch (InputMismatchException ex) {
                numero = 0;
                teclado.nextLine();
            }
        } while (!aux);
        return numero;
    }

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * y si se introduze un String , se vuelve a solicitar un numero
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return double numero
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        boolean aux = false;
        double numero;
        do {
            try {
                System.out.print(mensaje);
                numero = teclado.nextDouble();
                if (numero >= minimo && numero <= maximo) {
                    aux = true;
                }
            } catch (InputMismatchException ex) {
                numero = 0;
                teclado.nextLine();
            }
        } while (!aux);
        return numero;
    }

    /**
     * Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * y si se introduze un String , se vuelve a solicitar una letra
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return char letra
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        boolean aux = false;
        char letra;
        do {
            try {
                System.out.print(mensaje);
                letra = teclado.nextLine().charAt(0);
                if (letra >= minimo && letra <= maximo) {
                    aux = true;
                } else {
                    System.out.println("El valor de entrada debe ser 'n' 'e'");
                }
            } catch (InputMismatchException ex) {
                letra = 'a';
                teclado.nextLine();
            }
        } while (!aux);
        return letra;

    }


    /**
     * Solicita una fecha repetidamente hasta que se introduzca una correcta
     * y si se introduze un String , se vuelve a repetir el metodo
     *
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        boolean aux = false;
        int dia;
        int mes;
        int anio;
        do {
            try {
                System.out.println(mensaje);
                System.out.print("Ingrese día: ");
                dia = teclado.nextInt();
                while (dia <= 0 || dia > 31) {
                    System.out.print("Ingrese día: ");
                    dia = teclado.nextInt();
                }
                System.out.print("Ingrese mes: ");
                mes = teclado.nextInt();
                while (mes <= 0 || mes >= 13) {
                    System.out.print("Ingrese mes: ");
                    mes = teclado.nextInt();
                }
                System.out.print("Ingrese año: ");
                anio = teclado.nextInt();
                while (anio <= 0 || anio > 2024) {
                    System.out.print("Ingrese año: ");
                    anio = teclado.nextInt();
                }
                aux = true;
            } catch (InputMismatchException ex) {
                dia = 0;
                mes = 0;
                anio = 0;
                teclado.nextLine();
            }
        } while (!aux);
        return new Fecha(dia, mes, anio);
    }

    /**
     * Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * y si se introduze un String , se vuelve a repetir el metodo
     *
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        boolean aux = false;
        int dia;
        int mes;
        int anio;
        int hora;
        int minuto;
        int segundo;
        Fecha fecha = leerFecha(teclado, mensaje);
        dia = fecha.getDia();
        mes = fecha.getMes();
        anio = fecha.getAnio();
        do {
            try {
                System.out.print("Ingrese hora: ");
                hora = teclado.nextInt();
                while (hora < 0 || hora > 24) {
                    System.out.print("Ingrese hora: ");
                    hora = teclado.nextInt();
                }
                System.out.print("Ingrese minuto: ");
                minuto = teclado.nextInt();
                while (minuto < 0 || minuto > 60) {
                    System.out.print("Ingrese minuto: ");
                    minuto = teclado.nextInt();
                }
                System.out.print("Ingrese segundo: ");
                segundo = teclado.nextInt();
                while (segundo < 0 || segundo > 60) {
                    System.out.print("Ingrese segundo: ");
                    segundo = teclado.nextInt();
                }
                aux = true;
            } catch (InputMismatchException ex) {
                hora = 0;
                minuto = 0;
                segundo = 0;
                teclado.nextLine();
            }
        } while (!aux);
        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     *  Solicita un String por teclado
     *
     * @param teclado
     * @param s
     * @return
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.nextLine();
    }
}
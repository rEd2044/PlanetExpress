import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version     1.0
 */
public class Utilidades {

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero;
        System.out.print(mensaje);
        numero=teclado.nextInt();
        while(numero<minimo || numero>maximo){
            System.out.print(mensaje);
            numero=teclado.nextInt();
        }
        //teclado.nextLine();
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return long numero
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        long numero;
        System.out.println(mensaje);
        numero=teclado.nextInt();
        while(numero<minimo || numero>maximo){
            System.out.print(mensaje);
            numero=teclado.nextInt();
        }
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return double numero
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        double numero;
        System.out.print(mensaje);
        numero=teclado.nextInt();
        while(numero<minimo || numero>maximo){
            System.out.print(mensaje);
            numero=teclado.nextInt();
        }

        return numero;
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return char letra
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char letra;
        System.out.print(mensaje);
        letra=teclado.nextLine().charAt(0);
        while(letra!=minimo && letra!=maximo){
            System.out.println("El valor de entrada debe ser 'n' 'e'");
            System.out.print(mensaje);
            letra=teclado.nextLine().charAt(0);
        }
        return letra;
    }


    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        int dia;
        int mes;
        int anio;
        System.out.println(mensaje);
        System.out.print("Ingrese día: ");
        dia= teclado.nextInt();
        while (dia<=0 ||dia>31){
            System.out.print("Ingrese día: ");
            dia= teclado.nextInt();
        }
        System.out.print("Ingrese mes: ");
        mes= teclado.nextInt();
        while (mes<=0 || mes>=13){
            System.out.print("Ingrese mes: ");
            mes= teclado.nextInt();
        }
        System.out.print("Ingrese año: ");
        anio= teclado.nextInt();
        while (anio<=0 ||anio>2024){
            System.out.print("Ingrese año: ");
            anio= teclado.nextInt();
        }
        return new Fecha(dia, mes, anio);
    }


    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        int dia;
        int mes;
        int anio;
        int hora;
        int minuto;
        int segundo;
        Fecha fecha=leerFecha(teclado,mensaje);
        dia=fecha.getDia();
        mes=fecha.getMes();
        anio= fecha.getAnio();
        System.out.print("Ingrese hora: ");
        hora= teclado.nextInt();
        while (hora<0 ||hora>24){
            System.out.print("Ingrese hora: ");
            hora= teclado.nextInt();
        }
        System.out.print("Ingrese minuto: ");
        minuto= teclado.nextInt();
        while (minuto<0 || minuto>60){
            System.out.print("Ingrese minuto: ");
            minuto= teclado.nextInt();
        }
        System.out.print("Ingrese segundo: ");
        segundo= teclado.nextInt();
        while (segundo<0 ||segundo>60) {
            System.out.print("Ingrese segundo: ");
            segundo = teclado.nextInt();
        }
        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado
     * @param s
     * @return
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.nextLine();
    }
}

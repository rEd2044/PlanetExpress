import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.StringConcatFactory;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.Manifest;

/**
 * Description of the class
 *
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version 1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;

    /**
     * Constructor de la clase
     * @param id
     * @param nave
     * @param origen
     * @param muelleOrigen
     * @param salida
     * @param destino
     * @param muelleDestino
     * @param llegada
     * @param precio
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida,
                 PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id = id;
        this.nave = nave;
        this.origen = origen;
        this.muelleOrigen = muelleOrigen;
        this.salida = salida;
        this.destino = destino;
        this.muelleDestino = muelleDestino;
        this.llegada = llegada;
        this.precio = precio;
        this.huecos = new boolean[nave.getFilas()][nave.getColumnas()];
        int ocupacion = 0;
        for (int i = 0; i < nave.getFilas(); i++) {
            for (int j = 0; j < nave.getColumnas(); j++) {
                huecos[i][j] = false;
                ocupacion++;
            }
        }
        this.listaEnvios = new ListaEnvios(ocupacion);
    }

    public String getID() {
        return id;
    }

    public Nave getNave() {
        return nave;
    }

    public PuertoEspacial getOrigen() {
        return origen;
    }

    public int getMuelleOrigen() {
        return muelleOrigen;
    }

    public Fecha getSalida() {
        return salida;
    }

    public PuertoEspacial getDestino() {
        return destino;
    }

    public int getMuelleDestino() {
        return muelleDestino;
    }

    public Fecha getLlegada() {
        return llegada;
    }

    public double getPrecio() {
        return precio;
    }

    // Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int contador = 0;
        for (boolean[] hueco : huecos) {
            for (int columnas = 0; columnas < hueco.length; columnas++) {
                if (hueco[columnas]) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Devuelve true si el porte no tiene más capacidad o false si todavia tiene
    public boolean porteLleno() {
        if (numHuecosLibres() == 0) {
            return true;
        }
        return false;
    }

    // Metodo que devuelve si el hueco esta ocupado
    public boolean huecoOcupado(int fila, int columna) {
        return huecos[fila][columna];
    }

    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }


    /**
     * Devuelve el objeto Envio que corresponde con una fila o columna,
     *
     * @param fila
     * @param columna
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {
        if (huecos[fila][columna]) {
            System.out.println(fila + "," + columna);
            return listaEnvios.buscarEnvio(id, fila, columna);
        } else {
            return null;
        }

    }

    public boolean aniadirEnvio(Envio envio) {
        return this.listaEnvios.insertarEnvio(envio);
    }


    /**
     * Método que ocupa el hueco que indica el envio,
     *
     *
     * @param envio
     * @return true si lo ocupa false si no lo consigue
     */
    public boolean ocuparHueco(Envio envio) {
        int fila = envio.getFila();
        int columna = envio.getColumna();
        System.out.println(fila + "," + columna);
        huecos[fila][columna] = true;
        return !huecoOcupado(fila, columna);
    }


    /**
     *  A través del localizador del envio, se desocupará el hueco
     *
     * @param localizador
     * @return true si lo desocupa o false si no lo consigue
     */
    public boolean desocuparHueco(String localizador) {
        int fila = buscarEnvio(localizador).getFila();
        int columna = buscarEnvio(localizador).getColumna();
        huecos[fila][columna] = false;
        return huecoOcupado(fila, columna);
    }

    /**
     * Devuelve una cadena con información completa del porte
     *
     * @return "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     * Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {
        return "Porte " + id + " de " + origen + " " + muelleOrigen + " (" + salida + ") a " + destino + " " + muelleOrigen + " (" + llegada + ") en " + nave.getMarca() + " ("
                + nave.getModelo() + nave.getMatricula() + " por " + precio + " SSD, huecos libres: " + numHuecosLibres();
    }


    /**
     * Devuelve una cadena con información abreviada del porte
     *
     * @return "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte " + id + " de " + origen.toStringSimple() + " (" + salida + ") a " + destino.toStringSimple() + " (" + llegada + ") ";
    }


    /**
     * Metodo que compara los parametros pasados con los del porte
     *
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return true si coincide con los valores pasados como parametros o false si no lo consigue
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        if (origen.getCodigo().equals(codigoOrigen) && destino.getCodigo().equals(codigoDestino) && fecha.coincide(salida)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        for (int i = 0; i < huecos.length; i++) {
            for (int j = 0; j < huecos[i].length; j++) {
                if (huecoOcupado(i, j)) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println("");
        }
        System.out.print(" ");
    }

    /**
     * Crea un fichero con nombre pasado por argumento escribe lista envios del porte
     *
     * @param fichero
     * @return Devuelve true si ha podido escribir en un fichero la lista de envíos del porte o false si no lo consigue
     */
    public boolean generarListaEnvios(String fichero) {
        try (PrintWriter salida = new PrintWriter(fichero)) {
            salida.println(
                    "-----------------------------------------------------\n" +
                            "--------- Lista de envíos del porte" + getID() + "------\n" +
                            "-----------------------------------------------------\n" +
                            "Hueco\tCliente");
            char[] columnas = {'A', 'B', 'C', 'D', 'E','F','G','H','I','J'};
            for (int i = 0; i < huecos.length; i++) {
                for (int j = 0; j < huecos[i].length; j++) {
                    Envio envio = buscarEnvio(i, j);
                    if (envio != null) {
                        salida.println(envio.getHueco() + "\t" + envio.getCliente().getNombre() + ", " + envio.getCliente().getEmail());
                    } else {
                        salida.println((i + 1) + "" + columnas[j]);
                    }
                }
            }
            System.out.println("Fichero creado correctamente");
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no creado correctamente");
            return false;
        }
    }


    /**
     * Genera un ID de porte.
     *
     * @param rand
     * @return  "PM0123"
     */
    public static String generarID(Random rand) {
        int random = rand.nextInt(0, 9999);
        if (random <= 10) {
            String Random = "000" + random;
            return "PM" + Random;
        } else {
            if (random <= 100) {
                String Random = "00" + random;
                return "PM" + Random;
            } else {
                if (random <= 1000) {
                    String Random = "0" + random;
                    return "PM" + Random;
                } else {
                    return "PM" + random;
                }
            }
        }
    }

    /**
     * Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     * y naves y la restricción de que no puede estar repetido el identificador.
     *  La función solicita repetidamente los parametros hasta que sean correctos
     *
     * @param teclado
     * @param rand
     * @param puertosEspaciales
     * @param naves
     * @param portes
     * @return porte creado
     */
    public static Porte altaPorte(Scanner teclado, Random rand, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves, ListaPortes portes) {
        String id = generarID(rand);
        PuertoEspacial origen = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Origen: ");
        int muelleOrigen = Utilidades.leerNumero(teclado, "Ingrese el muelle de Origen (1-4): ", 1, 4);
        teclado = new Scanner(System.in);
        PuertoEspacial destino = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Destino: ");
        int muelleDestino = Utilidades.leerNumero(teclado, "Ingrese Terminal Destino (1-6): ", 1, 6);
        naves.mostrarNaves();
        Nave nave = naves.seleccionarNave(teclado, "Ingrese matrícula de la nave: ", origen.distancia(destino));
        Fecha fechasalida = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
        Fecha fechallegada = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada: ");
        while (fechallegada.anterior(fechasalida)) {
            System.out.println("\n\nLlegada debe ser posterior a salida");
            fechasalida = Utilidades.leerFechaHora(teclado, "Fecha de salida:");
            fechallegada = Utilidades.leerFechaHora(teclado, "Fecha de llegada: ");
        }
        System.out.print("Ingrese precio de pasaje: ");
        double precio = teclado.nextDouble();
        while (precio <= 0.0) {
            System.out.print("Ingrese precio de pasaje: ");
            precio = teclado.nextDouble();
        }
        Porte porte = new Porte(id, nave, origen, muelleOrigen, fechasalida, destino, muelleDestino, fechallegada, precio);
        System.out.println("\tPorte " + porte.getID() + " creado correctamente");
        portes.insertarPorte(porte);

        return porte;
    }
}
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * Marcos Cerrada García
 * Mario Blasco Puente
 * @version     1.0
 */
public class Envio {

    private String localizador;
    private Porte porte;
    private Cliente cliente;
    private int fila;
    private int columna;
    private double precio;

    /**
     * Constructor de la  clase
     *
     * @param localizador
     * @param porte
     * @param cliente
     * @param fila
     * @param columna
     * @param precio
     */
    public Envio(String localizador, Porte porte, Cliente cliente, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.porte = porte;
        this.cliente = cliente;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    /**
     *Metodo que devuelve el localizador
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Metodo que devuelve el porte
     * @return porte
     */
    public Porte getPorte() {
        return porte;
    }

    /**
     * Metodo que devuelve el cliente
     * @return cliente
     */

    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Metodo que devuelve la fila en la que se encuentra el envio
     * @return fila
     */
    public int getFila() {
        return fila;
    }

    /**
     *Metodo que devuelve la columna en la que se encuentar el envio
     * @return columnna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve el hueco
     * @return String.valueOf(this.fila)+letra
     */
    public String getHueco() {
        char letra = '@';
        for(int i = 1; i<=this.columna;i++){
            letra++;
        }
        return String.valueOf(this.fila)+letra;

    }

    /**
     * Metodo que devuelve el precio del envio
     * @return precio
     */
    public double getPrecio() {
        return precio;
    }
    /**
     *Crea un String con el siguente formato
     * @return "Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD"
     */

    public String toString() {
        return ("Envio "+this.localizador+" para "+this.porte.toStringSimple()+"en hueco "+getHueco()+" por "+this.porte.getPrecio()+" SSD");

    }
    // Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        return (this.cliente.cancelarEnvio(this.localizador));
    }

    /**
     * Método para imprimir la información de este envío en un fichero
     * @param fichero
     * @return Devuelve la información con el siguiente formato como ejemplo ->
     *     -----------------------------------------------------
     *     --------- Factura del envío PM1111AAAABBBBC ---------
     *     -----------------------------------------------------
     *     Porte: PM0066
     *     Origen: Gaia Galactic Terminal (GGT) M5
     *     Destino: Cidonia (CID) M1
     *     Salida: 01/01/2023 08:15:00
     *     Llegada: 01/01/2024 11:00:05
     *     Cliente: Zapp Brannigan, zapp.brannigan@dop.gov
     *     Hueco: 6C
     *     Precio: 13424,56 SSD
     */
    public boolean generarFactura(String fichero) {
        PrintWriter factura = null;
        boolean aux = true;
        try {
            factura = new PrintWriter(fichero);
            factura.println("-----------------------------------------------------"+
                          "\n--------- Factura del envío PM1111AAAABBBBC ---------"+
                          "\n-----------------------------------------------------"+
                          "\nPorte: "+ this.porte.getID()+
                          "\nOrigen: "+this.porte.getOrigen()+
                          "\nDestino: "+this.porte.getDestino()+
                          "\nSalida: "+this.porte.getSalida()+
                          "\nLlegada: "+this.porte.getLlegada()+
                          "\nCliente: "+this.cliente.toString()+
                          "\nHueco: "+getHueco()+
                          "\nPrecio: "+this.porte.getPrecio()+" SSD");
            System.out.println("Factura generada correctamente");
        } catch (FileNotFoundException e) {
            aux = false;
        }finally {
            if(factura!=null){
                factura.close();
            }
        }
        return aux;
    }



    /**
     *	Genera un localizador de envío.
     * @param rand
     * @param idPorte
     * @return idPorte + letrasAleatorias
     */
    public static String generarLocalizador(Random rand, String idPorte) {
        char aux ;
        StringBuilder letrasAleatorias = new StringBuilder();
        for(int j =0; j<9;j++) {
            aux = '@';
            int aleatorio = (rand.nextInt(25));
            for (int i = 0; i <= aleatorio; i++) {
                aux++;
            }
            letrasAleatorias.append(aux);
        }
        return idPorte + letrasAleatorias;
    }


    /**
     *  Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     *  necesarios al usuario en el orden.
     *  La función solicita repetidamente los parámetros hasta que sean correctos
     * @param teclado
     * @param rand
     * @param porte
     * @param cliente
     * @return Envio para el porte y cliente especificados
     */
    public static Envio altaEnvio(Scanner teclado, Random rand, Porte porte, Cliente cliente) {
        System.out.print("Fila del hueco: ");
        int fila = teclado.nextInt();
        System.out.print("Columna del hueco: ");
        int columna = teclado.nextInt();
        System.out.print("Precio del envío: ");
        double precio = teclado.nextDouble();
        String localizador = generarLocalizador(rand, porte.getID());
        return new Envio(localizador,porte,cliente,fila,columna,precio);
    }
}
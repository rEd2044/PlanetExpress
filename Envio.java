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
     * Constructor of the class
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
     *
     * @return
     */
    //metodo que devuelve el localizador
    public String getLocalizador() {
        return localizador;
    }

    /**
     *
     * @return
     */
    //metodo que devuelve el porte
    public Porte getPorte() {
        return porte;
    }

    /**
     *
     * @return
     */
    //metodo que devuelve el cliente
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @return
     */
    // metodo que devuelve la fila en la que se encuentra el envio
    public int getFila() {
        return fila;
    }

    /**
     *
     * @return
     */
    //metodo que devuelve la columna en la que se encuentar el envio
    public int getColumna() {
        return columna;
    }

    /**
     *
     * @return
     */
    // TODO: Ejemplos: "1A" para el hueco con fila 1 y columna 1, "3D" para el hueco con fila 3 y columna 4
    public String getHueco() {
        char letra = '@';
        for(int i = 1; i<=this.columna;i++){
            letra++;
        }
        return String.valueOf(this.fila)+letra;

    }

    /**
     *
     * @return
     */
    //metodo que devuelve el precio del envio
    public double getPrecio() {
        return precio;
    }
//                                                     S     I     N            T    E     R    M    I     N     A     R
    /**
     *
     * @return
     */
    //TODO: Texto que debe generar: Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD
    public String toString() {
        return ("Envio "+this.localizador+" para "+this.porte.toStringSimple()+"en hueco "+getHueco()+" por "+this.porte.getPrecio()+" SSD");

    }
    // TODO: Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        return ((this.porte.desocuparHueco(this.localizador)&&this.cliente.cancelarEnvio(this.localizador)));
    }

    /**
     * TODO: Método para imprimir la información de este envío en un fichero que respecta el formato descrito en el
     *  enunciado
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
            factura = new PrintWriter(fichero+".txt");
            factura.println("-----------------------------------------------------"+
                          "\n--------- Factura del envío PM1111AAAABBBBC ---------"+
                          "\n-----------------------------------------------------"+
                          "\nPorte: "+this.porte.getID()+
                          "\nOrigen: "+this.porte.getOrigen()+
                          "\nDestino: "+this.porte.getDestino()+
                          "\nSalida: "+this.porte.getSalida()+
                          "\nLlegada: "+this.porte.getLlegada()+
                          "\nCliente: "+this.cliente.toString()+
                          "\nHueco: "+getHueco()+
                          "\nPrecio: "+this.porte.getPrecio()+" SSD");
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
     *	TODO: Genera un localizador de envío. Este consistirá en una cadena de 15 caracteres, de los cuales los seis
	 *   primeros será el ID del porte asociado y los 9 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     *   NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand
     * @param idPorte
     * @return
     */
    //                                       P R E G U N T A R   S I   S E   P U E D E  H A C E R   A S I
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
     * TODO: Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     *  necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
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
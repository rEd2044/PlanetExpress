import java.io.*;
import java.util.AbstractQueue;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * Marcos Cerrada García
 * Mario Blasco Puente
 * @version 1.0
 */
public class ListaEnvios {
    private Envio[] envios;

    /**
     * Constructor de la clase
     *
     * @param capacidad
     */
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];

    }

    /**
     * Metodo que devuelve el número de envios que hay en la lista
     * @return la ocupacion de la lista
    */
    public int getOcupacion() {
        int ocupacion = 0;
        for (int i = 0; i < envios.length; i++) {
            if (envios[i] != null) {
                ocupacion++;
            }
        }
        return ocupacion;
    }

    /*
     * Metodo para comparar que esta llena la lista
     * @return true si esta llena false si todovía hay espacios libres
     */
    public boolean estaLlena() {
        if (getOcupacion() == envios.length) {
            return true;
        }
        return false;
    }


    //Devuelve el envio dado posición
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     * Insertamos un nuevo en envio en la lista
     *
     * @param envio
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarEnvio(Envio envio) {
        if (estaLlena()) {
            return false;
        } else {
            envios[getOcupacion()] = envio;
            return true;
        }
    }

    /**
     * Metodo que busca una nave a partir de la matricula pasado como parámetro
     *
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String localizador) {
        Envio envio=null;
        for(int i=0;i<getOcupacion();i++) {
            if (Objects.equals(localizador, getEnvio(i).getLocalizador())) {
                envio=getEnvio(i);
            }
        }

        return envio;
    }

    /**
     *Metodo que busca el envio a partir de la fila ,columna  y id del porte pasado como parámetro
     *
     * @param idPorte
     * @param fila
     * @param columna
     * @return el envio que encontramos o null si no existe
     */

    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        boolean aux =false;
        Envio envio = null;
        for(int i=0;i<getOcupacion();i++){
            if(getEnvio(i).getColumna()==columna && getEnvio(i).getFila()==fila){
                if(Objects.equals(getEnvio(i).getPorte().getID(), idPorte) && !aux ){
                    envio=getEnvio(i);
                    aux=true;
                }
            }
        }
        return envio;
    }

    /**
     * Elimina el envio tráces del localizador pasado por parámetro
     *
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    public boolean eliminarEnvio(String localizador) {
        boolean aux = false;
        for(int i =0;i< getOcupacion();i++){
            if(Objects.equals(getEnvio(i).getLocalizador(), localizador)){
                envios[i]= null;
                aux = true;
            }
        }
        return aux;
    }


    //Muestra por pantalla los Envios de la lista, con el formato que aparece
    //en el enunciado
    public void listarEnvios() {
        for(int i = 0; i< envios.length;i++){
            if(envios[i]!=null){
                System.out.println(envios[i].toString());
            }
        }

    }

    /**
     *Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     *para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *
     * @param teclado
     * @param mensaje
     * @return envio
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        Envio envio = null;
        do{
            teclado=new Scanner(System.in);
            System.out.print(mensaje);
            String localizador = teclado.nextLine();
            envio=buscarEnvio(localizador);
            if(envio==null){
                System.out.println("\tLocalizador incorrecto");
            }
        }while(envio==null);
        return envio;
    }


    /**
     * Añade los Envios al final de un fichero CSV, sin SOBREESCRIBIR la información
     *
     *
     * @param fichero
     * @return true si lo añade la información o false si no lo hace
     */
    public boolean aniadirEnviosCsv(String fichero) {
        try (PrintWriter salida = new PrintWriter(fichero)) {
            for (int i = 0; i < getOcupacion(); i++) {
                Envio envio = envios[i];
                salida.println(envio.getLocalizador() + ";" + envio.getPorte().getID() + ";" + envio.getCliente().getEmail() + ";" + envio.getFila() + ";" + envio.getColumna() + ";" + envio.getPrecio());
            }

            return true;
        }catch (Exception e) {
            System.out.println("Error de escritura en fichero " + fichero);
            return false;
        }
    }

    /**
     * Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     *
     * @param ficheroEnvios
     * @param portes
     * @param clientes
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        BufferedReader leer = null;
        try {
            leer = new BufferedReader(new FileReader(ficheroEnvios));
            String linea;
                while( (linea =leer.readLine())!= null) {
                    String[] parametros = linea.split(";");
                    Porte porte = portes.buscarPorte(parametros[1]);
                    Cliente cliente = clientes.buscarClienteEmail(parametros[2]);
                    int fila = Integer.parseInt(parametros[3]);
                    int columna = Integer.parseInt(parametros[4]);
                    double precio = Double.parseDouble(parametros[5]);
                    Envio envio = new Envio(parametros[0], porte, cliente, fila, columna, precio);
                    cliente.aniadirEnvio(envio);
                    porte.aniadirEnvio(envio);
                    porte.ocuparHueco(envio);
                    porte.imprimirMatrizHuecos();
                    parametros = linea.split(";");
                }

        }catch (IOException e){
            System.err.println("Fichero " + ficheroEnvios + " no encontrado");
        } finally {
            try {
                if (leer != null) {
                    leer.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}

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
     *
     *
     * @param capacidad
     */
    // TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];

    }

    /**
     * @return
     */
    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() { //posible fallo en el envio.length
        int ocupacion = 0;
        for (int i = 0; i < envios.length; i++) {
            if (envios[i] != null) {
                ocupacion++;
            }
        }
        return ocupacion;
    }

    /**
     * @return
     */
    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        if (getOcupacion() == envios.length) {
            return true;
        }
        return false;
    }

    /**
     * @param i
     * @return
     */
    //TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     *
     *
     * @param envio
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    //TODO: insertamos un nuevo envío en la lista
    public boolean insertarEnvio(Envio envio) {
        if (estaLlena()) {
            return false;
        } else {
            envios[getOcupacion()] = envio;
            return true;
        }
    }

    /**
     *
     *
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    //TODO: Buscamos el envio a partir del localizador pasado como parámetro
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
     *
     *
     * @param idPorte
     * @param fila
     * @param columna
     * @return el envio que encontramos o null si no existe
     */
    //TODO: Buscamos el envio a partir del idPorte, fila y columna pasados como parámetros
    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        boolean aux =false;
        Envio envio = null;
        for(int i=0;i<getOcupacion();i++){
            if(getEnvio(i).getColumna()==columna && getEnvio(i).getFila()==fila){
                if(Objects.equals(getEnvio(i).getPorte().getID(), idPorte) && aux ){
                    envio=getEnvio(i);
                    aux=true;
                }
            }
        }
        return envio;
    }

    /**
     *
     *
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    //TODO: Eliminamos un envio a través del localizador pasado por parámetro
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

    /**
     *
     *
     */
    //TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
    //en el enunciado
    public void listarEnvios() {
        for(int i = 0; i< envios.length;i++){
            if(envios[i]!=null){
                System.out.println(envios[i].toString());
            }
        }

    }

    /**
     *
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    //TODO: Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
    //para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
    //La función solicita repetidamente hasta que se introduzca un localizador correcto
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
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     *
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {
        try{FileWriter fw =new FileWriter(fichero,true);    //Esto sirve para no sobrescribir el archivo.
            try (PrintWriter salida = new PrintWriter(fw)) {
                for (int i = 0; i < getOcupacion(); i++) {
                Envio envio = envios[i];
                salida.println(envio.getLocalizador() + ";" + envio.getPorte().getID() + ";" + envio.getCliente().getEmail() + ";" + envio.getFila() + ";" + envio.getColumna() + ";" + envio.getPrecio());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero " + fichero);
            return false;
        }
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
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
                    clientes.insertarCliente(envio.getCliente());
                    portes.insertarPorte(envio.getPorte());
                    cliente.aniadirEnvio(envio);
                    parametros = linea.split(";");
                }

        }catch (Exception e){
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

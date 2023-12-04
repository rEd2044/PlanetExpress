import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
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
        int i =0;
        boolean aux = false;
        do{
            if (envios[i] == null) {
                envios[i] = envio;
                aux = true;
            }
            i++;
        }while(!aux && i<envios.length);
        return aux;
    }

    /**
     *
     *
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    //TODO: Buscamos el envio a partir del localizador pasado como parámetro
    public Envio buscarEnvio(String localizador) {
        int i =0;
        boolean aux = false;
        Envio aux1 = null;
        do{
            if (Objects.equals(envios[i].getLocalizador(), localizador)) {
                aux1 =envios[i];
                aux = true;
            }
            i++;
        }while(!aux && i<envios.length);
        if(aux){
            return aux1;
        }else{
            return null;
        }

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
        int i = 0;
        boolean aux =false;
        Envio aux1 = null;
        do{
            if(envios[i].getColumna()==columna && envios[i].getFila()== fila){
                aux1= envios[i];
                aux = true;
            }
            i++;

        }while(!aux && i<envios.length);
        if(aux){
            return aux1;
        }else{
            return null;
        }
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
        for(int i =0;i< envios.length;i++){
            if(Objects.equals(envios[i].getLocalizador(), localizador)){
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
        Envio aux1 = null;
        boolean aux2 = false;
        do{
            System.out.println(mensaje);
            String aux = teclado.nextLine();
            for(int i = 0; i< envios.length;i++){
                if(Objects.equals(envios[i].getLocalizador(), aux)){
                   aux1 = envios[i];
                   aux2 = true;
                }
            }
            if(!aux2){
                System.out.println("Localizador no encontrado.");
            }

        }while(!aux2);
        return aux1;
    }


    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {
        PrintWriter pw = null;
        try {

            return true;
        } catch (Exception e) {
            return false;
        } finally{

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
        Scanner sc = null;
        try {

        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero de envíos");
        } finally {

        }
    }
}

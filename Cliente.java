import java.util.Scanner;

/**
 * Description of the classs
 *
 * @author Taller de Programación
 * @version     1.0
 */
public class Cliente {

    private final ListaEnvios listaEnvios;
    private final String nombre;
    private final String apellidos;
    private final String email;

    /**
     * Constructor of the class
     *
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param email Email del cliente
     * @param maxEnvios Número máximo de envíos que puede tener el cliente
     */
    public Cliente(String nombre, String apellidos, String email, int maxEnvios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.listaEnvios = new ListaEnvios(maxEnvios);
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return
     */
    // TODO: Texto que debe generar: Zapp Brannigan, zapp.brannigan@dop.gov
    public String toString() {
        String aux;
        return aux= this.nombre.toLowerCase() +"."+this.apellidos.toLowerCase()+"@alumnos.upm.es";

    }

    /**
     *
     * @return
     */
    // TODO: Devuelve un booleano que indica si se ha alcanzado el número máximo de envíos
    public boolean maxEnviosAlcanzado() {
       return this.listaEnvios.estaLlena();
    }

    /**
     *
     * @param i
     * @return
     */
    // TODO: Devuelve un envío en función de su posición

    public Envio getEnvio(int i) {
    return this.listaEnvios.getEnvio(i);
    }

    /**
     *
     * @return
     */
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    /**
     *
     * @param envio
     * @return
     */
    // TODO: Añade un envío al cliente
    public boolean aniadirEnvio(Envio envio) {
    return this.listaEnvios.insertarEnvio(envio);
    }

    /**
     *
     * @param localizador
     * @return
     */
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     *
     * @param localizador
     * @return
     */
    // TODO: Elimina el envío de la lista de envíos del pasajero
    public boolean cancelarEnvio(String localizador) {
     return this.listaEnvios.eliminarEnvio(localizador);
    }

    /**
     *
     */
    public void listarEnvios() {
        listaEnvios.listarEnvios();
    }

    /**
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    // Encapsula la funcionalidad seleccionarEnvio de ListaEnvios
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        return listaEnvios.seleccionarEnvio(teclado, mensaje);
    }


    /**
     * TODO: Método estático para crear un nuevo cliente "no repetido", se pide por teclado los datos necesarios
     * al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     * La función tiene que solicitar repetidamente los parámetros hasta que sean correctos
     * @param teclado
     * @param clientes
     * @param maxEnvios
     * @return Cliente
     */
    //          P   O   S   I   B   L   E                  F   A   L   L   O
        public static Cliente altaCliente(Scanner teclado, ListaClientes clientes, int maxEnvios) {
        String email, nombre, apellidos;
        do {
            nombre = Utilidades.leerCadena(teclado, "Nombre:");
            apellidos = Utilidades.leerCadena(teclado, "Apellidos:");
            email = Utilidades.leerCadena(teclado, "Email:");
        }while (clientes.buscarClienteEmail(email)!=null|| !correctoEmail(email));
        return new Cliente(nombre, apellidos, email, maxEnvios);
    }


    /**
     * TODO: Metodo para comprobar que el formato de email introducido sea correcto
     * @param email
     * @return
     */
    public static boolean correctoEmail(String email) {
        email.contains("@alumnos.upm.es");
        if( email.contains("@alumnos.upm.es")){
            return true;
        }else{
            return false;
        }
    }
}

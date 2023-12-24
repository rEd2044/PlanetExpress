import java.util.Scanner;

/**
 * Description of the classs
 *
 * Marcos Cerrada García
 * Mario Blasco Puente
 * @version 1.0
 */
public class Cliente {

    private final ListaEnvios listaEnvios;
    private final String nombre;
    private final String apellidos;
    private final String email;

    /**
     * Constructor de la  clase
     *
     * @param nombre    Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param email     Email del cliente
     * @param maxEnvios Número máximo de envíos que puede tener el cliente
     */
    public Cliente(String nombre, String apellidos, String email, int maxEnvios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.listaEnvios = new ListaEnvios(maxEnvios);
    }

    /**
     * Metodo que devuelve el nombre del cliente
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el apellido del cliente
     * @return apellido
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Devuelve el Email del cliente
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Escribe un String con el formato indicado
     * @return "Zapp Brannigan, zapp.brannigan@alumnos.upm.es"
     */
    public String toString() {
        String aux;
        return aux = this.nombre.toLowerCase() + "." + this.apellidos.toLowerCase() + "@alumnos.upm.es";

    }

    /**
     * Metodo booleano que indica si se ha alcanzado el número máximo de envíos
     * @return true si esta llenada la listaenvios o false si hay espacios libres
     */
    public boolean maxEnviosAlcanzado() {
        return this.listaEnvios.estaLlena();
    }

    /**
     * Devuelve el envio en función de su posición
     * @param i
     * @return envio de la posición indicada o null si no existe
     */
    public Envio getEnvio(int i) {
        return this.listaEnvios.getEnvio(i);
    }

    /**
     * Devuelve la lista de envios
     * @return listaEnvios
     */
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    /**
     *  Añade un envio al cliente
     * @param envio
     * @return true si consiguio añadir bien el envio o false si no lo consiguio
     */
    public boolean aniadirEnvio(Envio envio) {
        return this.listaEnvios.insertarEnvio(envio);
    }

    /**
     * Devuelve el envio a partir de un localizador
     * @param localizador
     * @return envio encontrado o null si no existe
     */
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * Elimina el envío de la lista de envíos del pasajero
     *  @param localizador
     * @return true si se a eliminado y false si no se elimino el envio
     */
    public boolean cancelarEnvio(String localizador) {
        return this.listaEnvios.eliminarEnvio(localizador);
    }

    /**
     *
     */
    //metodo que imprime por pantalla la lsita de envios
    public void listarEnvios() {
        listaEnvios.listarEnvios();
    }

    /**
     * @param teclado
     * @param mensaje
     * @return
     */
    // Encapsula la funcionalidad seleccionarEnvio de ListaEnvios
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        return listaEnvios.seleccionarEnvio(teclado, mensaje);
    }


    /**
     * Método estático para crear un nuevo cliente "no repetido", se pide por teclado los datos necesarios
     * al usuario en el orden
     * La función tiene que solicitar repetidamente los parámetros hasta que sean correctos
     * @param teclado
     * @param clientes
     * @param maxEnvios
     * @return Cliente
     */
    public static Cliente altaCliente(Scanner teclado, ListaClientes clientes, int maxEnvios) {
        String email, nombre, apellidos;
        do {
            nombre = Utilidades.leerCadena(teclado, "Nombre:");
            apellidos = Utilidades.leerCadena(teclado, "Apellidos:");
            email = Utilidades.leerCadena(teclado, "Email:");
            if(clientes.buscarClienteEmail(email)!=null){
                System.out.println("Ya existe un cliente con ese email");
            }else if(clientes.buscarClienteEmail(email)==null){
                if(correctoEmail(email)){
                    System.out.println("Cliente con email "+ email + " creado correctamente.");
                }else{
                    System.out.println("Formato de email incorrecto.");

                }
            }
        } while (clientes.buscarClienteEmail(email) != null || !correctoEmail(email));
        return new Cliente(nombre, apellidos, email, maxEnvios);
    }



    /**
     * Metodo para comprobar que el formato de email introducido sea correcto
     *
     * @param email
     * @return true si es correcto o false si no lo es
     */

    public static boolean correctoEmail(String email) {
        email.contains("@alumnos.upm.es");
        if (email.contains("@alumnos.upm.es")) {
            return true;
        } else {
            return false;
        }
    }
}

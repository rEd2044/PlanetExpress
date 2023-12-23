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
     * Constructor of the class
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
     * @return
     */
    //metodo que devuelve el nombre del cliente
    public String getNombre() {
        return nombre;
    }

    /**
     * @return
     */
    // Devuelve el apellido del cliente
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @return
     */
    // Devuelve el Email del cliente
    public String getEmail() {
        return email;
    }

    /**
     * @return
     */
    // TODO: Texto que debe generar: Zapp Brannigan, zapp.brannigan@dop.gov
    public String toString() {
        String aux;
        return aux = this.nombre.toLowerCase() + "." + this.apellidos.toLowerCase() + "@alumnos.upm.es";

    }

    /**
     * @return
     */
    // TODO: Devuelve un booleano que indica si se ha alcanzado el número máximo de envíos
    public boolean maxEnviosAlcanzado() {
        return this.listaEnvios.estaLlena();
    }

    /**
     * @param i
     * @return
     */
    // TODO: Devuelve un envío en función de su posición
    public Envio getEnvio(int i) {
        return this.listaEnvios.getEnvio(i);
    }

    /**
     * @return
     */
    //Devuelve la lista de envios
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }

    /**
     * @param envio
     * @return
     */
    // TODO: Añade un envío al cliente
    public boolean aniadirEnvio(Envio envio) {
        System.out.println("aqui");
        return this.listaEnvios.insertarEnvio(envio);
    }

    /**
     * @param localizador
     * @return
     */
    //Devuelve el envio a partir de un localizador
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
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
     *
     * @param teclado
     * @param clientes
     * @param maxEnvios
     * @return Cliente
     */
    //TODO: Método estático para crear un nuevo cliente "no repetido", se pide por teclado los datos necesarios
    //al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
    //La función tiene que solicitar repetidamente los parámetros hasta que sean correctos
    //          P   O   S   I   B   L   E                  F   A   L   L   O
    public static Cliente altaCliente(Scanner teclado, ListaClientes clientes, int maxEnvios) {
        String email, nombre, apellidos;
        do {
            teclado=new Scanner(System.in);
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
     *
     *
     * @param email
     * @return
     */
    // TODO: Metodo para comprobar que el formato de email introducido sea correcto
    public static boolean correctoEmail(String email) {
        email.contains("@alumnos.upm.es");
        if (email.contains("@alumnos.upm.es")) {
            return true;
        } else {
            return false;
        }
    }
}

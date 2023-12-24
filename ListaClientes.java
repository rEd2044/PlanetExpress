import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *<p>
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version 1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     *Constructor de la clase
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        clientes = new Cliente[capacidad];
    }

    /**
     * Metodo que devuelve el número de clientes que hay en la lista
     * @return la ocupacion de la lista
     */
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                contador++;
            }
        }
        return contador;
    }

    /*
     * Metodo para comparar que esta llena la lista
     * @return true si esta llena false si todovía hay espacios libres
     */
    public boolean estaLlena() {
        boolean aux = true;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] == null) {
                aux = false;
            }
        }
        return aux;
    }

    // Devuelve el cliente dada la posición en la lista
    public Cliente getCliente(int i) {
        if (clientes[i] != null) {
            return clientes[i];
        } else {
            return null;
        }
    }

    /**
     * Insertamos una nuevo cliente en la lista
     * @param cliente
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */

    public boolean insertarCliente(Cliente cliente) {
        if (estaLlena()) {
            return false;
        } else {
            clientes[getOcupacion()] = cliente;
            return true;
        }

    }
    /**
     * Metodo que busca una cliente a partir de la matricula pasada como parámetro
     * @param email
     * @return el cliente que encontramos o null si no existe
     */
    public Cliente buscarClienteEmail(String email) {
        Cliente aux = null;
        if(getOcupacion()!=0) {
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] !=null && Objects.equals(email, clientes[i].getEmail())) {
                    aux = clientes[i];
                }
            }
        }
        return aux;
    }

    /**
     *  Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *
     *
     * @param teclado
     * @param mensaje
     * @return cliente
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = null;
        do {
            teclado=new Scanner(System.in);
            System.out.print(mensaje);
            String email = teclado.nextLine();
            cliente =buscarClienteEmail(email);
            if (cliente == null) {
                System.out.println("Email no encontrado.");
            }
        } while (cliente == null);
        return cliente;
    }

    /**
     * Genera un fichero CSV con la lista de Clientes correspondiente
     *
     * @param fichero
     * @return si lo escribe devuelve true si no false
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null) {
                    Cliente cliente = clientes[i];
                    salida.println(cliente.getNombre() + ";" + cliente.getApellidos() + ";" + cliente.getEmail());
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el fichero " + fichero);
            return false;
        } finally{
            if(salida!=null){
                salida.close();
            }
        }

    }

    /**
     * Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     *
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */

    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        ListaClientes listaClientes = new ListaClientes(capacidad);
        BufferedReader leer = null;
        try {
            leer = new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = leer.readLine()) != null) {
                String[] parametros = linea.split(";");
                Cliente cliente = new Cliente(parametros[0], parametros[1], parametros[2], maxEnviosPorCliente);
                listaClientes.insertarCliente(cliente);
            }
        } catch (IOException ex) {
            System.err.println("Fichero " + fichero+ " no encontrado");
            return null;
        } finally {
            try {
                if (leer != null) {
                    leer.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return listaClientes;
    }
}
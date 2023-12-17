import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 * <p>
 * Marcos Cerrada García
 * Mario Blasco Puente
 *
 * @version 1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        clientes = new Cliente[capacidad];
    }

    /**
     * @return
     */
    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * @return
     */
    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        boolean aux = true;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] == null) {
                aux = false;
            }
        }
        return aux;
    }

    // TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        if (clientes[i] != null) {
            return clientes[i];
        } else {
            return null;
        }
    }

    /**
     * @param cliente
     * @return
     */
    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        boolean aux = false;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] == null) {
                clientes[i] = cliente;
                aux = true;
            }
        }
        return aux;
    }

    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        Cliente aux = null;
        for (int i = 0; i < clientes.length; i++) {
            if (Objects.equals(email, clientes[i].getEmail())) {
                aux = clientes[i];
            }
        }
        return aux;
    }

    /**
     * TODO: Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *  La función debe solicitar repetidamente hasta que se introduzca un email correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = null;
        do {
            System.out.println(mensaje);
            String email = teclado.nextLine();
            for (int i = 0; i < clientes.length; i++) {
                if (Objects.equals(email, clientes[i])) {
                    cliente = clientes[i];
                }
            }
            if (cliente == null) {
                System.out.println("Email no encontrado.");
            }
        } while (cliente == null);
        return cliente;
    }

    /**
     * TODO: Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     *  fichero
     *
     * @param fichero
     * @return
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero + ".csv");
            for (int i = 0; i < clientes.length; i++) {
                salida.println(clientes[i].getNombre() + ";" + clientes[i].getApellidos() + ";" + clientes[i].getEmail());
            }
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
        return true;
    }

    /**
     * TODO: Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     *
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        ListaClientes listaClientes = null;
        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(fichero + ".csv")));
            do {
                String linea = sc.nextLine();
                String[] parametros = linea.split(";");
                Cliente cliente = new Cliente(parametros[0], parametros[1], parametros[2], maxEnviosPorCliente);
                listaClientes.insertarCliente(cliente);
            } while (sc.hasNextLine());

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return listaClientes;
    }
}
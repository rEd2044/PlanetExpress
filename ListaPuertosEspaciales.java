import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;

    /**
     * Constructor de la clase
     *
     * @param capacidad
     */
    public ListaPuertosEspaciales(int capacidad) {
        this.lista = new PuertoEspacial[capacidad];

    }

    /**
     * Metodo que devuelve el número de puertos espaciales que hay en la lista
     * @return la ocupacion de la lista
     */
    public int getOcupacion() {
        int ocupacion = 0;
        int i = 0;
        while (i < lista.length) {
            if (lista[i] == null) {
                i++;
            } else {
                ocupacion++;
                i++;
            }
        }
        return ocupacion;

    }

    /*
    * Metodo para comparar que esta llena la lista
    * @return true si esta llena false si todovía hay espacios libres
     */
    public boolean estaLlena() {
        int ocupacion = getOcupacion();
        if (ocupacion == lista.length) {
            return true;
        }
        return false;
    }

    // Devuelve un puerto espacial dado posicíon
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * Insertamos un Puerto espacial nuevo en la lista
     *
     * @param puertoEspacial
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        if (estaLlena()) {
            return false;
        } else {
            int ocupacion = getOcupacion();
            lista[ocupacion] = puertoEspacial;
            return true;
        }

    }

    /**
     * Metodo que busca un Puerto espacial a partir del codigo pasado como parámetro
     *
     * @param codigo
     * @return Puerto espacial que encontramos o null si no existe
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        PuertoEspacial puertoEspacial = null;
        for (int i = 0; i < getOcupacion(); i++) {
            if (Objects.equals(codigo, getPuertoEspacial(i).getCodigo())) {
                puertoEspacial = getPuertoEspacial(i);
            }
        }
        return puertoEspacial;
    }

    /**
     * Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *
     * @param teclado
     * @param mensaje
     * @return puerto espacial
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        PuertoEspacial puertoEspacial = null;
        System.out.print(mensaje);
        String codigo1 = teclado.nextLine();
        puertoEspacial = buscarPuertoEspacial(codigo1);
        while (puertoEspacial == null) {
            System.out.println("\tCódigo de puerto no encontrado");
            System.out.print(mensaje);
            String codigo = teclado.nextLine();
            puertoEspacial = buscarPuertoEspacial(codigo);
        }
        return puertoEspacial;
    }

    /**
     * Genera un fichero CSV con la lista de puertos espaciales
     *
     * @param nombre
     * @return true si consiguio escribirlo  o false si no lo consiguio
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        try (PrintWriter salida = new PrintWriter(nombre)) {
            for (int i = 0; i < getOcupacion(); i++) {
                if (lista[i] != null) {
                    PuertoEspacial puertoEspacial = lista[i];
                    salida.println(puertoEspacial.getNombre() + ";" + puertoEspacial.getCodigo() +
                            ";" + puertoEspacial.getRadio() + ";" + puertoEspacial.getAzimut() + ";" + puertoEspacial.getPolar() + ";" + puertoEspacial.getMuelles());
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error de escritura en fichero " + nombre);
            return false;
        }

    }


    /**
     * Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     *
     * @param fichero
     * @param capacidad
     * @return listapuertosespaciales
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fichero));
            do {
                String linea = sc.nextLine();
                String[] parametros = linea.split(";");
                double radio = Double.parseDouble(parametros[2]);
                double azimut = Double.parseDouble(parametros[3]);
                double polar = Double.parseDouble(parametros[4]);
                int numMuelles = Integer.parseInt(parametros[5]);
                PuertoEspacial puertoEspacial = new PuertoEspacial(parametros[0], parametros[1], radio, azimut, polar, numMuelles);
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
            } while (sc.hasNextLine());
        } catch (Exception e) {
            System.out.println("Error de lectura de fichero " + fichero);
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return listaPuertosEspaciales;
    }
}

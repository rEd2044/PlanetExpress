import java.io.PrintWriter;
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
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPuertosEspaciales(int capacidad) {
        this.lista = new PuertoEspacial[capacidad];

    }

    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int ocupacion = 0;
        int i = 0;
        while (i < lista.length) {
            if (lista == null) {
                i = lista.length;
            }
            ocupacion++;
            i++;
        }
        return ocupacion;

    }

    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        int ocupacion = getOcupacion();
        if (ocupacion == lista.length) {
            return true;
        }
        return false;
    }

    // TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     *
     * @param puertoEspacial
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        int posicion = getOcupacion();
        lista[posicion] = puertoEspacial;
        posicion++;
        if (lista[posicion - 1] == puertoEspacial) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO: Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     *
     * @param codigo
     * @return Puerto espacial que encontramos o null si no existe
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        int i = 0;
        while (i < lista.length) {
            if (codigo == getPuertoEspacial(i).getCodigo()) {
                return getPuertoEspacial(i);
            }
            i++;
        }
        return null;
    }

    /**
     * TODO: Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente el código hasta que se introduzca uno correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        PuertoEspacial puertoEspacial = null;
        System.out.println(mensaje);
        String codigo = teclado.nextLine();
        while (puertoEspacial != buscarPuertoEspacial(codigo)) {
            System.out.println(mensaje);
            codigo = teclado.nextLine();
        }
        puertoEspacial = buscarPuertoEspacial(codigo);
        return puertoEspacial;
    }

    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     *
     * @param nombre
     * @return
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw=new PrintWriter(nombre+".csv");
            if(nombre==null){
                for(PuertoEspacial p:lista){
                    pw.println(p);
                }
            }else{
                for(PuertoEspacial p:lista){
                    pw.println(p);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if(pw !=null){
                pw.close();
            }
        }
    }


    /**
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     *
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        Scanner sc = null;
        try {

        } catch (Exception e) {
            return null;
        } finally {

        }
        return listaPuertosEspaciales;
    }
}

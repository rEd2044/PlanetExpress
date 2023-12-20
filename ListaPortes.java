import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaPortes {
    private Porte[] portes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPortes(int capacidad) {
        this.portes = new Porte[capacidad];
    }

    // TODO: Devuelve el número de portes que hay en la lista
    public int getOcupacion() {
        int ocupacion = 0;
        int i = 0;
        while (i < portes.length) {
            if (portes[i]== null) {
                i++;
            }else {
                ocupacion++;
                i++;
            }
        }
        return ocupacion;
    }

    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        int ocupacion = getOcupacion();
        if (ocupacion == portes.length) {
            return true;
        }else{
            return false;
        }

    }

    //TODO: devuelve un porte dado un indice
    public Porte getPorte(int i) {
        return portes[i];
    }


    /**
     * TODO: Devuelve true si puede insertar el porte
     *
     * @param porte
     * @return false en caso de estar llena la lista o de error
     */
    public boolean insertarPorte(Porte porte) {
        if (estaLlena()) {
            return false;
        } else {
            portes[getOcupacion()] = porte;
            return true;
        }
    }


    /**
     * TODO: Devuelve el objeto Porte que tenga el identificador igual al parámetro id
     *
     * @param id
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        int i = 0;
        while (i < portes.length) {
            if (id == getPorte(i).getID()) {
                return getPorte(i);
            }
            i++;
        }
        return null;
    }

    /**
     * TODO: Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     *
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        int i = 0;
        ListaPortes listaPortes=new ListaPortes(i);
        while (i < portes.length) {
            if (getPorte(i).coincide(codigoOrigen, codigoDestino, fecha)) {
                Porte porte =getPorte(i);
                listaPortes.insertarPorte(porte);
                System.out.println("Porte "+porte.toStringSimple());

            }
            i++;
        }
        return listaPortes;
    }

    /**
     * TODO: Muestra por pantalla los Portes siguiendo el formato de los ejemplos del enunciado
     */
    public void listarPortes() {
        for (Porte port : portes) {
            System.out.println(port.toStringSimple());
        }
    }


    /**
     * TODO: Permite seleccionar un Porte existente a partir de su ID, usando el mensaje pasado como argumento para
     *  la solicitud y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para
     *  salir devolviendo null.
     *  La función solicita repetidamente hasta que se introduzca un ID correcto
     *
     * @param teclado
     * @param mensaje
     * @param cancelar
     * @return
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        listarPortes();
        Porte porte = null;
        System.out.println(mensaje);
        String id = teclado.nextLine();
        if (id == cancelar) {
            return null;
        } else {
            int i = 0;
            while (i < portes.length) {
                if (id == getPorte(i).getID()) {
                    porte = buscarPorte(id);
                }
            }
        }
        return porte;
    }

    /**
     * TODO: Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *  Si existe el fichero, SE SOBREESCRIBE, si no existe se crea.
     *
     * @param fichero
     * @return
     */
    public boolean escribirPortesCsv(String fichero) {
        try (PrintWriter salida = new PrintWriter(fichero)) {
            for (int i = 0; i < getOcupacion(); i++) {
                if (portes[i] != null) {
                    Porte por = portes[i];
                    salida.print(por.getID() + ";"+por.getNave().getMatricula()+";" + por.getOrigen().getCodigo() + ";" + por.getMuelleOrigen() + ";" + por.getSalida() + ";" + por.getDestino().getCodigo() + ";" + por.getMuelleDestino() + ";" + por.getLlegada() + ";" + por.getPrecio());
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error de escritura en fichero " + fichero);
            return false;
        }

    }
    //salida.print(por.getID()+";"+por.getNave()+";"+por.getOrigen()+";"+por.getMuelleOrigen()+";"+por.getSalida()+";"
    //                            +por.getDestino()+";"+por.getMuelleDestino()+";"+por.getLlegada()+";"+por.getPrecio());

    /**
     * TODO: Genera una lista de Portes a partir del fichero CSV, usando los límites especificados como argumentos para
     *  la capacidad de la lista
     *
     * @param fichero
     * @param capacidad
     * @param puertosEspaciales
     * @param naves
     * @return
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        ListaPortes listaPortes = new ListaPortes(capacidad);
        Scanner sc = null;
        try   {
            sc = new Scanner(new BufferedReader(new FileReader(fichero)));
            do{
                if(sc.hasNextLine()) {
                    String linea = sc.nextLine();
                    String[] parametros = linea.split(";");
                    Nave nave = naves.buscarNave(parametros[1]);
                    PuertoEspacial origen = puertosEspaciales.buscarPuertoEspacial(parametros[2]);
                    int muelleorigen = Integer.parseInt(parametros[3]);
                    Fecha salida = Fecha.fromString(parametros[4]);
                    PuertoEspacial destino = puertosEspaciales.buscarPuertoEspacial(parametros[5]);
                    int muelledestino = Integer.parseInt(parametros[6]);
                    Fecha llegada = Fecha.fromString(parametros[7]);
                    double precio = Double.parseDouble(parametros[8]);
                    Porte porte = new Porte(parametros[0], nave, origen, muelleorigen, salida, destino, muelledestino, llegada, precio);
                    listaPortes.insertarPorte(porte);
                }

            }while (sc.hasNextLine());
        } catch (Exception ex) {
            System.err.println("Error de lectura del fichero "+fichero);
            return  null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return listaPortes;
    }
}

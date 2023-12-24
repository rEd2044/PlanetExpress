import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Description of the class
 * <p>
 * Marcos Cerrada García
 * Mario Blasco Puente
 * @version 1.0
 */
public class ListaPortes {
    private Porte[] portes;

    /**
     * Constructor de la clase
     *
     * @param capacidad
     */
    public ListaPortes(int capacidad) {
        this.portes = new Porte[capacidad];
    }

    /**
     * Metodo que devuelve el número de portes que hay en la lista
     * @return la ocupacion de la lista
     */
    public int getOcupacion() {
        int ocupacion = 0;
        int i = 0;
        while (i < portes.length) {
            if (portes[i] == null) {
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
        if (ocupacion == portes.length) {
            return true;
        } else {
            return false;
        }

    }

    //Devuelve un porte dado poscicíon
    public Porte getPorte(int i) {
        return portes[i];
    }


    /**
     * Insertamos un porte nuevo en la lista
     *
     * @param porte
     * @return false en caso de estar llena la lista o de error o true si es inserta correctamente
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
     * Metodo que busca un porte a partir del id pasado como parámetro
     *
     * @param id
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        Porte porte=null;
        for(int i=0;i<getOcupacion();i++) {
            if (Objects.equals(id, getPorte(i).getID())) {
               porte=getPorte(i);
            }
        }
        return porte;
    }

    /**
     * Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     *
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return listaPortes o null si no la encontrado
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaPortes listaPortes = null;
            for (int i = 0; i < getOcupacion(); i++) {
                if (portes[i].coincide(codigoOrigen, codigoDestino, fecha)) {
                    Porte porte=portes[i];
                    listaPortes =new ListaPortes(getOcupacion());
                    listaPortes.insertarPorte(porte);
                }

            }

        return listaPortes;
    }

    /**
     * Muestra todos los portes de la lista con su correspondiente formato
     */
    public void listarPortes() {
        for (int i = 0; i < getOcupacion(); i++) {
           if(getPorte(i)!=null) {
               System.out.println(getPorte(i).toStringSimple());
           }
        }
    }


    /**
     * Permite seleccionar un porte existente a partir de su id, usando el mensaje pasado como
     * argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     * Si recibe CANCELAR vuelve al menú
     *
     *
     *
     * @param teclado
     * @param mensaje
     * @param cancelar
     * @return  porte seleccionado o null si le has escrito CANCELAR
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        Porte porte = null;
        boolean aux1 = false;
        String aux;
        do{
            System.out.print(mensaje);
            String id = teclado.nextLine();
            porte=buscarPorte(id);
            aux = id.toUpperCase();
            if (Objects.equals(aux, cancelar)) {
                aux1 = true;
            }
            if(porte==null) {
                System.out.println("\t Porte no encontrado");
            }else{
                aux1= true;
            }
        }while(!aux1);
        if (Objects.equals(aux, cancelar)) {
            return null;
        }else {
            return porte;
        }
    }

    /**
     * Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *
     * @param fichero
     * @return si lo escribe devuelve true si no false
     */
    public boolean escribirPortesCsv(String fichero) {
        try (PrintWriter salida = new PrintWriter(fichero)) {
            if(estaLlena()){
                return true;
            }else {
                for (int i = 0; i < getOcupacion(); i++) {
                    if (portes[i] != null) {
                        Porte por = portes[i];
                        salida.println(por.getID() + ";" + por.getNave().getMatricula() + ";" + por.getOrigen().getCodigo() + ";" + por.getMuelleOrigen() + ";" + por.getSalida() + ";" + por.getDestino().getCodigo() + ";" + por.getMuelleDestino() + ";" + por.getLlegada() + ";" + por.getPrecio());
                    }
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error de escritura en fichero " + fichero);
            return false;
        }

    }

    /**
     * Genera una lista de Portes a partir del fichero CSV, usando  como capacidad la pasado como parametros
     *
     *
     * @param fichero
     * @param capacidad
     * @param puertosEspaciales
     * @param naves
     * @return listaportes
     *
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        ListaPortes listaPortes = new ListaPortes(capacidad);
        BufferedReader leer = null;
        try {
            leer= new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = leer.readLine()) != null){
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


        } catch (IOException ex) {
            System.err.println("Fichero " + fichero + " no encontrado");
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
        return listaPortes;
    }
}

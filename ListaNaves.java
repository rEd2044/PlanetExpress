import javax.imageio.IIOException;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.SimpleTimeZone;

/**
 * Description of the class
 *<p>
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version     1.0
 */
public class ListaNaves {
    private Nave[] naves;

    /**
     * Constructor de la clase
     *
     * @param capacidad
     */
    public ListaNaves(int capacidad) {
        this.naves=new Nave[capacidad];

    }
    /**
     * Metodo que devuelve el número de naves que hay en la lista
     * @return la ocupacion de la lista
     */
    public int getOcupacion() {
        int ocupacion=0;
        int i=0;
        while(i< naves.length){
            if(naves[i] == null){
                i++;
            }else {
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
        int ocupacion=getOcupacion();
        if(ocupacion == naves.length){
            return true;
        }
        return  false;

    }
	// Devuelve nave dado posicion
    public Nave getNave(int posicion) {
        return naves[posicion];
    }

    /**
     * Insertamos una nueva nave en la lista
     * @param nave
     * @return <true en caso de que se añada correctamente, false en caso de lista llena o error>
     */
    public boolean insertarNave(Nave nave) {
        int posicion=getOcupacion();
        naves[posicion]=nave;
        posicion++;
        if(naves[posicion-1]==nave){
            return true;
        }else {
            return false;
        }
    }
    /**
     * Metodo que busca una nave a partir de la matricula pasado como parámetro
     * @param matricula
     * @return la nave que encontramos o null si no existe
     */
    public Nave buscarNave(String matricula) {
        Nave nave=null;
       for(int i=0;i<getOcupacion();i++){
           if(Objects.equals(matricula, getNave(i).getMatricula())){
               nave=getNave(i);
           }
       }
       return nave;
    }
    /**
     * Muestra todos las naves de la lista con su correspondiente formato
     */
    public void mostrarNaves() {
        for (Nave nave : naves) {
            if (nave != null) {
                System.out.println("\t"+ nave);
            }
        }
    }



    /**
     * Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     * @param teclado
     * @param mensaje
     * @param alcance
     * @return nave
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave=null;
       // teclado.nextLine(); ESTA LINEA DA PROBLEMAS AL EJECUTAR LA OPCION 1 DEL MENU
        System.out.print(mensaje);
        String matricula=teclado.nextLine();
        while(buscarNave(matricula)==null){
            System.out.println("\tMatrícula de avión no encontrada");
            System.out.print(mensaje);
            matricula=teclado.nextLine();
            buscarNave(matricula);
        }
        nave=buscarNave(matricula);
        while(alcance > nave.getAlcance()){
            System.out.println("\tNave seleccionada con alcance insuficiente");
            System.out.print(mensaje);
            matricula=teclado.nextLine();
            nave=buscarNave(matricula);
            while(nave==null){
                System.out.println("Matrícula de avión no encontrada");
                System.out.print(mensaje);
                matricula=teclado.nextLine();
                buscarNave(matricula);
            }
        }

        return nave;
    }


    /**
     *Genera un fichero CSV con la lista de Naves correspondiente
     * @param nombre
     * @return si lo escribe devuelve true si no false
     */
    public boolean escribirNavesCsv(String nombre) {
        try (PrintWriter salida = new PrintWriter(nombre)) {
            for (int i = 0; i < getOcupacion(); i++) {
                if (naves[i] != null) {
                    Nave nave = naves[i];
                    salida.println(nave.getMarca()+";"+nave.getModelo()+";"+nave.getMatricula()+";"+nave.getFilas()+";"+nave.getColumnas()+";"+nave.getAlcance());
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error de escritura en fichero " + nombre);
            return false;
        }

    }


    /**
     * Genera una lista de naves a partir del fichero CSV, usando  como capacidad la pasado como parametros
     * @param fichero
     * @param capacidad
     * @return listaNave
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fichero));

            while(sc.hasNextLine()){
                    String linea = sc.nextLine();
                    String[] parametros = linea.split(";");
                    int columna = Integer.parseInt(parametros[3]);
                    int fila = Integer.parseInt(parametros[4]);
                    float alcance = Float.parseFloat(parametros[5]);
                    Nave nave = new Nave(parametros[0], parametros[1], parametros[2], columna, fila, alcance);
                    listaNaves.insertarNave(nave);
                }
        } catch (Exception e) {
            System.out.println("Error de lectura del fichero " + fichero);
            return null;
        }finally {
            if(sc !=null){
                sc.close();
            }
        }
        return listaNaves;
    }
}

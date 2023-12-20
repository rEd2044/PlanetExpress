import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;
import java.util.SimpleTimeZone;

/**
 * Description of the class
 *
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version     1.0
 */
public class ListaNaves {
    private Nave[] naves;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaNaves(int capacidad) {
        this.naves=new Nave[capacidad];

    }
    // TODO: Devuelve el número de naves que hay en la lista
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
    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        int ocupacion=getOcupacion();
        if(ocupacion == naves.length){
            return true;
        }
        return  false;

    }
	// TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion];
    }

    /**
     * TODO: insertamos una nueva nave en la lista
     * @param nave
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
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
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
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
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for (Nave nave : naves) {
            if (nave != null) {
                System.out.println("\t"+nave.toString());
            }
        }
    }



    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     * @param teclado
     * @param mensaje
     * @param alcance
     * @return
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave=null;
        teclado.nextLine();
        System.out.print(mensaje);
        String matricula=teclado.nextLine();
        while(buscarNave(matricula)==null){
            System.out.println("\tMatrícula de avión no encontrada");
            System.out.print(mensaje);
            matricula=teclado.nextLine();
            buscarNave(matricula);
        }
        nave=buscarNave(matricula);
        System.out.println(alcance);
        System.out.println(nave.getAlcance());
        while(alcance > nave.getAlcance()){
            System.out.println("\tNave seleccionada con alcance insuficiente");
            System.out.print(mensaje);
            matricula=teclado.nextLine();
            while(buscarNave(matricula)==null){
                System.out.println("Matrícula de avión no encontrada");
                System.out.print(mensaje);
                matricula=teclado.nextLine();
                buscarNave(matricula);
            }
        }

        return nave;
    }


    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     * @param nombre
     * @return
     */
    public boolean escribirNavesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw=new PrintWriter(nombre);
            if(nombre==null){
                for(Nave nav:naves){
                    pw.println(nav);
                }
            }else{
                for(Nave nav:naves){
                    pw.println(nav);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero "+nombre);
            return false;
        } finally {
            if(pw !=null){
                pw.close();
            }
        }
    }


    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        try (Scanner sc = new Scanner(new FileReader(fichero))) {
            do{
                if(sc.hasNextLine()) {
                    String linea = sc.nextLine();
                    String[] parametros = linea.split(";");
                    int columna = Integer.parseInt(parametros[3]);
                    int fila = Integer.parseInt(parametros[4]);
                    float alcance = Float.parseFloat(parametros[5]);
                    Nave nave = new Nave(parametros[0], parametros[1], parametros[2], columna, fila, alcance);
                    listaNaves.insertarNave(nave);
                }
            }while(sc.hasNextLine());
        } catch (Exception e) {
            System.out.println("Error de lectura del fichero " + fichero);
            return null;
        }
        return listaNaves;
    }
}

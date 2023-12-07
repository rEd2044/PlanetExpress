import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.Manifest;

/**
 * Description of the class
 *
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version     1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;

    /**
     * TODO: Completa el constructo de la clase
     * 
     * @param id
     * @param nave
     * @param origen
     * @param muelleOrigen
     * @param salida
     * @param destino
     * @param muelleDestino
     * @param llegada
     * @param precio
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida,
                 PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id=id;
        this.nave=nave;
        this.origen=origen;
        this.muelleOrigen=muelleOrigen;
        this.salida=salida;
        this.destino=destino;
        this.muelleDestino=muelleDestino;
        this.llegada=llegada;
        this.precio=precio;
    }
    public String getID() {
        return id;
    }
    public Nave getNave(){
        return nave;
    }
    public PuertoEspacial getOrigen() {
        return origen;
    }
    public int getMuelleOrigen() {
        return muelleOrigen;
    }
    public Fecha getSalida(){
        return salida;
    }
    public PuertoEspacial getDestino() {
        return destino;
    }
    public int getMuelleDestino() {
        return muelleDestino;
    }
    public Fecha getLlegada() {
        return llegada;
    }
    public double getPrecio() {
        return precio;
    }
    // TODO: Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int contador=0;
        for (boolean[] hueco : huecos) {
            for (int columnas = 0; columnas < huecos.length; columnas++) {
                if (hueco[columnas]) {
                    contador++;
                }
            }
        }
        return contador;
    }
    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        if(numHuecosLibres()==0){
            return true;
        }
        return false;
    }
    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {
        if(huecos[fila][columna]){
            return true;
        }
        return false;
    }
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }


    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     * @param fila
     * @param columna
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {
         if(huecos[fila][columna]){
             return listaEnvios.buscarEnvio(id,fila,columna);
         }
        return null;
    }


    /**
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     * @param envio
     * @return
     */
    public boolean ocuparHueco(Envio envio) {
       int fila= envio.getFila();
       int columna= envio.getColumna();
       return !huecoOcupado(fila,columna);
    }


    /**
     * TODO: A través del localizador del envio, se desocupará el hueco
     * @param localizador
     * @return
     */
    public boolean desocuparHueco(String localizador) {
        Envio envio=listaEnvios.buscarEnvio(localizador);
        int fila= envio.getFila(),
        columna=envio.getColumna();
        return huecoOcupado(fila,columna);
    }

    /**
     * TODO: Devuelve una cadena con información completa del porte
     * @return ejemplo del formato -> "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     *  Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {
        return "Porte "+id+"de "+origen+" "+muelleOrigen+" ("+salida+") a "+destino+" "+muelleOrigen+" ("+llegada+") en"+nave.getMarca()+"("
                +nave.getModelo()+nave.getMatricula()+" por "+precio+" SSD, huecos libres: "+numHuecosLibres();
    }


    /**
     * TODO: Devuelve una cadena con información abreviada del vuelo
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte"+id+"de "+origen+"("+salida+") a"+destino+"("+llegada+")";
    }


    /**
     * TODO: Devuelve true si el código origen, destino y fecha son los mismos que el porte
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        if(origen.getCodigo().equals(codigoOrigen) && destino.getCodigo().equals(codigoDestino) && fecha.coincide(llegada)){
            return true;
        }else {
            return false;
        }
    }


    /**
     * TODO: Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        for(int i=0;i<huecos.length;i++){
            for(int j=0;j<3;j++){
                System.out.print(huecos[i][j]);
            }
        }
        System.out.print(" ");
    }

    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     * @param fichero
     * @return
     */
    public boolean generarListaEnvios(String fichero){
        try (PrintWriter salida = new PrintWriter(fichero + "csv")) {
            ListaEnvios[] envios1=new ListaEnvios[listaEnvios.getOcupacion()];
            for (int i=0; i<listaEnvios.getOcupacion() ;i++){
                salida.print(envios1[i]);
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }


    /**
     * TODO: Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     *  serán PM y los 4 siguientes serán números aleatorios.
     *  NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        return "PM"+rand;
    }

    /**
     * TODO: Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     *  y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     *  del enunciado.
     *  La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado
     * @param rand
     * @param puertosEspaciales
     * @param naves
     * @param portes
     * @return
     */
    public static Porte altaPorte(Scanner teclado, Random rand, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves, ListaPortes portes) {
        String id=generarID(rand);
        System.out.print("Ingrese código de puerto Origen: ");
        String codigorigen = teclado.nextLine();
        PuertoEspacial puertoEspacial = puertosEspaciales.buscarPuertoEspacial(codigorigen);
        while(puertoEspacial!=null){
            System.out.print("Ingrese código de puerto Origen: ");
             codigorigen = teclado.nextLine();
             puertoEspacial = puertosEspaciales.buscarPuertoEspacial(codigorigen);
        }
        System.out.print("Ingrese el muelle de Origen (1 - 4): ");
        int muelleOrigen=teclado.nextInt();
        while(muelleOrigen>4 ||muelleOrigen<1){
            System.out.print("Ingrese el muelle de Origen (1 - 4): ");
            muelleOrigen=teclado.nextInt();
        }
        System.out.print("Ingrese código de puerto Destino: ");
        String codigoDestino = teclado.nextLine();
        PuertoEspacial puertoEspacial1=puertosEspaciales.buscarPuertoEspacial(codigoDestino);
        while(puertoEspacial1 !=null){
            System.out.print("Ingrese código de puerto Destino: ");
            codigoDestino = teclado.nextLine();
            puertoEspacial1=puertosEspaciales.buscarPuertoEspacial(codigoDestino);
        }
        System.out.print("Ingrese código de puerto Destino: ");
        int muelleDestino=teclado.nextInt();
        while(muelleDestino>6 || muelleDestino<1){
            System.out.print("Ingrese código de puerto Destino: ");
            muelleDestino=teclado.nextInt();
        }
        System.out.print("Ingrese matrícula de la nave: ");
        String matricula= teclado.nextLine();
        while(naves.buscarNave(matricula)==null){
            System.out.print("Matrícula de nave  no encontrada");
            System.out.print("Ingrese matrícula de la nave: ");
            matricula= teclado.nextLine();
        }
        Nave nave1=naves.buscarNave(matricula);
        double alcance= nave1.getAlcance();
        double distancia=puertoEspacial.distancia(puertoEspacial1);
        while(distancia>alcance){
            System.out.print("Nave seleccionado con alcance insuficiente" );
            System.out.print("Ingrese matrícula de la nave: ");
            matricula= teclado.nextLine();
        }
        System.out.println("Introduzca la fecha de salida: ");
        System.out.print("Ingrese día: ");
        int dia= teclado.nextInt();
        while (dia<=0 ||dia>=31){
            System.out.print("Ingrese día: ");
            dia= teclado.nextInt();
        }
        System.out.print("Ingrese mes: ");
        int mes= teclado.nextInt();
        while (mes<=0 || mes>=13){
            System.out.print("Ingrese mes: ");
            mes= teclado.nextInt();
        }
        System.out.print("Ingrese año: ");
        int ano= teclado.nextInt();
        while (ano<=0 ||ano>=2024){
            System.out.print("Ingrese año: ");
            ano= teclado.nextInt();
        }
        System.out.println("Ingrese hora: ");
        int hora= teclado.nextInt();
        while (hora<0 ||hora>=24){
            System.out.println("Ingrese hora: ");
            hora= teclado.nextInt();
        }
        System.out.print("Ingrese minuto: ");
        int minuto= teclado.nextInt();
        while (minuto<0 || minuto>=60){
            System.out.print("Ingrese minuto: ");
            minuto= teclado.nextInt();
        }
        System.out.print("Ingrese segundo: ");
        int segundo= teclado.nextInt();
        while (segundo<0 ||segundo>=60){
            System.out.print("Ingrese segundo: ");
             segundo= teclado.nextInt();
        }
        while(!Fecha.comprobarFecha(dia, mes, ano)){
            System.out.print("Ingrese día: ");
            dia= teclado.nextInt();
            while (dia<=0 ||dia>=31){
                System.out.print("Ingrese día: ");
                dia= teclado.nextInt();
            }
            System.out.print("Ingrese mes: ");
            mes= teclado.nextInt();
            while (mes<=0 ||mes >=13){
                System.out.print("Ingrese mes: ");
                mes= teclado.nextInt();
            }
            System.out.print("Ingrese año: ");
            ano= teclado.nextInt();
            while (ano<=0 ||ano>=2024){
                System.out.print("Ingrese año: ");
                ano= teclado.nextInt();
            }
        }
        System.out.println("Introduzca la fecha de llegada: ");
        System.out.print("Ingrese día: ");
        int diaL= teclado.nextInt();
        while (diaL<=0 ||diaL>=31){
            System.out.print("Ingrese día: ");
            diaL= teclado.nextInt();
        }
        System.out.print("Ingrese mes: ");
        int mesL= teclado.nextInt();
        while (mesL<=0 ||mesL>=13){
            System.out.print("Ingrese mes: ");
            mesL= teclado.nextInt();
        }
        System.out.print("Ingrese año: ");
        int anoL= teclado.nextInt();
        while (anoL<=0 ||anoL>=2024){
            System.out.print("Ingrese año: ");
            anoL= teclado.nextInt();
        }
        System.out.print("Ingrese hora: ");
        int horaL= teclado.nextInt();
        while (horaL<0 ||horaL>=24){
            System.out.print("Ingrese hora: ");
            horaL= teclado.nextInt();
        }
        System.out.print("Ingrese minuto: ");
        int minutoL= teclado.nextInt();
        while (minutoL<0 || minutoL>=60){
            System.out.print("Ingrese minuto: ");
            minutoL= teclado.nextInt();
        }
        System.out.print("Ingrese segundo: ");
        int segundoL= teclado.nextInt();
        while (segundoL<0 ||segundoL>=60){
            System.out.print("Ingrese segundo: ");
            segundoL= teclado.nextInt();
        }
        while(!Fecha.comprobarFecha(diaL, mesL, anoL)){
            System.out.print("Ingrese día: ");
            diaL= teclado.nextInt();
            while (diaL<=0 ||diaL>=31){
                System.out.print("Ingrese día: ");
                diaL= teclado.nextInt();
            }
            System.out.print("Ingrese mes: ");
            mesL= teclado.nextInt();
            while (mesL<=0 || mesL>=13){
                System.out.print("Ingrese mes: ");
                mesL= teclado.nextInt();
            }
            System.out.print("Ingrese año: ");
            anoL= teclado.nextInt();
            while (anoL<=0 ||anoL>=2024){
                System.out.print("Ingrese año: ");
                anoL= teclado.nextInt();
            }
        }
        Fecha fechasalida=new Fecha(dia,mes,ano,hora,minuto,segundo);
        Fecha fechallegada=new Fecha(diaL,mesL,anoL,horaL,minutoL,segundoL);
        while(!fechallegada.anterior(fechasalida)){
            System.out.println("Introduzca la fecha de salida: ");
            System.out.print("Ingrese día: ");
            dia= teclado.nextInt();
            while (dia<=0 ||dia>=31){
                System.out.print("Ingrese día: ");
                dia= teclado.nextInt();
            }
            System.out.print("Ingrese mes: ");
            mes= teclado.nextInt();
            while (mes<=0 || mes>=13){
                System.out.print("Ingrese mes: ");
                mes= teclado.nextInt();
            }
            System.out.print("Ingrese año: ");
            ano= teclado.nextInt();
            while (ano<=0 ||ano>=2024){
                System.out.print("Ingrese año: ");
                ano= teclado.nextInt();
            }
            System.out.print("Ingrese hora: ");
            hora= teclado.nextInt();
            while (hora<0 ||hora>=24){
                System.out.print("Ingrese hora: ");
                hora= teclado.nextInt();
            }
            System.out.print("Ingrese minuto: ");
            minuto= teclado.nextInt();
            while (minuto<0 || minuto>=60){
                System.out.print("Ingrese minuto: ");
                minuto= teclado.nextInt();
            }
            System.out.print("Ingrese segundo: ");
             segundo= teclado.nextInt();
            while (segundo<0 ||segundo>=60){
                System.out.print("Ingrese segundo: ");
                segundo= teclado.nextInt();
            }
            while(!Fecha.comprobarFecha(dia, mes, ano)){
                System.out.print("Ingrese día: ");
                dia= teclado.nextInt();
                while (dia<=0 ||dia>=31){
                    System.out.print("Ingrese día: ");
                    dia= teclado.nextInt();
                }
                System.out.print("Ingrese mes: ");
                mes= teclado.nextInt();
                while (mes<=0 ||mes >=13){
                    System.out.print("Ingrese mes: ");
                    mes= teclado.nextInt();
                }
                System.out.print("Ingrese año: ");
                ano= teclado.nextInt();
                while (ano<=0 ||ano>=2024){
                    System.out.print("Ingrese año: ");
                    ano= teclado.nextInt();
                }
            }
            System.out.println("Introduzca la fecha de llegada: ");
            System.out.print("Ingrese día: ");
            diaL= teclado.nextInt();
            while (diaL<=0 ||diaL>=31){
                System.out.print("Ingrese día: ");
                diaL= teclado.nextInt();
            }
            System.out.print("Ingrese mes: ");
            mesL= teclado.nextInt();
            while (mesL<=0 ||mesL>=13){
                System.out.print("Ingrese mes: ");
                mesL= teclado.nextInt();
            }
            System.out.print("Ingrese año: ");
            anoL= teclado.nextInt();
            while (anoL<=0 ||anoL>=2024){
                System.out.print("Ingrese año: ");
                anoL= teclado.nextInt();
            }
            System.out.print("Ingrese hora: ");
            horaL= teclado.nextInt();
            while (horaL<0 ||horaL>=24){
                System.out.print("Ingrese hora: ");
                horaL= teclado.nextInt();
            }
            System.out.print("Ingrese minuto: ");
            minutoL= teclado.nextInt();
            while (minutoL<0 || minutoL>=60){
                System.out.print("Ingrese minuto: ");
                minutoL= teclado.nextInt();
            }
            System.out.print("Ingrese segundo: ");
            segundoL= teclado.nextInt();
            while (segundoL<0 ||segundoL>=60){
                System.out.print("Ingrese segundo: ");
                segundoL= teclado.nextInt();
            }
            while(!Fecha.comprobarFecha(diaL, mesL, anoL)){
                System.out.print("Ingrese día: ");
                diaL= teclado.nextInt();
                while (diaL<=0 ||diaL>=31){
                    System.out.print("Ingrese día: ");
                    diaL= teclado.nextInt();
                }
                System.out.print("Ingrese mes: ");
                mesL= teclado.nextInt();
                while (mesL<=0 || mesL>=13){
                    System.out.print("Ingrese mes: ");
                    mesL= teclado.nextInt();
                }
                System.out.print("Ingrese año: ");
                anoL= teclado.nextInt();
                while (anoL<=0 ||anoL>=2024){
                    System.out.print("Ingrese año: ");
                    anoL= teclado.nextInt();
                }
            }
            fechasalida=new Fecha(dia,mes,ano,hora,minuto,segundo);
            fechallegada=new Fecha(diaL,mesL,anoL,horaL,minutoL,segundoL);
        }
        System.out.print("Ingrese precio de pasaje: ");
        double precio=teclado.nextDouble();
        while(precio<=0.0){
            System.out.print("Ingrese precio de pasaje: ");
            precio=teclado.nextDouble();
        }
        Porte porte=new Porte(id,nave1,puertoEspacial,muelleOrigen,fechasalida,puertoEspacial1,muelleDestino,fechallegada,precio);
        portes.insertarPorte(porte);
        return porte;
    }
}
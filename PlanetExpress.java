import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de Planet Express App, la práctica de Taller de Programación
 *
 * @author      Taller de Progamación
 * @version     1.0
 */
public class PlanetExpress {
    private final int maxPortes;
    private final int maxNaves;
    private final int maxClientes;
    private final int maxEnviosPorCliente;
    private ListaPuertosEspaciales listaPuertosEspaciales;
    private final int maxPuertosEspaciales;
    private ListaNaves listaNaves;
    private ListaClientes listaClientes;
    private ListaPortes listaPortes;


    /**
     * TODO: Rellene el constructor de la clase
     *
     * @param maxPuertosEspaciales Máximo número de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * @param maxNaves Máximo número de naves que tendrá la lista de naves de PlanetExpress App.
     * @param maxPortes Máximo número de portes que tendrá la lista de portes de PlanetExpress App.
     * @param maxClientes Máximo número de clientes que tendrá la lista de clientes de PlanetExpress App.
     * @param maxEnviosPorCliente Máximo número de envíos por cliente.
     */
    public PlanetExpress(int maxPuertosEspaciales, int maxNaves, int maxPortes, int maxClientes, int maxEnviosPorCliente) {
        this.maxPuertosEspaciales=maxPuertosEspaciales;
        this.maxNaves=maxNaves;
        this.maxPortes=maxPortes;
        this.maxClientes=maxClientes;
        this.maxEnviosPorCliente=maxEnviosPorCliente;
        this.listaPuertosEspaciales=new ListaPuertosEspaciales(maxPuertosEspaciales);
        this.listaNaves=new ListaNaves(maxNaves);
        this.listaClientes=new ListaClientes(maxClientes);
        this.listaPortes=new ListaPortes(maxPortes);
    }
    /**
     * TODO: Metodo para leer los datos de los ficheros específicados en el enunciado y los agrega a
     *  la información de PlanetExpress (listaPuertosEspaciales, listaNaves, listaPortes, listaClientes)
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        ListaPuertosEspaciales puertosEspaciales=ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos,maxPuertosEspaciales);
        ListaNaves naves=ListaNaves.leerNavesCsv(ficheroNaves,maxNaves);
        ListaPortes portes=ListaPortes.leerPortesCsv(ficheroPortes,maxPortes,puertosEspaciales,naves);
        ListaClientes clientes=ListaClientes.leerClientesCsv(ficheroClientes,maxClientes,maxEnviosPorCliente);
        ListaEnvios.leerEnviosCsv(ficheroEnvios,portes,clientes);



    }


    /**
     * TODO: Metodo para almacenar los datos de PlanetExpress en los ficheros .csv especificados
     *  en el enunciado de la práctica
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {



    }
    public boolean maxPortesAlcanzado() {
        return listaPortes.estaLlena();
    }
    public boolean insertarPorte (Porte porte) {
        return listaPortes.insertarPorte(porte);
    }
    public boolean maxClientesAlcanzado() {
        return listaClientes.estaLlena();
    }
    public boolean insertarCliente (Cliente cliente) {
        return listaClientes.insertarCliente(cliente);
    }

    /**
     * TODO: Metodo para buscar los portes especificados tal y como aparece en el enunciado de la práctica,
     *  Devuelve una lista de los portes entre dos puertos espaciales con una fecha de salida solicitados por teclado
     *  al usuario en el orden y con los textos establecidos (tomar como referencia los ejemplos de ejecución en el
     *  enunciado de la prática)
     * @param teclado
     * @return
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        PuertoEspacial puertoEspacials=listaPuertosEspaciales.seleccionarPuertoEspacial(teclado,"Ingrese un código de puerto Origen: ");
        String codigoOrigen=puertoEspacials.getCodigo();
        PuertoEspacial puertoEspaciall=listaPuertosEspaciales.seleccionarPuertoEspacial(teclado,"Ingrese un código de puerto Destino: ");
        String codigoDestino=puertoEspaciall.getCodigo();
        Fecha fecha=Utilidades.leerFecha(teclado,"Ingrese la fecha de salida: ");
        return listaPortes.buscarPortes(codigoOrigen, codigoDestino, fecha);
    }


    /**
     * TODO: Metodo para contratar un envio tal y como se indica en el enunciado de la práctica. Se contrata un envio para un porte
     *  especificado, pidiendo por teclado los datos necesarios al usuario en el orden y con los textos (tomar como referencia los
     *  ejemplos de ejecución en el enunciado de la prática)
     * @param teclado
     * @param rand
     * @param porte
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        if (porte != null) {
            char sino=Utilidades.leerLetra(teclado,"¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)? ",'a','z');
            while(sino!='e' || sino!='n'){
                System.out.println("El valor de entrada debe ser 'n' o 'e'");
                sino =Utilidades.leerLetra(teclado,"¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)? ",'a','z');
            }
            if(sino=='e'){
                Cliente cliente=listaClientes.seleccionarCliente(teclado,"Email del cliente: ");
                Envio envio=Envio.altaEnvio(teclado,rand,porte,cliente);
                System.out.println("Envío "+envio.getLocalizador()+" creado correctamente");
            }else {
                Cliente cliente=Cliente.altaCliente(teclado,listaClientes,maxEnviosPorCliente);
                listaClientes.insertarCliente(cliente);
                Envio envio=Envio.altaEnvio(teclado,rand,porte,cliente);
                System.out.println("Envío "+envio.getLocalizador()+" creado correctamente");
            }
        }

    }


    /**
     * TODO Metodo statico con la interfaz del menú de entrada a la App.
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     * @param teclado
     * @return opción seleccionada
     */
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta de Porte\n" +
                "2. Alta de Cliente\n" +
                "3. Buscar Porte\n" +
                "4. Mostrar envíos de un cliente\n" +
                "5. Generar lista de envíos\n" +
                "0. Salir");
        return teclado.nextInt();

    }

    /**
     * TODO: Método Main que carga los datos de los ficheros CSV pasados por argumento (consola) en PlanetExpress,
     *  llama iterativamente al menú y realiza la opción especificada hasta que se indique la opción Salir. Al finalizar
     *  guarda los datos de PlanetExpress en los mismos ficheros CSV.
     * @param args argumentos de la línea de comandos, recibe **10 argumentos** estrictamente en el siguiente orden:
     * 1. Número máximo de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * 2. Número máximo de naves que tendrá la lista de naves de PlanetExpress App.
     * 3. Número máximo de portes que tendrá la lita de portes de PlanetExpress App.
     * 4. Número máximo de clientes que tendrá la lista de clientes de PlanetExpress App.
     * 5. Número máximo de envíos por pasajero.
     * 6. Nombre del fichero CSV que contiene la lista de puertos espaciales de PlanetExpress App (tanto para lectura como escritura).
     * 7. Nombre del fichero CSV que contiene la lista de naves de PlanetExpress App (tanto para lectura como escritura).
     * 8. Nombre del fichero CSV que contiene la lista de portes de PlanetExpress App (tanto para lectura como escritura).
     * 9. Nombre del fichero CSV que contiene la lista de clientes de PlanetExpress App (tanto para lectura como escritura).
     * 10. Nombre del fichero CSV que contiene los envíos adquiridos en PlanetExpress App (tanto para lectura como escritura).
     * En el caso de que no se reciban exactamente estos argumentos, el programa mostrará el siguiente mensaje
     * y concluirá la ejecución del mismo: `Número de argumentos incorrecto`.
     */
    public static void main(String[] args) {
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto");
            return;
        }
        Scanner teclado=new Scanner(System.in);
        Random rand =new Random();
        int opcion;
        ListaPuertosEspaciales listaPuertosEspaciales=new ListaPuertosEspaciales(Integer.parseInt(args[0]));
        ListaNaves listaNaves=new ListaNaves(Integer.parseInt(args[1]));
        ListaPortes listaPortes=new ListaPortes(Integer.parseInt(args[2]));
        ListaClientes listaClientes=new ListaClientes(Integer.parseInt(args[3]));
        ListaEnvios listaEnvios= new ListaEnvios(Integer.parseInt(args[4]));
        PlanetExpress planetExpress=new PlanetExpress(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        System.out.println("Seleccione una opción ");
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1:     // TODO: Alta de Porte
                    boolean llenaPortes=listaPortes.estaLlena();
                    if(llenaPortes){
                        System.out.println("No se pueden dar de alta más vuelos");
                    }else{
                        Porte porte=Porte.altaPorte(teclado,rand,listaPuertosEspaciales,listaNaves,listaPortes);
                    }
                    break;
                case 2:     // TODO: Alta de Cliente
                    boolean llenaClientes=listaClientes.estaLlena();
                    if(llenaClientes){
                        System.out.println("No se ha podido dar de alta más clientes ");
                    }else {
                        Cliente cliente = Cliente.altaCliente(teclado, listaClientes, Integer.parseInt(args[4]));
                        System.out.println("Cliente con email "+cliente.getEmail()+" creado correctamente");
                    }
                    break;
                case 3:     // TODO: Buscar Porte
                    planetExpress.buscarPorte(teclado);
                    Porte porte=listaPortes.seleccionarPorte(teclado,"Seleccione un porte: ","CANCELAR");
                    while(porte==null){
                        porte=listaPortes.seleccionarPorte(teclado,"Seleccione un porte: ","CANCELAR");
                    }
                    planetExpress.contratarEnvio(teclado,rand,porte);
                    break;
                case 4:     // TODO: Listado de envíos de un cliente
                    Cliente cliente=listaClientes.seleccionarCliente(teclado,"Email del cliente: ");
                    Envio envio=listaEnvios.seleccionarEnvio(teclado,"Seleccione un envío: ");
                    char sino=Utilidades.leerLetra(teclado,"¿Cancelar envío (c), o generar factura (f)?",'a','z');
                    while(sino!='c' || sino !='f') {
                        System.out.println("El valor de entrada debe ser 'c' o 'f'");
                        sino = Utilidades.leerLetra(teclado, "¿Cancelar envío (c), o generar factura (f)?", 'a', 'z');
                    }if(sino =='f'){
                        System.out.println("Nombre del fichero: ");
                        String nomFactura=teclado.nextLine();
                        envio.generarFactura(nomFactura);
                    }else{
                        if(envio.cancelar()){
                            System.out.println("El envió se cancelado correctamente");
                        }else{
                            System.out.println("El envio no se ha podido cancelar");
                        }
                    }
                    break;
                case 5:     // TODO: Lista de envíos de un porte
                    if(listaPortes.getOcupacion()==0){
                        System.out.println("Lista esta vacía");
                    }else {
                        listaPortes.listarPortes();
                        porte = listaPortes.seleccionarPorte(teclado, "Seleccione un porte", "CANCELAR");
                        System.out.println("Nombre del fichero: ");
                        String nomFich = teclado.nextLine();
                        porte.generarListaEnvios(nomFich);
                    }
                    break;
            }
        } while (opcion != 0);


    }
}

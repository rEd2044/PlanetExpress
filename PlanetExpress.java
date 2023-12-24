import java.io.*;
import java.util.Arrays;
import java.util.List;
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
     * Constructor de la clase inicializa los atributos necesarios
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
     * Metodo para leer los datos de los ficheros.csv y agrega la información a Planet express.
     *
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        //No se si esta bien pero entiendo que hay que hacer esto
        listaPuertosEspaciales=ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos,maxPuertosEspaciales);
        listaNaves=ListaNaves.leerNavesCsv(ficheroNaves,maxNaves);
        listaPortes=ListaPortes.leerPortesCsv(ficheroPortes,maxPortes,listaPuertosEspaciales,listaNaves);
        listaClientes=ListaClientes.leerClientesCsv(ficheroClientes,maxClientes,maxEnviosPorCliente);
        ListaEnvios.leerEnviosCsv(ficheroEnvios,listaPortes,listaClientes);
        PlanetExpress planetExpress=new PlanetExpress(maxPuertosEspaciales,maxNaves,maxPortes,maxClientes,maxEnviosPorCliente);
    }


    /**
     * Metodo para almacenar los datos de PlanetExpress en los ficheros.csv
     *
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales.escribirPuertosEspacialesCsv(ficheroPuertos);
        listaNaves.escribirNavesCsv(ficheroNaves);
        listaClientes.escribirClientesCsv(ficheroClientes);
        listaPortes.escribirPortesCsv(ficheroPortes);
        ListaEnvios listaTodosEnvios = new ListaEnvios(maxEnviosPorCliente*listaClientes.getOcupacion());
        for(int i=0;i<listaClientes.getOcupacion();i++){
            ListaEnvios listaEnvios = listaClientes.getCliente(i).getListaEnvios();
            for (int j=0;j<listaEnvios.getOcupacion();j++){
                if(listaEnvios.getEnvio(j)!=null) {
                    listaTodosEnvios.insertarEnvio(listaEnvios.getEnvio(j));
                }
            }
        }
        listaTodosEnvios.aniadirEnviosCsv(ficheroEnvios);
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
     * Metodo para buscar los portes especificados.Devuelve una lista de los portes entre dos puertos espaciales
     * con una fecha de salida solicitados por teclado al usuario
     *
     * @param teclado
     * @return listaporte donde esta los portes encontrados con esas especificaciones puede ser uno o dos.
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        ListaPortes listaPortes1 = null;
        do {
            teclado=new Scanner(System.in);
            String codigoOrigen = listaPuertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese un código de puerto Origen: ").getCodigo();
            String codigoDestino = listaPuertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese un código de puerto Destino: ").getCodigo();
            Fecha fecha = Utilidades.leerFecha(teclado, "Ingrese la fecha de salida: ");
            int dia = fecha.getDia(), mes = fecha.getMes(), anio = fecha.getAnio();
            while (!Fecha.comprobarFecha(dia, mes, anio)) {
                System.out.println("\tFecha introducida incorrecta");
                fecha = Utilidades.leerFecha(teclado, "Ingrese la fecha de salida: ");
                dia = fecha.getDia();
                mes = fecha.getMes();
                anio = fecha.getAnio();

            }
            listaPortes1 = listaPortes.buscarPortes(codigoOrigen, codigoDestino, fecha);
            if(listaPortes1==null) {
                System.out.println("El porte no se ha encontrado");
            }
        }while (listaPortes1==null);
        return listaPortes1;

    }


    /**
     * Metodo para contratar un envio. Se contrata un envio para un porte especificado, pidiendo por teclado los datos neceserarios
     * al usuario.
     *
     * @param teclado
     * @param rand
     * @param porte
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        if (porte != null) {
            char sino=Utilidades.leerLetra(teclado,"¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)? ",'e','n');
            if(sino=='e'){
                if(listaClientes.getOcupacion()==0){
                    System.out.println("No hay clientes");
                }else {
                    Cliente cliente = listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
                    Envio envio = Envio.altaEnvio(teclado, rand, porte, cliente);
                    cliente.aniadirEnvio(envio);
                    porte.aniadirEnvio(envio);
                    porte.ocuparHueco(envio);
                    System.out.println("\tEnvío " + envio.getLocalizador() + " creado correctamente");
                }
            }else {
                teclado=new Scanner(System.in);
                Cliente cliente=Cliente.altaCliente(teclado,listaClientes,maxEnviosPorCliente);
                listaClientes.insertarCliente(cliente);
                Envio envio=Envio.altaEnvio(teclado,rand,porte,cliente);
                cliente.aniadirEnvio(envio);
                porte.aniadirEnvio(envio);
                porte.ocuparHueco(envio);

                System.out.println("\tEnvío "+envio.getLocalizador()+" creado correctamente");
            }
        }

    }


    /**
     *  Metodo statico con la interfaz del menú de entrada a la APP
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     * @param teclado
     * @return opción seleccionada
     */
    public static int menu(Scanner teclado) {
        System.out.println("""
                1. Alta de Porte
                2. Alta de Cliente
                3. Buscar Porte
                4. Mostrar envíos de un cliente
                5. Generar lista de envíos
                0. Salir""");
        return Utilidades.leerNumero(teclado,"Seleccione una opción: ",0,5);
    }

    /**
     * Método Main que carga los datos del fichero CSV pasados por argumento, llama iterativamente al
     * menú y realiza la opción es cogida hasta que le des la opción de Salir. Al finalizar
     * guarda los datos en los ficheros .csv pasados como argumento
     *
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
        PlanetExpress planetExpress = new PlanetExpress(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        planetExpress.cargarDatos(args[5],args[6],args[7],args[8],args[9]);
        Scanner teclado=new Scanner(System.in);
        Random rand =new Random();
        int opcion;
        ListaPuertosEspaciales listaPuertosEspaciales=planetExpress.listaPuertosEspaciales;
        ListaNaves listaNaves=planetExpress.listaNaves;
        ListaPortes listaPortes=planetExpress.listaPortes;
        ListaClientes listaClientes=planetExpress.listaClientes;
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1:     // TODO: Alta de Porte
                    if(planetExpress.maxPortesAlcanzado()){
                        System.out.println("No se pueden dar de alta más vuelos");
                    }else{
                        teclado.nextLine();
                        Porte porte=Porte.altaPorte(teclado,rand,listaPuertosEspaciales,listaNaves,listaPortes);
                    }
                    break;
                case 2:     // TODO: Alta de Cliente
                    if(planetExpress.maxClientesAlcanzado()){
                        System.out.println("No se ha podido dar de alta más clientes ");
                    }else {
                        teclado.nextLine();
                        Cliente cliente = Cliente.altaCliente(teclado, listaClientes, Integer.parseInt(args[4]));
                        planetExpress.insertarCliente(cliente);
                    }
                    break;
                case 3:     // TODO: Buscar Porte
                    if(listaPortes.getOcupacion()==0){
                        System.out.println("\tLa lista esta vacía no se puede buscar");
                    }else {
                        teclado = new Scanner(System.in);
                        ListaPortes portes=planetExpress.buscarPorte(teclado);
                        portes.listarPortes();
                        System.out.println( );
                        Porte porte = listaPortes.seleccionarPorte(teclado, "Seleccione un porte: ", "CANCELAR");
                        if(porte !=null){
                            planetExpress.contratarEnvio(teclado,rand,porte);
                        }
                    }
                    break;
                case 4:     // TODO: Listado de envíos de un cliente
                    if(planetExpress.listaClientes.getOcupacion()==0){
                        System.out.println("\tLa lista esta vacía no se puede buscar");
                        break;
                    }
                    Cliente cliente = listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
                    if(cliente.getListaEnvios().getOcupacion()==0){
                        System.out.println("No hay envios Disponibles");
                    }else {
                        teclado= new Scanner(System.in);
                        Envio envio = cliente.getListaEnvios().seleccionarEnvio(teclado, "Seleccione un envío: ");
                        char sino = Utilidades.leerLetra(teclado, "¿Cancelar envío (c), o generar factura (f)? ", 'c', 'f');
                        if (sino == 'f') {
                            System.out.print("Nombre del fichero: ");
                            String nomFactura = teclado.nextLine();
                            envio.generarFactura(nomFactura);
                        } else {
                            if (envio.cancelar()) {
                                System.out.println("El envió se cancelado correctamente");
                            } else {
                                System.out.println("El envio no se ha podido cancelar");
                            }
                        }
                    }
                    break;
                case 5:     // TODO: Lista de envíos de un porte
                    if(listaPortes.getOcupacion()==0){
                        System.out.println("Lista esta vacía");
                    }else {
                        listaPortes.listarPortes();
                        teclado=new Scanner(System.in);
                        Porte porte = listaPortes.seleccionarPorte(teclado, "Seleccione un porte:", "CANCELAR");
                        if(porte !=null){
                            System.out.print("Nombre del fichero: ");
                            String nomFich = teclado.nextLine();
                            porte.generarListaEnvios(nomFich);
                        }
                    }
                    break;
            }
            planetExpress.guardarDatos(args[5],args[6],args[7],args[8],args[9]);
        } while (opcion != 0);

    }
}

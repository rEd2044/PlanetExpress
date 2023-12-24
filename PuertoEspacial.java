/**
 * Description of the class
 *<p>
 * @author Marcos Cerrada García
 * @author Mario Blasco Puente
 * @version     1.0
 */
public class PuertoEspacial {

    private String nombre;
    private String codigo;
    private double radio;
    private double azimut;
    private double polar;
    private int numMuelles;

    /**
     * Constructor de la clase
     *
     * @param nombre
     * @param codigo
     * @param radio
     * @param azimut
     * @param polar
     * @param numMuelles
     */
    public PuertoEspacial(String nombre, String codigo, double radio, double azimut, double polar, int numMuelles) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.radio = radio;
        this.azimut = azimut;
        this.polar = polar;
        this.numMuelles = numMuelles;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public double getRadio() {
        return radio;
    }
    public double getAzimut() {
        return azimut;
    }

    public double getPolar() {
        return polar;
    }
    public int getMuelles() {
        return numMuelles;
    }

    /**
     *  Método para calcular la distancia entre el puerto espacial que recibe el mensaje y el puerto
     *  espacial destino
     *
     * @param destino
     * @return un double que es la distancia calculada
     */
    public double distancia(PuertoEspacial destino) {
        double x1=radio*Math.sin(azimut)*Math.cos(polar),
        y1=radio*Math.sin(azimut)*Math.cos(polar),
        z1= radio *Math.cos(azimut);
        double x2= destino.getRadio()*Math.sin(destino.getAzimut())*Math.cos(destino.getPolar()),
                y2=destino.getRadio()*Math.sin(destino.getAzimut())*Math.cos(destino.getPolar()),
                z2= destino.getRadio()*Math.cos(destino.getAzimut());
        double x=Math.pow((x2-x1),2),
        y=Math.pow((y2-y1),2),
        z=Math.pow(z2-z1,2);
        return Math.sqrt(x+y+z);
    }

    /**
     * Método que crea un String con los datos del puerto espacial
     * @return  "Gaia Galactic Terminal(GGT), en (1.0 90.0 0.0), con 8 muelles" (Radio, Azimut, Polar)
     */
    public String toString() {
        return nombre+"("+codigo+"), en ("+radio+" "+azimut+" "+polar+"), con "+numMuelles+" muelles";
    }

    /**
     * Método que crea un String con los datos de un aeropuerto
     * @return "Gaia Galactic Terminal (GGT)"
     */
    public String toStringSimple() {
        return nombre+"("+codigo+")";
    }
}

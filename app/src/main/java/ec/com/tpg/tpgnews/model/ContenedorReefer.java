package ec.com.tpg.tpgnews.model;

/**
 * Created by us on 15/5/2018.
 */

public class ContenedorReefer {

    int ano_operacion;
    int cor_operacion;
    String ubicacion;
    String nombre_nave;
    String isocode_descripcion;
    String impo_expo;
    String nombre_linea;

    public int getAno_operacion() {
        return ano_operacion;
    }

    public void setAno_operacion(int ano_operacion) {
        this.ano_operacion = ano_operacion;
    }

    public int getCor_operacion() {
        return cor_operacion;
    }

    public void setCor_operacion(int cor_operacion) {
        this.cor_operacion = cor_operacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre_nave() {
        return nombre_nave;
    }

    public void setNombre_nave(String nombre_nave) {
        this.nombre_nave = nombre_nave;
    }

    public String getIsocode_descripcion() {
        return isocode_descripcion;
    }

    public void setIsocode_descripcion(String isocode_descripcion) {
        this.isocode_descripcion = isocode_descripcion;
    }

    public String getImpo_expo() {
        return impo_expo;
    }

    public void setImpo_expo(String impo_expo) {
        this.impo_expo = impo_expo;
    }

    public String getNombre_linea() {
        return nombre_linea;
    }

    public void setNombre_linea(String nombre_linea) {
        this.nombre_linea = nombre_linea;
    }

}
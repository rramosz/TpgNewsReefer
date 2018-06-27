package ec.com.tpg.tpgnews.model;

/**
 * Created by Ricky Ramos on 20/4/2018.
 */

public class Tomas {


    String nombre_generador;
    int total_fila;
    int total_columna;
    int fila;
    int columna;
    int estado;
    String descripcion_estado;
    String uid_toma;

    public String getNombre_generador() {
        return nombre_generador;
    }

    public void setNombre_generador(String nombre_generador) {
        this.nombre_generador = nombre_generador;
    }

    public int getTotal_fila() {
        return total_fila;
    }

    public void setTotal_fila(int total_fila) {
        this.total_fila = total_fila;
    }

    public int getTotal_columna() {
        return total_columna;
    }

    public void setTotal_columna(int total_columna) {
        this.total_columna = total_columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDescripcion_estado() {
        return descripcion_estado;
    }

    public void setDescripcion_estado(String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }

    public String getUid_toma() {
        return uid_toma;
    }

    public void setUid_toma(String uid_toma) {
        this.uid_toma = uid_toma;
    }


}

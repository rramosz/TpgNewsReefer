package ec.com.tpg.tpgnews.model;

/**
 * Created by Ricky Ramos on 23/3/2018.
 */

public class Toma {
    private int id_generador;
    private int id_toma;
    private int fila;
    private int columna;
    private String estado;


    public int getId_generador() {
        return id_generador;
    }

    public void setId_generador(int id_generador) {
        this.id_generador = id_generador;
    }

    public int getId_toma() {
        return id_toma;
    }

    public void setId_toma(int id_toma) {
        this.id_toma = id_toma;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

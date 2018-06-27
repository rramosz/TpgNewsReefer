package ec.com.tpg.tpgnews.model;

/**
 * Created by Ricky Ramos on 27/3/2018.
 */

public class Proveedor {


    private String id_rf_proveedor;
    private String ruc_proveedor;
    private String nombre_razon_social;
    private String direccion;
    private String observacion;
    private String fecha_registro;
    private String usuario_registra;
    private int estado;
    private String fecha_actualiza;
    private String usuario_actualiza;

    public String getId_rf_proveedor() {
        return id_rf_proveedor;
    }

    public void setId_rf_proveedor(String id_rf_proveedor) {
        this.id_rf_proveedor = id_rf_proveedor;
    }

    public String getRuc_proveedor() {
        return ruc_proveedor;
    }

    public void setRuc_proveedor(String ruc_proveedor) {
        this.ruc_proveedor = ruc_proveedor;
    }

    public String getNombre_razon_social() {
        return nombre_razon_social;
    }

    public void setNombre_razon_social(String nombre_razon_social) {
        this.nombre_razon_social = nombre_razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getUsuario_registra() {
        return usuario_registra;
    }

    public void setUsuario_registra(String usuario_registra) {
        this.usuario_registra = usuario_registra;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_actualiza() {
        return fecha_actualiza;
    }

    public void setFecha_actualiza(String fecha_actualiza) {
        this.fecha_actualiza = fecha_actualiza;
    }

    public String getUsuario_actualiza() {
        return usuario_actualiza;
    }

    public void setUsuario_actualiza(String usuario_actualiza) {
        this.usuario_actualiza = usuario_actualiza;
    }

}

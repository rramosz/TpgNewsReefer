package ec.com.tpg.tpgnews.ws;

/**
 * Created by us on 27/3/2018.
 */

import android.os.StrictMode;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.MainActivity;
import ec.com.tpg.tpgnews.model.CargaCombustibleGenerador;
import ec.com.tpg.tpgnews.model.Contenedor;
import ec.com.tpg.tpgnews.model.ContenedorReefer;
import ec.com.tpg.tpgnews.model.Deposito;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Identificacion;
import ec.com.tpg.tpgnews.model.Proveedor;
import ec.com.tpg.tpgnews.model.Tomas;


/**
 * Created by Ricky Ramos. on 19/2/2018.
 */

public class WebServicesPowerPack {




    private String NAMESPACE = "http://main.tpg.inarpi.ecuador.com/";
    private String URL ="http://192.168.0.247:8080/PowerPackReefer/PowerPackReefer?wsdl";
    private String SOAP_ACTION ="http://main.tpg.inarpi.ecuador.com/login";
    private String METHOD_NAME = "login";
    private String KEY="6d272487ba304cd3874883eccb3bc140";
    private Contenedor contenedor;
    private ArrayList<Proveedor> array_proveedor;
    private ArrayList<Tomas> array_tomas;
    private ArrayList<Generador> array_generador;
    private Generador generador;
    private CargaCombustibleGenerador carga_combustible_generador;
    private Identificacion identificacion;
    private ContenedorReefer contenedor_reefer;




    public Errores loginWs(String usuario, String password)
    {

        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "login";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("usuario",usuario);
            request.addProperty("password",password);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();

            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());


        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }

        //System.out.println("Fin  void loginWs()");
        return resultado_errores;

    }









    public Errores consultaProveedores()
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "consultaProveedores";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del contenedor y el listado de depositos
            {



                JSONArray json_array_depot=new JSONArray(json_array.get(1).toString());
                for (int ig=0; ig< json_array_depot.length(); ig++)
                {
                    Gson gson=new Gson();
                    JSONObject json_object_depot=new JSONObject(json_array_depot.get(ig).toString());
                    Proveedor proveedor= new Proveedor();
                    proveedor=gson.fromJson(json_object_depot.toString(), Proveedor.class);
                    array_proveedor.add(proveedor);
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }







    public Errores registraGenerador(String id_rf_proveedor,
                                     String nombre,
                                     String descripcion,
                                     String usuario_registra,
                                     int filas,
                                     int columnas,
                                     int capacidad_galones,
                                     String image_base_64,
                                     String bloque)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraGenerador";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id_rf_proveedor",id_rf_proveedor);
            request.addProperty("nombre",nombre);
            request.addProperty("descripcion",descripcion);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("filas",filas);
            request.addProperty("columnas",columnas);
            request.addProperty("capacidad_galones",capacidad_galones);
            request.addProperty("image_base_64",image_base_64);
            request.addProperty("bloque",bloque);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }







    public Errores consultaGeneradores(String usuario)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "consultaGeneradores";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_generador = new ArrayList<Generador>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("usuario",usuario);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del contenedor y el listado de depositos
            {



                JSONArray json_array_depot=new JSONArray(json_array.get(1).toString());
                for (int ig=0; ig< json_array_depot.length(); ig++)
                {
                    Gson gson=new Gson();
                    JSONObject json_object_depot=new JSONObject(json_array_depot.get(ig).toString());
                    Generador generador= new Generador();
                    generador=gson.fromJson(json_object_depot.toString(), Generador.class);
                    array_generador.add(generador);
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }


    public ArrayList<Generador> getArray_generador() {
        return array_generador;
    }

    public void setArray_generador(ArrayList<Generador> array_generador) {
        this.array_generador = array_generador;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
    }

    public ArrayList<Proveedor> getArray_proveedor() {
        return array_proveedor;
    }

    public void setArray_deposito(ArrayList<Proveedor> array_proveedor) {
        this.array_proveedor = array_proveedor;
    }

    public Generador getGenerador() {
        return generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }


    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public void setNAMESPACE(String NAMESPACE) {
        this.NAMESPACE = NAMESPACE;
    }

    public CargaCombustibleGenerador getCarga_combustible_generador() {
        return carga_combustible_generador;
    }

    public void setCarga_combustible_generador(CargaCombustibleGenerador carga_combustible_generador) {
        this.carga_combustible_generador = carga_combustible_generador;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }


    public ArrayList<Tomas> getArray_tomas() {
        return array_tomas;
    }

    public void setArray_tomas(ArrayList<Tomas> array_tomas) {
        this.array_tomas = array_tomas;
    }

    public Errores registraInicioCargaCombustibleGenerador(String id_rf_generador,
                                                           String usuario_registra,
                                                           String num_guia_remision,
                                                           String num_orden_despacho,
                                                           float horometro,
                                                           float remanente_combustible,
                                                           String image_base_64)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraIniciaCargaCombustible";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id_rf_generador",id_rf_generador);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("num_guia_remision",num_guia_remision);
            request.addProperty("num_orden_despacho",num_orden_despacho);
            request.addProperty("horometro",String.valueOf(horometro));
            request.addProperty("remanente_combustible",String.valueOf(remanente_combustible));
            request.addProperty("image_base_64",image_base_64);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }






    public Errores consultaGeneradorId(String usuario,
                                       String uid_generador)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        CargaCombustibleGenerador carga_generador=new CargaCombustibleGenerador();
        METHOD_NAME = "consultaGeneradorId";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_generador = new ArrayList<Generador>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("usuario",usuario);
            request.addProperty("uid_generador",uid_generador);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del Generador
            {
                JSONObject json_object_generador=new JSONObject(json_array.get(1).toString());
                Generador generador= new Generador();
                Gson gson=new Gson();
                generador=gson.fromJson(json_object_generador.toString(), Generador.class);
                this.setGenerador(generador);

                if(generador.getFinalizo_carga_combustible().equals("NO"))
                {
                    JSONObject json_object_combust=new JSONObject(json_array.get(2).toString());
                    carga_generador=  gson.fromJson(json_object_combust.toString(), CargaCombustibleGenerador.class);
                    this.setCarga_combustible_generador(carga_generador);
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }




    public Errores consultaChofer(String cedula_chofer)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Identificacion identificacion=new Identificacion();
        METHOD_NAME = "consultaChofer";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("cedula_chofer",cedula_chofer);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del Chofer
            {
                JSONObject json_object_generador=new JSONObject(json_array.get(1).toString());
                Gson gson=new Gson();
                identificacion=gson.fromJson(json_object_generador.toString(), Identificacion.class);
                this.setIdentificacion(identificacion);


            }



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }





    public Errores consultaPlaca(String placa)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Identificacion identificacion=new Identificacion();
        METHOD_NAME = "consultaPlaca";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;

        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);


            request.addProperty("uid_aplicacion",KEY);
            request.addProperty("placa",placa);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del Vehículo
            {
                JSONObject json_object_generador=new JSONObject(json_array.get(1).toString());
                Gson gson=new Gson();
                identificacion=gson.fromJson(json_object_generador.toString(), Identificacion.class);
                this.setIdentificacion(identificacion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }






    public Errores registraFinCargaCombustibleGenerador   (String id_rf_generador,
                                                           String id_rf_det_gene_carga_combust,
                                                           String usuario_registra,
                                                           String placa,
                                                           String ci_chofer,
                                                           String nombres_chofer,
                                                           String empresa_transporte_vehic,
                                                           String ruc_empresa_trans_vehic,
                                                           float galones_abastecidos,
                                                           String image_base_64)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraFinCargaCombustible";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id_rf_generador",id_rf_generador);
            request.addProperty("id_rf_det_gene_carga_combust",id_rf_det_gene_carga_combust);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("galones_abastecidos",String.valueOf(galones_abastecidos));
            request.addProperty("placa",placa);
            request.addProperty("ci_chofer",ci_chofer);
            request.addProperty("nombres_chofer",nombres_chofer);
            request.addProperty("empresa_transporte_vehic",empresa_transporte_vehic);
            request.addProperty("ruc_empresa_trans_vehic",ruc_empresa_trans_vehic);
            request.addProperty("image_base_64",image_base_64);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }









    public Errores enciendeGenerador   (String uid_generador,
                                        String usuario_registra,
                                        String ubicacion,
                                        float remanente_combustible
                                                          )
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraEncendidoApagadoGenerador";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("uid_generador",uid_generador);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("ubicacion",ubicacion);
            request.addProperty("tipo_accion",1);
            request.addProperty("remanente_combustible",String.valueOf(remanente_combustible));
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }





    public Errores apagaGenerador   (   String uid_generador,
                                        String usuario_registra,
                                        float remanente_combustible) //El SP valida si el generador no tiene conectado contenedores, caso contrario no permite apagar.
    { //Solo se puede apagar si es que no tiene conectado ningun contenedor Reefer.
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraEncendidoApagadoGenerador";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("uid_generador",uid_generador);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("ubicacion","");
            request.addProperty("tipo_accion",2);
            request.addProperty("remanente_combustible",String.valueOf(remanente_combustible));
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }





    public Errores consultaTomasGenerador(String uid_det_generador,
                                          String usuario)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "consultaEstadoTomasGenerador";
        String namespace=NAMESPACE.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");
        String url=URL.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");
        SOAP_ACTION=namespace+METHOD_NAME;
        array_tomas= new ArrayList<Tomas>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(namespace, METHOD_NAME);
            request.addProperty("uid_generador",uid_det_generador);
            request.addProperty("usuario",usuario);
            request.addProperty("uid_aplicacion",KEY);



            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(url);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del contenedor y el listado de depositos
            {



                JSONArray json_array_tomas=new JSONArray(json_array.get(1).toString());
                for (int ig=0; ig< json_array_tomas.length(); ig++)
                {
                    Gson gson=new Gson();
                    JSONObject json_object_tomas=new JSONObject(json_array_tomas.get(ig).toString());
                    Tomas tomas= new Tomas();
                    tomas=gson.fromJson(json_object_tomas.toString(), Tomas.class);
                    array_tomas.add(tomas);
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }






    public Errores actualizaTomaDaniadoVacioS   (  String uid_toma,
                                                   String usuario_registra,
                                                   int estado,
                                                   String observacion) //El SP valida si el generador no tiene conectado contenedores, caso contrario no permite apagar.
    { //Solo se puede apagar si es que no tiene conectado ningun contenedor Reefer.
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        String namespace=NAMESPACE.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");
        String url=URL.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");

        METHOD_NAME = "actualizaTomaDaniadoVacio";

        SOAP_ACTION=namespace+METHOD_NAME;
        array_proveedor = new ArrayList<Proveedor>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;

            request = new SoapObject(namespace, METHOD_NAME);
            request.addProperty("uid_toma",uid_toma);
            request.addProperty("usuario_registra",usuario_registra);
            request.addProperty("estado",estado);
            request.addProperty("observacion",observacion);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(url);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONObject jsonObj = new JSONObject(resultado);
            resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
            resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());



        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }


    public ContenedorReefer getContenedor_reefer() {
        return contenedor_reefer;
    }

    public void setContenedor_reefer(ContenedorReefer contenedor_reefer) {
        this.contenedor_reefer = contenedor_reefer;
    }

    public Errores consultaContenedorReefer(String uid_generador,
                                            String usuario,
                                            String contenedor)
    {
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "consultaContenedorRf";
        String namespace=NAMESPACE.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");
        String url=URL.replace("http://192.168.0.247:8080", "http://192.168.149.56:8084");
        SOAP_ACTION=namespace+METHOD_NAME;
        contenedor_reefer= new ContenedorReefer();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(namespace, METHOD_NAME);
            request.addProperty("uid_generador",uid_generador);
            request.addProperty("usuario",usuario);
            request.addProperty("contenedor",contenedor);
            request.addProperty("uid_aplicacion",KEY);



            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(url);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());
            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del contenedor y el listado de depositos
            {
                JSONObject json_contenedor=new JSONObject(json_array.get(1).toString());
                Gson gson=new Gson();
                contenedor_reefer=gson.fromJson(json_contenedor.toString(), ContenedorReefer.class);
                this.setContenedor_reefer(contenedor_reefer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }


        return resultado_errores;

    }




}

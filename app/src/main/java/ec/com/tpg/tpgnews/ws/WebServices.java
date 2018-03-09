package ec.com.tpg.tpgnews.ws;

/**
 * Created by Ricky Ramos. on 19/2/2018.
 */


import android.os.StrictMode;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.MainActivity;
import ec.com.tpg.tpgnews.model.Contenedor;
import ec.com.tpg.tpgnews.model.Deposito;
import ec.com.tpg.tpgnews.model.Errores;

public class WebServices {




        private String NAMESPACE = "http://main.tpg.inarpi.ecuador.com/";
        private String URL ="http://192.168.0.247:8080/RegistraAlertasCntReefer/RegistraAlertasCntReefer?wsdl";
        private String SOAP_ACTION ="http://main.tpg.inarpi.ecuador.com/login";
        private String METHOD_NAME = "login";
        private String KEY="17aca7055e4f4817b1ff83eccb3bc140";
        private Contenedor contenedor;
        private ArrayList<Deposito> array_deposito;




        public Errores loginWs(String usuario, String password)
        {
            System.out.println("Inicio  void loginWs(String usuario "+usuario+", String password "+password+")");
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
                System.out.println(">>>>>>"+resultado);
                JSONObject jsonObj = new JSONObject(resultado);
                resultado_errores.setCod_error(Integer.parseInt(jsonObj.get("cod_error").toString()));
                resultado_errores.setMsg_error(jsonObj.get("msg_error").toString());
                System.out.println("APP WS ERROR:" + jsonObj);

            } catch (Exception e) {
                e.printStackTrace();
                resultado_errores.setCod_error(e.hashCode());
                resultado_errores.setMsg_error("Error: "+e.getMessage());
                System.out.println("APP WS ERROR exception:" + e.getMessage());
            }

            //System.out.println("Fin  void loginWs()");
            return resultado_errores;

        }









    public Errores consultaContenedor(String contenedor)
    {
        System.out.println("Inicio  Errores consultaContenedor(String contenedor "+contenedor+")");
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "consultaContenedor";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_deposito = new ArrayList<Deposito>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("contenedor",contenedor);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            System.out.println(">>>>>>"+resultado);
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());

            if(resultado_errores.getCod_error()==0)//SI es OK se obtiene la informacion del contenedor y el listado de depositos
            {
               JSONObject json_object_cnt=new JSONObject(json_array.get(1).toString());
               Gson gson=new Gson();
               Contenedor cnt_result=gson.fromJson(json_object_cnt.toString(), Contenedor.class);

               /* cnt_result.setMsg_error(json_object_cnt.get("msg_error").toString());
                cnt_result.setAgencia(json_object_cnt.get("agencia").toString());
                cnt_result.setAnio_operacion(Integer.parseInt(json_object_cnt.get("anio_operacion").toString()));
                cnt_result.setCor_operacion(Integer.parseInt(json_object_cnt.get("cor_operacion").toString()));
                cnt_result.setBooking(json_object_cnt.get("booking").toString());
                cnt_result.setCod_error(Integer.parseInt(json_object_cnt.get("cod_error").toString()));
                cnt_result.setContenedor(json_object_cnt.get("contenedor").toString());
                cnt_result.setExportador(json_object_cnt.get("exportador").toString());
                cnt_result.setUbicacion(json_object_cnt.get("ubicacion").toString());
                cnt_result.setProducto(json_object_cnt.get("producto").toString());*/
                this.setContenedor(cnt_result);

                JSONArray json_array_depot=new JSONArray(json_array.get(2).toString());
                for (int ig=0; ig< json_array_depot.length(); ig++)
                {

                    JSONObject json_object_depot=new JSONObject(json_array_depot.get(ig).toString());
                    Deposito depot_result=new Deposito();
                    depot_result.setId_deposito(Integer.parseInt(json_object_depot.get("id_deposito").toString()));
                    depot_result.setDescripcion_depot(json_object_depot.get("descripcion_depot").toString());
                    array_deposito.add(depot_result);
                }


            }

            System.out.println("APP WS ERROR:" + json_object);

        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }

        System.out.println("Fin  Errores consultaContenedor(String contenedor "+contenedor+")");
        return resultado_errores;

    }









    public Errores registraAlarmaCnt(String contenedor ,
                                     int anio_operacion,
                                     int cor_operacion,
                                     String cod_error ,
                                     String observacion ,
                                     String usuario_registra,
                                     float temp_sum ,
                                     float tem_ret ,
                                     String ubicacion,
                                     String booking,
                                     String lampas,
                                     int id_deposito)
    {
        System.out.println("Inicio  Errores consultaContenedor(String contenedor "+contenedor+", " +
                "int anio_operacion "+anio_operacion+", " +
                "int cor_operacion "+cor_operacion+", " +
                "String cod_error "+cod_error+", " +
                "String observacion "+observacion+", " +
                "String usuario_registra "+usuario_registra+", " +
                "float temp_sum "+temp_sum+"," +
                "float tem_ret "+tem_ret+", " +
                "String ubicacion "+ubicacion+", " +
                "String booking "+booking+", " +
                "String lampas "+lampas+", " +
                "int id_deposito "+id_deposito+" )");
        Errores resultado_errores=new Errores();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        METHOD_NAME = "registraAlertaCnt";
        SOAP_ACTION=NAMESPACE+METHOD_NAME;
        array_deposito = new ArrayList<Deposito>();
        try{

            SoapObject request;

            SoapSerializationEnvelope envelope;

            HttpTransportSE androidHttpTransport;

            Object result;
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("contenedor",contenedor);
            request.addProperty("anio_oper",anio_operacion);
            request.addProperty("cor_oper",cor_operacion);
            request.addProperty("cod_error",cod_error);
            request.addProperty("observacion",observacion);
            request.addProperty("usuario",usuario_registra);
            request.addProperty("tempsum",String.valueOf(temp_sum));
            request.addProperty("temret",String.valueOf(tem_ret));
            request.addProperty("ubicacion",ubicacion);
            request.addProperty("booking",booking);
            request.addProperty("lampas",lampas);
            request.addProperty("deposito",id_deposito);
            request.addProperty("uid_aplicacion",KEY);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//                envelope.dotNet=false;
            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            result = envelope.getResponse();

            String resultado=result.toString();
            System.out.println(">>>>>>"+resultado);
            JSONArray json_array = new JSONArray(resultado);

            JSONObject json_object=new JSONObject(json_array.get(0).toString());

            resultado_errores.setCod_error(Integer.parseInt(json_object.get("cod_error").toString()));
            resultado_errores.setMsg_error(json_object.get("msg_error").toString());
            System.out.println("APP WS ERROR:" + json_object);

        } catch (Exception e) {
            e.printStackTrace();
            resultado_errores.setCod_error(e.hashCode());
            resultado_errores.setMsg_error("Error: "+e.getMessage());
            System.out.println("APP WS ERROR exception:" + e.getMessage());
        }

        System.out.println("Fin  Errores consultaContenedor(String contenedor "+contenedor+", " +
                            "int anio_operacion "+anio_operacion+", " +
                            "int cor_operacion "+cor_operacion+", " +
                            "String cod_error "+cod_error+", " +
                            "String observacion "+observacion+", " +
                            "String usuario_registra "+usuario_registra+", " +
                            "float temp_sum "+temp_sum+"," +
                            "float tem_ret "+tem_ret+", " +
                            "String ubicacion "+ubicacion+", " +
                            "String booking "+booking+", " +
                            "String lampas "+lampas+", " +
                            "int id_deposito "+id_deposito+" )");
        return resultado_errores;

    }





    public Contenedor getContenedor() {
        return contenedor;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
    }

    public ArrayList<Deposito> getArray_deposito() {
        return array_deposito;
    }

    public void setArray_deposito(ArrayList<Deposito> array_deposito) {
        this.array_deposito = array_deposito;
    }
}

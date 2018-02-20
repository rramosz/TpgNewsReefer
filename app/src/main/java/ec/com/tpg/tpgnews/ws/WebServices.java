package ec.com.tpg.tpgnews.ws;

/**
 * Created by Ricky Ramos. on 19/2/2018.
 */


import android.os.StrictMode;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServices {




        private static final String NAMESPACE = "http://main.tpg.inarpi.ecuador.com/";
        private static final String URL ="http://192.168.0.247:8080/RegistraAlertasCntReefer/RegistraAlertasCntReefer?wsdl";
        private static final String SOAP_ACTION ="http://main.tpg.inarpi.ecuador.com/login";
        private static final String METHOD_NAME = "login";




        public void loginWs()
        {
            System.out.println("Inicio  void loginWs()");

            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);

            try{

                SoapObject request;

                SoapSerializationEnvelope envelope;

                HttpTransportSE androidHttpTransport;

                Object result;
                request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("usuario","desa1");
                request.addProperty("password","USuario001");
                request.addProperty("uid_aplicacion","122555511244488888888");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                //envelope.dotNet=false;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug=true;
                androidHttpTransport.call(SOAP_ACTION, envelope);

                result = envelope.getResponse();

                String resultado=result.toString();
                System.out.println(">>>>>>"+resultado);
                JSONObject jsonObj = new JSONObject(resultado);
                System.out.println(">>>>>>Cod_error: "  +jsonObj.get("cod_error")+">>>>>> MsgError: "+jsonObj.get("msg_error"));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: "+e.toString());
            }

            System.out.println("Fin  void loginWs()");

        }



}
package layout;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.model.CargaCombustibleGenerador;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Identificacion;
import ec.com.tpg.tpgnews.model.Proveedor;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegistroFinCargaCombustible extends Fragment {

    ImageView image_fin_generador;
    static int TAKE_PICTURE = 100;

    Button btpic, btnup;
    private Uri fileUri;
    String picturePath = null;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "Paste your URL here";
    WebServicesPowerPack ws_power_transportista;

    Identificacion idenficacion_vehiculo;
    Identificacion idenficacion_chofer;

    TextInputEditText txt_guia_remision;
    TextInputEditText txt_num_orden;
    TextInputEditText txt_horometro;
    TextInputEditText txt_remanente;
    TextView txt_nombre_generador;


    TextInputEditText txt_placa;
    TextInputEditText txt_empresa_transporte;
    TextInputEditText txt_ci_chofer;
    TextInputEditText txt_nombre_chofer;
    TextInputEditText txt_galones_abastecidos;


    String txt_placa_st;
    String txt_empresa_transporte_st;
    String txt_ci_chofer_st;
    String txt_nombre_chofer_st;
    String txt_galones_abastecidos_st;
    float txt_galones_abastecidos_fl;


    Dialog dialog_alert = null;
    Dialog dialog_popup_ok = null;
    Dialog dialog_confirm = null;
    int deposito_seleccionado;
    List<Proveedor> arraylist_proveedor;
    SearchableSpinner spinner_depot;
    Generador generador;
    CargaCombustibleGenerador carga_combustible;


    String txt_guia_remision_st = new String();
    String txt_num_orden_st = new String();
    String txt_horometro_st = new String();
    String txt_remanente_st = new String();
    float horometro_fl;
    float remanente_fl;
    String txt_nombre_generador_st = new String();
    String uid_generador = new String();
    ProgressDialog pd;

    public FragmentRegistroFinCargaCombustible() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_registra_fin_carga_combustible, container, false);
        FloatingActionButton button_foto = (FloatingActionButton) view.findViewById(R.id.action_button_capture_foto_fin);
        image_fin_generador = (ImageView) view.findViewById(R.id.image_fin_generador);
        txt_guia_remision = (TextInputEditText) view.findViewById(R.id.txt_guia_remision);
        txt_num_orden = (TextInputEditText) view.findViewById(R.id.txt_num_orden);
        txt_horometro = (TextInputEditText) view.findViewById(R.id.txt_horometro);
        txt_remanente = (TextInputEditText) view.findViewById(R.id.txt_remanente);
        txt_nombre_generador = (TextView) view.findViewById(R.id.txt_nombre_generador);
        ws_power_transportista=new WebServicesPowerPack();

        txt_placa = (TextInputEditText) view.findViewById(R.id.txt_placa);
        txt_empresa_transporte = (TextInputEditText) view.findViewById(R.id.txt_empresa_transporte);
        txt_empresa_transporte.setEnabled(false);
        txt_ci_chofer = (TextInputEditText) view.findViewById(R.id.txt_ci_chofer);
        txt_nombre_chofer = (TextInputEditText) view.findViewById(R.id.txt_nombre_chofer);
        txt_nombre_chofer.setEnabled(false);
        txt_galones_abastecidos = (TextInputEditText) view.findViewById(R.id.txt_galones_abastecidos);

        ba1 = null;
        button_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });


        FloatingActionButton button_grabar = (FloatingActionButton) view.findViewById(R.id.action_button_grabar_fin_combustible);
        FloatingActionButton action_button_back = (FloatingActionButton) view.findViewById(R.id.action_button_back);


        button_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registrarFinCargaCombustibleGenerador();
            }
        });


        action_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentListadoGeneradores fragment_listado_generadores = new FragmentListadoGeneradores();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();
            }
        });



        dialog_alert = new Dialog(getActivity());
        dialog_alert.setContentView(R.layout.popup_alert);
        dialog_alert.setCancelable(false);
        dialog_alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FloatingActionButton btnclose = (FloatingActionButton) dialog_alert.findViewById(R.id.floating_button_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarPopupAlert();
            }
        });


        dialog_popup_ok = new Dialog(getActivity());
        dialog_popup_ok.setContentView(R.layout.popup_alert_ok);
        dialog_popup_ok.setCancelable(false);
        dialog_popup_ok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FloatingActionButton btnclose_ok = (FloatingActionButton) dialog_popup_ok.findViewById(R.id.floating_button_close);
        btnclose_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarPopupAlertOk();
            }
        });


        dialog_confirm = new Dialog(getActivity());
        dialog_confirm.setContentView(R.layout.popup_confirm);
        dialog_confirm.setCancelable(false);
        dialog_confirm.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        FloatingActionButton btnclose_confirm = (FloatingActionButton) dialog_confirm.findViewById(R.id.floating_button_close);
        btnclose_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_confirm.hide();
            }
        });


        Button button_aceptar = (Button) dialog_confirm.findViewById(R.id.button_aceptar_confirm);
        button_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Grabar la finalizacion de la carga de combustible
                dialog_confirm.hide();
                grabarWs();
            }
        });


        Button button_cancelar = (Button) dialog_confirm.findViewById(R.id.button_cancelar_confirm);
        button_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Grabar la nueva alarma Reefer.
                dialog_confirm.hide();

            }
        });


        FloatingActionButton btnclose_confirm_ok = (FloatingActionButton) dialog_popup_ok.findViewById(R.id.floating_button_close);
        btnclose_confirm_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_popup_ok.hide();
                FragmentListadoGeneradores fragment_listado_generadores = new FragmentListadoGeneradores();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();
            }
        });


        FloatingActionButton act_button_buscar_placa = (FloatingActionButton) view.findViewById(R.id.act_button_buscar_placa);
        act_button_buscar_placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consultarPlaca();

            }
        });


        FloatingActionButton act_button_borrar_placa = (FloatingActionButton) view.findViewById(R.id.act_button_borrar_placa);
        act_button_borrar_placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idenficacion_vehiculo = new Identificacion();
                txt_empresa_transporte.setText("");
                txt_empresa_transporte_st = new String();
                txt_placa.setEnabled(true);
                txt_placa.setText("");
            }
        });


        FloatingActionButton act_button_buscar_chofer = (FloatingActionButton) view.findViewById(R.id.act_button_buscar_chofer);
        act_button_buscar_chofer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consultarChofer();

            }
        });


        FloatingActionButton act_button_borrar_chofer = (FloatingActionButton) view.findViewById(R.id.act_button_borrar_chofer);
        act_button_borrar_chofer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idenficacion_chofer = new Identificacion();
                txt_nombre_chofer.setText("");
                txt_ci_chofer.setText("");
                txt_nombre_chofer_st = new String();
            }
        });


        String json_code = getArguments().getString("generador_resultado");
        String json_code_carga_combustible = getArguments().getString("carga_combustible");
        Gson gson = new Gson();
        generador = new Generador();
        generador = gson.fromJson(json_code.toString(), Generador.class);
        carga_combustible = gson.fromJson(json_code_carga_combustible.toString(), CargaCombustibleGenerador.class);
        txt_nombre_generador_st = generador.getNombre();
        uid_generador = generador.getId_rf_generador();
        txt_nombre_generador.setText(txt_nombre_generador_st);
        txt_nombre_generador.setEnabled(false);
        ImageView circleImageView = (ImageView) view.findViewById(R.id.image_circle_generador);
        String url_picture = "http://192.168.0.247:8080/PowerPackReefer/image?location_image=" + generador.getPicture();

        Picasso.with(this.getActivity())
                .load(url_picture)
                .transform(new CropSquareTransformation())
                .resize(500, 500)
                .centerCrop()
                .into(circleImageView);

        txt_guia_remision.setText(carga_combustible.getNum_guia_remision());
        txt_guia_remision.setEnabled(false);
        txt_num_orden.setText(carga_combustible.getNum_orden_despacho());
        txt_num_orden.setEnabled(false);
        txt_horometro.setText(String.valueOf(carga_combustible.getHorometro()));
        txt_horometro.setEnabled(false);
        txt_remanente.setText(String.valueOf(carga_combustible.getRemanente_combustible()));
        txt_remanente.setEnabled(false);


        txt_placa.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    consultarPlaca();
                    return true;
                }
                return false;
            }
        });


        txt_ci_chofer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    consultarChofer();
                    return true;
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == 100 && resultCode == RESULT_OK) {

            selectedImage = intent.getData();
            photo = (Bitmap) intent.getExtras().get("data");

            // Cursor to get image uri to display

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap photo = (Bitmap) intent.getExtras().get("data");
            image_fin_generador.setImageBitmap(photo);
            image_fin_generador.setImageBitmap(photo);
            int w = photo.getWidth();
            int h = photo.getHeight();


            image_fin_generador.setImageBitmap(photo);
            image_fin_generador.setVisibility(View.VISIBLE);
            image_fin_generador.getLayoutParams().width = w * 2;
            image_fin_generador.getLayoutParams().height = h * 2;


        }


    }


    private void registrarFinCargaCombustibleGenerador() {
        // Image location URL

        System.out.println("Ingresandooo en registrarFinCargaCombustibleGenerador()");


        txt_placa_st = txt_placa.getText().toString();
        txt_empresa_transporte_st = txt_empresa_transporte.getText().toString();
        txt_ci_chofer_st = txt_ci_chofer.getText().toString();
        txt_nombre_chofer_st = txt_nombre_chofer.getText().toString();
        txt_galones_abastecidos_st = txt_galones_abastecidos.getText().toString();
        txt_galones_abastecidos_fl = 0;


        int bandera = 0;

        String id_seleccionado = "";
        Proveedor proveedor;


        if (bandera == 0) {
            if (txt_empresa_transporte_st == null) {
                mostrarPopUp("Por favor consulte los datos del Vehículo");
                bandera = 1;
            } else {
                txt_empresa_transporte_st = txt_empresa_transporte_st.trim();
                txt_empresa_transporte_st = txt_empresa_transporte_st.toUpperCase();

                if (txt_empresa_transporte_st.isEmpty()) {
                    mostrarPopUp("Por favor consulte los datos del Vehículo");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (txt_nombre_chofer_st == null) {
                mostrarPopUp("Por favor consulte los datos del Chófer");
                bandera = 1;
            } else {
                txt_nombre_chofer_st = txt_nombre_chofer_st.trim();
                txt_nombre_chofer_st = txt_nombre_chofer_st.toUpperCase();

                if (txt_nombre_chofer_st.isEmpty()) {
                    mostrarPopUp("Por favor consulte los datos del Chófer");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (txt_galones_abastecidos_st == null) {
                mostrarPopUp("Por favor ingrese el campo Galones abastecidos");
                bandera = 1;
            } else {
                txt_galones_abastecidos_st = txt_galones_abastecidos_st.trim();
                txt_galones_abastecidos_st = txt_galones_abastecidos_st.toUpperCase();

                if (txt_galones_abastecidos_st.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Galones Abastecidos");
                    bandera = 1;
                } else {
                    txt_galones_abastecidos_fl = Float.parseFloat(txt_galones_abastecidos_st);
                    if (txt_galones_abastecidos_fl <= 0) {
                        mostrarPopUp("El campo Galones Abastecidos debe de ser mayor a cero");
                        bandera = 1;
                    }
                }


            }

        }


        if (bandera == 0) {
            if (txt_nombre_generador_st == null || uid_generador == null) {
                mostrarPopUp("Ocurrio un ERROR no se pudo obtener informacion del generador, por favor intente nuevamente");
                bandera = 1;
            } else {
                txt_nombre_generador_st = txt_nombre_generador_st.trim();
                txt_nombre_generador_st = txt_nombre_generador_st.toUpperCase();

                if (txt_nombre_generador_st.isEmpty() || uid_generador.isEmpty()) {
                    mostrarPopUp("Ocurrio un ERROR no se pudo obtener informacion del generador, por favor intente nuevamente");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (picturePath == null) {
                mostrarPopUp("Por favor Capture la foto del Generador que desea registrar");
            } else {
                System.out.println("RUTA IMAGEN : " + picturePath);

                // Image
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
                TextView txt_label_mensaje = (TextView) dialog_confirm.findViewById(R.id.mensaje_descripcion);
                txt_label_mensaje.setText("¿ Seguro que desea registrar el Fin de carga de Combustible para el Generador (" + txt_nombre_generador_st + ") ?");
                dialog_confirm.show();

            }
        }


    }


    public void ocultarPopupAlert() {
        dialog_alert.hide();
    }

    public void ocultarPopupAlertOk() {
        dialog_popup_ok.hide();
    }


    public void mostrarPopUp(String mensaje) {
        if (pd != null) {
            pd.dismiss();
        }
        //imagen_popup.setImageResource(R.drawable.icons_warning);
        TextView txt_label_mensaje = (TextView) dialog_alert.findViewById(R.id.mensaje_descripcion);
        txt_label_mensaje.setText(mensaje);
        dialog_alert.show();

    }


    public void mostrarPopUpOk(String mensaje) {
        if (pd != null) {
            pd.dismiss();
        }
        TextView txt_label_mensaje = (TextView) dialog_popup_ok.findViewById(R.id.mensaje_descripcion);
        txt_label_mensaje.setText(mensaje);
        dialog_popup_ok.show();

    }


    public void grabarWs() {

        pd = ProgressDialog.show(getActivity(), "Cargando", "Procesando su transaccion por favor espere...", true, false);

        WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();



        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
        Object[] objects = new Object[10];
        objects[0] = uid_generador;
        objects[1] = carga_combustible.getId_rf_det_gene_carga_combust();
        objects[2] = usuario_logoneado_tr;
        objects[3] = txt_placa_st;
        objects[4] = txt_ci_chofer_st;
        objects[5] = idenficacion_chofer.getNombre_razon_social();
        objects[6] = idenficacion_vehiculo.getNombre_razon_social();
        objects[7] = idenficacion_vehiculo.getIdenctificacion();
        objects[8] = txt_galones_abastecidos_fl;
        objects[9] = ba1;


        new ProcesarWs().execute(objects);


    }


    public void consultarPlaca() {

        txt_placa_st = txt_placa.getText().toString();
        if (txt_placa_st == null) {
            mostrarPopUp("Por favor ingrese la placa del Vehículo");

        } else {
            txt_placa_st = txt_placa_st.trim();
            txt_placa_st = txt_placa_st.toUpperCase();

            if (txt_placa_st.isEmpty()) {
                mostrarPopUp("Por favor ingrese la placa del Vehículo");

            } else {
                if (txt_placa_st.length() < 7) {
                    mostrarPopUp("Por favor ingrese la placa del Vehículo, minimo 7 caracteres ejmplo: ABC12345");
                } else {


                    pd = ProgressDialog.show(getActivity(), "Cargando", "Procesando su transaccion por favor espere...", true, false);
                    WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
                    Object[] objects = new Object[1];
                    objects[0] = txt_placa_st;
                    new ProcesarVehiculoWs().execute(objects);
                }

            }
        }

    }


    public void consultarChofer() {
        txt_ci_chofer_st = txt_ci_chofer.getText().toString();


        if (txt_ci_chofer_st == null) {
            mostrarPopUp("Por favor ingrese la placa del Vehículo");

        } else {
            txt_ci_chofer_st = txt_ci_chofer_st.trim();
            txt_ci_chofer_st = txt_ci_chofer_st.toUpperCase();

            if (txt_ci_chofer_st.isEmpty()) {
                mostrarPopUp("Por favor ingrese la placa del Vehículo");

            } else {
                if (txt_ci_chofer_st.length() < 7) {
                    mostrarPopUp("Por favor ingrese la placa del Vehículo, minimo 7 caracteres ejmplo: ABC12345");
                } else {
                    pd = ProgressDialog.show(getActivity(), "Cargando", "Procesando su transaccion por favor espere...", true, false);
                    WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
                    Object[] objects = new Object[1];
                    objects[0] = txt_ci_chofer_st;
                    new ProcesarChoferWs().execute(objects);
                }

            }
        }

    }


    public class CropSquareTransformation implements Transformation { //Calse que se usa para cargar de forma mas optima las imagenes de internet reduciendo el consumo de memoria de dispositivo
        //URL de ejemplo: https://desarrollador-android.com/librerias/square/picasso/
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);


            /*
            *  Inicio Codigo para Girar la Imagen para mostrarla como fue capturada
            * */
            //Matrix matrix = new Matrix();
            //matrix.postRotate(90);  // La rotación debe ser decimal (float o double)
            //Bitmap rotatedBitmap = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
           /*
            *  Fin Codigo para Girar la Imagen para mostrarla como fue capturada
            * */


            if (result != source) {
                source.recycle();
            }



            return result;
        }

        @Override public String key() { return "square()"; }
    }



    private class ProcesarWs extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores = ws_power_pack.registraFinCargaCombustibleGenerador(objects[0].toString(),
                    objects[1].toString(),
                    objects[2].toString(),
                    objects[3].toString(),
                    objects[4].toString(),
                    objects[5].toString(),
                    objects[6].toString(),
                    objects[7].toString(),
                    Float.parseFloat(objects[8].toString()),
                    objects[9].toString());

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (FragmentRegistroFinCargaCombustible.this.pd != null) {
                FragmentRegistroFinCargaCombustible.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                mostrarPopUpOk(errores_ws.getMsg_error());
            } else {
                mostrarPopUp(errores_ws.getMsg_error());
            }

        }
    }






    private class ProcesarChoferWs extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {
            Errores errors_chofer = ws_power_transportista.consultaChofer(objects[0].toString());
            return errors_chofer;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (FragmentRegistroFinCargaCombustible.this.pd != null) {
                FragmentRegistroFinCargaCombustible.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                choferOk();

            } else {
                mostrarPopUp(errores_ws.getMsg_error());
            }

        }
    }






    private class ProcesarVehiculoWs extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {


            Errores errors_placa = ws_power_transportista.consultaPlaca(objects[0].toString());
            return errors_placa;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (FragmentRegistroFinCargaCombustible.this.pd != null) {
                FragmentRegistroFinCargaCombustible.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                vehiculoOk();

            } else {
                mostrarPopUp(errores_ws.getMsg_error());
            }

        }
    }


    public void choferOk()
    {
        idenficacion_chofer = ws_power_transportista.getIdentificacion();
        txt_nombre_chofer.setText(idenficacion_chofer.getNombre_razon_social());
        txt_ci_chofer.setEnabled(false);
    }


    public void vehiculoOk() {
        idenficacion_vehiculo = ws_power_transportista.getIdentificacion();
        txt_empresa_transporte.setText(idenficacion_vehiculo.getNombre_razon_social());
        txt_placa.setEnabled(false);

    }




    }

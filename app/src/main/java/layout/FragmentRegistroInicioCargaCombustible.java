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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.adapter.GeneradorAdapterRecyclerView;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Proveedor;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegistroInicioCargaCombustible extends Fragment {

    ImageView image_incio_generador;
    static int TAKE_PICTURE = 100;

    Button btpic, btnup;
    private Uri fileUri;
    String picturePath = null;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "Paste your URL here";

    TextInputEditText txt_guia_remision;
    TextInputEditText txt_num_orden;
    TextInputEditText txt_horometro;
    TextInputEditText txt_remanente;
    TextView txt_nombre_generador;
    Dialog dialog_alert = null;
    Dialog dialog_popup_ok = null;
    Dialog dialog_confirm = null;
    int deposito_seleccionado;
    List<Proveedor> arraylist_proveedor;
    SearchableSpinner spinner_depot;
    Generador generador;


    String txt_guia_remision_st = new String();
    String txt_num_orden_st = new String();
    String txt_horometro_st = new String();
    String txt_remanente_st = new String();
    float horometro_fl;
    float remanente_fl;
    String txt_nombre_generador_st = new String();
    String uid_generador = new String();
    ProgressDialog pd;

    public FragmentRegistroInicioCargaCombustible() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_registra_inicio_carga_combustible, container, false);
        FloatingActionButton button_foto = (FloatingActionButton) view.findViewById(R.id.action_button_capture_foto_inicio);
        FloatingActionButton action_button_back = (FloatingActionButton) view.findViewById(R.id.action_button_back);

        image_incio_generador = (ImageView) view.findViewById(R.id.image_incio_generador);
        txt_guia_remision = (TextInputEditText) view.findViewById(R.id.txt_guia_remision);
        txt_num_orden = (TextInputEditText) view.findViewById(R.id.txt_num_orden);
        txt_horometro = (TextInputEditText) view.findViewById(R.id.txt_horometro);
        txt_remanente = (TextInputEditText) view.findViewById(R.id.txt_remanente);
        txt_nombre_generador = (TextView) view.findViewById(R.id.txt_nombre_generador);
        ba1 = null;
        button_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
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

        FloatingActionButton button_grabar = (FloatingActionButton) view.findViewById(R.id.action_button_grabar_inicio_combustible);


        button_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registrarInicioCargaCombustibleGenerador();
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
            public void onClick(View v) {//Grabar la nueva alarma Reefer.
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


        String json_code=getArguments().getString("generador_resultado");
        Gson gson=new Gson();
        generador=new Generador();
        generador=gson.fromJson(json_code.toString(), Generador.class);
        txt_nombre_generador_st = generador.getNombre();
        uid_generador = generador.getId_rf_generador();
        txt_nombre_generador.setText(txt_nombre_generador_st);
        txt_nombre_generador.setEnabled(false);
        ImageView circleImageView= (ImageView) view.findViewById(R.id.image_circle_generador);
        String url_picture = "http://192.168.0.247:8080/PowerPackReefer/image?location_image=" + generador.getPicture();


        Picasso.with(this.getActivity())
                .load(url_picture)
                .transform(new CropSquareTransformation())
                .resize(500, 500)
                .centerCrop()
                .into(circleImageView);






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
            image_incio_generador.setImageBitmap(photo);
            image_incio_generador.setImageBitmap(photo);
            int w = photo.getWidth();
            int h = photo.getHeight();


            image_incio_generador.setImageBitmap(photo);
            image_incio_generador.setVisibility(View.VISIBLE);
            image_incio_generador.getLayoutParams().width = w * 2;
            image_incio_generador.getLayoutParams().height = h * 2;


        }


    }


    private void registrarInicioCargaCombustibleGenerador() {
        // Image location URL

        System.out.println("Ingresandooo en registrarGenerador()");


        txt_guia_remision_st = txt_guia_remision.getText().toString();
        txt_num_orden_st = txt_num_orden.getText().toString();
        txt_horometro_st = txt_horometro.getText().toString();
        txt_remanente_st = txt_remanente.getText().toString();
        horometro_fl = 0;
        remanente_fl = 0;
        txt_nombre_generador_st = txt_nombre_generador.getText().toString();


        int bandera = 0;

        String id_seleccionado = "";
        Proveedor proveedor;


        if (bandera == 0) {
            if (txt_guia_remision_st == null) {
                mostrarPopUp("Por favor ingrese el campo Guía de Remisión");
                bandera = 1;
            } else {
                txt_guia_remision_st = txt_guia_remision_st.trim();
                txt_guia_remision_st = txt_guia_remision_st.toUpperCase();

                if (txt_guia_remision_st.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Guía de Remisión");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (txt_num_orden_st == null) {
                mostrarPopUp("Por favor ingrese el campo No.- Orden.");
                bandera = 1;
            } else {
                txt_num_orden_st = txt_num_orden_st.trim();
                txt_num_orden_st = txt_num_orden_st.toUpperCase();

                if (txt_num_orden_st.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo No.- Orden.");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (txt_horometro_st == null) {
                mostrarPopUp("Por favor ingrese el campo Horometro");
                bandera = 1;
            } else {
                txt_horometro_st = txt_horometro_st.trim();
                txt_horometro_st = txt_horometro_st.toUpperCase();

                if (txt_horometro_st.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Horometro");
                    bandera = 1;
                } else {
                    horometro_fl = Float.parseFloat(txt_horometro_st);
                    if (horometro_fl <= 0) {
                        mostrarPopUp("El campo Horometro debe de ser mayor a cero");
                        bandera = 1;
                    }
                }


            }

        }


        if (bandera == 0) {
            if (txt_remanente_st == null) {
                mostrarPopUp("Por favor ingrese el campo Remanente");
                bandera = 1;
            } else {
                txt_remanente_st = txt_remanente_st.trim();
                txt_remanente_st = txt_remanente_st.toUpperCase();

                if (txt_remanente_st.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Remanente");
                    bandera = 1;
                } else {
                    remanente_fl = Float.parseFloat(txt_remanente_st);
                    if (remanente_fl <= 0) {
                        mostrarPopUp("El campo Remanente debe de ser mayor a cero");
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
                txt_label_mensaje.setText("¿ Seguro que desea registrar el inicio de carga de Combustible para el Generador (" + txt_nombre_generador_st + ") ?");
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


        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
        Object[] objects = new Object[7];
        objects[0] = uid_generador;
        objects[1] = usuario_logoneado_tr;
        objects[2] = txt_guia_remision_st;
        objects[3] = txt_num_orden_st;
        objects[4] = horometro_fl;
        objects[5] = remanente_fl;
        objects[6] = ba1;


        new ProcesarWs().execute(objects);



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
            //matrix.postRotate(180);  // La rotación debe ser decimal (float o double)
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
            Errores errores = ws_power_pack.registraInicioCargaCombustibleGenerador(objects[0].toString(),
                    objects[1].toString(),
                    objects[2].toString(),
                    objects[3].toString(),
                    Float.parseFloat(objects[4].toString()),
                    Float.parseFloat(objects[5].toString()),
                    objects[6].toString());

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (FragmentRegistroInicioCargaCombustible.this.pd != null) {
                FragmentRegistroInicioCargaCombustible.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                mostrarPopUpOk(errores_ws.getMsg_error());
            } else {
                mostrarPopUp(errores_ws.getMsg_error());
            }

        }
    }


}

package layout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.adapter.GeneradorAdapterRecyclerView;
import ec.com.tpg.tpgnews.adapter.PictureAdapterRecyclerView;
import ec.com.tpg.tpgnews.model.Contenedor;
import ec.com.tpg.tpgnews.model.ContenedorReefer;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Tomas;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;


public class DetalleTomasGenerador extends Fragment {
    public DetalleTomasGenerador() {
        // Required empty public constructor
    }

    Generador generador_gl;
    String usuario_logoneado;
    Toast toast_alert;
    View view_toast;
    TextView text_view_titulo;
    TextView text_view_mensaje;
    ImageView image_view_icono;
    RelativeLayout loadingPanelCardView;
    String nombre_generador_st;
    ProgressDialog pd;
    View view;
    ArrayList<Tomas> lista_tomas;
    com.github.clans.fab.FloatingActionButton acbutton_toma_vacia, acbutton_toma_daniada, acbutton_ver_toma, acbutton_asignar_cnt, acbutton_actualizar;
    String uid_toma_seleccionado ;
    FloatingActionMenu materialDesignFAM;
    String button_selecc;
    int estado_toma_int;
    ContenedorReefer contenedor_reefer;


    TextView txt_label_name_generador;
    TextView txt_label_name_toma;
    TextInputEditText txt_observacion;
    Dialog dialog_toma_generador = null;
    String txt_observacion_st=null;
    TextView txt_label_name_estado;
    Button button_aceptar_dialog;
    String uid_toma_st;

    Dialog dialog_conectar_cnt_toma = null;
    TextView txt_label_name_generador_cn;
    TextView txt_label_name_toma_cn;
    TextInputEditText txt_cnt_consulta;
    TextView txt_label_name_ubicacion;
    TextView txt_label_name_operacion;
    TextView txt_label_name_isocode;
    TextView txt_label_name_nave;
    TextView txt_label_name_linea;
    TextView txt_label_name_impo_expo;
    TextView txt_label_name_estado_cn;
    TextView txt_observacion_cn;
    Button button_aceptar_confirm;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("********** Cargando Detalle de Tomas **************");

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_detalle_tomas_generador, container, false);

        dialog_toma_generador = new Dialog(view.getContext());
        dialog_toma_generador.setContentView(R.layout.popup_toma_vacia_daniada);
        dialog_toma_generador.setCancelable(false);
        dialog_toma_generador.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        txt_label_name_generador= (TextView)  dialog_toma_generador.findViewById(R.id.txt_label_name_generador);
        txt_label_name_toma= (TextView)  dialog_toma_generador.findViewById(R.id.txt_label_name_toma);
        txt_label_name_estado=(TextView)  dialog_toma_generador.findViewById(R.id.txt_label_name_estado);
        txt_observacion= (TextInputEditText)  dialog_toma_generador.findViewById(R.id.txt_observacion);


        android.support.design.widget.FloatingActionButton btnclose_encender = (android.support.design.widget.FloatingActionButton) dialog_toma_generador.findViewById(R.id.floating_button_close);
        btnclose_encender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_toma_generador.hide();
            }
        });

        Button button_cancelar_confirm = (Button) dialog_toma_generador.findViewById(R.id.button_cancelar_confirm);
        button_cancelar_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_toma_generador.hide();
            }
        });



        button_aceptar_dialog = (Button) dialog_toma_generador.findViewById(R.id.button_aceptar_confirm);



        dialog_conectar_cnt_toma = new Dialog(view.getContext());
        dialog_conectar_cnt_toma.setContentView(R.layout.popup_conectar_toma_cnt);
        dialog_conectar_cnt_toma.setCancelable(false);
        dialog_conectar_cnt_toma.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        txt_label_name_generador_cn= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_generador);
        txt_label_name_toma_cn= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_toma);
        txt_cnt_consulta= (TextInputEditText)  dialog_conectar_cnt_toma.findViewById(R.id.txt_cnt_consulta);
        txt_label_name_ubicacion= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_ubicacion);
        txt_label_name_operacion= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_operacion);
        txt_label_name_isocode= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_isocode);
        txt_label_name_nave= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_nave);
        txt_label_name_linea= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_linea);
        txt_label_name_impo_expo= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_impo_expo);
        txt_label_name_estado_cn= (TextView)  dialog_conectar_cnt_toma.findViewById(R.id.txt_label_name_estado);
        txt_observacion_cn= (TextInputEditText)  dialog_conectar_cnt_toma.findViewById(R.id.txt_observacion);



        android.support.design.widget.FloatingActionButton btnclose_conectar = (android.support.design.widget.FloatingActionButton) dialog_conectar_cnt_toma.findViewById(R.id.floating_button_close);
        btnclose_conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_conectar_cnt_toma.hide();
            }
        });

        Button button_cancelar_confirm_cnt_toma = (Button) dialog_conectar_cnt_toma.findViewById(R.id.button_cancelar_confirm);
        button_cancelar_confirm_cnt_toma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_conectar_cnt_toma.hide();
            }
        });



        button_aceptar_confirm = (Button) dialog_conectar_cnt_toma.findViewById(R.id.button_aceptar_confirm);
        button_aceptar_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contenedor_reefer.getCor_operacion() ==0 || contenedor_reefer.getAno_operacion() ==0 )
                {

                    showToastError("Por favor consulte los datos del contenedor ", "Error ("+generador_gl.getNombre()+")");
                }
                else
                {
                    showToastOk("Proceso de conexion de toma a contenedor", "Registro Exitoso ("+generador_gl.getNombre()+")");
                }
            }
        });


        txt_cnt_consulta.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                System.out.println("Validando la tecla presionada: ");
                //txt_cnt.setText(txt_cnt.getText().toString().toUpperCase());
                if (txt_cnt_consulta.getText().toString().length() >= 4) {
                    System.out.println("La longitud de la cadena del contenedor es mayor a 4");
                    txt_cnt_consulta.setInputType(InputType.TYPE_CLASS_NUMBER);
                    txt_cnt_consulta.setSelection(txt_cnt_consulta.getText().toString().length());

                } else {
                    System.out.println("La longitud de la cadena del contenedor es menor a 4");
                    txt_cnt_consulta.setInputType(InputType.TYPE_CLASS_TEXT);
                    txt_cnt_consulta.setSelection(txt_cnt_consulta.getText().toString().length());
                    //txt_cnt_consulta.setText(txt_cnt_consulta.getText().toString().toUpperCase());
                }

                return;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                return;
            }
            @Override
            public void afterTextChanged(Editable s) {
                return;
            }

        });



        txt_cnt_consulta.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    consultaContenedor();
                    return true;
                }

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_DEL)) {
                    // Perform action on key press
                    txt_label_name_ubicacion.setText("");
                    txt_label_name_operacion.setText("");
                    txt_label_name_isocode.setText("");
                    txt_label_name_nave.setText("");
                    txt_label_name_linea.setText("");
                    txt_label_name_impo_expo.setText("");
                    contenedor_reefer=new ContenedorReefer();
                    return false;
                }

                return false;
                // return false;
            }

        });


        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.acbutton_menu);


        uid_toma_seleccionado="NO_SELECCIONADO";


        acbutton_toma_vacia = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_toma_vacia);
        acbutton_toma_daniada = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_toma_daniada);
        acbutton_ver_toma = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_ver_toma);
        acbutton_asignar_cnt = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_asignar_cnt);
        acbutton_actualizar = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_actualizar);

        acbutton_toma_vacia.setColorNormal(Color.parseColor("#7D7C7C"));
        acbutton_toma_vacia.setColorPressed(Color.parseColor("#B7B5B5"));



        acbutton_toma_daniada.setColorNormal(Color.parseColor("#F30707"));
        acbutton_toma_daniada.setColorPressed(Color.parseColor("#ED3F3F"));


        acbutton_ver_toma.setColorNormal(Color.parseColor("#FFFF00"));
        acbutton_ver_toma.setColorPressed(Color.parseColor("#D7DF01"));


        acbutton_asignar_cnt.setColorNormal(Color.parseColor("#06D309"));
        acbutton_asignar_cnt.setColorPressed(Color.parseColor("#31E634"));


        acbutton_actualizar.setColorNormal(Color.parseColor("#0489B1"));
        acbutton_actualizar.setColorPressed(Color.parseColor("#31E634"));


        materialDesignFAM.setMenuButtonLabelText("Seleccione la Toma");


        acbutton_toma_vacia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastError("Por favor seleccione la Toma que desea Procesar", "Acción Toma Vacía");

            }
        });
        acbutton_toma_daniada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastError("Por favor seleccione la Toma que desea Procesar", "Acción Toma Dañada");

            }
        });

        acbutton_ver_toma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastError("Por favor seleccione la Toma que desea Procesar", "Acción Ver Toma");

            }
        });



        acbutton_asignar_cnt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToastError("Por favor seleccione la Toma que desea Procesar", "Acción Asignar CNT");

            }
        });





        view_toast = inflater.inflate(R.layout.toast_alert_error, (ViewGroup) getActivity().findViewById(R.id.relativeLayout1));

        toast_alert = new Toast(getActivity().getApplicationContext());
        toast_alert.setView(view_toast);
        text_view_titulo= (TextView) view_toast.findViewById(R.id.text_view_titulo);
        text_view_mensaje= (TextView) view_toast.findViewById(R.id.text_view_mensaje);
        image_view_icono = (ImageView) view_toast.findViewById(R.id.image_view_icono);


        ImageView picture_card_image_generador= (ImageView) view.findViewById(R.id.picture_card_image_generador);
        TextView nombre_generador=(TextView) view.findViewById(R.id.nombre_generador) ;
        ImageView img_apagado= (ImageView) view.findViewById(R.id.img_apagado);
        ImageView img_abasteciendo_cmbtbl= (ImageView) view.findViewById(R.id.img_abasteciendo_cmbtbl);
        TextView filas_columnas=(TextView) view.findViewById(R.id.filas_columnas) ;
        TextView tomas_disponibles=(TextView) view.findViewById(R.id.tomas_disponibles) ;
        TextView txt_empresa_proveedora=(TextView) view.findViewById(R.id.txt_empresa_proveedora) ;
        TextView txt_bloque=(TextView) view.findViewById(R.id.txt_bloque) ;
        TextView txt_fecha_desde=(TextView) view.findViewById(R.id.txt_fecha_desde) ;
        loadingPanelCardView = (RelativeLayout) view.findViewById(R.id.loadingPanelCardView);

        String generador=getArguments().getString("generador");
        Gson gson=new Gson();
        generador_gl=gson.fromJson(generador, Generador.class);
        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        usuario_logoneado = myIntent.getStringExtra("usuario_logoneado");


        String generador_encendido_apagado="APAGADO";

        filas_columnas.setText("FxC : " + String.valueOf(generador_gl.getFilas()) + "x" + String.valueOf(generador_gl.getColumnas()));
        nombre_generador.setText(generador_gl.getNombre());
        nombre_generador_st=generador_gl.getNombre();
        tomas_disponibles.setText("Ocpds: " + String.valueOf(generador_gl.getTomas_conectadas()) + " Utiles: " + String.valueOf(generador_gl.getTomas_utiles()) + " Libres: " + generador_gl.getCant_disponible());
        txt_bloque.setText("Bloque : " + generador_gl.getBloque());
        txt_empresa_proveedora.setText(generador_gl.getNombre_razon_social_proveedor());
        if (generador_gl.getHoras_conexion().equals(".")) {
            txt_fecha_desde.setText("APAGADO");
            generador_encendido_apagado="APAGADO";
            img_apagado.setVisibility(View.VISIBLE);
        } else {
            txt_fecha_desde.setText(generador_gl.getFecha_inicio_conexion() + "         " + generador_gl.getHoras_conexion() + " Horas");
            generador_encendido_apagado="ENCENDIDO";
            img_apagado.setVisibility(View.INVISIBLE);
        }

        if(generador_gl.getFinalizo_carga_combustible().equals("NO"))//SI AUN NO TERMINA DE ABASTECER muestra el icono de la manguera que indica que esta abasteciendo.
        {
            img_abasteciendo_cmbtbl.setVisibility(View.VISIBLE);
        }
        else
        {
            img_abasteciendo_cmbtbl.setVisibility(View.INVISIBLE); //Si no esta abasteciendo oculta el icono.
        }
        //Para visualizar la imagen desde internet.
        String url_picture = "http://192.168.0.247:8080/PowerPackReefer/image?location_image=" + generador_gl.getPicture();

        Picasso.with(getActivity())
                .load(url_picture)
                .transform(new GeneradorAdapterRecyclerView.CropSquareTransformation())
                .resize(500, 500)
                .centerCrop()
                //.placeholder(R.drawable.loading)
                .into(picture_card_image_generador, new Callback() {

                    @Override
                    public void onSuccess() {

                        loadingPanelCardView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        loadingPanelCardView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Ocurrio un Error al tratar de cargar la Imagen del Generador ("+nombre_generador_st+") del servidor",
                                Toast.LENGTH_LONG).show();

                    }
                });




        pd = ProgressDialog.show(getActivity(), "Cargando", "Consultando el estado de Tomas...", true, false);

        Object[] objects = new Object[2];
        objects[0] = generador_gl.getNombre();
        objects[1] = usuario_logoneado;
        new ProcesarWs().execute(objects);





        FloatingActionButton action_button_back = (FloatingActionButton) view.findViewById(R.id.action_button_back);
        action_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentListadoGeneradores fragment_listado_generadores = new FragmentListadoGeneradores();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();
            }
        });

        System.out.println("********** Fin Cargando Detalle de Tomas **************");
        return view;
    }


    public void showToastError(String mensaje, String titulo)
    {
        if (DetalleTomasGenerador.this.pd != null) {
            DetalleTomasGenerador.this.pd.dismiss();
        }
        image_view_icono.setImageResource(R.drawable.icons_warning);
        text_view_mensaje.setText(mensaje);
        text_view_titulo.setText(titulo);
        text_view_titulo.setTextColor(Color.RED);
        toast_alert.show();
    }


    public void showToastOk(String mensaje, String titulo)
    {
        if (DetalleTomasGenerador.this.pd != null) {
            DetalleTomasGenerador.this.pd.dismiss();
        }
        image_view_icono.setImageResource(R.drawable.icon_successful);
        text_view_mensaje.setText(mensaje);
        text_view_titulo.setText(titulo);
        text_view_titulo.setTextColor(Color.GREEN);
        toast_alert.show();
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
            //matrix.postRotate(ExifInterface.ORIENTATION_ROTATE_90);  // La rotación debe ser decimal (float o double)
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

            WebServicesPowerPack ws_power=new WebServicesPowerPack();
            Errores errores=ws_power.consultaTomasGenerador(generador_gl.getId_rf_generador(), usuario_logoneado);
            lista_tomas=ws_power.getArray_tomas();
            //String tomas_st=gson.toJson(lista_tomas);
            //System.out.println("--->"+tomas_st);

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (DetalleTomasGenerador.this.pd != null) {
                DetalleTomasGenerador.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                dibujarBotonesTomas();
            } else {
                showToastError(errores_ws.getMsg_error(), "Error ("+generador_gl.getNombre()+")");
            }

        }
    }




    public void  dibujarBotonesTomas()
    {

        if(lista_tomas.isEmpty() )
        {
            showToastError("Ocurrio un error al obtener estado de Tomas.", "Error ("+generador_gl.getNombre()+")");

        }
        else {


            acbutton_actualizar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("generador", new Gson().toJson(generador_gl));
                    DetalleTomasGenerador detalleTomasGenerador = new DetalleTomasGenerador();
                    detalleTomasGenerador.setArguments(bundle);
                    ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, detalleTomasGenerador).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            addToBackStack(null).commit();
                }
            });


            int posicion_toma = 0;


            for (int k = 0; k < lista_tomas.get(0).getTotal_fila(); k++) {

                LinearLayout layout_principal = (LinearLayout) view.findViewById(R.id.linear_principal_detalle_tomas);
                LinearLayout layout_fila = new LinearLayout(view.getContext());
                layout_fila.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i < lista_tomas.get(0).getTotal_columna(); i++) {
                    Button btn = null;
                    int buttonStyle = 0;
                    btn = new Button(view.getContext());

                    if (lista_tomas.get(posicion_toma).getEstado() == 20) { //Color Verde (Ocupado)
                        buttonStyle = R.style.GreenButtonTheme;
                        btn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    } else {

                        if (lista_tomas.get(posicion_toma).getEstado() == 30) { //Color Blanco Disponible
                            buttonStyle = R.style.WhiteButtonTheme;
                            btn.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

                        } else {
                            if (lista_tomas.get(posicion_toma).getEstado() == 40) { //Color Gris (VACIO)

                                buttonStyle = R.style.GrisButtonTheme;
                                btn.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                            } else {

                                if (lista_tomas.get(posicion_toma).getEstado() == 10) { //Color ROJO (DAÑADO)
                                    buttonStyle = R.style.RedButtonTheme;
                                    btn.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                                } else {
                                    System.out.println("------------------------------NO EXISTE COLOR----------------------------------");
                                    btn.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                                    buttonStyle = R.style.BlueButtonTheme;
                                }
                            }
                        }

                    }

                    btn.setText(String.valueOf(lista_tomas.get(posicion_toma).getFila()) + "-" + String.valueOf(lista_tomas.get(posicion_toma).getColumna()));
                    button_selecc= btn.getText().toString();
                    estado_toma_int=lista_tomas.get(posicion_toma).getEstado();
                    uid_toma_st =  lista_tomas.get(posicion_toma).getUid_toma();
                    btn.setOnClickListener(new View.OnClickListener() {
                        String boton_seleccionado=button_selecc;
                        String uid_toma= uid_toma_st;
                        int estado_toma= estado_toma_int;

                        @Override
                        public void onClick(View v) {


                            txt_label_name_generador.setText(generador_gl.getNombre());
                            txt_label_name_toma.setText(boton_seleccionado);


                            txt_label_name_generador_cn.setText(generador_gl.getNombre());
                            txt_label_name_toma_cn.setText(boton_seleccionado);
                            txt_observacion.setText("");
                            txt_observacion_cn.setText("");
                            txt_label_name_estado_cn.setText("CONECTADO");

                            acbutton_toma_vacia.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    txt_label_name_estado.setText("VACÍA");
                                    dialog_toma_generador.show();
                                    button_aceptar_dialog.setOnClickListener(new View.OnClickListener() {


                                        @Override
                                        public void onClick(View v) {
                                            dialog_toma_generador.hide();
                                            pd = ProgressDialog.show(getActivity(), "Cargando", "Actualizando Toma a estado VACÍA...", true, false);

                                            Object[] objects = new Object[4];
                                            objects[0] = uid_toma;
                                            objects[1] = usuario_logoneado;
                                            objects[2] = String.valueOf(40);
                                            objects[3] = txt_observacion.getText().toString().trim();

                                            new ProcesarEstadoToma().execute(objects);

                                        }
                                    });


                                }
                            });
                            acbutton_toma_daniada.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    txt_label_name_estado.setText("DAÑADA");
                                    dialog_toma_generador.show();
                                    button_aceptar_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog_toma_generador.hide();
                                            pd = ProgressDialog.show(getActivity(), "Cargando", "Actualizando Toma a estado DAÑADA...", true, false);

                                            Object[] objects = new Object[4];
                                            objects[0] = uid_toma;
                                            objects[1] = usuario_logoneado;
                                            objects[2] = String.valueOf(10);
                                            objects[3] = txt_observacion.getText().toString().trim();

                                            new ProcesarEstadoToma().execute(objects);

                                        }
                                    });

                                }
                            });

                            acbutton_ver_toma.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    showToastError("Por favor seleccione la Toma que desea Procesar", "Acción Ver Toma");

                                }
                            });



                            acbutton_asignar_cnt.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    dialog_conectar_cnt_toma.show();
                                    txt_label_name_estado_cn.setText("CONECTADO");

                                }
                            });




                            materialDesignFAM.setMenuButtonLabelText("Toma Seleccionada : "+ boton_seleccionado );
                            materialDesignFAM.open(true);

                        }
                    });

                    System.out.println("VERDE----->" + String.valueOf(lista_tomas.get(posicion_toma).getFila()) + "-" + String.valueOf(lista_tomas.get(posicion_toma).getColumna()) + ", " + posicion_toma);
                    LayoutParams params = new LayoutParams(layout_principal.getLayoutParams());
                    params.weight = 1.0f;
                    btn.setLayoutParams(params);
                    layout_fila.addView(btn);
                    posicion_toma = posicion_toma + 1;

                }

                layout_principal.addView(layout_fila);
            }
        }
    }




    private class ProcesarEstadoToma extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {
            if(objects == null)
            {
                System.out.println("Array size:  NULL");
            }
            else
            {
                System.out.println("Array size:  "+objects.length+ "-"+objects[0]);
            }

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores = ws_power_pack.actualizaTomaDaniadoVacioS(  objects[0].toString(),
                    objects[1].toString(),
                    Integer.parseInt(objects[2].toString()),
                    objects[3].toString());

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (DetalleTomasGenerador.this.pd != null) {
                DetalleTomasGenerador.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {

                image_view_icono.setImageResource(R.drawable.icon_successful);
                text_view_titulo.setText("Actualizacion Toma ("+generador_gl.getNombre()+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.GREEN);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();
                Bundle bundle = new Bundle();
                Gson gson=new Gson();
                bundle.putString("generador", gson.toJson(generador_gl));
                DetalleTomasGenerador fragment_tomas_generador = new DetalleTomasGenerador();
                fragment_tomas_generador.setArguments(bundle);

                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_tomas_generador).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();

            } else {
                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_titulo.setText("Actualizacion Toma ("+generador_gl.getNombre()+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();

            }

        }
    }







    private class ProcesarConsultaCnt extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores = ws_power_pack.consultaContenedorReefer(objects[0].toString(),
                    objects[1].toString(),
                    objects[2].toString());
            contenedor_reefer= ws_power_pack.getContenedor_reefer();

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (DetalleTomasGenerador.this.pd != null) {
                DetalleTomasGenerador.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {
                txt_cnt_consulta.setText(txt_cnt_consulta.getText().toString().toUpperCase().trim());
                txt_label_name_ubicacion.setText(contenedor_reefer.getUbicacion());
                txt_label_name_operacion.setText(contenedor_reefer.getAno_operacion()+"-"+contenedor_reefer.getCor_operacion());
                txt_label_name_isocode.setText(contenedor_reefer.getIsocode_descripcion());
                txt_label_name_nave.setText(contenedor_reefer.getNombre_nave());
                txt_label_name_linea.setText(contenedor_reefer.getNombre_linea());
                txt_label_name_impo_expo.setText(contenedor_reefer.getImpo_expo());
            } else {
                showToastError(errores_ws.getMsg_error(), "Error ("+generador_gl.getNombre()+")");

            }

        }
    }





    public void consultaContenedor() {
        System.out.println(">>>>>>>>>>>>>>> Consultando contenedor<<<<<<<<<<<<<<");
        String cnt = txt_cnt_consulta.getText().toString();


        if(cnt==null)
        {
            System.out.println(">>>>>>>>>>>>>>>1<<<<<<<<<<<<<<");
            showToastError("Por favor ingrese el número de Contenedor", "Error Contenedor");
        }
        else
        {
            if(cnt.isEmpty())
            {
                System.out.println(">>>>>>>>>>>>>>>2<<<<<<<<<<<<<<");
                showToastError("Por favor ingrese el número de Contenedor", "Error Contenedor");
            }
            else
            {
                cnt = cnt.toUpperCase();
                cnt = cnt.trim();



                if(cnt.length()!=11)
                {    System.out.println(">>>>>>>>>>>>>>>3<<<<<<<<<<<<<<");
                     showToastError("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).", "Error Contenedor");
                }
                else
                {
                    String sigla_cnt=new String();
                    String num_cnt=new String();
                    sigla_cnt = cnt.substring(0, 4);
                    num_cnt=cnt.substring(5);
                    int bandera_ok=0;
                    for(int ik=0; ik<sigla_cnt.length();ik++)
                    {
                        char char_letra=sigla_cnt.charAt(ik);
                        if(!Character.isLetter(char_letra))
                        {    System.out.println(">>>>>>>>>>>>>>>4<<<<<<<<<<<<<<");
                             showToastError("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).", "Error Contenedor");

                            bandera_ok= 1;
                            break;

                        }
                    }

                    if(bandera_ok==0)
                    {
                        for(int ik=0; ik<num_cnt.length();ik++)
                        {
                            char char_letra=num_cnt.charAt(ik);
                            if(!Character.isDigit(char_letra))
                            {  System.out.println(">>>>>>>>>>>>>>>5<<<<<<<<<<<<<<");
                                showToastError("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).", "Error Contenedor");
                                bandera_ok= 1;
                                break;

                            }
                        }
                    }

                    if(bandera_ok==0)
                    {
                        pd = ProgressDialog.show(getActivity(), "Cargando", "Consultando contenedor...", true, false);

                        Object[] objects = new Object[3];
                        objects[0] = generador_gl.getId_rf_generador();
                        objects[1] = usuario_logoneado;
                        objects[2] = cnt;


                        new ProcesarConsultaCnt().execute(objects);



                    }








                }


            }



        }




    }


}

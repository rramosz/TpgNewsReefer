package ec.com.tpg.tpgnews.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;




import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.menu_principal;
import ec.com.tpg.tpgnews.model.CargaCombustibleGenerador;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Picture;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;
import layout.DetalleTomasGenerador;
import layout.FragmentListadoGeneradores;
import layout.FragmentNuevoGenerador;
import layout.FragmentRegAlarmaContenedor;
import layout.FragmentRegistroFinCargaCombustible;
import layout.FragmentRegistroInicioCargaCombustible;
import layout.activity_consulta_contenedor;
import com.squareup.picasso.Callback;

/**
 * Created by Ricky Ramos. on 6/2/2018.
 */

public class GeneradorAdapterRecyclerView extends RecyclerView.Adapter<GeneradorAdapterRecyclerView.GeneradorViewHolder> {

    private ArrayList<Generador> generadores;
    private int resource;
    private Activity activity;
    private String nombre_generador;
    private String uid_generador;
    private String uid_seleccionado="NO_SELECCIONADO";
    Dialog dialog_alert = null;
    Dialog dialog_encender_generador = null;
    Dialog dialog_apagar_generador = null;
    TextView txt_label_name_generador = null;
    TextInputEditText txt_ubicacion=null;
    TextInputEditText txt_remanente=null;
    Button boton_aceptar_encender_generador=null;
    String generador_encendido_apagado=null;
    ProgressDialog pd;
    String  txt_ubicacion_st=null;
    String  txt_remanente_st=null;
    float   txt_remanente_fl;
    Toast toast_alert;
    View view_toast;
    TextView text_view_titulo= null;
    TextView text_view_mensaje= null;
    ImageView image_view_icono=null;






    TextView txt_label_name_apaga_generador = null;
    TextInputEditText txt_apaga_ubicacion=null;
    TextInputEditText txt_apaga_remanente=null;
    Button boton_aceptar_apaga_generador=null;
    String  txt_remanente_apagado_st=null;
    float   txt_remanente_apagado_fl;

    public GeneradorAdapterRecyclerView(ArrayList<Generador> generadores, int resources, Activity activity) {
        this.generadores = generadores;
        this.resource = resources;
        this.activity = activity;
    }

    @Override
    public GeneradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        dialog_encender_generador = new Dialog(activity);
        dialog_encender_generador.setContentView(R.layout.popup_enciende_generador);
        dialog_encender_generador.setCancelable(false);
        dialog_encender_generador.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.support.design.widget.FloatingActionButton btnclose_encender = (android.support.design.widget.FloatingActionButton) dialog_encender_generador.findViewById(R.id.floating_button_close);
        btnclose_encender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_encender_generador.hide();
            }
        });



        dialog_apagar_generador= new Dialog(activity);
        dialog_apagar_generador.setContentView(R.layout.popup_apaga_generador);
        dialog_apagar_generador.setCancelable(false);
        dialog_apagar_generador.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.support.design.widget.FloatingActionButton btnclose_apagar = (android.support.design.widget.FloatingActionButton) dialog_apagar_generador.findViewById(R.id.floating_button_close);
        btnclose_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_apagar_generador.hide();
            }
        });

        Button boton_cancelar_apagar = (Button) dialog_apagar_generador.findViewById(R.id.button_cancelar_confirm);
        boton_cancelar_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_apagar_generador.hide();
            }
        });




        txt_label_name_apaga_generador= (TextView) dialog_apagar_generador.findViewById(R.id.txt_label_name_generador);
        txt_apaga_ubicacion = (TextInputEditText) dialog_apagar_generador.findViewById(R.id.txt_ubicacion);
        txt_apaga_ubicacion.setEnabled(false);
        txt_apaga_remanente = (TextInputEditText) dialog_apagar_generador.findViewById(R.id.txt_remanente);


        txt_label_name_generador= (TextView) dialog_encender_generador.findViewById(R.id.txt_label_name_generador);
        txt_ubicacion = (TextInputEditText) dialog_encender_generador.findViewById(R.id.txt_ubicacion);
        txt_remanente = (TextInputEditText) dialog_encender_generador.findViewById(R.id.txt_remanente);





        Button boton_cancelar = (Button) dialog_encender_generador.findViewById(R.id.button_cancelar_confirm);
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_encender_generador.hide();
            }
        });





        LayoutInflater inflater = activity.getLayoutInflater();
        view_toast = inflater.inflate(R.layout.toast_alert_error, (ViewGroup) activity.findViewById(R.id.relativeLayout1));

        toast_alert = new Toast(activity.getApplicationContext());
        toast_alert.setView(view_toast);
         text_view_titulo= (TextView) view_toast.findViewById(R.id.text_view_titulo);
         text_view_mensaje= (TextView) view_toast.findViewById(R.id.text_view_mensaje);
         image_view_icono = (ImageView) view_toast.findViewById(R.id.image_view_icono);


        /*android.support.design.widget.FloatingActionButton btnclose = (android.support.design.widget.FloatingActionButton) dialog_alert.findViewById(R.id.floating_button_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarPopupAlert();
            }
        });*/
        FloatingActionMenu materialDesignFAM;
        materialDesignFAM = (FloatingActionMenu) activity.findViewById(R.id.acbutton_menu);

        FloatingActionButton acbutton_cargar_combustible, acbutton_encender_apagar_generador, acbutton_tomas, acbutton_agregar_generador,acbutton_actualizar;
        uid_seleccionado="NO_SELECCIONADO";


        acbutton_cargar_combustible = (FloatingActionButton) activity.findViewById(R.id.acbutton_cargar_combustible);
        acbutton_encender_apagar_generador = (FloatingActionButton) activity.findViewById(R.id.acbutton_encender_apagar_generador);
        acbutton_tomas = (FloatingActionButton) activity.findViewById(R.id.acbutton_tomas);
        acbutton_agregar_generador = (FloatingActionButton) activity.findViewById(R.id.acbutton_agregar_generador);
        acbutton_actualizar = (FloatingActionButton) activity.findViewById(R.id.acbutton_actualizar);


        acbutton_cargar_combustible.setColorNormal(Color.parseColor("#01DF01"));
        acbutton_cargar_combustible.setColorPressed(Color.parseColor("#04B45F"));



        acbutton_encender_apagar_generador.setColorNormal(Color.parseColor("#FF8000"));
        acbutton_encender_apagar_generador.setColorPressed(Color.parseColor("#DF7401"));


        acbutton_tomas.setColorNormal(Color.parseColor("#FFFF00"));
        acbutton_tomas.setColorPressed(Color.parseColor("#D7DF01"));


        acbutton_agregar_generador.setColorNormal(Color.parseColor("#2E64FE"));
        acbutton_agregar_generador.setColorPressed(Color.parseColor("#0040FF"));

        acbutton_actualizar.setColorNormal(Color.parseColor("#0489B1"));
        acbutton_actualizar.setColorPressed(Color.parseColor("#086A87"));


        if(uid_seleccionado.equals("NO_SELECCIONADO"))
        {
            materialDesignFAM.setMenuButtonLabelText("Seleccione el Generador");



            acbutton_cargar_combustible.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(activity, "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });
            acbutton_encender_apagar_generador.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(activity, "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });

            acbutton_tomas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(activity, "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });


            acbutton_agregar_generador.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentNuevoGenerador fragment_nuevo_cnt = new FragmentNuevoGenerador();
                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_nuevo_cnt).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            addToBackStack(null).commit();
                }
            });

            acbutton_actualizar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentListadoGeneradores listado_generadores = new FragmentListadoGeneradores();

                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, listado_generadores).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            addToBackStack(null).commit();

                }
            });


        }
        //materialDesignFAM.open(false);



        return new GeneradorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GeneradorViewHolder holder, int position) {

        final Generador generador = generadores.get(position);
        generador_encendido_apagado="APAGADO";

        holder.filas_columnas.setText("FxC : " + String.valueOf(generador.getFilas()) + "x" + String.valueOf(generador.getColumnas()));
        holder.nombre_generador.setText(generador.getNombre());
        nombre_generador=generador.getNombre();
        uid_generador=generador.getId_rf_generador();
        holder.tomas_disponibles.setText("Ocpds: " + String.valueOf(generador.getTomas_conectadas()) + " Utiles: " + String.valueOf(generador.getTomas_utiles()) + " Libres: " + generador.getCant_disponible());
        holder.txt_bloque.setText("Bloque : " + generador.getBloque());
        holder.txt_empresa_proveedora.setText(generador.getNombre_razon_social_proveedor());
        if (generador.getHoras_conexion().equals(".")) {
            holder.txt_fecha_desde.setText("APAGADO");
            generador_encendido_apagado="APAGADO";
            holder.img_apagado.setVisibility(View.VISIBLE);
        } else {
            holder.txt_fecha_desde.setText(generador.getFecha_inicio_conexion() + "         " + generador.getHoras_conexion() + " Horas");
            generador_encendido_apagado="ENCENDIDO";
            holder.img_apagado.setVisibility(View.INVISIBLE);
        }

        if(generador.getFinalizo_carga_combustible().equals("NO"))//SI AUN NO TERMINA DE ABASTECER muestra el icono de la manguera que indica que esta abasteciendo.
        {
            holder.img_abasteciendo_cmbtbl.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.img_abasteciendo_cmbtbl.setVisibility(View.INVISIBLE); //Si no esta abasteciendo oculta el icono.
        }
        //Para visualizar la imagen desde internet.
        String url_picture = "http://192.168.0.247:8080/PowerPackReefer/image?location_image=" + generador.getPicture();



        //Picasso.with(activity).load(url_picture).resize(50, 50).centerCrop().into(holder.picture_card_image_generador);

        /*Picasso.with(activity)
                .load(url_picture)
                .transform(new CropSquareTransformation())
                .resize(500, 500)
                .centerCrop()
                .into(holder.picture_card_image_generador);*/


        /*Inicio Carga la imagen en el card view*/

        /*Picasso picasso = new Picasso.Builder(activity).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                // Do something here
                activity.findViewById(R.id.loadingPanelCardView).setVisibility(View.GONE);
                Toast.makeText(activity, "Ocurrio un Error al tratar de cargar la Imagen del Generador ("+nombre_generador+") del servidor",
                        Toast.LENGTH_LONG).show();
            }
        }).build();*/

        // Load the image into image view from assets folder
        Picasso.with(activity)
                .load(url_picture)
                .transform(new CropSquareTransformation())
                .resize(500, 500)
                .centerCrop()
                //.placeholder(R.drawable.loading)
                .into(holder.picture_card_image_generador, new Callback() {

                    @Override
                    public void onSuccess() {

                        holder.loadingPanelCardView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holder.loadingPanelCardView.setVisibility(View.GONE);
                        Toast.makeText(activity, "Ocurrio un Error al tratar de cargar la Imagen del Generador ("+nombre_generador+") del servidor",
                                Toast.LENGTH_LONG).show();

                    }
                });

        /*Fin Carga la imagen en el card View*/










        /*byte[] decodedString = Base64.decode(generador.getPicture(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.picture_card_image_generador.setImageBitmap(decodedByte);*/
        holder.picture_card_image_generador.setOnClickListener(new View.OnClickListener() {


            String generador_local= nombre_generador;
            String uid_generador_local= uid_generador;


            @Override
            public void onClick(View view) {


                FloatingActionMenu materialDesignFAM;
                materialDesignFAM = (FloatingActionMenu) activity.findViewById(R.id.acbutton_menu);
                materialDesignFAM.open(false);
                FloatingActionButton acbutton_cargar_combustible, acbutton_encender_apagar_generador, acbutton_tomas, acbutton_agregar_generador;
                uid_seleccionado=uid_generador;


                acbutton_cargar_combustible = (FloatingActionButton) activity.findViewById(R.id.acbutton_cargar_combustible);
                acbutton_encender_apagar_generador = (FloatingActionButton) activity.findViewById(R.id.acbutton_encender_apagar_generador);
                acbutton_tomas = (FloatingActionButton) activity.findViewById(R.id.acbutton_tomas);
                acbutton_agregar_generador = (FloatingActionButton) activity.findViewById(R.id.acbutton_agregar_generador);




                if(uid_seleccionado.equals("NO_SELECCIONADO"))
                {
                    materialDesignFAM.setMenuButtonLabelText("Seleccione el Generador");
                }
                else
                {
                    materialDesignFAM.setMenuButtonLabelText(generador_local);
                }

                //Ocultar Menu De la Tablet.
                //materialDesignFAM.setVisibility(View.INVISIBLE);


                //Mostrar Menu De la Tablet.
                materialDesignFAM.setVisibility(View.VISIBLE);


                acbutton_cargar_combustible.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        Bundle bundle = new Bundle();
                        Generador generador_resultado=new Generador();
                        Gson gson=new Gson();
                        WebServicesPowerPack wsp=new WebServicesPowerPack();
                        Intent myIntent = activity.getIntent(); // gets the previously created intent
                        String usuario_logoneado = myIntent.getStringExtra("usuario_logoneado");
                        Errores errores_result=wsp.consultaGeneradorId(usuario_logoneado,uid_generador_local  );
                        if(errores_result.getCod_error()==0) {
                            generador_resultado=wsp.getGenerador();
                            CargaCombustibleGenerador carga_combust=new CargaCombustibleGenerador();
                            carga_combust=wsp.getCarga_combustible_generador();
                            bundle.putString("generador_resultado", gson.toJson(generador_resultado));
                            bundle.putString("carga_combustible", gson.toJson(carga_combust));

                            if(generador.getFinalizo_carga_combustible().equals("SI")) {

                                FragmentRegistroInicioCargaCombustible fragment_inicio_carga = new FragmentRegistroInicioCargaCombustible();
                                fragment_inicio_carga.setArguments(bundle);
                                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_inicio_carga).
                                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                        addToBackStack(null).commit();
                            }
                            else
                            {
                                FragmentRegistroFinCargaCombustible fragment_fin_carga = new FragmentRegistroFinCargaCombustible();
                                fragment_fin_carga.setArguments(bundle);
                                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_fin_carga).
                                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                        addToBackStack(null).commit();
                            }
                        }
                        else
                        {



                            dialog_alert.setContentView(R.layout.popup_alert);
                            dialog_alert.setCancelable(false);
                            dialog_alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            android.support.design.widget.FloatingActionButton btnclose = (android.support.design.widget.FloatingActionButton) dialog_alert.findViewById(R.id.floating_button_close);
                            btnclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ocultarPopupAlert();
                                }
                            });

                            TextView txt_label_mensaje = (TextView) dialog_alert.findViewById(R.id.mensaje_descripcion);
                            txt_label_mensaje.setText(errores_result.getMsg_error());
                            dialog_alert.show();



                        }


                    }
                });



                acbutton_encender_apagar_generador.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        nombre_generador=generador.getNombre();
                        if(holder.txt_fecha_desde.getText().toString().equals("APAGADO")) //Si el Generador esta apagado  se muestra el popup para poder encender el Generador
                        {
                            dialog_encender_generador.show();
                            txt_label_name_generador.setText(generador_local);
                            boton_aceptar_encender_generador= (Button) dialog_encender_generador.findViewById(R.id.button_aceptar_confirm);
                            txt_remanente_st="";
                            txt_remanente.setText("");
                            txt_ubicacion_st="";
                            txt_ubicacion.setText("");
                            txt_remanente.setOnKeyListener(new View.OnKeyListener() {
                                public boolean onKey(View v, int keyCode, KeyEvent event) {
                                    // If the event is a key-down event on the "enter" button
                                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                        // Perform action on key press
                                        validaCampos(generador.getId_rf_generador());
                                        return true;
                                    }
                                    return false;
                                }
                            });


                            boton_aceptar_encender_generador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //dialog_encender_generador.hide();
                                    validaCampos(generador.getId_rf_generador());
                                }
                            });
                        }
                        else
                        {
                            System.out.println("----------->"+holder.txt_bloque.getText().toString());
                            dialog_apagar_generador.show();

                            txt_label_name_apaga_generador.setText(generador_local);
                            txt_remanente_apagado_fl=0;
                            txt_remanente_apagado_st="";
                            txt_apaga_remanente.setText("");
                            txt_apaga_ubicacion.setText("");
                            txt_apaga_ubicacion.setText(holder.txt_bloque.getText().toString().replace("Bloque : ", ""));
                            boton_aceptar_apaga_generador= (Button) dialog_apagar_generador.findViewById(R.id.button_aceptar_confirm);


                            txt_apaga_remanente.setOnKeyListener(new View.OnKeyListener() {
                                public boolean onKey(View v, int keyCode, KeyEvent event) {
                                    // If the event is a key-down event on the "enter" button
                                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                        // Perform action on key press
                                        validaCamposApagado(generador.getId_rf_generador());
                                        return true;
                                    }
                                    return false;
                                }
                            });

                            boton_aceptar_apaga_generador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //dialog_encender_generador.hide();
                                    validaCamposApagado(generador.getId_rf_generador());
                                }
                            });
                        }





                    }
                });




                acbutton_tomas.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        Generador generador_resultado_local=new Generador();
                        Gson gson=new Gson();
                        WebServicesPowerPack wsp=new WebServicesPowerPack();
                        Intent myIntent = activity.getIntent(); // gets the previously created intent
                        String usuario_logoneado = myIntent.getStringExtra("usuario_logoneado");
                        Errores errores_result=wsp.consultaGeneradorId(usuario_logoneado,uid_generador_local  );
                        generador_resultado_local=wsp.getGenerador();
                        String generador_usuario=gson.toJson(generador_resultado_local);
                        if(errores_result.getCod_error()==0) {


                            if (generador_resultado_local.getHoras_conexion().equals(".")) {

                                image_view_icono.setImageResource(R.drawable.icons_warning);
                                text_view_mensaje.setText("El Generador ('"+generador_resultado_local.getNombre()+"'), se encuentra Apagado.");
                                text_view_titulo.setText("Generador Apagado");
                                text_view_titulo.setTextColor(Color.RED);
                                toast_alert.show();
                            }
                             else {
                                System.out.println("Generador--->"+generador_usuario);
                                bundle.putString("generador", generador_usuario);
                                DetalleTomasGenerador detalleTomasGenerador = new DetalleTomasGenerador();
                                detalleTomasGenerador.setArguments(bundle);
                                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, detalleTomasGenerador).
                                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                        addToBackStack(null).commit();
                            }
/*
                            generador_encendido_apagado="APAGADO";

                            holder.filas_columnas.setText("FxC : " + String.valueOf(generador.getFilas()) + "x" + String.valueOf(generador.getColumnas()));
                            holder.nombre_generador.setText(generador.getNombre());
                            nombre_generador=generador.getNombre();
                            uid_generador=generador.getId_rf_generador();
                            holder.tomas_disponibles.setText("Ocpds: " + String.valueOf(generador.getTomas_conectadas()) + " Utiles: " + String.valueOf(generador.getTomas_utiles()) + " Libres: " + generador.getCant_disponible());
                            holder.txt_bloque.setText("Bloque : " + generador.getBloque());
                            holder.txt_empresa_proveedora.setText(generador.getNombre_razon_social_proveedor());
                            if (generador.getHoras_conexion().equals(".")) {
                                holder.txt_fecha_desde.setText("APAGADO");
                                generador_encendido_apagado="APAGADO";
                                holder.img_apagado.setVisibility(View.VISIBLE);
                            } else {
                                holder.txt_fecha_desde.setText(generador.getFecha_inicio_conexion() + "         " + generador.getHoras_conexion() + " Horas");
                                generador_encendido_apagado="ENCENDIDO";
                                holder.img_apagado.setVisibility(View.INVISIBLE);
                            }

                            if(generador.getFinalizo_carga_combustible().equals("NO"))//SI AUN NO TERMINA DE ABASTECER muestra el icono de la manguera que indica que esta abasteciendo.
                            {
                                holder.img_abasteciendo_cmbtbl.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                holder.img_abasteciendo_cmbtbl.setVisibility(View.INVISIBLE); //Si no esta abasteciendo oculta el icono.
                            }
                            //Para visualizar la imagen desde internet.
                            String url_picture = "http://192.168.0.247:8080/PowerPackReefer/image?location_image=" + generador.getPicture();

                            // Load the image into image view from assets folder
                            Picasso.with(activity)
                                    .load(url_picture)
                                    .transform(new CropSquareTransformation())
                                    .resize(500, 500)
                                    .centerCrop()
                                    //.placeholder(R.drawable.loading)
                                    .into(holder.picture_card_image_generador, new Callback() {

                                        @Override
                                        public void onSuccess() {

                                            holder.loadingPanelCardView.setVisibility(View.GONE);

                                        }

                                        @Override
                                        public void onError() {
                                            holder.loadingPanelCardView.setVisibility(View.GONE);
                                            Toast.makeText(activity, "Ocurrio un Error al tratar de cargar la Imagen del Generador ("+nombre_generador+") del servidor",
                                                    Toast.LENGTH_LONG).show();

                                        }
                                    });

*/






                        }
                        else
                        {
                            dialog_alert.setContentView(R.layout.popup_alert);
                            dialog_alert.setCancelable(false);
                            dialog_alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            android.support.design.widget.FloatingActionButton btnclose = (android.support.design.widget.FloatingActionButton) dialog_alert.findViewById(R.id.floating_button_close);
                            btnclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ocultarPopupAlert();
                                }
                            });

                            TextView txt_label_mensaje = (TextView) dialog_alert.findViewById(R.id.mensaje_descripcion);
                            txt_label_mensaje.setText(errores_result.getMsg_error());
                            dialog_alert.show();
                        }

                    }
                });

                 /*
                acbutton_agregar_generador.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        FragmentNuevoGenerador fragment_nuevo_cnt = new FragmentNuevoGenerador();
                        ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_nuevo_cnt).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                addToBackStack(null).commit();
                    }
                });*/


                //Intent intent = new Intent(activity, PictureDetailActvity.class);  Se comento para invocar al menu
                //Intent intent = new Intent(activity, menu_principal.class);
                /*Toast.makeText(activity, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();*/

                /*
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    Explode explode=new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transition_name_picture) ).toBundle());
                }
                else {
                    activity.startActivity(intent);

                }*/
//Para Abiri el Menu directamente
                materialDesignFAM.open(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return generadores.size();
    }

    public class GeneradorViewHolder extends RecyclerView.ViewHolder {

        private ImageView picture_card_image_generador;
        private ImageView img_abasteciendo_cmbtbl;
        private ImageView img_apagado;
        private TextView nombre_generador;
        private TextView filas_columnas;
        private TextView tomas_disponibles;
        private TextView txt_empresa_proveedora;
        private TextView txt_bloque;
        private TextView txt_fecha_desde;
        private RelativeLayout loadingPanelCardView;

        public GeneradorViewHolder(View itemView) {
            super(itemView);
            picture_card_image_generador = (ImageView) itemView.findViewById(R.id.picture_card_image_generador);
            img_abasteciendo_cmbtbl = (ImageView)  itemView.findViewById(R.id.img_abasteciendo_cmbtbl);
            img_apagado  = (ImageView)  itemView.findViewById(R.id.img_apagado);
            nombre_generador = (TextView) itemView.findViewById(R.id.nombre_generador);
            filas_columnas = (TextView) itemView.findViewById(R.id.filas_columnas);
            tomas_disponibles = (TextView) itemView.findViewById(R.id.tomas_disponibles);
            txt_empresa_proveedora = (TextView) itemView.findViewById(R.id.txt_empresa_proveedora);
            txt_bloque = (TextView) itemView.findViewById(R.id.txt_bloque);
            txt_fecha_desde = (TextView) itemView.findViewById(R.id.txt_fecha_desde);
            loadingPanelCardView = (RelativeLayout) itemView.findViewById(R.id.loadingPanelCardView);

        }
    }


    public void  ocultarPopupAlert()
    {
        dialog_alert.hide();
    }


    public void validaCampos(String uid_seleccionado_pa)
    {
        txt_ubicacion = (TextInputEditText) dialog_encender_generador.findViewById(R.id.txt_ubicacion);
        txt_remanente = (TextInputEditText) dialog_encender_generador.findViewById(R.id.txt_remanente);

        txt_ubicacion_st=txt_ubicacion.getText().toString();
        txt_remanente_st=txt_remanente.getText().toString();
        txt_remanente_fl=0;
        int bandera=0;

        if(txt_ubicacion_st.length()==0)
        {

            image_view_icono.setImageResource(R.drawable.icons_warning);
            text_view_mensaje.setText("Por favor ingrese el campo Ubicación, Ejemplo: T1-A");
            text_view_titulo.setText("Error Faltan Datos (Ubicación)");
            text_view_titulo.setTextColor(Color.RED);
            toast_alert.show();
            bandera=1;
        }
        else
        {
            txt_ubicacion_st=txt_ubicacion_st.toUpperCase();
            System.out.println("---->"+txt_ubicacion_st.length());
            if(txt_ubicacion_st.length()!=4)
            {

                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_mensaje.setText("Por favor ingrese el campo Ubicación, Ejemplo: T1-A");
                text_view_titulo.setText("Error Faltan Datos (Ubicación)");
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.show();
                bandera=1;
            }
        }

        if(bandera == 0)
        {

            if(txt_remanente_st.length()==0)
            {

                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_mensaje.setText("Por favor ingrese el campo Remanente");
                text_view_titulo.setText("Error Faltan Datos (Remanente GAL)");
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.show();
                bandera=1;
            }
            else
            {
                txt_remanente_fl=Float.parseFloat(txt_remanente_st.toString());
                if(txt_remanente_fl<0)
                {
                    image_view_icono.setImageResource(R.drawable.icons_warning);
                    text_view_titulo.setText("Error Remanente GAL");
                    text_view_mensaje.setText("El campo Remanente GAL, debe de ser mayor a cero.");
                    text_view_titulo.setTextColor(Color.RED);
                    toast_alert.show();
                    bandera=1;

                }

            }

        }

        if(bandera == 0)
        {
            dialog_encender_generador.hide();
            pd = ProgressDialog.show(activity, "Cargando", "Procesando su transaccion por favor espere...", true, false);
            Intent myIntent = activity.getIntent(); // gets the previously created intent
            String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
            Object[] objects = new Object[4];
            objects[0] = uid_seleccionado_pa;
            objects[1] = usuario_logoneado_tr;
            objects[2] = txt_ubicacion_st;
            objects[3] = txt_remanente_fl;
            new ProcesarWs().execute(objects);
        }


    }




    public void validaCamposApagado(String uid_seleccionado_pa)
    {
        txt_apaga_ubicacion = (TextInputEditText) dialog_apagar_generador.findViewById(R.id.txt_ubicacion);
        txt_apaga_remanente = (TextInputEditText) dialog_apagar_generador.findViewById(R.id.txt_remanente);

        txt_ubicacion_st=txt_apaga_ubicacion.getText().toString();
        txt_remanente_apagado_st=txt_apaga_remanente.getText().toString();
        txt_remanente_apagado_fl=0;
        int bandera=0;


        if(bandera == 0)
        {

            if(txt_remanente_apagado_st.length()==0)
            {

                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_mensaje.setText("Por favor ingrese el campo Remanente");
                text_view_titulo.setText("Error Faltan Datos (Remanente GAL)");
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.show();
                bandera=1;
            }
            else
            {
                txt_remanente_apagado_fl=Float.parseFloat(txt_remanente_apagado_st.toString());
                if(txt_remanente_fl<0)
                {
                    image_view_icono.setImageResource(R.drawable.icons_warning);
                    text_view_titulo.setText("Error Remanente GAL");
                    text_view_mensaje.setText("El campo Remanente GAL, debe de ser mayor a cero.");
                    text_view_titulo.setTextColor(Color.RED);
                    toast_alert.show();
                    bandera=1;

                }

            }

        }

        if(bandera == 0)
        {
            dialog_apagar_generador.hide();
            pd = ProgressDialog.show(activity, "Cargando", "Procesando su transaccion por favor espere...", true, false);
            Intent myIntent = activity.getIntent(); // gets the previously created intent
            String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
            Object[] objects = new Object[3];
            objects[0] = uid_seleccionado_pa;
            objects[1] = usuario_logoneado_tr;
            objects[2] = txt_remanente_apagado_fl;
            new ProcesarWsApaga().execute(objects);
        }


    }


    public static class CropSquareTransformation implements Transformation { //Calse que se usa para cargar de forma mas optima las imagenes de internet reduciendo el consumo de memoria de dispositivo
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

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores = ws_power_pack.enciendeGenerador(  objects[0].toString(),
                                                                objects[1].toString(),
                                                                objects[2].toString(),
                                                                Float.parseFloat(objects[3].toString()));

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (GeneradorAdapterRecyclerView.this.pd != null) {
                GeneradorAdapterRecyclerView.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {

                image_view_icono.setImageResource(R.drawable.icon_successful);
                text_view_titulo.setText("Encendido Exitoso Generador ("+nombre_generador+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.GREEN);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();
                FragmentListadoGeneradores fragment_listado_generadores = new FragmentListadoGeneradores();

                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();
            } else {
                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_titulo.setText("Error Encender Generador ("+nombre_generador+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();

            }

        }
    }





    private class ProcesarWsApaga extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores = ws_power_pack.apagaGenerador(  objects[0].toString(),
                    objects[1].toString(),
                    Float.parseFloat(objects[2].toString()));

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (GeneradorAdapterRecyclerView.this.pd != null) {
                GeneradorAdapterRecyclerView.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            if (errores_ws.getCod_error() == 0) {

                image_view_icono.setImageResource(R.drawable.icon_successful);
                text_view_titulo.setText("Apagado Exitoso Generador ("+nombre_generador+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.GREEN);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();

                FragmentListadoGeneradores fragment_listado_generadores = new FragmentListadoGeneradores();

                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();

            } else {
                image_view_icono.setImageResource(R.drawable.icons_warning);
                text_view_titulo.setText("Error Apagar Generador ("+nombre_generador+")");
                text_view_mensaje.setText(errores_ws.getMsg_error());
                text_view_titulo.setTextColor(Color.RED);
                toast_alert.setDuration(Toast.LENGTH_LONG);
                toast_alert.show();

            }

        }
    }

}

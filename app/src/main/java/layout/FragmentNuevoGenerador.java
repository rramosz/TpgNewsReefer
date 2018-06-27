package layout;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ec.com.tpg.tpgnews.MainActivity;
import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.model.Deposito;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Proveedor;
import ec.com.tpg.tpgnews.ws.WebServices;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNuevoGenerador extends Fragment {

    ImageView foto_generador;
    static int TAKE_PICTURE = 100;

    Button btpic, btnup;
    private Uri fileUri;
    String picturePath = null;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "Paste your URL here";

    TextInputEditText txt_cnt;
    TextInputEditText txt_nomnbre_generador;
    TextInputEditText txt_descripcion;
    TextInputEditText txt_filas;
    TextInputEditText txt_columnas;
    TextInputEditText txt_bloque;
    TextInputEditText txt_capacidad_galones;
    Dialog dialog_alert = null;
    Dialog dialog_popup_ok = null;
    Dialog dialog_confirm = null;
    int deposito_seleccionado;
    List<Proveedor> arraylist_proveedor;
    SearchableSpinner spinner_depot;
    ProgressDialog pd;


    String nombre_generador = new String();
    String descripcion = new String();
    String filas = new String();
    String columnas = new String();
    String bloque = new String();
    String capacidad_galones = new String();
    String id_proveedor = new String();
    Button button_aceptar;

    public FragmentNuevoGenerador() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_nuevo_generador, container, false);
        FloatingActionButton button_foto = (FloatingActionButton) view.findViewById(R.id.action_button_capture_foto);

        foto_generador = (ImageView) view.findViewById(R.id.image_generador);
        txt_nomnbre_generador = (TextInputEditText) view.findViewById(R.id.txt_nomnbre_generador);
        txt_descripcion = (TextInputEditText) view.findViewById(R.id.txt_descripcion);
        txt_filas = (TextInputEditText) view.findViewById(R.id.txt_filas);
        txt_columnas = (TextInputEditText) view.findViewById(R.id.txt_columnas);
        txt_bloque = (TextInputEditText) view.findViewById(R.id.txt_bloque);
        txt_capacidad_galones = (TextInputEditText) view.findViewById(R.id.txt_capacidad_galones);
        ba1 = null;

        button_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });


        FloatingActionButton button_grabar = (FloatingActionButton) view.findViewById(R.id.action_button_grabar_generador);


        button_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registrarGenerador();
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


        button_aceptar = (Button) dialog_confirm.findViewById(R.id.button_aceptar_confirm);
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


        spinner_depot = (SearchableSpinner) view.findViewById(R.id.spinner_proveedor);

        WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
        Errores errores = ws_power_pack.consultaProveedores();
        if (errores.getCod_error() != 0) {

            TextView txt_label_mensaje = (TextView) dialog_alert.findViewById(R.id.mensaje_descripcion);
            txt_label_mensaje.setText(errores.getMsg_error());
            dialog_alert.show();
        } else {
            ArrayList<String> arrayList = new ArrayList<String>();

            deposito_seleccionado = -1;
            arraylist_proveedor = ws_power_pack.getArray_proveedor();
            for (int ikl = 0; ikl < arraylist_proveedor.size(); ikl++) {
                arrayList.add(arraylist_proveedor.get(ikl).getNombre_razon_social());
            }

            final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //adapter.addAll(arrayList);
            spinner_depot.setAdapter(adapter);
            spinner_depot.setTitle("Seleccione el Proveedor");

        }


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
            foto_generador.setImageBitmap(photo);
            foto_generador.setImageBitmap(photo);
            int w = photo.getWidth();
            int h = photo.getHeight();


            foto_generador.setImageBitmap(photo);
            foto_generador.setVisibility(View.VISIBLE);
            foto_generador.getLayoutParams().width = w * 2;
            foto_generador.getLayoutParams().height = h * 2;


        }


    }


    private void registrarGenerador() {
        // Image location URL

        System.out.println("Ingresandooo en registrarGenerador()");

        nombre_generador = txt_nomnbre_generador.getText().toString();
        descripcion = txt_descripcion.getText().toString();
        filas = txt_filas.getText().toString();
        columnas = txt_columnas.getText().toString();
        bloque = txt_bloque.getText().toString();
        capacidad_galones = txt_capacidad_galones.getText().toString();
        int bandera = 0;

        String id_seleccionado = "";
        Proveedor proveedor;
        id_proveedor = "";

        if (bandera == 0) {
            if (nombre_generador == null) {
                mostrarPopUp("Por favor ingrese el campo SUM");
                bandera = 1;
            } else {
                nombre_generador = nombre_generador.trim();
                nombre_generador = nombre_generador.toUpperCase();

                if (nombre_generador.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Nombre");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (descripcion == null) {
                mostrarPopUp("Por favor ingrese el campo Descripción");
                bandera = 1;
            } else {
                descripcion = descripcion.trim();
                descripcion = descripcion.toUpperCase();

                if (descripcion.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Descripción");
                    bandera = 1;
                }
            }

        }


        if (bandera == 0) {
            if (filas == null) {
                mostrarPopUp("Por favor ingrese el campo Filas");
                bandera = 1;
            } else {
                filas = filas.trim();
                filas = filas.toUpperCase();

                if (filas.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Filas");
                    bandera = 1;
                } else {
                    int numfila = Integer.parseInt(filas);
                    if (numfila <= 0) {
                        mostrarPopUp("El campo Fila debe de ser mayor a cero");
                        bandera = 1;
                    }
                }


            }

        }


        if (bandera == 0) {
            if (columnas == null) {
                mostrarPopUp("Por favor ingrese el campo Columna");
                bandera = 1;
            } else {
                columnas = columnas.trim();
                columnas = columnas.toUpperCase();

                if (columnas.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Columna");
                    bandera = 1;
                } else {
                    int numcolumna = Integer.parseInt(columnas);
                    if (numcolumna <= 0) {
                        mostrarPopUp("El campo Columna debe de ser mayor a cero");
                        bandera = 1;
                    }
                }

            }

        }


        if (bandera == 0) {
            if (bloque == null) {
                mostrarPopUp("Por favor ingrese el campo Bloque, ejemplo: T1-A");
                bandera = 1;
            } else {
                bloque = bloque.trim();
                bloque = bloque.toUpperCase();

                if (bloque.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Bloque de forma correcta, ejemplo: T1-A");
                    bandera = 1;
                } else {
                    if (bloque.length() != 4) {
                        mostrarPopUp("Por favor ingrese el campo Bloque de forma correcta, ejemplo: T1-A");
                        bandera = 1;
                    }
                }
            }

        }


        if (bandera == 0) {
            if (capacidad_galones == null) {
                mostrarPopUp("Por favor ingrese el campo Capacidad de galones de combustible del generador");
                bandera = 1;
            } else {
                capacidad_galones = capacidad_galones.trim();
                capacidad_galones = capacidad_galones.toUpperCase();

                if (capacidad_galones.isEmpty()) {
                    mostrarPopUp("Por favor ingrese el campo Capacidad de galones de combustible del generador");
                    bandera = 1;
                } else {
                    int numgalones = Integer.parseInt(capacidad_galones);
                    if (numgalones <= 0) {
                        mostrarPopUp("El campo Capacidad de Galones debe de ser mayor a cero");
                        bandera = 1;
                    }
                }
            }

        }


        if (bandera == 0) {
            if (spinner_depot.getSelectedItemPosition() == -1) {
                mostrarPopUp("Por favor Seleccione el Proveedor ");
                bandera = 1;

            } else {

                id_seleccionado = arraylist_proveedor.get(spinner_depot.getSelectedItemPosition()).getNombre_razon_social();
                id_proveedor = arraylist_proveedor.get(spinner_depot.getSelectedItemPosition()).getId_rf_proveedor();
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
                System.out.println("Imagen Base64: " + ba1);
                System.out.println("ID_PROVEEDOR:" + id_proveedor + ", Proveedor:" + id_seleccionado);


                //bloque


                TextView txt_label_mensaje = (TextView) dialog_confirm.findViewById(R.id.mensaje_descripcion);
                txt_label_mensaje.setText("¿ Seguro que desea registrar el Generador (" + nombre_generador + ") ?");
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


// To dismiss the dialog
        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
        Object[] objects = new Object[9];
        objects[0] = id_proveedor;
        objects[1] = nombre_generador;
        objects[2] = descripcion;
        objects[3] = usuario_logoneado_tr;
        objects[4] = Integer.parseInt(filas);
        objects[5] = Integer.parseInt(columnas);
        objects[6] = Integer.parseInt(capacidad_galones);
        objects[7] = ba1;
        objects[8] = bloque;

        new ProcesarWs().execute(objects);


    }


    private class ProcesarWs extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {

            WebServicesPowerPack ws_power_pack = new WebServicesPowerPack();
            Errores errores =   ws_power_pack.registraGenerador(objects[0].toString(),
                                                                objects[1].toString(),
                                                                objects[2].toString(),
                                                                objects[3].toString(),
                                                                Integer.parseInt(objects[4].toString()),
                                                                Integer.parseInt(objects[5].toString()),
                                                                Integer.parseInt(objects[6].toString()),
                                                                objects[7].toString(),
                                                                objects[8].toString());

            return errores;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Procesamos los resuktados obtenidos despues de la invocacion al Web Services

            if (FragmentNuevoGenerador.this.pd != null) {
                FragmentNuevoGenerador.this.pd.dismiss();
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

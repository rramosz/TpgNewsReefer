package layout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.ws.WebServices;


public class activity_consulta_contenedor extends Fragment {

    TextInputEditText txt_cnt;
    Dialog dialog_loading = null;
    Dialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save_instance) {


        CardView picture_card_consulta = (CardView) container.findViewById(R.id.picture_card_consulta);
        //picture_card_consulta.setVisibility(View.GONE);

        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        String usuario_logoneado = myIntent.getStringExtra("usuario_logoneado");
        System.out.println(">>>>>>>>>>>>>>>>>>>El usuario logoneado en la sesion es: " + usuario_logoneado + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        View view = inflater.inflate(R.layout.fragment_activity_consulta_contenedor,
                container, false);
        dialog_loading = new Dialog(getActivity());
        dialog_loading.setContentView(R.layout.popup_loading);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_loading.setCancelable(false);

        dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_alert);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FloatingActionButton btnclose          = (FloatingActionButton) dialog.findViewById(R.id.floating_button_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarPopupAlert();
            }
        });

        FloatingActionButton btnconsulta = (FloatingActionButton) view.findViewById(R.id.action_button_cnt_cons);
        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaContenedor();
            }
        });
        txt_cnt = (TextInputEditText) view.findViewById(R.id.txt_cnt_consulta);
        txt_cnt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    consultaContenedor();
                    return true;
                }
                    return false;
               // return false;
            }

        });


        txt_cnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                System.out.println("Validando la tecla presionada: ");
                //txt_cnt.setText(txt_cnt.getText().toString().toUpperCase());
                if (txt_cnt.getText().toString().length() >= 4) {
                    System.out.println("La longitud de la cadena del contenedor es mayor a 4");
                    txt_cnt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    txt_cnt.setSelection(txt_cnt.getText().toString().length());

                } else {
                    System.out.println("La longitud de la cadena del contenedor es menor a 4");
                    txt_cnt.setInputType(InputType.TYPE_CLASS_TEXT);
                    txt_cnt.setSelection(txt_cnt.getText().toString().length());
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




        return view;//inflater.inflate(R.layout.fragment_activity_consulta_contenedor, container, false);
    }


    public void consultaContenedor() {
        System.out.println(">>>>>>>>>>>>>>> Consultando contenedor<<<<<<<<<<<<<<");
        dialog_loading.show();
        String cnt = txt_cnt.getText().toString();


        if(cnt==null)
        {
            System.out.println(">>>>>>>>>>>>>>>1<<<<<<<<<<<<<<");
            dialog_loading.hide();
            TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
            txt_label_mensaje.setText("Por favor ingrese el número de Contenedor");
            dialog.show();
        }
        else
        {
            if(cnt.isEmpty())
            {
                System.out.println(">>>>>>>>>>>>>>>2<<<<<<<<<<<<<<");
                dialog_loading.hide();
                TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                txt_label_mensaje.setText("Por favor ingrese el número de Contenedor");
                dialog.show();

            }
            else
            {
                cnt = cnt.toUpperCase();
                cnt = cnt.trim();



                if(cnt.length()!=11)
                {    System.out.println(">>>>>>>>>>>>>>>3<<<<<<<<<<<<<<");
                    dialog_loading.hide();
                    TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                    txt_label_mensaje.setText("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).");
                    dialog.show();

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
                            dialog_loading.hide();
                            TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                            txt_label_mensaje.setText("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).");
                            dialog.show();
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
                                dialog_loading.hide();
                                TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                                txt_label_mensaje.setText("Por favor ingrese el número de Contenedor de forma correcta ejemplo (HLXU1234567).");
                                dialog.show();
                                bandera_ok= 1;
                                break;

                            }
                        }
                    }

                    if(bandera_ok==0)
                    {

                        WebServices ws = new WebServices();
                        Errores errores = ws.consultaContenedor(cnt);
                        if (errores.getCod_error() == 0) {
                            dialog_loading.hide();

                            //Si el contenedor existe de forma exitosa, se carga el nuevo fragment

                            Gson json = new Gson();
                            String contenedor=json.toJson( ws.getContenedor());
                            String lista_depositos=json.toJson( ws.getArray_deposito());


                            Bundle bundle = new Bundle();
                            bundle.putString("contenedor", contenedor);
                            bundle.putString("array_depositos", lista_depositos);
// set MyFragment Arguments




                            FragmentRegAlarmaContenedor fragment_cnt_reg_alarma=new FragmentRegAlarmaContenedor();
                            fragment_cnt_reg_alarma.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_cnt_reg_alarma).
                                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                                    addToBackStack(null).commit();


                        }
                        else
                        {
                            dialog_loading.hide();
                            TextView txt_label_mensaje= (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                            txt_label_mensaje.setText(errores.getMsg_error());
                            dialog.show();

                        }


                    }




                }


            }



        }




    }



    public void ocultarPopupAlert()
    {
        dialog.hide();
    }



}

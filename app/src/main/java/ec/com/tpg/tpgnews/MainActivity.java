package ec.com.tpg.tpgnews;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.view.ContainerActivity;
import ec.com.tpg.tpgnews.view.CreateAccount;
import ec.com.tpg.tpgnews.ws.WebServices;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {


    Dialog dialog = null;
    Toast myToast;
    ProgressDialog pd = null;
    TextInputEditText txt_user;
    TextInputEditText txt_password;
    TextView txt_label_mensaje;
    //Dialog dialog_loading = null;

    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setOnKeyListener(this);


        /*rl = (RelativeLayout) findViewById(R.id.loadingPanel);
        rl.setVisibility(View.GONE);*/
        myToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
        //dialog_loading = new Dialog(this);


        //dialog_loading.setContentView(R.layout.popup_loading);


        final TextInputEditText edittext = (TextInputEditText) findViewById(R.id.txt_password);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    login(v);
                    return true;
                }
                return false;
            }
        });


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_alert);
        txt_label_mensaje = (TextView) dialog.findViewById(R.id.mensaje_descripcion);
        FloatingActionButton btnclose = (FloatingActionButton) dialog.findViewById(R.id.floating_button_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarPopupAlert();
            }
        });

    }


    public void irCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


    public void login(View view) {



       /* pd=new ProgressDialog(this);
        pd.setMessage("Cargando...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();*/
        pd = ProgressDialog.show(this, "Cargando", "Procesando su transaccion por favor espere...", true, false);


        txt_user = (TextInputEditText) findViewById(R.id.txt_usuario);
        txt_password = (TextInputEditText) findViewById(R.id.txt_password);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txt_user.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(txt_password.getWindowToken(), 0);


        // disable dismiss by tapping outside of the dialog


        //  final EditText editText = (EditText) dialog.findViewById(R.id.editText);

        //Button btnCancel        = (Button) dialog.findViewById(R.id.cancel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);


        //dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog_loading.setCancelable(false);
        //dialog_loading.show();


        if (txt_user.getText().toString().isEmpty()) {
            //dialog_loading.hide();
            TextView txt_label_mensaje = (TextView) dialog.findViewById(R.id.mensaje_descripcion);
            txt_label_mensaje.setText("Por favor ingrese el campo Usuario.");
            dialog.show();
            pd.dismiss();


        } else {
            if (txt_password.getText().toString().isEmpty()) {
                //dialog_loading.hide();
                TextView txt_label_mensaje = (TextView) dialog.findViewById(R.id.mensaje_descripcion);
                txt_label_mensaje.setText("Por favor ingrese el campo Password.");
                dialog.show();
                pd.dismiss();

            } else {


                System.out.println("---------------->Login con WS<---------------");
                Object[] objects = new Object[2];
                objects[0] = txt_user.getText();
                objects[1] = txt_password.getText();
                Object object_result = null;

                new DownloadTask().execute(objects);

            }


        }


        //pd.hide();

        /*
        ProgressDialog progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();*/

        /*Intent intent =new Intent(this, ContainerActivity.class);
        startActivity(intent);*/
    }


    public void ocultarPopupAlert() {
        dialog.hide();
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        System.out.println("Usted DIO CLIC DENTRO ");

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("BACK");

        }

        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.ACTION_UP)) {
            System.out.println("Usted DIO CLIC EN ATRAS");
            // Perform action on key press

            return true;
        }


        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            System.out.println("Cerrando Aplicacion TPG Alarmas Reefer");

// Esto es lo que hace mi botón al pulsar ir a atrás
            Toast.makeText(getApplicationContext(), "Acción Inhabilitada",
                    Toast.LENGTH_SHORT).show();

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


    private class DownloadTask extends AsyncTask {


        @Override
        protected Errores doInBackground(Object[] objects) {

            WebServicesPowerPack ws = new WebServicesPowerPack();
            Errores errores_ws = ws.loginWs(objects[0].toString(), objects[1].toString());

            return errores_ws;
        }

        @Override
        protected void onPostExecute(Object result) {
            // Pasamos el resultado de los datos a la Acitvity principal

            if (MainActivity.this.pd != null) {
                System.out.println("OCULTANDOOOOOOOOO");
                MainActivity.this.pd.dismiss();
            }

            Errores errores_ws = (Errores) result;
            System.out.println("--->" + errores_ws.getMsg_error());
            if (errores_ws.getCod_error() == 0) {
                loginExitoso();
            } else {
                alertError(errores_ws.getMsg_error());
            }

        }
    }


    public void loginExitoso() {
        Intent intent = new Intent(this, menu_principal.class);
        intent.putExtra("usuario_logoneado", txt_user.getText().toString());
        startActivity(intent);
    }

    public void alertError(String mensaje_error) {

        txt_label_mensaje.setText(mensaje_error);
        dialog.show();
    }


}

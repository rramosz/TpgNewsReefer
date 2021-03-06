package ec.com.tpg.tpgnews;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ec.com.tpg.tpgnews.view.MaterialDesignFloatingActionMenuActivity;
import layout.DetalleTomasGenerador;
import layout.FragmentListadoGeneradores;
import layout.activity_consulta_contenedor;

public class menu_principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //toolbar.setTitle("TPG");

        Intent myIntent = getIntent(); // gets the previously created intent
        String usuario_logoneado = myIntent.getStringExtra("usuario_logoneado");

        TextView txt_usuario=(TextView) findViewById(R.id.txt_usuario);
        txt_usuario.setText("Usuario: "+usuario_logoneado);

        //toolbar.setSubtitle("Bienvenido: " + usuario_logoneado);
        //toolbar.setLogo(R.drawable.icono_tpg);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*activity_consulta_contenedor fragment_cnt = new activity_consulta_contenedor();


        getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_cnt).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                addToBackStack(null).commit();*/


        FragmentListadoGeneradores listado_generadores = new FragmentListadoGeneradores();

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, listado_generadores).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                addToBackStack(null).commit();

    }


    @Override
    protected void onStop() {
        //Toast.makeText(this, ">>>>>Reinicie Sesion<<<<<<", Toast.LENGTH_SHORT).show();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ONSTOP<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        super.onStop();
        //super.onRestart();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cerrar_sesion) { //Controles del Menu lateral DERECHO
            // return true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //Controles del menu Lateral Izquierdo
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nueva_alarma) {
            activity_consulta_contenedor fragment_cnt = new activity_consulta_contenedor();

            getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_cnt).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                    addToBackStack(null).commit();


            // Handle the camera action
            //} else if (id == R.id.nav_gallery) {

            //} else if (id == R.id.nav_slideshow) {

            //} else if (id == R.id.nav_manage) {

        } else {


            if (id == R.id.detalle_tomas_generador) {
                //DetalleTomasGenerador  detalle_tomas=new DetalleTomasGenerador();
                FragmentListadoGeneradores listado_generadores = new FragmentListadoGeneradores();

                getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, listado_generadores).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();


                // Handle the camera action
                //} else if (id == R.id.nav_gallery) {

                //} else if (id == R.id.nav_slideshow) {

                //} else if (id == R.id.nav_manage) {

            } else {
                /*if (id == R.id.ejemplo_menu) {
                    //DetalleTomasGenerador  detalle_tomas=new DetalleTomasGenerador();
                    MaterialDesignFloatingActionMenuActivity listado_generadores = new MaterialDesignFloatingActionMenuActivity();

                    getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, listado_generadores).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            addToBackStack(null).commit();

                } else*/ if (id == R.id.nav_cerrar_sesion) {

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);


                }
            } /*else if (id == R.id.nav_send) {

        }*/

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

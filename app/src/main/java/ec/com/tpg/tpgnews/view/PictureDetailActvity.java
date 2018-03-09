package ec.com.tpg.tpgnews.view;


import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;


import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.ws.WebServices;

import static android.app.ActionBar.DISPLAY_HOME_AS_UP;

public class PictureDetailActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail_actvity);
        //showToolBar("TPG", false);



       /*

        SearchableSpinner spinner_depot=(SearchableSpinner) findViewById(R.id.spinner_depositos);
        Resources res = getResources();


        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("Lunes");
        arrayList.add("Martes");
        arrayList.add("Miercoles");
        arrayList.add("Jueves");
        arrayList.add("viernes");
        arrayList.add("Sabado");



        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,arrayList);

        adapter.addAll(arrayList);
        spinner_depot.setAdapter(adapter);
        spinner_depot.setTitle("Seleccione el Deposito");
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.depot_fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDeposito();
            }
        });*/
    }
    public void getDeposito()
    {
        System.out.println("--------------------------------------------->getDeposito");

        SearchableSpinner spinner_depot=(SearchableSpinner) findViewById(R.id.spinner_depositos);

        System.out.println("--->"+spinner_depot.getSelectedItem());
        WebServices ws=new WebServices();
        //ws.loginWs();

    }




    public void showToolBar(String title, boolean atras)
    {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("Registro de Alarmas Reefer");
        toolbar.setLogo(R.drawable.icono_tpg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


            // Fixes issue #746
            int displayOptions = getSupportActionBar().getDisplayOptions();
        getSupportActionBar().setHomeButtonEnabled((displayOptions & DISPLAY_HOME_AS_UP) != 0);


//        getSupportActionBar().addOnMenuVisibilityListener();
 //       getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 //       getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        CollapsingToolbarLayout collapsing_tool_bar_layout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);

        //Codigo para asignar un icono ocmo UP
        /*final Drawable upArrow = getResources().getDrawable(R.drawable.tpg_logo);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        */
        //getSupportActionBar().

       /* final Drawable drawable2 = toolbar.getNavigationIcon();
        drawable2.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(drawable2);
*/
    }

}

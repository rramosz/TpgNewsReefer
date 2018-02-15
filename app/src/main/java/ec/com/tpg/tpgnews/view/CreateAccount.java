package ec.com.tpg.tpgnews.view;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ec.com.tpg.tpgnews.R;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        String titulo= getResources().getString(R.string.tootlbar_title_create_account);
        showToolBar(titulo, true);
    }


    public void showToolBar(String title, boolean atras)
    {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("Prueba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);

        //Codigo para asignar un icono ocmo UP
        /*final Drawable upArrow = getResources().getDrawable(R.drawable.tpg_logo);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        */
        //getSupportActionBar().

        final Drawable drawable2 = toolbar.getNavigationIcon();
        drawable2.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(drawable2);

    }
}

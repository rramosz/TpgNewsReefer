package ec.com.tpg.tpgnews.view;


import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import ec.com.tpg.tpgnews.R;

public class PictureDetailActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail_actvity);
        showToolBar("",true );
    }


    public void showToolBar(String title, boolean atras)
    {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("Prueba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
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

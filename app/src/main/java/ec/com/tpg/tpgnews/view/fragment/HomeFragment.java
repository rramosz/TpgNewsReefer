package ec.com.tpg.tpgnews.view.fragment;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.adapter.PictureAdapterRecyclerView;
import ec.com.tpg.tpgnews.model.Picture;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String TAG = "MyActivity";
        Log.i(TAG,"onCreateView Home");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        showToolBar(getResources().getString(R.string.tab_home), true, view);
        RecyclerView picture_recycler_view= (RecyclerView) view.findViewById(R.id.picture_recicler_view);
        LinearLayoutManager linear_layout_manager=new LinearLayoutManager(getContext());
        linear_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);
        picture_recycler_view.setLayoutManager(linear_layout_manager);

        PictureAdapterRecyclerView picture_adapter_recycler_view= new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity() );
        picture_recycler_view.setAdapter(picture_adapter_recycler_view);
        return view;
    }


    public ArrayList<Picture> buildPictures()
    {

        ArrayList<Picture> pictures=new ArrayList<>();
        pictures.add(new Picture("http://www.tpg.com.ec/images/galeria/galeria2/galeria04.jpg", "Ricky Ramos", "4 Días", "3 Me Gusta" ));
        pictures.add(new Picture("http://www.tpg.com.ec/images/galeria/galeria17.jpg", "Petter Lenin", "4 Días", "2 Me Gusta" ));
        pictures.add(new Picture("http://www.tpg.com.ec/images/galeria/galeria2/galeria08.jpg", "Rihanna Ramos", "4 Días", "15 Me gusta" ));
        pictures.add(new Picture("http://www.tpg.com.ec/images/galeria/galeria14.jpg", "Fernanda Miño", "4 Días", "6 Me Gusta" ));
        return pictures;
    }

    public void showToolBar(String title, boolean atras, View view)
    {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Prueba");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(atras);

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

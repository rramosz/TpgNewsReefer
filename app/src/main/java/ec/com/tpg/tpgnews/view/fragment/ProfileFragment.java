package ec.com.tpg.tpgnews.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        showToolBar("",false, view);
        RecyclerView picture_recycler_view= (RecyclerView) view.findViewById(R.id.picture_profile_recicler_view);
        LinearLayoutManager linear_layout_manager=new LinearLayoutManager(getContext());
        linear_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);
        picture_recycler_view.setLayoutManager(linear_layout_manager);

        PictureAdapterRecyclerView picture_adapter_recycler_view= new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity() );
        picture_recycler_view.setAdapter(picture_adapter_recycler_view);
        return view;
    }


    public void showToolBar(String title, boolean atras, View view)
    {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Prueba");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(atras);

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


}

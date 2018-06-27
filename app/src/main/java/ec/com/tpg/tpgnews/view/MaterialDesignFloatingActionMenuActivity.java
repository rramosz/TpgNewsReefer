package ec.com.tpg.tpgnews.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import ec.com.tpg.tpgnews.R;

public class MaterialDesignFloatingActionMenuActivity extends Fragment {



    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.material_design_floating_action_menu, container, false);



        materialDesignFAM = (FloatingActionMenu)       view.findViewById(R.id.acbutton_menu);
        floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.acbutton_cargar_combustible);
        floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.acbutton_encender_apagar_generador);
        floatingActionButton3 = (FloatingActionButton) view.findViewById(R.id.acbutton_agregar_generador);



        //Para Abiri el Menu directamente
        //materialDesignFAM.open(true);

        //Ocultar Menu De la Tablet.
        materialDesignFAM.setVisibility(View.INVISIBLE);


        //Mostrar Menu De la Tablet.
        materialDesignFAM.setVisibility(View.VISIBLE);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });


        return view;
    }
}

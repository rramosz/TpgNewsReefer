package layout;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import ec.com.tpg.tpgnews.R;
import ec.com.tpg.tpgnews.adapter.GeneradorAdapterRecyclerView;
import ec.com.tpg.tpgnews.adapter.PictureAdapterRecyclerView;
import ec.com.tpg.tpgnews.model.Errores;
import ec.com.tpg.tpgnews.model.Generador;
import ec.com.tpg.tpgnews.model.Picture;
import ec.com.tpg.tpgnews.ws.WebServicesPowerPack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListadoGeneradores extends Fragment {



    View view =null;
    String total_generadores="0";
    String uid_seleccionado;

    public FragmentListadoGeneradores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_listado_generadores, container, false);
        setHasOptionsMenu(false);

        FloatingActionMenu materialDesignFAM;
        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.acbutton_menu);

        com.github.clans.fab.FloatingActionButton acbutton_cargar_combustible, acbutton_encender_apagar_generador, acbutton_tomas, acbutton_agregar_generador;
        uid_seleccionado="NO_SELECCIONADO";


        acbutton_cargar_combustible = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_cargar_combustible);
        acbutton_encender_apagar_generador = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_encender_apagar_generador);
        acbutton_tomas = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_tomas);
        acbutton_agregar_generador = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.acbutton_agregar_generador);

        acbutton_cargar_combustible.setColorNormal(Color.parseColor("#01DF01"));
        acbutton_cargar_combustible.setColorPressed(Color.parseColor("#04B45F"));



        acbutton_encender_apagar_generador.setColorNormal(Color.parseColor("#FF8000"));
        acbutton_encender_apagar_generador.setColorPressed(Color.parseColor("#DF7401"));


        acbutton_tomas.setColorNormal(Color.parseColor("#FFFF00"));
        acbutton_tomas.setColorPressed(Color.parseColor("#D7DF01"));


        acbutton_agregar_generador.setColorNormal(Color.parseColor("#2E64FE"));
        acbutton_agregar_generador.setColorPressed(Color.parseColor("#0040FF"));





            materialDesignFAM.setMenuButtonLabelText("Seleccione el Generador");


            acbutton_cargar_combustible.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });
            acbutton_encender_apagar_generador.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });

            acbutton_tomas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "Por favor seleccione el Generador que desea Procesar",
                            Toast.LENGTH_LONG).show();

                }
            });


            acbutton_agregar_generador.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentNuevoGenerador fragment_nuevo_cnt = new FragmentNuevoGenerador();
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_nuevo_cnt).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            addToBackStack(null).commit();
                }
            });


        return view;


        //return inflater.inflate(R.layout.fragment_listado_generadores, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("Agregando elementos al FRAGMENT");


    }





    public ArrayList<Generador> getGeneradores()
    {
        WebServicesPowerPack ws_power_pack=new WebServicesPowerPack();
        Intent myIntent = getActivity().getIntent(); // gets the previously created intent
        String usuario_logoneado_tr = myIntent.getStringExtra("usuario_logoneado");
        Errores errores =ws_power_pack.consultaGeneradores(usuario_logoneado_tr);
        total_generadores=String.valueOf(ws_power_pack.getArray_generador().size());
       /* LinearLayout mainLayout=(LinearLayout) getActivity().findViewById(R.id.layout_loading);
        mainLayout.setVisibility(LinearLayout.INVISIBLE);*/
        return ws_power_pack.getArray_generador();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("SE ASIGNO EL FRAGMENT AL ACTIVITY");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("DESPUES DE SE ASIGNO EL FRAGMENT AL ACTIVITY");
        FloatingActionButton btn_nuevo_generador          = (FloatingActionButton) view.findViewById(R.id.floating_abutton_add_generador);
        btn_nuevo_generador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNuevoGenerador fragment_generador=new FragmentNuevoGenerador();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menu_principal, fragment_generador).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                        addToBackStack(null).commit();
            }

        });

        RecyclerView picture_recycler_view= (RecyclerView) view.findViewById(R.id.picture_recicler_view_generadores);
        TextView txt_total_generadores= (TextView) view.findViewById(R.id.txt_total_generadores);

        LinearLayoutManager linear_layout_manager=new LinearLayoutManager(getContext());
        linear_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);
        picture_recycler_view.setLayoutManager(linear_layout_manager);

        GeneradorAdapterRecyclerView picture_adapter_recycler_view= new GeneradorAdapterRecyclerView(getGeneradores(), R.layout.cardview_picture_generador, getActivity() );
        txt_total_generadores.setText("Total de Generadores: "+total_generadores);
        picture_recycler_view.setAdapter(picture_adapter_recycler_view);
    }

    public ArrayList<Generador> buildPictures()
    {

        ArrayList<Generador> generador_array=new ArrayList<>();
        Generador generador=new Generador();
        generador.setCant_disponible(10);
        generador.setCant_tomas(30);
        generador.setCapacidad_galones(500);
        generador.setColumnas(4);
        generador.setDescripcion("Generador Rojo 1");
        generador.setEstado(1);
        generador.setEstado_descripcion("Activo");
        generador.setFecha_actualiza("27/03/2018 11:20:35");
        generador.setFecha_registro("27/03/2018 11:20:35");
        generador.setFilas(8);
        generador.setId_rf_generador("1233");
        generador.setId_rf_proveedor("123");
        generador.setNombre("Generador Azul 1");
        generador.setNombre_razon_social_proveedor("Provee Power Pack");
        generador.setRuc_proveedor("0924194970001");
        generador.setTomas_conectadas(15);
        generador.setTomas_utiles(28);
        generador.setUsuario_actualiza("rramos");
        generador.setUsuario_registra("rramos");
        generador.setBloque("T1-A");
        generador.setFecha_inicio_conexion("27/03/2018 11:20:35");
        generador.setHoras_conexion("128");
        generador.setPicture("http://www.tpg.com.ec/images/galeria/galeria2/galeria08.jpg");



        Generador generador2=new Generador();
        generador2.setCant_disponible(10);
        generador2.setCant_tomas(30);
        generador2.setCapacidad_galones(500);
        generador2.setColumnas(5);
        generador2.setDescripcion("Generador Rojo 1");
        generador2.setEstado(1);
        generador2.setEstado_descripcion("Activo");
        generador2.setFecha_actualiza("27/03/2018 11:20:35");
        generador2.setFecha_registro("27/03/2018 11:20:35");
        generador2.setFilas(7);
        generador2.setId_rf_generador("1233");
        generador2.setId_rf_proveedor("123");
        generador2.setNombre("Generador Rojo 1");
        generador2.setNombre_razon_social_proveedor("Provee Ricky Ramos Power Pack");
        generador2.setRuc_proveedor("0924194970001");
        generador2.setTomas_conectadas(18);
        generador2.setTomas_utiles(33);
        generador2.setUsuario_actualiza("rramos");
        generador2.setUsuario_registra("rramos");
        generador2.setBloque("T2-B");
        generador2.setFecha_inicio_conexion("27/03/2018 11:20:35");
        generador2.setHoras_conexion("128");
        generador2.setPicture("http://www.tpg.com.ec/images/galeria/galeria14.jpg");



        Generador generador3=new Generador();
        generador3.setCant_disponible(10);
        generador3.setCant_tomas(30);
        generador3.setCapacidad_galones(500);
        generador3.setColumnas(5);
        generador3.setDescripcion("Generador Verde 1");
        generador3.setEstado(1);
        generador3.setEstado_descripcion("Activo");
        generador3.setFecha_actualiza("27/03/2018 11:20:35");
        generador3.setFecha_registro("27/03/2018 11:20:35");
        generador3.setFilas(7);
        generador3.setId_rf_generador("1233");
        generador3.setId_rf_proveedor("123");
        generador3.setNombre("Generador Verde 1");
        generador3.setNombre_razon_social_proveedor("Provee Ricky Ramos Power Pack");
        generador3.setRuc_proveedor("0924194970001");
        generador3.setTomas_conectadas(18);
        generador3.setTomas_utiles(33);
        generador3.setUsuario_actualiza("rramos");
        generador3.setUsuario_registra("rramos");
        generador3.setBloque("T2-B");
        generador3.setFecha_inicio_conexion("27/03/2018 11:20:35");
        generador3.setHoras_conexion("128");
        generador3.setPicture("http://www.tpg.com.ec/images/galeria/DSC_0146.jpg");







        Generador generador4=new Generador();
        generador4.setCant_disponible(10);
        generador4.setCant_tomas(30);
        generador4.setCapacidad_galones(500);
        generador4.setColumnas(5);
        generador4.setDescripcion("Generador Amarillo 1");
        generador4.setEstado(1);
        generador4.setEstado_descripcion("Activo");
        generador4.setFecha_actualiza("27/03/2018 11:20:35");
        generador4.setFecha_registro("27/03/2018 11:20:35");
        generador4.setFilas(7);
        generador4.setId_rf_generador("1233");
        generador4.setId_rf_proveedor("123");
        generador4.setNombre("Generador Amarillo 1");
        generador4.setNombre_razon_social_proveedor("Provee Ricky Ramos Power Pack");
        generador4.setRuc_proveedor("0924194970001");
        generador4.setTomas_conectadas(18);
        generador4.setTomas_utiles(33);
        generador4.setUsuario_actualiza("rramos");
        generador4.setUsuario_registra("rramos");
        generador4.setBloque("T2-B");
        generador4.setFecha_inicio_conexion("27/03/2018 11:20:35");
        generador4.setHoras_conexion("128");
        generador4.setPicture("http://www.tpg.com.ec/images/galeria/_DSC0110.jpg");




        Generador generador5=new Generador();
        generador5.setCant_disponible(10);
        generador5.setCant_tomas(30);
        generador5.setCapacidad_galones(500);
        generador5.setColumnas(5);
        generador5.setDescripcion("Generador Gris 1");
        generador5.setEstado(1);
        generador5.setEstado_descripcion("Activo");
        generador5.setFecha_actualiza("27/03/2018 11:20:35");
        generador5.setFecha_registro("27/03/2018 11:20:35");
        generador5.setFilas(7);
        generador5.setId_rf_generador("1233");
        generador5.setId_rf_proveedor("123");
        generador5.setNombre("Generador Gris 1");
        generador5.setNombre_razon_social_proveedor("Provee Ricky Ramos Power Pack");
        generador5.setRuc_proveedor("0924194970001");
        generador5.setTomas_conectadas(18);
        generador5.setTomas_utiles(33);
        generador5.setUsuario_actualiza("rramos");
        generador5.setUsuario_registra("rramos");
        generador5.setBloque("T2-B");
        generador5.setFecha_inicio_conexion("27/03/2018 11:20:35");
        generador5.setHoras_conexion("128");
        generador5.setPicture("http://www.tpg.com.ec/images/galeria/_EVM1091.jpg");

        generador_array.add(generador);
        generador_array.add(generador2);
        generador_array.add(generador3);
        generador_array.add(generador4);
        generador_array.add(generador5);

        return generador_array;
    }

}

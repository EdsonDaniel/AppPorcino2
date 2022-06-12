package com.example.appporcino.ui.ganado;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.DetallePorcino;
import com.example.appporcino.EjemplarFragment;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.MyEjemplarRecyclerViewAdapter;
import com.example.appporcino.R;
import com.example.appporcino.data.OperacionesDB;

import java.util.ArrayList;

public class GanadoSementalFragment extends Fragment {

    private GanadoViewModel ganadoViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ganadoViewModel =
                ViewModelProviders.of(this).get(GanadoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ganado, container, false);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.text_ganado);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Porcino> porcinos = OperacionesDB.getInstancia(getContext()).selectPorcino();
        porcinos = filtro(porcinos);
        if(porcinos.isEmpty()){
            textView.setText("Aún no agregas ninguún ejemplar");
        }else{
            textView.setVisibility(View.GONE);
        }
        mAdapter = new MyEjemplarRecyclerViewAdapter(porcinos, new EjemplarFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Porcino item) {
                Intent intent = new Intent(getContext(), DetallePorcino.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }

            @Override
            public void onListFragmentInteractionLong(Porcino item, View v) {

            }
        });
        recyclerView.setAdapter(mAdapter);
    }
    private ArrayList<Porcino> filtro(ArrayList<Porcino> objetos){
        int size = objetos.size();
        //int i = 0;
        for(int i = 0; i<objetos.size();i++){
            if(!objetos.get(i).getTipo().equalsIgnoreCase("SEMENTAL")){
                objetos.remove(i);
                i--;
            }
        }
        return objetos;
    }
}
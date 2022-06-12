package com.example.appporcino.ui.alertas;

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

import com.example.appporcino.AgregarRecordatorio;
import com.example.appporcino.AlertaFragment;
import com.example.appporcino.DetalleRecordatorio;
import com.example.appporcino.Modelo.Recordatorio;
import com.example.appporcino.MyAlertaRecyclerViewAdapter;
import com.example.appporcino.R;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlertasFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;

    private AlertasViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(AlertasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alertas, container, false);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list_alerta);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        textView = view.findViewById(R.id.text_alertas);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Recordatorio> records = OperacionesDB.getInstancia(getContext()).selectRecord();
        if(records.isEmpty()){
            textView.setText("Aún no agregas ningún Recordatorio");
        }else{
            textView.setVisibility(View.GONE);
        }
        FloatingActionButton add = view.findViewById(R.id.add_record);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgregarRecordatorio.class);
                startActivity(intent);
            }
        });
        mAdapter = new MyAlertaRecyclerViewAdapter(records , new AlertaFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Recordatorio item) {
                Intent intent = new Intent(getContext(), DetalleRecordatorio.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
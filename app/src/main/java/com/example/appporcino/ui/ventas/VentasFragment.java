package com.example.appporcino.ui.ventas;

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

import com.example.appporcino.DetalleVenta;
import com.example.appporcino.Modelo.Venta;
import com.example.appporcino.MyVentaRecyclerViewAdapter;
import com.example.appporcino.NuevaVenta;
import com.example.appporcino.R;
import com.example.appporcino.VentaFragment;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VentasFragment extends Fragment {

    private VentasViewModel slideshowViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(VentasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ventas, container, false);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        textView = view.findViewById(R.id.text_ventas);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Venta> ventas = OperacionesDB.getInstancia(getContext()).selectVenta();
        if(ventas.isEmpty()){
            textView.setText("Aún no agrega ninguna Venta");
        }else{
            textView.setVisibility(View.GONE);
        }
        FloatingActionButton add = view.findViewById(R.id.add_venta);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NuevaVenta.class);
                startActivity(intent);
            }
        });
        mAdapter = new MyVentaRecyclerViewAdapter(ventas , new VentaFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Venta item) {
                Intent intent = new Intent(getContext(), DetalleVenta.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
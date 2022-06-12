package com.example.appporcino;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.Modelo.Venta;
import com.example.appporcino.data.OperacionesDB;

import java.util.ArrayList;

public class DetalleVenta extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Venta venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        venta = (Venta) getIntent().getSerializableExtra("item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rellenarCampos();
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPadding(6,6,6,6);
        ArrayList<Porcino> porkis = OperacionesDB.getInstancia(getApplicationContext()).getVendidos(venta.getId());
        mAdapter = new MyEjemplarRecyclerViewAdapter(porkis , new EjemplarFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Porcino item) {}
            @Override
            public void onListFragmentInteractionLong(Porcino item, View v) {}

        });
        recyclerView.setAdapter(mAdapter);

    }
    private void rellenarCampos(){
        TextView fecha = findViewById(R.id.titulo);
        TextView cliente = findViewById(R.id.conten_cliente);
        TextView monto = findViewById(R.id.content_monto);
        TextView ejemplares = findViewById(R.id.conten_ejemplares);
        TextView notas = findViewById(R.id.conten_notas);

        fecha.setText(venta.getFecha_venta());
        cliente.setText(venta.getCliente());
        monto.setText(""+venta.getMonto());
        ejemplares.setText(""+venta.getEjemplares());
        notas.setText(venta.getNotas());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}

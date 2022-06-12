package com.example.appporcino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.Modelo.Parto;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetallesCrias extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    private Porcino p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_crias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        p = (Porcino) getIntent().getSerializableExtra("item");

        recyclerView = findViewById(R.id.list_partos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        textView = findViewById(R.id.text_parto);
        ArrayList<Parto> partos = OperacionesDB.getInstancia(this).selectParto((int)p.getId());
        if(partos.isEmpty()){
            textView.setText("Aún no registras ningún parto");
        }else{
            textView.setVisibility(View.GONE);
        }
        mAdapter = new MyPartosRecyclerViewAdapter(partos, new PartoFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Parto item) {
                Intent intent = new Intent(getApplicationContext(), DetallePorcino.class);
                intent.putExtra("item",item);
                //startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = findViewById(R.id.add_parto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgregarParto.class);
                intent.putExtra("item",p);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}

package com.example.appporcino;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Recordatorio;

public class DetalleRecordatorio extends AppCompatActivity {
    private Recordatorio recor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recor = (Recordatorio) getIntent().getSerializableExtra("item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rellenarCampos();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    private void rellenarCampos() {
        TextView fecha = findViewById(R.id.conten_fecha);
        TextView hora = findViewById(R.id.conten_hora);
        TextView titulo = findViewById(R.id.titulo);
        TextView notas = findViewById(R.id.conten_notas);

        fecha.setText(recor.getFecha());
        hora.setText(recor.getHora());
        titulo.setText(recor.getTitulo());
        notas.setText(recor.getDescripcion());
        String co = ""+titulo.getText();
        if(co.length()>33){
            co = co.substring(0,33);
            co += "...";
            titulo.setText(co);
        }
    }

}

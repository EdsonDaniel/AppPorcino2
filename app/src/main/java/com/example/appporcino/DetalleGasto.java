package com.example.appporcino;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Gasto;

public class DetalleGasto extends AppCompatActivity {
    private Gasto gasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gasto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gasto = (Gasto) getIntent().getSerializableExtra("item");
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
        TextView monto = findViewById(R.id.content_monto);
        TextView concep = findViewById(R.id.concepto);
        TextView notas = findViewById(R.id.conten_notas);
        ImageView img = findViewById(R.id.img_concepto);
        String co = ""+concep.getText();
        fecha.setText(gasto.getFecha_pago());
        concep.setText(gasto.getConcepto());
        monto.setText(""+gasto.getMonto());
        notas.setText(gasto.getDescripcion());
        switch (co){
            case "Visita del veterinario":
                img.setImageResource(R.drawable.veterinario);
                break;
            case "Pago de alimento":
                img.setImageResource(R.drawable.trigo);
                break;
            case "Pago de agua potable":
                img.setImageResource(R.drawable.grifo);
                break;
            case "Pago de la luz":
                img.setImageResource(R.drawable.enchufe);
                break;
            case "Pago por inseminación":
                img.setImageResource(R.drawable.inseminacion);
                break;
            default:
                img.setImageResource(R.drawable.gasto);
                if(co.length()>33){
                    co = co.substring(0,33);
                    co += "...";
                    concep.setText(co);
                }
                break;
        }
    }

}

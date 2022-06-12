package com.example.appporcino;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DetallePorcino extends AppCompatActivity {
    private CardView layout_num;
    private TextView numero;
    private TextView label_num;
    private TextView nombre;
    private TextView proposito;
    private TextView estado;
    private TextView raza;
    private TextView fecha_n;
    private TextView fecha_a;
    private TextView edad;
    private TextView costo;
    private Toolbar toolbar;
    private Porcino p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_porcino);
        toolbar = findViewById(R.id.toolbar_detalle_porcino);
        setSupportActionBar(toolbar);

        p = (Porcino) getIntent().getSerializableExtra("item");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre = findViewById(R.id.titulo);
        proposito = findViewById(R.id.proposi);
        estado = findViewById(R.id.conten_estate);
        raza = findViewById(R.id.conten_raza);
        fecha_n = findViewById(R.id.conten_fechaN);
        fecha_a = findViewById(R.id.conten_fechaA);
        edad = findViewById(R.id.conten_edad);
        costo = findViewById(R.id.conten_costoA);
        label_num = findViewById(R.id.text_numero);
        numero = findViewById(R.id.conten_numero);
        layout_num = findViewById(R.id.card_numero);

        rellenarCampos(p);
        determinarTipo(p);
        setTitle(p.getNombre());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void rellenarCampos(Porcino p) {
        nombre.setText(p.getNombre());
        fecha_a.setText(p.getFecha_compra());
        fecha_n.setText(p.getFecha_nac());
        proposito.setText(p.getTipo());
        estado.setText(p.getEstado());
        raza.setText(p.getRaza());
        edad.setText(p.getEdad());
        if(p.getCosto()==0){
            costo.setText("Nacido en la granja");
        }else{
            costo.setText("$ "+p.getCosto());
        }
    }


    private void determinarTipo(Porcino p) {
        if(p.getTipo().equalsIgnoreCase("ENGORDA")){
            layout_num.setVisibility(View.GONE);
            return;
        }
        numero.setText(""+p.getNumero());
        if(p.getTipo().equalsIgnoreCase("CRíA")){
            label_num.setText("Número de Partos");
            TextView link = findViewById(R.id.link_detalles);
            link.setPaintFlags(link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            return;
        }
        if(p.getTipo().equalsIgnoreCase("SEMENTAL")){
            label_num.setText("Número de montas");
            TextView link = findViewById(R.id.link_detalles);
            link.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones_ganado, menu);
        if(p == null || !p.getTipo().equalsIgnoreCase("CRÍA")){
            menu.removeGroup(R.id.grupo_hembra);
        }
        if(p == null || p.getTipo().equalsIgnoreCase("ENGORDA")){
            menu.removeGroup(R.id.grupo_reprod);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_nueva_monta:
                if(p.getTipo().equalsIgnoreCase("SEMENTAL")){
                    intent = new Intent(DetallePorcino.this, NuevaMontaSemental.class);
                    intent.putExtra("item",p);
                    startActivity(intent);
                    return true;
                }
                intent = new Intent(DetallePorcino.this, NuevaMonta.class);
                intent.putExtra("item",p);
                startActivity(intent);
                return true;
            case R.id.action_editar:
                intent = new Intent(DetallePorcino.this, AgregarGanado.class);
                intent.putExtra("item",p);
                startActivity(intent);
                return true;
            case R.id.action_eliminar:
                crearDialog(p);
                return true;
            case R.id.action_ver_partos:
                intent = new Intent(DetallePorcino.this, DetallesCrias.class);
                intent.putExtra("item",p);
                startActivity(intent);
                return true;
            case R.id.action_vender:
                if(p.getEdadDias()<50){
                    crearDialog2();
                    return true;
                }
                intent = new Intent(DetallePorcino.this, NuevaVenta.class);
                intent.putExtra("item",p);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void crearDialog(Porcino p){
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_eliminar)
                .setTitle(R.string.title_eliminar);

        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                boolean b = OperacionesDB.getInstancia(context).deletePorcino(p);
                if(b){
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Ejemplar eliminado correctamente.", Toast.LENGTH_LONG);
                    toast1.show();
                    finish();
                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "No se pudo eliminar el ejemplar.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Acción cancelada", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void crearDialog2(){
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_peque)
                .setTitle(R.string.title_pequ);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}

package com.example.appporcino;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.MontasH;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class NuevaMonta extends AppCompatActivity implements View.OnClickListener {
    private Porcino p;
    private TextInputEditText fecha;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_monta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextInputEditText nombre = findViewById(R.id.nombre);
        p = (Porcino) getIntent().getSerializableExtra("item");
        nombre.setText(p.getNombre());
        fecha = findViewById(R.id.fecha_monta);
        fecha.setOnClickListener(this);
        fecha.setShowSoftInputOnFocus(false);
        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha.getError();
                    if(error==null){
                        showDatePickerDialog(fecha);
                    }
                    //fecha_compra.setTextIsSelectable(true);
                }
            }
        });
        Button agregar =findViewById(R.id.agregar);
        agregar.setOnClickListener(this);
        ponerFecha();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fecha_monta){
            showDatePickerDialog(fecha);
            return;
        }
        TextInputEditText fecha = findViewById(R.id.fecha_monta);
        TextInputEditText semental = findViewById(R.id.semental);
        TextInputEditText notas = findViewById(R.id.descripcion);
        RadioButton natural = findViewById(R.id.natural);
        String fech;
        if(Validacion.validaMonta(fecha,p.getFecha_nac())){
            fech = ""+fecha.getText();
            String sem = "" +semental.getText();
            int nat;
            if(natural.isChecked())
                nat = 1;
            else nat = 0;
            String nota = ""+notas.getText();
            MontasH monta = new MontasH((int)(p.getId()),fech,sem,nat,nota);
            long id = OperacionesDB.getInstancia(this).insertarMontaH(monta);
            if(id>-1){
                p.setEstado(Validacion.ESTADO_ESPERA_GESTACION);
                p.setFoto_url(fech);
                OperacionesDB.getInstancia(this).updatePorcino(p);
                Snackbar.make(v, "Monta agregada exitosamente: ID:" + id, Snackbar.LENGTH_LONG)
                        .setAction("Inserción exitosa", null).show();
                finish();
            }else{
                Snackbar.make(v, "No se pudo agregar Monta" + id, Snackbar.LENGTH_LONG)
                        .setAction("Inserción fallida", null).show();
                finish();
            }
        }else {
            return;
        }
    }
    private void ponerFecha(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
        fecha.setText(selectedDate);
    }
    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    private void showDatePickerDialog(final TextInputEditText edittext) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                edittext.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

}

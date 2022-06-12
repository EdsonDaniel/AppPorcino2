package com.example.appporcino;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Recordatorio;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.example.appporcino.ui.dialog.TimePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AgregarRecordatorio extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText fecha;
    private TextInputEditText hora;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_recordatorio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fecha = findViewById(R.id.fecha_recorda);
        fecha.setShowSoftInputOnFocus(false);
        fecha.setOnClickListener(this);
        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha.getError();
                    if(error==null){
                        showDatePickerDialog(fecha);
                    }
                }
            }
        });
        hora = findViewById(R.id.hora);
        hora.setShowSoftInputOnFocus(false);
        hora.setOnClickListener(this);
        hora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = hora.getError();
                    if(error==null){
                        showTimePicker(hora);
                    }
                }
            }
        });
        Button aceptar = findViewById(R.id.agregar);
        aceptar.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ponerFecha();
    }
    private void ponerFecha(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
        fecha.setText(selectedDate);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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
    private void showTimePicker(final TextInputEditText edittext) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedDate = hourOfDay+":"+minute;
                edittext.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }
    private void insertar(View v) {
        TextInputEditText descrip = findViewById(R.id.descripcion);
        TextInputEditText tit = findViewById(R.id.titulo);

        if(Validacion.validaRecordatorio(fecha)){
            String titulo = ""+tit.getText();
            String fech = ""+fecha.getText();
            String hour = ""+hora.getText();
            String descri = ""+descrip.getText();
            Recordatorio g = new Recordatorio(fech,titulo,descri,hour);
            long id = OperacionesDB.getInstancia(this).insertarRecordatorio(g);
            Snackbar.make(v, "Recordatorio agregado exitosamente: ID:" + id, Snackbar.LENGTH_LONG)
                    .setAction("Inserción exitosa", null).show();
            finish();
        }
        return;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fecha_recorda:
                showDatePickerDialog(fecha);
                break;
                case R.id.hora:
                    showTimePicker(hora);
                    break;
            case R.id.agregar:
                insertar(v);
                break;
        }
    }
}

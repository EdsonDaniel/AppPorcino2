package com.example.appporcino;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Gasto;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AgregarGasto extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText fecha;
    private AutoCompleteTextView concepto;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_gasto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        concepto = findViewById(R.id.concepto);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource
                (this, R.array.pagos, R.layout.dropdown_menu_popup_item);
        concepto.setAdapter(arrayAdapter);
        fecha = findViewById(R.id.fecha_pago);
        ponerFecha();
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
        Button aceptar = findViewById(R.id.agregar);
        aceptar.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fecha_pago:
                showDatePickerDialog(fecha);
                break;
            case R.id.agregar:
                insertar(v);
                break;
        }

    }

    private void insertar(View v) {
        TextInputEditText monto = findViewById(R.id.monto);
        TextInputEditText descrip = findViewById(R.id.descripcion);

        if(Validacion.validaGasto(fecha,monto)){
            String fech = ""+fecha.getText();
            String concep = ""+concepto.getText();
            String descri = ""+descrip.getText();
            double montoo;
            try{
                montoo = Double.parseDouble(""+monto.getText());

            }catch (Exception e){
                montoo = 0;
            }
            Gasto g = new Gasto(concep,fech,montoo,descri);
            long id = OperacionesDB.getInstancia(this).insertarGasto(g);
            Snackbar.make(v, "Gasto agregado exitosamente: ID:" + id, Snackbar.LENGTH_LONG)
                    .setAction("Inserción exitosa", null).show();
            finish();
        }
        return;
    }
}

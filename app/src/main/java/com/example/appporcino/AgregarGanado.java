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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarGanado extends AppCompatActivity
implements View.OnClickListener {

    private TextInputEditText fecha_nac;
    private TextInputEditText fecha_compra;
    private AutoCompleteTextView tipo;
    final Validacion valid = new Validacion();
    Porcino p;
    private Button add;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ganado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add = findViewById(R.id.agregar);
        tipo = findViewById(R.id.filled_exposed_dropdown);
        tipo.setShowSoftInputOnFocus(false);
        fecha_nac = findViewById(R.id.fecha_nac);
        fecha_nac.setOnClickListener(this);
        fecha_nac.setShowSoftInputOnFocus(false);
        //fecha_nac.setTextIsSelectable(false);
        fecha_nac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_nac.getError();
                    if(error==null){
                        showDatePickerDialog(fecha_nac);
                    }
                }
            }
        });

        fecha_compra = findViewById(R.id.fecha_compra);
        fecha_compra.setOnClickListener(this);
        fecha_compra.setShowSoftInputOnFocus(false);
        fecha_compra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_compra.getError();
                    if(error==null){
                        showDatePickerDialog(fecha_compra);
                    }
                    //fecha_compra.setTextIsSelectable(true);
                }
            }
        });
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource
                (this, R.array.tipos, R.layout.dropdown_menu_popup_item);
        tipo.setAdapter(arrayAdapter);
        tipo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                }
            }
        });
        ArrayAdapter<CharSequence> arrayRazas = ArrayAdapter.createFromResource
                (this, R.array.razas, R.layout.dropdown_menu_popup_item);
        AutoCompleteTextView razas = findViewById(R.id.raza);
        razas.setAdapter(arrayRazas);
        Button agregar = findViewById(R.id.agregar);
        agregar.setOnClickListener(this);
        RadioButton hembra = findViewById(R.id.hembra);
        RadioButton macho = findViewById(R.id.macho);
        hembra.setOnClickListener(this);
        macho.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        p = (Porcino) getIntent().getSerializableExtra("item");
        rellenar(p);
    }

    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fecha_nac:
                showDatePickerDialog(fecha_nac);
                break;
            case R.id.fecha_compra:
                showDatePickerDialog(fecha_compra);
                break;
            case R.id.agregar:
                insertar(v);
                break;
            case R.id.hembra:
                camposGenero(1);
                break;
            case R.id.macho:
                camposGenero(2);
                break;
        }
    }
    private void camposGenero(int genero) {
        TextInputLayout numero = findViewById(R.id.input_numero);
        String prop = ""+tipo.getText();

        if (genero == 1) {
            if(prop.equalsIgnoreCase("Cría")){
                numero.setHint("Num Crías*");
            }
            else numero.setHint("Num Crías");
        } else {
            if(prop.equalsIgnoreCase("Semental"))
                numero.setHint("Num Montas*");
            else numero.setHint("Num Montas");
        }
    }

    private void showDatePickerDialog(final TextInputEditText edittext) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                edittext.setText(selectedDate);
                if(edittext.getId()==R.id.fecha_nac){
                    fecha_compra.setText(selectedDate);
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void insertar(View v) {
        RadioButton hembra = findViewById(R.id.hembra);
        TextInputEditText nom = findViewById(R.id.nombre);
        AutoCompleteTextView razas = findViewById(R.id.raza);
        TextInputEditText costo = findViewById(R.id.costo);
        AutoCompleteTextView tip = findViewById(R.id.filled_exposed_dropdown);
        TextInputEditText numero = findViewById(R.id.num_crias);
        String foto = "";
        String fecha_venta = "";
        String genero;
        String num = "" + numero.getText();
        String nombre = "" + nom.getText();
        String fecha_n = "" + fecha_nac.getText();
        String raza = "" + razas.getText();
        String fecha_c = "" + fecha_compra.getText();
        String costoo = "" + costo.getText();
        String tipo = "" + tip.getText();
        String descrip = ""+((TextInputEditText)findViewById(R.id.descripcion)).getText();
        int max = 999;
        int n = 0;
        String estado = "En desarrollo";
        long id = -1;
        if (hembra.isChecked()) {
            genero = "H";
            max = 30;
        } else genero = "M";
        String labelB = ""+add.getText();
        if (valid.validaCampos(nom, fecha_nac, fecha_compra, razas, tip, costo, numero,genero)
                && valid.validaNum(num, max, numero,tipo)) {
            double p;
            try {
                n = Integer.parseInt(num);
            } catch (Exception e) {
                n = 0;
            }
            try {
                p = Double.parseDouble(costoo);
            } catch (Exception e) {
                p = 0;
            }
            Porcino porcino = new Porcino(nombre, fecha_n, tipo,
                    foto, genero, raza, descrip, fecha_c, fecha_venta, 0,p,estado,n);
            if(labelB.equalsIgnoreCase("ACTUALIZAR")){
                porcino.setId(this.p.getId());
                estado = Validacion.validaEstado(porcino.getFecha_nac(),porcino.getTipo());
                porcino.setEstado(estado);
                boolean b = OperacionesDB.getInstancia(this).updatePorcino(porcino);
                if(b){
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Actualizado correctamente. ID: "+porcino.getId(), Toast.LENGTH_SHORT);
                    toast1.show();
                    finish();
                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Hubo un error al actualizar", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }else{
                estado = Validacion.validaEstado(porcino.getFecha_nac(),porcino.getTipo());
                porcino.setEstado(estado);
                id = OperacionesDB.getInstancia(this).insertarPorcino(porcino);
                Snackbar.make(v, "Porcino agregado exitosamente: ID:" + id, Snackbar.LENGTH_LONG)
                        .setAction("Inserción exitosa", null).show();
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Propósito "+tipo+" agregado", Toast.LENGTH_SHORT);
                toast1.show();
                finish();
            }
        }
        else {
            Snackbar.make(v, "Verifica los datos", Snackbar.LENGTH_LONG)
                    .setAction("Insersión fallida", null).show();
        }
    }
    private void rellenar(Porcino p){
        if(p==null)
            return;
        RadioButton hembra = findViewById(R.id.hembra);
        RadioButton macho = findViewById(R.id.macho);
        TextInputEditText nom = findViewById(R.id.nombre);
        AutoCompleteTextView razas = findViewById(R.id.raza);
        TextInputEditText costo = findViewById(R.id.costo);
        AutoCompleteTextView tip = findViewById(R.id.filled_exposed_dropdown);
        TextInputEditText numero = findViewById(R.id.num_crias);

        if(p.getGenero().equalsIgnoreCase("H"))
            hembra.setChecked(true);
        else macho.setChecked(true);
        nom.setText(p.getNombre());
        razas.setText(p.getRaza());
        tip.setText(p.getTipo());
        fecha_nac.setText(p.getFecha_nac());
        fecha_compra.setText(p.getFecha_compra());
        numero.setText(""+p.getNumero());
        costo.setText(""+p.getCosto());
        add.setText("Actualizar");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
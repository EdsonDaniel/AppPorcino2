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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appporcino.Modelo.Parto;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AgregarParto extends AppCompatActivity implements View.OnClickListener {
    private Porcino p;
    private Parto parto;
    private TextInputEditText fecha_monta;
    private TextInputEditText fecha_parto;
    private TextInputEditText fecha_destete;
    private Button add;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_parto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add = findViewById(R.id.agregar);
        fecha_monta = findViewById(R.id.fecha_cruza);
        fecha_parto = findViewById(R.id.fecha_parto);
        fecha_destete = findViewById(R.id.fecha_destete);
        fecha_monta.setOnClickListener(this);
        fecha_destete.setOnClickListener(this);
        fecha_parto.setOnClickListener(this);

        fecha_monta.setShowSoftInputOnFocus(false);
        fecha_parto.setShowSoftInputOnFocus(false);
        fecha_destete.setShowSoftInputOnFocus(false);
        fecha_monta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_monta.getError();
                    if(error==null){
                        showDatePickerDialog(fecha_monta);
                    }
                }
            }
        });
        fecha_destete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_destete.getError();
                    if(error==null){
                        showDatePickerDialog(fecha_destete);
                    }
                }
            }
        });
        fecha_parto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_parto.getError();
                    if(error==null){
                        showDatePickerDialog(fecha_parto);
                    }
                }
            }
        });
        p = (Porcino) getIntent().getSerializableExtra("item");
        parto = (Parto) getIntent().getSerializableExtra("item_parto");
        ponerFecha();
        rellenar(p,parto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    private void ponerFecha(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
        fecha_parto.setText(selectedDate);
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DAY_OF_YEAR,-115);
        String s = twoDigits(c2.get(Calendar.DAY_OF_MONTH)) + "/" + twoDigits(c2.get(Calendar.MONTH)+1) + "/" + c2.get(Calendar.YEAR);
        fecha_monta.setText(s);
    }

    private void showDatePickerDialog(final TextInputEditText edittext) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                edittext.setText(selectedDate);
                if(edittext.getId()==R.id.fecha_parto){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    Date part;
                    try{part = formatter.parse(selectedDate);}catch (Exception e){return;}
                    c.setTime(part);
                    c.add(Calendar.DAY_OF_YEAR,-115);
                    String s = twoDigits(c.get(Calendar.DAY_OF_MONTH)) + "/" + twoDigits(c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);
                    fecha_monta.setText(s);
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fecha_monta:
                showDatePickerDialog(fecha_monta);
                break;
            case R.id.fecha_parto:
                showDatePickerDialog(fecha_parto);
                break;
            case R.id.fecha_destete:
                showDatePickerDialog(fecha_destete);
                break;
            case R.id.agregar:
                insertar(v);
                break;
        }
    }
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void insertar(View va) {
        RadioButton bueno = findViewById(R.id.bueno);
        RadioButton malo = findViewById(R.id.malo);
        TextInputEditText nom = findViewById(R.id.nombre2);
        TextInputEditText num = findViewById(R.id.num_cria);
        TextInputEditText num_lechon = findViewById(R.id.num_lechon);
        TextInputEditText num_viv = findViewById(R.id.num_vivos);
        TextInputEditText notas = findViewById(R.id.notasparto);
        String fech_monta = "";
        String fech_parto = "";
        String fech_destete = "";
        String num_l = "" + num_lechon.getText();
        String num_v = "" + num_viv.getText();
        String nume = "" + num.getText();
        String nombre = "" + nom.getText();
        String descrip = ""+notas.getText();
        String labelB = ""+add.getText();
        int natu;
        if(bueno.isChecked())
            natu = 1;
        else natu = 0;
        int v,t;
        try{
            v = Integer.parseInt(num_v);
            t = Integer.parseInt(num_l);
        }
        catch (Exception e){
            v = 0;
            t = 0;
        }

        Parto par = new Parto(fech_parto,fech_monta,fech_destete,"",1,natu,(int)p.getId(),t,v,descrip);
        long id = OperacionesDB.getInstancia(this).insertarParto(par);
        if(id>0){
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "Parto agregado correctamente", Toast.LENGTH_LONG);
            toast1.show();
            finish();
        }
        else {
            Snackbar.make(va, "Verifica los datos", Snackbar.LENGTH_LONG)
                    .setAction("Insersión fallida", null).show();
        }
    }
    private void rellenar(Porcino p, Parto parto){
        TextInputEditText nom = findViewById(R.id.nombre2);
        TextInputEditText num = findViewById(R.id.num_cria);
        nom.setText(p.getNombre());
        ArrayList<Parto> partos = OperacionesDB.getInstancia(this).selectParto((int)p.getId());
        String numero = "";
        if(p.getNumero()==0){
            numero = "1";
            num.setFocusable(false);
        }else{
            numero = ""+partos.size()+1;
        }
        num.setText(numero);
        if(parto==null)
            return;
        RadioButton bueno = findViewById(R.id.bueno);
        RadioButton malo = findViewById(R.id.malo);
        TextInputEditText num_lechon = findViewById(R.id.num_lechon);
        TextInputEditText num_viv = findViewById(R.id.num_vivos);
        TextInputEditText num_mue = findViewById(R.id.num_muertos);
        TextInputEditText notas = findViewById(R.id.notasparto);

        if(parto.getExitoso()==1)
            bueno.setChecked(true);
        else malo.setChecked(true);
        num_viv.setText(""+parto.getVivos());
        num_lechon.setText(""+parto.getLechones());
        int muertos = parto.getLechones()-parto.getVivos();
        num_mue.setText(""+muertos);
        num_viv.setText(""+parto.getVivos());
        fecha_monta.setText(parto.getFecha_cruza());
        fecha_destete.setText(parto.getFecha_destete());
        fecha_parto.setText(parto.getFecha_parto());
        notas.setText(parto.getNotas());
        add.setText("Actualizar");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}

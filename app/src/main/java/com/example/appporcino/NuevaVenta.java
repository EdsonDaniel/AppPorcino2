package com.example.appporcino;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.Modelo.Detalle_venta;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.Modelo.Venta;
import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;
import com.example.appporcino.ui.dialog.DatePickerFragment;
import com.example.appporcino.ui.dialog.DialogSeleccion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class NuevaVenta extends AppCompatActivity
        implements DialogSeleccion.NoticeDialogListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Porcino> carrito;
    private ArrayList<Porcino> a_vender;
    private DialogSeleccion dialogo;
    private TextInputEditText cliente;
    private boolean[] selected;
    private double[] precios;
    private TextInputEditText fecha_venta;
    private TextInputEditText monto;
    private Porcino p;
    private CardView card;


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_venta);
        FloatingActionButton fab = findViewById(R.id.add_ejem);
        fab.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cliente = findViewById(R.id.cliente);
        fecha_venta = findViewById(R.id.fecha_venta);
        fecha_venta.setOnClickListener(this);
        fecha_venta.setShowSoftInputOnFocus(false);
        fecha_venta.setTextIsSelectable(false);
        card = findViewById(R.id.card);
        card.setOnClickListener(this);
        //fab.setTooltipText("Presiona el botón para agregar");
        fecha_venta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    closeSoftKeyBoard();
                    CharSequence error = fecha_venta.getError();
                    if(error==null){
                        closeSoftKeyBoard();
                        showDatePickerDialog(fecha_venta);
                    }
                }
            }
        });
        ponerFecha();
        monto = findViewById(R.id.monto_total);
        recyclerView = findViewById(R.id.list);
        recyclerView.requestFocus();
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        a_vender = new ArrayList<>();
        carrito = new ArrayList<Porcino>();
        p = (Porcino) getIntent().getSerializableExtra("item");
        if(p!=null){
            carrito.add(p);
            card.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            cliente.requestFocus();
        }
        ///////////////////////////////////////////////////////////////////////////7
        Button agregar = findViewById(R.id.agregar);
        agregar.setOnClickListener(this);
        new Consultas().execute();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    private CharSequence[] getNames(ArrayList<Porcino> porcinos){
        CharSequence[] names = new CharSequence[porcinos.size()];
        for (int i = 0; i<porcinos.size(); i++){
            names[i] = porcinos.get(i).getNombre();
        }
        return names;
    }
    private void quitarSeleccinado(String n){
        for(int i = 0; i<a_vender.size(); i++){
            if(a_vender.get(i).getNombre().equalsIgnoreCase(n)){
                selected[i] = false;
                precios[i] = 0;
                dialogo.setItems(selected.clone());
                return;
            }
        }
        ArrayList<Porcino> car = (ArrayList<Porcino>) ((MyEjemplarRecyclerViewAdapter2) mAdapter).getItems();
        if(car.isEmpty()){
            card.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            card.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }
    private void inicia_precios(){
        precios = new double[selected.length];
        for(int i = 0; i<precios.length;i++){
            precios[i] = 0;
        }
    }
    private void actualizarLista(boolean[] nuevo){
        ArrayList<Porcino> car = (ArrayList<Porcino>) ((MyEjemplarRecyclerViewAdapter2) mAdapter).getItems();
        for(int i = 0; i<selected.length; i++){
            if(selected[i] != nuevo[i]){
                if(nuevo[i]){
                    ((MyEjemplarRecyclerViewAdapter2) mAdapter).addItems(a_vender.get(i),mAdapter.getItemCount());
                }else{
                    int delete = indexOf(a_vender.get(i),car);
                    ((MyEjemplarRecyclerViewAdapter2) mAdapter).deleteItem(delete);
                }
            }
        }
        if(car.isEmpty()){
            card.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            card.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        actualizarLista(dialogo.getSeleccionados());
        selected = dialogo.getSeleccionados().clone();
    }
    public int indexOf(Porcino p, ArrayList<Porcino> porcinos){
        if(p==null){
            return -1;
        }
        for (int i = 0; i<porcinos.size(); i++){
            if(p.getId()== porcinos.get(i).getId())
                return i;
        }
        return -1;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agregar:
                insertar(v);
                break;
            case R.id.card:
            case R.id.add_ejem:
                dialogo.show(getSupportFragmentManager(),"dialog");
                break;
                //return;
        }
    }
    private void insertar(View v) {
        TextInputEditText name = findViewById(R.id.cliente);
        String descrip = ""+((TextInputEditText)findViewById(R.id.descripcion)).getText();
        String cliente = ""+name.getText();
        ArrayList pre = ((MyEjemplarRecyclerViewAdapter2) mAdapter).getPrecios();
        double pre_total = ((MyEjemplarRecyclerViewAdapter2) mAdapter).getTotal();
        String f = ""+fecha_venta.getText();
        carrito = (ArrayList<Porcino>) ((MyEjemplarRecyclerViewAdapter2) mAdapter).getItems();
        if(carrito.isEmpty()){
            Snackbar.make(v, "Debe agregar por lo menos un ejemplar a la lista", Snackbar.LENGTH_LONG)
                    .setAction("Insersión fallida", null).show();
            return;
        }
        if (Validacion.validaFutura(fecha_venta,name)){
            if(!Validacion.validaPrecios(pre)){
                Snackbar.make(v, "Los precios de venta deben ser mayores a 0", Snackbar.LENGTH_LONG)
                        .setAction("Insersión fallida", null)
                        .setBackgroundTint(getResources().getColor(R.color.colorPrimary))
                        .show();
                return;
            }
            double[] p = Validacion.getPrecios(pre);
            Venta ventas = new Venta(f,cliente,pre_total,descrip);
            long id = OperacionesDB.getInstancia(this).insertarVenta(ventas);
            updateEstado(carrito);
            addDetalleVenta(p,(int)id,carrito);
            Toast toast1 = Toast.makeText(getApplicationContext(),"Venta registrada exitosamente: ID:" + id, Toast.LENGTH_SHORT);
            toast1.show();
            finish();
        }
        else {
            Snackbar.make(v, "Verifica los datos", Snackbar.LENGTH_LONG)
                    .setAction("Insersión fallida", null).show();
        }
    }
    private void updateEstado(ArrayList<Porcino> carito) {
        for(int i = 0; i<carito.size(); i++){
            carito.get(i).setEstado("Vendido");
            carito.get(i).setFecha_venta(""+fecha_venta.getText());
            OperacionesDB.getInstancia(this).updatePorcino(carito.get(i));
        }
    }
    private void showDatePickerDialog(final TextInputEditText edittext) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                edittext.setText(selectedDate);
                closeSoftKeyBoard();
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }
    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    private void ponerFecha(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
        fecha_venta.setText(selectedDate);
    }
    private void addDetalleVenta(double[] pre, int id_v, ArrayList<Porcino> por){
        for(int i = 0; i<pre.length; i++){
            Detalle_venta det = new Detalle_venta(id_v,pre[i],(int)por.get(i).getId());
            OperacionesDB.getInstancia(this).insertarVentaDetalle(det);
        }
    }

    private void consulta(){
        a_vender = OperacionesDB.getInstancia(getApplicationContext()).selectPorcinoVentas();
    }

    private void notificarAdaptadores(){
        CharSequence[] names = getNames(a_vender);
        int i = indexOf(p, a_vender);
        dialogo = new DialogSeleccion(names,i);
        selected = dialogo.getSeleccionados();
        selected = selected.clone();
        inicia_precios();
        mAdapter = new MyEjemplarRecyclerViewAdapter2(carrito, new EjemplarFragment2.OnListFragmentInteractionListener() {
            public void onListFragmentInteractionDelete(View v, RecyclerView.ViewHolder viewHolder) {
                MyEjemplarRecyclerViewAdapter2.ViewHolder vi;
                vi = (MyEjemplarRecyclerViewAdapter2.ViewHolder) viewHolder;
                String s = ""+vi.titulo.getText();
                vi.deleteItem(vi.getAdapterPosition());
                quitarSeleccinado(s);
            }
        },monto);
        recyclerView.setAdapter(mAdapter);
    }
    private class Consultas extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(@NonNull Void... voids) {
            consulta();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            notificarAdaptadores();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}

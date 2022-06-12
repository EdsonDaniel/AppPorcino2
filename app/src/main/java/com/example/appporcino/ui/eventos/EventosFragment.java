package com.example.appporcino.ui.eventos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.EventFragment;
import com.example.appporcino.Modelo.Evento;
import com.example.appporcino.MyEventRecyclerViewAdapter;
import com.example.appporcino.R;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventosFragment extends Fragment implements OnDateSelectedListener {

    private EventosViewModel eventosViewModel;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventosViewModel =
                ViewModelProviders.of(this).get(EventosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_eventos, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = getActivity().findViewById(R.id.add_event);
        MaterialCalendarView calendar = view.findViewById(R.id.calendar);
        calendar.setTopbarVisible(false);
        calendar.setOnDateChangedListener(this);
        calendar.setCurrentDate(LocalDate.now());
        calendar.setSelectedDate(LocalDate.now());
        calendar.addDecorators(oneDayDecorator);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR},
                            1);
                    return;
                }
                Calendar beginTime = Calendar.getInstance();
                Date hoy = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(hoy);
                calendar.add(Calendar.DAY_OF_YEAR,1);
                beginTime.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 7, 00);
                Calendar endTime = Calendar.getInstance();
                endTime.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 8, 00);
                //insetrta();
                Intent intent = new Intent(Intent.ACTION_INSERT).
                        setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Nueva tarea")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Tarea pendiente")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Mi granja")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivityForResult(intent, 1);
            }
        });
        recyclerView = view.findViewById(R.id.list_event);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        textView = view.findViewById(R.id.text_events);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Evento> events = OperacionesDB.getInstancia(getContext()).selectEventos();
        if(events.isEmpty()){textView.setText("Aún no agregas ningún Evento");}else{textView.setVisibility(View.GONE);}
        mAdapter = new MyEventRecyclerViewAdapter(events , new EventFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Evento item) {
                Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,item.getId());
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                startActivity(intent);
            }
            @Override
            public void onListFragmentInteractionLong(Evento itemm, View v) {
                final int id = itemm.getId();
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.inflate(R.menu.opciones);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Uri uri;
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.action_ver:
                                uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                                intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                                startActivity(intent);
                                break;
                            case R.id.action_editar:
                                uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                                intent = new Intent(Intent.ACTION_EDIT).setData(uri);
                                startActivity(intent);
                                break;
                            case R.id.action_eliminar:
                                crearDialog(itemm);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
    private void insetrta(){
        Evento ev = new Evento(6,"12/12/12","12/12/12",1,"Titulo de ","DEscripcioni","Loca","12:00","");
        long i = OperacionesDB.getInstancia(getContext()).insertarEvento(ev);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri result;
        long id = 0;
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                ContentResolver cr = getContext().getContentResolver();
                result = Uri.parse("content://com.android.calendar/events");
                Cursor cursor = cr.query(result, new String[]{ "MAX(_id) as max_id" }, null, null, "_id");
                cursor.moveToFirst();
                long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));
                id = max_val;
                datosFecha(id);
            }
            else{
                Toast toast1 = Toast.makeText(getContext(),"Evento FALIDO: "+resultCode, Toast.LENGTH_LONG);
                toast1.show();
            }
        }
    }
    private void datosFecha(long id){
        String[] projection = new String[]{
                CalendarContract.Events.TITLE,          //index id = 0
                CalendarContract.Events._ID,            //index id = 1
                CalendarContract.Events.DESCRIPTION,    //index id = 2
                CalendarContract.Events.EVENT_LOCATION, //index id = 3
                CalendarContract.Events.AVAILABILITY,   //index id = 4
                CalendarContract.Events.DTEND,          //index id = 5
                CalendarContract.Events.DTSTART,        //index id = 6
                CalendarContract.Events.ALL_DAY         //index id = 7
        };
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
        Cursor cursor = getContext().getContentResolver().query(uri,projection,null,null,null);
        cursor.moveToFirst();
        String titulo = cursor.getString(0);
        long _id = cursor.getLong(1);
        String des = cursor.getString(2);
        String loca = cursor.getString(3);
        String disp = cursor.getString(4);
        long fin = cursor.getLong(5);
        long ini = cursor.getLong(6);
        int all = cursor.getInt(7);

        Calendar inicio = Calendar.getInstance();
        inicio.setTimeInMillis(ini);
        Calendar termina = Calendar.getInstance();
        termina.setTimeInMillis(fin);
        int year1 = inicio.get(Calendar.YEAR);
        int year2 = termina.get(Calendar.YEAR);
        int mes1 = inicio.get(Calendar.MONTH);
        int mes2 = termina.get(Calendar.MONTH);
        int day1 = inicio.get(Calendar.DAY_OF_MONTH);
        int day2 = termina.get(Calendar.DAY_OF_MONTH);
        int hour1 = inicio.get(Calendar.HOUR_OF_DAY);
        int hour2 = termina.get(Calendar.HOUR_OF_DAY);
        int min1 = inicio.get(Calendar.MINUTE);
        int min2 = termina.get(Calendar.MINUTE);
        String date1 = twoDigits(day1) + "/" + twoDigits(mes1 + 1) + "/" + year1;
        String date2 = twoDigits(day2) + "/" + twoDigits(mes2 + 1) + "/" + year2;
        String h1 = String.format("%02d",hour1) + ":" + String.format("%02d",min1);
        String h2 = String.format("%02d",hour2) + ":" + String.format("%02d",min2);
        Evento ev = new Evento((int)(_id),date1,date2,all,titulo,des,loca,h1+"-"+h2,"");
        long i = OperacionesDB.getInstancia(getContext()).insertarEvento(ev);
        if(i<0){
            Toast toast1 = Toast.makeText(getContext(),
                    "Evento No Agregado:", Toast.LENGTH_LONG);
            toast1.show();
            return;
        }
        Toast toast1 = Toast.makeText(getContext(),
                "Evento agregado: ID: "
                        +_id+"\nTitulo: "+titulo
                        +"\nfecha: "+date1+"\nHora1: "
                +h1+"\nMinuto: "+min1, Toast.LENGTH_LONG);
        toast1.show();

    }
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }
    private void crearDialog(Evento e){
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.message_eliminar_evento)
                .setTitle(R.string.title_eliminar_event);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //String DEBUG_TAG = "MyActivity";
                ContentResolver cr = getActivity().getContentResolver();
                Uri deleteUri = null;
                deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, e.getId());
                int rows = cr.delete(deleteUri, null, null);
                boolean b = OperacionesDB.getInstancia(context).deleteEvento(e);
                if(b){
                    Toast toast1 = Toast.makeText(getContext(),
                            "Ejemplar eliminado correctamente.", Toast.LENGTH_LONG);
                    toast1.show();
                }else{
                    Toast toast1 = Toast.makeText(getContext(),
                            "No se pudo eliminar el ejemplar.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast toast1 = Toast.makeText(getContext(),
                        "Acción cancelada", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
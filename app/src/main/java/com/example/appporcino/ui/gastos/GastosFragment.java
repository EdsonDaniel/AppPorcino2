package com.example.appporcino.ui.gastos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.AgregarGasto;
import com.example.appporcino.DetalleGasto;
import com.example.appporcino.GastoFragment;
import com.example.appporcino.Modelo.Gasto;
import com.example.appporcino.MyGastoRecyclerViewAdapter;
import com.example.appporcino.R;
import com.example.appporcino.data.OperacionesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GastosFragment extends Fragment {

    private GastosViewModel gastosViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gastosViewModel =
                ViewModelProviders.of(this).get(GastosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gastos, container, false);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list_gastos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        textView = view.findViewById(R.id.text_gastos);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Gasto> gastos = OperacionesDB.getInstancia(getContext()).selectGasto();
        if(gastos.isEmpty()){
            textView.setText("Aún no agregas ningún Gasto");
        }else{
            textView.setVisibility(View.GONE);
        }
        FloatingActionButton add = view.findViewById(R.id.add_gasto);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgregarGasto.class);
                startActivity(intent);
            }
        });
        mAdapter = new MyGastoRecyclerViewAdapter(gastos , new GastoFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Gasto item) {
                Intent intent = new Intent(getContext(), DetalleGasto.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }

            @Override
            public void onListFragmentInteractionLong(Gasto itemm, View v) {
                final int id = (int)itemm.getId();
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.inflate(R.menu.opciones);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Uri uri;
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.action_ver:
                                /*uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                                intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                                startActivity(intent);*/
                                break;
                            case R.id.action_editar:
                                /*uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                                intent = new Intent(Intent.ACTION_EDIT).setData(uri);
                                startActivity(intent);
                                break;*/
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
    private void crearDialog(Gasto e){
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.message_eliminar_elemento)
                .setTitle(R.string.title_eliminar_elemento);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                boolean b = OperacionesDB.getInstancia(context).deleteGasto(e);
                if(b){
                    Toast toast1 = Toast.makeText(getContext(),
                            "Elemento eliminado correctamente.", Toast.LENGTH_LONG);
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








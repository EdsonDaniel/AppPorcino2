package com.example.appporcino;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.data.OperacionesDB;

import java.util.ArrayList;


public class BlankFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    private TextView textView2;
    private ArrayList<Object> datos = new ArrayList<>();
    private View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return root = inflater.inflate(R.layout.fragment_blank, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        textView = view.findViewById(R.id.text_inicio);
        textView2 = view.findViewById(R.id.label);
        recyclerView.setLayoutManager(layoutManager);
        new Consultas().execute();
    }
    private void consulta(){
        datos = OperacionesDB.getInstancia(getContext()).notificaciones();
    }
    private void notificarAdaptadores(){
        mAdapter = new AdapterObject(datos, new ObjectFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Object item) {

            }
        });
        recyclerView.setAdapter(mAdapter);
        if(datos.isEmpty()){
            textView.setText("No hay eventos ni alertas por el momento");
            textView2.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.GONE);
        }
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

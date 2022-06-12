package com.example.appporcino.ui.produccion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.R;
import com.example.appporcino.data.OperacionesDB;

import java.util.ArrayList;

public class ProduccionFragment extends Fragment {

    private ArrayList<Porcino> semen = new ArrayList<Porcino>();
    private ArrayList<Porcino> hembras= new ArrayList<Porcino>();
    private ArrayList<Porcino> engorda= new ArrayList<Porcino>();
    private ArrayList<Porcino> vendidos= new ArrayList<Porcino>();
    private ArrayList<ArrayList<Porcino>> set= new ArrayList<>();
    private double m_ventas;
    private double m_montas;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProduccionViewModel homeViewModel = ViewModelProviders.of(this).get(ProduccionViewModel.class);
        root = inflater.inflate(R.layout.fragment_produccion, container, false);
        new Consultas().execute();
        return root;
    }

    private void init(){
        TextView tot_ani = root.findViewById(R.id.conten_total_ani);
        TextView tot_hem = root.findViewById(R.id.conten_total_hembras);
        TextView tot_sem = root.findViewById(R.id.conten_sementales);
        TextView tot_eng = root.findViewById(R.id.conten_engorda);
        TextView tot_ven = root.findViewById(R.id.conten_vendidos);
        TextView cargadas = root.findViewById(R.id.conten_cargadas);
        TextView lactandp = root.findViewById(R.id.conten_lactando);
        TextView monto_ven= root.findViewById(R.id.conten_monto_vendidos);
        TextView monto_sem= root.findViewById(R.id.conten_monto_montas);
        TextView total = root.findViewById(R.id.conten_total);
        String ej = " ejemplares";

        String tot = ""+(hembras.size()+semen.size()+engorda.size())+ej;
        tot_ani.setText(tot);
        tot_hem.setText(""+hembras.size()+ej);
        tot_eng.setText(""+engorda.size()+ej);
        tot_sem.setText(""+semen.size()+ej);
        tot_ven.setText(""+vendidos.size()+ej);
        monto_sem.setText("$"+m_montas);
        monto_ven.setText("$"+m_ventas);
        tot = "$"+(m_ventas+m_montas);
        total.setText(tot);
        tot = ""+conteo("En gestación")+ej;
        cargadas.setText(tot);
        tot = ""+conteo("En Lactancia")+ej;
        lactandp.setText(tot);
    }

    private int conteo(String tipo){
        int count = 0;
        for(int i = 0; i<hembras.size();i++){
            if(hembras.get(i).getEstado().equalsIgnoreCase(tipo)){
                count++;
            }
        }
        return count;
    }

    private void consulta(){
        set = OperacionesDB.getInstancia(getContext()).getConjuto();
        hembras = set.get(0);
        engorda = set.get(1);
        semen = set.get(2);
        vendidos = set.get(3);
        m_ventas = OperacionesDB.getInstancia(getContext()).montoTotalVentas();
        m_montas = OperacionesDB.getInstancia(getContext()).montoCruzas();
    }
    private void cargarUI(){
        init();
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
            cargarUI();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}
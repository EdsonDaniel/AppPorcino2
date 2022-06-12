package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.Modelo.Evento;
import com.example.appporcino.Modelo.Recordatorio;
import com.example.appporcino.ObjectFragment.OnListFragmentInteractionListener;

import java.util.List;

public class AdapterObject extends RecyclerView.Adapter<AdapterObject.ViewHolder> {

    private final List<Object> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AdapterObject(List<Object> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterObject.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Evento e = null;
        Recordatorio r = null;
        String titulo="";
        String f1="";
        String f2="";
        String des="";
        if(holder.mItem instanceof Evento){
            e = (Evento)holder.mItem;
            titulo = e.getTitulo();
            f1 = e.getFecha_inicio();
            f2 = e.getTipo();
            des = e.getDescripcion();
            holder.img.setImageResource(R.drawable.calendar);
        }else{
            r = (Recordatorio)holder.mItem;
            titulo = r.getTitulo();
            f1 = r.getFecha();
            f2 = r.getHora();
            des = r.getDescripcion();
            holder.img.setImageResource(R.drawable.recordatorio);
        }
        if(titulo.length()>17){
            titulo = titulo.substring(0,17);
            titulo += "...";
        }
        if(des.length()>25){
            des = des.substring(0,25);
            des += "...";
        }
        holder.titulo.setText(titulo);
        holder.fecha1.setText(f1);
        holder.fecha2.setText(f2);
        holder.descrip.setText(des);

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView fecha1;
        public final TextView fecha2;
        public final TextView titulo;
        public final TextView descrip;
        public final ImageView img;
        public Object mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fecha1 = (TextView) view.findViewById(R.id.fecha);
            fecha2 = (TextView) view.findViewById(R.id.hora);
            titulo = (TextView) view.findViewById(R.id.titulo);
            descrip = (TextView) view.findViewById(R.id.descrip);
            img = view.findViewById(R.id.imageView3);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}

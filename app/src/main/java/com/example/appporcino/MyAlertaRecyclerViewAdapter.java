package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.AlertaFragment.OnListFragmentInteractionListener;
import com.example.appporcino.Modelo.Recordatorio;
import com.example.appporcino.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlertaRecyclerViewAdapter extends RecyclerView.Adapter<MyAlertaRecyclerViewAdapter.ViewHolder> {

    private final List<Recordatorio> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAlertaRecyclerViewAdapter(List<Recordatorio> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String des = holder.mItem.getDescripcion();
        String title = holder.mItem.getTitulo();
        if(des.length()>25){
            des = des.substring(0,25);
            des += "...";
        }
        if(title.length()>16){
            title = title.substring(0,16);
            title += "...";
        }
        holder.descripcion.setText(des);
        holder.hora.setText(mValues.get(position).getHora());
        holder.fecha.setText(mValues.get(position).getFecha());
        holder.titulo.setText(title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView titulo;
        public final TextView descripcion;
        public final TextView hora;
        public final TextView fecha;
        public Recordatorio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = (TextView) view.findViewById(R.id.titulo);
            hora = (TextView) view.findViewById(R.id.hora);
            fecha = (TextView) view.findViewById(R.id.fecha);
            descripcion = (TextView) view.findViewById(R.id.descrip);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}

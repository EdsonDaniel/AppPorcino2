package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.GastoFragment.OnListFragmentInteractionListener;
import com.example.appporcino.Modelo.Gasto;
import com.example.appporcino.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGastoRecyclerViewAdapter extends RecyclerView.Adapter<MyGastoRecyclerViewAdapter.ViewHolder> {

    private final List<Gasto> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGastoRecyclerViewAdapter(List<Gasto> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_gasto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.concepto.setText(mValues.get(position).getConcepto());
        holder.fecha.setText(mValues.get(position).getFecha_pago());
        holder.monto.setText("$"+mValues.get(position).getMonto());
        String concepto = ""+holder.concepto.getText();
        switch (concepto){
            case "Visita del veterinario":
                holder.foto.setImageResource(R.drawable.veterinario);
                break;
            case "Pago de alimento":
                holder.foto.setImageResource(R.drawable.trigo);
                break;
            case "Pago de agua potable":
                holder.foto.setImageResource(R.drawable.grifo);
                break;
            case "Pago de la luz":
                holder.foto.setImageResource(R.drawable.enchufe);
                break;
            case "Pago por inseminación":
                holder.foto.setImageResource(R.drawable.inseminacion);
                break;
                default:
                    holder.foto.setImageResource(R.drawable.gasto);
                    if(concepto.length()>27){
                        concepto = concepto.substring(0,27);
                        concepto += "...";
                        holder.concepto.setText(""+concepto);
                    }
                break;
        }
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
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteractionLong(holder.mItem, v);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView concepto;
        public final TextView fecha;
        public final TextView monto;
        public final ImageView foto;
        public Gasto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            concepto = (TextView) view.findViewById(R.id.concepto_label);
            fecha = (TextView) view.findViewById(R.id.fecha_pago_label);
            monto = (TextView) view.findViewById(R.id.monto_label);
            foto = view.findViewById(R.id.imageViewGasto);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + concepto.getText() + "'";
        }
    }
}

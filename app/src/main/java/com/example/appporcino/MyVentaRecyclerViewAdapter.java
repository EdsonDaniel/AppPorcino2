package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.Modelo.Venta;
import com.example.appporcino.VentaFragment.OnListFragmentInteractionListener;
import com.example.appporcino.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyVentaRecyclerViewAdapter extends RecyclerView.Adapter<MyVentaRecyclerViewAdapter.ViewHolder> {

    private final List<Venta> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyVentaRecyclerViewAdapter(List<Venta> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_venta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.fecha.setText(mValues.get(position).getFecha_venta());
        holder.cliente.setText(mValues.get(position).getCliente());
        holder.monto.setText(""+mValues.get(position).getMonto());
        if(mValues.get(position).getEjemplares()==1){
            holder.ejemplares.setText(""+mValues.get(position).getEjemplares()+" ejemplar vendido");
        }else
        holder.ejemplares.setText(""+mValues.get(position).getEjemplares()+"ejemplares vendidos");

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
        public final TextView fecha;
        public final TextView monto;
        public final TextView ejemplares;
        public final TextView cliente;
        public Venta mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fecha = (TextView) view.findViewById(R.id.titulo);
            cliente = (TextView) view.findViewById(R.id.cliente);
            monto = (TextView) view.findViewById(R.id.monto_total);
            ejemplares = (TextView) view.findViewById(R.id.ejemplares);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + fecha.getText() + "'";
        }
    }
}

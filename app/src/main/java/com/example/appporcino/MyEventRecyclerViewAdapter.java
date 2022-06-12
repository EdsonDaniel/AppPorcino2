package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.EventFragment.OnListFragmentInteractionListener;
import com.example.appporcino.Modelo.Evento;
import com.example.appporcino.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyEventRecyclerViewAdapter(List<Evento> items, OnListFragmentInteractionListener listener) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String tip = holder.mItem.getTipo();
        String h1 = mValues.get(position).getDescripcion();
        String h2 = mValues.get(position).getTitulo();
        if(h2.length()>17){
            h2 = h2.substring(0,17);
            h2 += "...";
        }
        if(h1.length()>25){
            h1 = h1.substring(0,25);
            h1 += "...";
        }
        holder.titulo.setText(h2);
        holder.fecha1.setText(mValues.get(position).getFecha_inicio());
        holder.fecha2.setText(tip);
        holder.descrip.setText(h1);

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
        public final TextView fecha1;
        public final TextView fecha2;
        public final TextView titulo;
        public final TextView descrip;
        public Evento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fecha1 = (TextView) view.findViewById(R.id.fecha);
            fecha2 = (TextView) view.findViewById(R.id.hora);
            titulo = (TextView) view.findViewById(R.id.titulo);
            descrip = (TextView) view.findViewById(R.id.descrip);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}

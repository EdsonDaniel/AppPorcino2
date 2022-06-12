package com.example.appporcino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.EjemplarFragment.OnListFragmentInteractionListener;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEjemplarRecyclerViewAdapter extends RecyclerView.Adapter<MyEjemplarRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private final List<Porcino> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyEjemplarRecyclerViewAdapter(List<Porcino> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        //porcinos = new ArrayList<Porcino>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titulo.setText(mValues.get(position).getNombre());
        holder.proposito.setText(mValues.get(position).getTipo());
        holder.edad.setText(mValues.get(position).getEdad());
        holder.estado.setText(mValues.get(position).getEstado());

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
        public final TextView titulo;
        public final TextView proposito;
        public final TextView edad;
        public final TextView estado;
        public Porcino mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = (TextView) view.findViewById(R.id.titulo);
            edad = (TextView) view.findViewById(R.id.edad);
            estado = (TextView) view.findViewById(R.id.estado);
            proposito = (TextView) view.findViewById(R.id.propo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}

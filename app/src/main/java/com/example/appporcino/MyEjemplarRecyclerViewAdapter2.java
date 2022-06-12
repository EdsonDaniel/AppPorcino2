package com.example.appporcino;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appporcino.EjemplarFragment2.OnListFragmentInteractionListener;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.dummy.DummyContent.DummyItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEjemplarRecyclerViewAdapter2 extends RecyclerView.Adapter<MyEjemplarRecyclerViewAdapter2.ViewHolder>{

    //private final List<DummyItem> mValues;
    private final List<Porcino> mValues;
    private final TextInputEditText total;
    private final ArrayList precio;
    private final OnListFragmentInteractionListener mListener;
    private double m_total;

    public MyEjemplarRecyclerViewAdapter2(List<Porcino> items, OnListFragmentInteractionListener listener,TextInputEditText total) {
        mValues = items;
        mListener = listener;
        precio = new ArrayList<>();
        this.total = total;
        llenar_precio();
        m_total = 0;
    }
    private void llenar_precio(){
        for(int i = 0; i<mValues.size();i++){
            precio.add(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view2, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titulo.setText(mValues.get(position).getNombre());
        holder.proposito.setText(mValues.get(position).getTipo());
        holder.edad.setText(mValues.get(position).getEdad());
        holder.monto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s) {
                String pre;
                pre = s.toString();
                double prr = 0;
                precio.set(position, pre);
                sumaTodo();
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentInteractionDelete(v,holder);
            }
        });
    }
    public void addItems(Porcino p, int index){
        mValues.add(p);
        precio.add(0);
        notifyItemInserted(index);
    }

    void deleteItem(int index) {
        mValues.remove(index);
        precio.remove(index);
        sumaTodo();
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public List<Porcino> getItems(){
        return mValues;
    }
    public ArrayList getPrecios(){
        return precio;
    }
    private void sumaTodo(){
        m_total = 0;
        for(int i = 0; i<precio.size();i++){
            m_total+=Double.parseDouble((String)precio.get(i));
        }
        total.setText(""+m_total);
    }
    public double getTotal(){
        return m_total;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView titulo;
        public final TextView proposito;
        public final TextView edad;
        public final TextInputEditText monto;
        public final ImageButton close;
        public Porcino mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = (TextView) view.findViewById(R.id.titulo);
            edad = (TextView) view.findViewById(R.id.edad);
            proposito = (TextView) view.findViewById(R.id.propo);
            monto = view.findViewById(R.id.monto);
            close = view.findViewById(R.id.cancel);
        }


        void deleteItem(int index) {
            mValues.remove(index);
            precio.remove(index);
            sumaTodo();
            notifyItemRemoved(index);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }

    }
}

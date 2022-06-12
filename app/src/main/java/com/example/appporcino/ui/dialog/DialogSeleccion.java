package com.example.appporcino.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.appporcino.R;

import java.util.ArrayList;

public class DialogSeleccion extends DialogFragment {
    private ArrayList selectedItems;
    private CharSequence[] items;
    private CharSequence[] items_amostrar;
    private boolean items_selected[];

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    NoticeDialogListener listener;

    public DialogSeleccion(CharSequence[] items, int item ){
        this.items = items;
        if(items.length>0){
            items_selected = new boolean[items.length];
            for(int i = 0; i<items_selected.length; i++){
                items_selected[i] = false;
            }
        }
        else{
            items_selected = null;
            items = new CharSequence[1];
            items[0] = "No tienes ejemplares para vender";
        }
        if(item>-1){
            items_selected[item]=true;
        }

        //items_amostrar = items.clone();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(selectedItems==null){
            selectedItems = new ArrayList();  // Where we track the selected items
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.action_vender)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(items, items_selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(which);
                                    items_selected[which] = true;
                                } else if (selectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(Integer.valueOf(which));
                                    items_selected[which] = false;
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(DialogSeleccion.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
    public boolean[] getSeleccionados(){
        return items_selected;
    }
    public void setItems(boolean[] it){
        items_selected = it;
        /*items_amostrar = new CharSequence[it.length];
        for(int i = 0; i<it.length; i++){

        }*/
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement NoticeDialogListener");
        }
    }
}
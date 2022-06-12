package com.example.appporcino.ui.ventas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VentasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VentasViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is about fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
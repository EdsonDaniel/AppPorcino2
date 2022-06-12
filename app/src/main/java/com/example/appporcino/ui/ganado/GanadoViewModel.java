package com.example.appporcino.ui.ganado;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GanadoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GanadoViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}
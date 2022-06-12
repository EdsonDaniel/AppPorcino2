package com.example.appporcino.ui.produccion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProduccionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProduccionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is produccion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.appporcino.ui.alertas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AlertasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is alertas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
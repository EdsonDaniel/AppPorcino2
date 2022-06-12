package com.example.appporcino.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeGanadoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeGanadoViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("*");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
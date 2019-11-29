package com.example.appedu.ui.Subir_Notas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Seleccione el Archivo");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
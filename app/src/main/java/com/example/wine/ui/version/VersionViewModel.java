package com.example.wine.ui.version;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VersionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VersionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Version fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
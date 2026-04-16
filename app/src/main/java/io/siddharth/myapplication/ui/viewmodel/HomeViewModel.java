package io.siddharth.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> _searchQuery = new MutableLiveData<>("");
    public LiveData<String> searchQuery = _searchQuery;

    public void setSearchQuery(String query) {
        _searchQuery.setValue(query);
    }
}
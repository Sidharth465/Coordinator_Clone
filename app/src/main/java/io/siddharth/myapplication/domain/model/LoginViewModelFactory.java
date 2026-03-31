package io.siddharth.myapplication.domain.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.siddharth.myapplication.data.repository.LoginRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final LoginRepository repository;

    public LoginViewModelFactory(LoginRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
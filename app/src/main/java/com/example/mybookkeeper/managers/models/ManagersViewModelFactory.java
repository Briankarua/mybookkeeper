package com.example.mybookkeeper.managers.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.managers.source.ManagerPagingSource;

public class ManagersViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public ManagersViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ManagersViewModel.class)) {
            //noinspection unchecked
            return (T) new ManagersViewModel(new SqliteDatabase(application.getApplicationContext()));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

package com.example.mybookkeeper.managers.models;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.managers.source.ManagerPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ManagersViewModel extends ViewModel {
    private final SqliteDatabase mDatabase;
    private final Flowable<PagingData<Manager>> allManagers;

    public ManagersViewModel(SqliteDatabase mDatabase) {
        this.mDatabase = mDatabase;
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Manager> pager = new Pager<>(
                new PagingConfig(/* pageSize = */ 20),
                () -> new ManagerPagingSource(mDatabase));
        Flowable<PagingData<Manager>> flowable = PagingRx.getFlowable(pager);
        allManagers = PagingRx.cachedIn(flowable, viewModelScope);
    }

    public Flowable<PagingData<Manager>> allManagers() {
        return allManagers;
    }

    public void create(Manager manager) {
        SqliteDatabase.ioThread(() -> mDatabase.addManagers(manager));
    }

    public void delete(int managerId) {
        SqliteDatabase.ioThread(() -> mDatabase.deleteManager(managerId));
    }

    public int totalManagers() {
        return mDatabase.totalManagers();
    }
}

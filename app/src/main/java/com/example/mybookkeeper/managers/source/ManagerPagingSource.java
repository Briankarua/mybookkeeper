package com.example.mybookkeeper.managers.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.managers.Manager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ManagerPagingSource extends RxPagingSource<Integer, Manager> {
    private final SqliteDatabase mDatabase;

    public ManagerPagingSource(SqliteDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Manager> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }
        LoadResult.Page<Integer, Manager> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Manager>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int key  = loadParams.getKey() == null ? 0 : loadParams.getKey();
        final int offset = Math.max(0, key);
        return Single.fromCallable(() -> {
            final int pageSize = loadParams.getLoadSize();
            List<Manager> managers = mDatabase.managersInPage(offset, pageSize);
            int count = mDatabase.totalManagers();

            final Integer previousKey;
            int diff;
            if (offset == 0 || ((diff = offset - pageSize) <= 0)) {
                previousKey = null;
            } else {
                previousKey = diff;
            }
            final Integer nextKey;
            int sum;
            if (offset >= count || (sum = offset + pageSize) >= count) {
                nextKey = null;
            } else {
                nextKey = sum;
            }
            return new LoadResult.Page<>(managers, previousKey, nextKey);
        });
    }
}

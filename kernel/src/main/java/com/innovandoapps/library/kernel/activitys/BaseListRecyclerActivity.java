package com.innovandoapps.library.kernel.activitys;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseListRecyclerActivity extends BaseActivity {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected abstract int getIdListRecicler();
    protected abstract RecyclerView.Adapter getMyAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(getIdListRecicler());
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(getMyAdapter());
    }

    protected void updateList(){
        mRecyclerView.setAdapter(getMyAdapter());
    }
}

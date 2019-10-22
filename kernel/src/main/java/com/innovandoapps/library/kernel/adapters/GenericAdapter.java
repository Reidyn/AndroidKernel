package com.innovandoapps.library.kernel.adapters;

import android.widget.BaseAdapter;
import java.util.List;

public abstract class GenericAdapter<T> extends BaseAdapter{


    @Override
    public int getCount() {
        return getListObjet().size();
    }

    @Override
    public Object getItem(int position) {
        return getListObjet().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract List<T> getListObjet();
}

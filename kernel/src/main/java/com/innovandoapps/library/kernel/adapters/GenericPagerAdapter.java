package com.innovandoapps.library.kernel.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class GenericPagerAdapter extends FragmentStatePagerAdapter {


    public GenericPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return getStringTitles().length;
    }

    public abstract String[] getStringTitles();

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getStringTitles()[position];
    }
}

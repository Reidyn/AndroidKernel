package com.innovandoapps.library.kernel.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public abstract class GenericPagerAdapter extends FragmentStatePagerAdapter {

    public GenericPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
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

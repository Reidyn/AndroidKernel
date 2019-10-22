package com.innovandoapps.library.kernel.fragments;

import java.util.List;

public abstract class BaseListFragments<T> extends BaseFragment{

    public abstract List<T> getListItems();
}

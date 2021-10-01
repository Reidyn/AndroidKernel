package com.innovandoapps.library;

import com.innovandoapps.library.kernel.activitys.BaseMapsActivity;

public class MapaExampleActivity extends BaseMapsActivity {

    @Override
    protected int getIdMaps() {
        return R.id.map;
    }

    @Override
    public void initController() {

    }

    @Override
    public int getLayoutResources() {
        return R.layout.activity_mapa_example;
    }
}

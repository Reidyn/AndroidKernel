package com.innovandoapps.library.kernel.activitys;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

public abstract class ToolbarSearchActivity extends BaseToolbarListActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuId(), menu);
        MenuItem mSearch = menu.findItem(getIdActionSearch());
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getQueryHint());
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                QueryTextSubmit(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                QueryTextChange(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    protected abstract int getMenuId();

    protected abstract int getIdActionSearch();

    protected abstract String getQueryHint();

    protected void QueryTextSubmit(String query){

    }

    protected void QueryTextChange(String newText){

    }
}

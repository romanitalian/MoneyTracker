package net.romanitalian.moneytrackerapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.fragments.CategoriesFragment;
import net.romanitalian.moneytrackerapp.fragments.StatisticsFragment;
import net.romanitalian.moneytrackerapp.fragments.TransactionsFragment;


public class MainActivity extends ActionBarActivity {
    private String dateFormat = "dd-MM-yyyy";
    private Toolbar toolbarMenu;
    private DrawerLayout drawerWidget;
    private ListView leftMenu;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenus();
        setTitle(getString(R.string.transactions_title));
        setFragmentParams(new TransactionsFragment());
    }

    public void setFragmentParams(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("dateFormat", dateFormat);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.drawer_layout_frame_id, fragment).commit();
    }

    public void initMenus() {
        toolbarMenu = (Toolbar) findViewById(R.id.toolbar_id);
        if (toolbarMenu != null) {
            setSupportActionBar(toolbarMenu);
        }

        drawerWidget = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        leftMenu = (ListView) findViewById(R.id.drawer_list_left_id);

        String[] menuLabels = new String[]
                {
                        getString(R.string.transactions_title),
                        getString(R.string.transactions_categories_title),
                        getString(R.string.transactions_statistics_title)
                };
        ArrayAdapter<String> menu_labels_adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, menuLabels);
        leftMenu.setAdapter(menu_labels_adapter);

        // set onClickListener on left menu
        leftMenu.setOnItemClickListener(new DrawerItemClickListener());

        // set toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerWidget, toolbarMenu, R.string.app_name, R.string.app_name);
        drawerWidget.setDrawerListener(drawerToggle);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // set icon widget for toggle
        drawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            leftMenu.setItemChecked(position, true);
            drawerWidget.closeDrawer(leftMenu);
            if (position == 0) {
                setTitle(getString(R.string.transactions_title));
                setFragmentParams(new TransactionsFragment());
            } else if (position == 1) {
                setTitle(getString(R.string.transactions_categories_title));
                setFragmentParams(new CategoriesFragment());
            } else if (position == 2) {
                setTitle(getString(R.string.transactions_statistics_title));
                setFragmentParams(new StatisticsFragment());
            }
        }
    }
}



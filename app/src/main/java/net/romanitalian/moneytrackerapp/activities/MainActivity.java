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
    private DrawerLayout drawer_widget;
    private ListView left_menu;
    private ActionBarDrawerToggle drawer_toggle;

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

        drawer_widget = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        left_menu = (ListView) findViewById(R.id.drawer_list_left_id);

        String[] menuLabels = new String[]
                {
                        getString(R.string.transactions_title),
                        getString(R.string.transactions_categories_title),
                        getString(R.string.transactions_statistics_title)
                };
        ArrayAdapter<String> menu_labels_adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, menuLabels);
        left_menu.setAdapter(menu_labels_adapter);

        // set onClickListener on left menu
        left_menu.setOnItemClickListener(new DrawerItemClickListener());

        // set toggle
        drawer_toggle = new ActionBarDrawerToggle(this, drawer_widget, toolbarMenu, R.string.app_name, R.string.app_name);
        drawer_widget.setDrawerListener(drawer_toggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // set icon widget for toggle
        drawer_toggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            left_menu.setItemChecked(position, true);
            drawer_widget.closeDrawer(left_menu);
            switch (position) {
                case 0:
                    setTitle(getString(R.string.transactions_title));
                    setFragmentParams(new TransactionsFragment());
                    break;
                case 1:
                    setTitle(getString(R.string.transactions_categories_title));
                    setFragmentParams(new CategoriesFragment());
                    break;
                case 2:
                    setTitle(getString(R.string.transactions_statistics_title));
                    setFragmentParams(new StatisticsFragment());
                    break;
            }
        }
    }
}



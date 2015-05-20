package net.romanitalian.moneytrackerapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.fragments.CategoriesFragment;
import net.romanitalian.moneytrackerapp.fragments.StatisticsFragment;
import net.romanitalian.moneytrackerapp.fragments.TransactionsFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
    @SuppressWarnings("FieldCanBeLocal")
    private String dateFormat = "dd-MM-yyyy";
    private ActionBarDrawerToggle drawerToggle;
    @ViewById
    private Toolbar toolbar;
    @ViewById
    private DrawerLayout drawerWidget;
    @ViewById
    private ListView drawerList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.transactions_title),
                        new PrimaryDrawerItem().withName(R.string.categories_title),
                        new PrimaryDrawerItem().withName(R.string.statistics_title)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        setFragment(position);
                        view.setBackgroundResource(R.color.green);
                    }
                })
                .build();
        setFragment(0);

        drawerWidget = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_list);
        // set toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerWidget, toolbar, R.string.app_name, R.string.app_name);
        drawerWidget.setDrawerListener(drawerToggle);
    }

    public void setFragmentParams(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("dateFormat", dateFormat);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.drawer_layout_frame, fragment).commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//         set icon widget for toggle
        drawerToggle.syncState();
    }

    public void setFragment(int position) {
        switch (position) {
            case 0:
                setTitle(getString(R.string.transactions_title));
                setFragmentParams(new TransactionsFragment());
                break;
            case 1:
                setTitle(getString(R.string.categories_title));
                setFragmentParams(new CategoriesFragment());
                break;
            case 2:
                setTitle(getString(R.string.statistics_title));
                setFragmentParams(new StatisticsFragment());
                break;
        }
    }
}

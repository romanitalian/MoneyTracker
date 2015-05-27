package net.romanitalian.moneytrackerapp.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.fragments.CategoriesFragment_;
import net.romanitalian.moneytrackerapp.fragments.StatisticsFragment_;
import net.romanitalian.moneytrackerapp.fragments.TransactionsFragment_;
import net.romanitalian.moneytrackerapp.rest.AuthInterceptor;
import net.romanitalian.moneytrackerapp.rest.AuthResult;
import net.romanitalian.moneytrackerapp.rest.RestClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import io.fabric.sdk.android.Fabric;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private String dateFormat = "dd-MM-yyyy";

    @ViewById
    Toolbar toolbar;

    @ViewById
    DrawerLayout drawerWidget;

    @ViewById
    ListView drawerList;

    private ActionBarDrawerToggle drawerToggle;

    @RestService
    RestClient api;

    @AfterViews
    void ready() {
        Fabric.with(this, new Crashlytics());
        setSupportActionBar(toolbar);
        Drawer.Result result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.transactions_title),
                        new PrimaryDrawerItem().withName(R.string.categories_title),
                        new PrimaryDrawerItem().withName(R.string.statistics_title),
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

        testNetwork();
    }

    @Background
    void testNetwork() {
        final AuthResult login = api.login("roman", "123456");
        AuthInterceptor.authToken = login.authToken;
    }

    public void setFragmentParams(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("dateFormat", dateFormat);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.drawer_layout_frame, fragment).commit();
    }

    public void setFragment(int position) {
        switch (position) {
            case 0:
                setTitle(getString(R.string.transactions_title));
                setFragmentParams(TransactionsFragment_.builder().build());
                break;
            case 1:
                setTitle(getString(R.string.categories_title));
                setFragmentParams(CategoriesFragment_.builder().build());
                break;
            case 2:
                setTitle(getString(R.string.statistics_title));
                setFragmentParams(StatisticsFragment_.builder().build());
                break;
            case 3:
                Intent intent = new Intent(this, LoginActivity.class);
                break;
        }
    }
}

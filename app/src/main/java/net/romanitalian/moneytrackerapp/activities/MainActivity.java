package net.romanitalian.moneytrackerapp.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.romanitalian.moneytrackerapp.MoneyTrackerApplication;
import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.auth.SessionManager;
import net.romanitalian.moneytrackerapp.fragments.CategoriesFragment_;
import net.romanitalian.moneytrackerapp.fragments.StatisticsFragment_;
import net.romanitalian.moneytrackerapp.fragments.TransactionsFragment_;
import net.romanitalian.moneytrackerapp.rest.RestClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private String dateFormat = "dd-MM-yyyy";

    @ViewById
    Toolbar toolbar;

    @ViewById
    DrawerLayout drawerWidget;

    @ViewById
    ListView drawerList;

    @RestService
    RestClient api;

    @Bean
    SessionManager sessionManager;


    @AfterViews
    void ready() {
        Toast.makeText(this, "start", Toast.LENGTH_LONG).show();
    }

//    @AfterViews
//    void ready() {
//        Fabric.with(this, new Crashlytics());
//        FlightRecorder.getInstance().setAuthentication("53dd85ad-c174-44f4-a787-beed4c26c4a7", "14f00dc2-2aac-4148-8298-3156460f93df");
//        FlightRecorder.getInstance().startFlight();
//        setFragment(0);
//    }

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

    @Receiver(actions = {SessionManager.SESSION_OPENED_BROADCAST}, registerAt = Receiver.RegisterAt.OnResumeOnPause, local = true)
    void onSessionOpen() {
        testNetwork();
    }

    @Background
    public void testNetwork() {
//        final AuthResult login = api.login("roman2", "123456");
//        sessionManager.createAccount("roman2", login.authToken);
//        AuthInterceptor.authToken = login.authToken;
//        api.addCategory("category_02");
//        final Result result = api.addTransaction(100, "test", 1, "2015-06-01");
//        final TransactionsResult transactionsResult = api.getTransactions();
    }

    @Override
    public void onResume() {
        super.onResume();
//        sessionManager.login(this);
        setMenu();
    }

    public void setMenu() {
        setSupportActionBar(toolbar);
        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.transactions_title),
                        new PrimaryDrawerItem().withName(R.string.categories_title),
                        new PrimaryDrawerItem().withName(R.string.statistics_title),
                        new PrimaryDrawerItem().withName(!MoneyTrackerApplication.isAuth ? R.string.title_activity_login : R.string.title_activity_logout)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        setFragment(position);
//                        view.setBackgroundResource(R.color.green);
                    }
                })
                .build();
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
                if (MoneyTrackerApplication.isAuth) {
                    MoneyTrackerApplication.isAuth = false;
                    setMenu();
                } else {
                    Intent intent = new Intent(this, LoginActivity_.class);
                    startActivity(intent);
                }
                break;
        }
    }
}

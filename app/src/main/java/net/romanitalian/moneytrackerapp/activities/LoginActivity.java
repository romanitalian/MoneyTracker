package net.romanitalian.moneytrackerapp.activities;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.romanitalian.moneytrackerapp.MoneyTrackerApplication;
import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.auth.SessionManager;
import net.romanitalian.moneytrackerapp.rest.AuthResult;
import net.romanitalian.moneytrackerapp.rest.RestClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AccountAuthenticatorActivity {

    @RestService
    RestClient api;

    @ViewById
    TextView login, password;

    @Bean
    SessionManager sessionManager;

    @Click
    void ok() {
        login();
    }

    @Background
    void login() {
        final String loginName = this.login.getText().toString();
        final AuthResult loginResult = api.login(loginName, password.getText().toString());
        handleLoginResult(loginName, loginResult);
    }

    @UiThread
    void handleLoginResult(String loginName, AuthResult loginResult) {
        if (loginResult.isSuccessfull()) {
            sessionManager.createAccount(loginName, loginResult.authToken);
            MoneyTrackerApplication.isAuth = sessionManager.getAuthToken() != null;
            setAccountAuthenticatorResult(new Bundle());
            Toast.makeText(this, R.string.auth_success, Toast.LENGTH_LONG).show();
            finish();
        } else
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
    }
}

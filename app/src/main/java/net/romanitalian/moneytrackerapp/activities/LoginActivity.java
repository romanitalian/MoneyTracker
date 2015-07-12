package net.romanitalian.moneytrackerapp.activities;

import android.accounts.AccountAuthenticatorActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.romanitalian.moneytrackerapp.MoneyTrackerApplication;
import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.auth.SessionManager;
import net.romanitalian.moneytrackerapp.rest.AuthResult;
import net.romanitalian.moneytrackerapp.rest.RegisterResult;
import net.romanitalian.moneytrackerapp.rest.RestClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
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

    @ViewById
    Button ok;

    @ViewById
    Button register;

    @Click
    void ok() {
        login();
    }

    @Click
    void register() {
        doRegister();
    }

    @TextChange({R.id.login, R.id.password})
    void loginTextChanged(CharSequence text) {
        ok.setEnabled(!TextUtils.isEmpty(text));
        register.setEnabled(!TextUtils.isEmpty(text));
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
            setAccountAuthenticatorResult(new Bundle());
            MoneyTrackerApplication.isAuth = loginResult.authToken != null;
            Toast.makeText(this, R.string.success_auth, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_login, Toast.LENGTH_LONG).show();
        }
    }

    @Background
    void doRegister() {
        final RegisterResult registerResult = api.register(login.getText().toString(), password.getText().toString());
        handleRegisterResult(registerResult);
    }

    @UiThread
    void handleRegisterResult(RegisterResult registerResult) {
        boolean isSuccessfull = registerResult.isSuccessfull();
        MoneyTrackerApplication.isAuth = isSuccessfull;
        if (isSuccessfull) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.congratulations_register);
            builder.setCancelable(true);

            builder.setNegativeButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            SystemClock.sleep(3000);
            finish();
        } else {
            Toast.makeText(this, R.string.error_register, Toast.LENGTH_LONG).show();
        }
    }
}

package net.romanitalian.moneytrackerapp.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;


@EBean(scope = EBean.Scope.Singleton)
public class SessionManager {
    private static final String LOG_TAG = SessionManager.class.getSimpleName();

    public static final String AUTH_ACCOUNT_TYPE = "net.romanitalian.moneytrackerapp";
    private static final String AUTH_TOKEN_TYPE_FULL_ACCESS = AUTH_ACCOUNT_TYPE + ".authtokenFull";
    public static final String SESSION_OPENED_BROADCAST = "session-open";

    @SystemService
    AccountManager accountManager;

    @RootContext
    Context context;

    private String authToken;
    private boolean isSynced;

    public void createAccount(String login, String authToken) {
        Account account = new Account(login, AUTH_ACCOUNT_TYPE);
        if (accountManager.addAccountExplicitly(account, null, null)) {
            accountManager.setAuthToken(account, AUTH_TOKEN_TYPE_FULL_ACCESS, authToken);
        }
    }

    @Background
    public void login(Activity activity) {
        if (restoreAccount()) {
            return;
        }

        try {
            AccountManagerFuture<Bundle> future = accountManager.addAccount(AUTH_ACCOUNT_TYPE, AUTH_TOKEN_TYPE_FULL_ACCESS, null, null, activity, null, null);
            future.getResult();
        } catch (OperationCanceledException | AuthenticatorException | IOException e) {
            e.printStackTrace();
        }
    }

    boolean restoreAccount() {
        Account[] availableAccounts = accountManager.getAccountsByType(AUTH_ACCOUNT_TYPE);

        if (availableAccounts.length == 0)
            return false;

        AccountManagerFuture<Bundle> future = accountManager.getAuthToken(availableAccounts[0], AUTH_TOKEN_TYPE_FULL_ACCESS, null, false, null, null);
        try {
            ContentResolver.setSyncAutomatically(availableAccounts[0], AUTH_ACCOUNT_TYPE, true);
            onSessionOpen(future.getResult());
            return true;
        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
            e.printStackTrace();
        }
        return false;
    }

    @UiThread
    void onSessionOpen(Bundle result) {
        final String token = result.getString(AccountManager.KEY_AUTHTOKEN);
        authToken = token;
        Log.d(LOG_TAG, "restoreAccount authToken:" + token);
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(SESSION_OPENED_BROADCAST));
        if (!isSynced) {
            sync();
        }
    }

    public String getAuthToken() {
        return authToken;
    }


    public void sync() {
        isSynced = true;

        android.accounts.Account[] availableAccounts = accountManager.getAccountsByType(AUTH_ACCOUNT_TYPE);
        if (availableAccounts.length == 0) {
            Log.d(LOG_TAG, "sync(), Account not found");
            return;
        }

        ContentResolver.requestSync(availableAccounts[0], AUTH_ACCOUNT_TYPE, new Bundle());
    }
}

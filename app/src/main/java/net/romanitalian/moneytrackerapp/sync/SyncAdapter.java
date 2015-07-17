package net.romanitalian.moneytrackerapp.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.rest.RestClient;
import net.romanitalian.moneytrackerapp.rest.RestClient_;
import net.romanitalian.moneytrackerapp.utils.Udate;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String SYNCED = "synced";

//    @RestService
//    RestClient api;

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            final RestClient api = new RestClient_(getContext());
            final List<Transaction> unsyncedTransactions = Transaction.getUnsynced();
            for (Transaction transaction : unsyncedTransactions) {
                api.addTransaction(transaction.sum, transaction.comment, 1, Udate.getDateNowToString());
                transaction.markSynced();
            }
            final Transaction[] serverTransactions = api.getTransactions().data;
            for (Transaction transaction : serverTransactions) {
                if (!transaction.isInDatabase()) {
                    transaction.save();
                }
            }
            final List<Transaction> syncedTransactions = Transaction.getSynced();
            for (Transaction transaction : syncedTransactions) {
                transaction.delete();
            }
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(SYNCED));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

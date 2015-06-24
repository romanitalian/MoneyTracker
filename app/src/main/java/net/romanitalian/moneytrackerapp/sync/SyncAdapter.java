package net.romanitalian.moneytrackerapp.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.rest.RestClient;
import net.romanitalian.moneytrackerapp.rest.RestClient_;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            final RestClient api = new RestClient_(getContext());
            for (Transaction transaction : Transaction.getUnsynced()) {
                api.addTransaction(transaction.sum, transaction.comment, 1, transaction.tr_date.toString());
                transaction.markSynced();
            }
            for (Transaction transaction : api.getTransactions().data) {
                if (!transaction.isInDatabase()) {
                    transaction.save();
                }
            }
            for (Transaction transaction : Transaction.getSynced()) {
                transaction.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

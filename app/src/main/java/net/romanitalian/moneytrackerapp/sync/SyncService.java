package net.romanitalian.moneytrackerapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new SyncAdapter(getApplicationContext()).getSyncAdapterBinder();
    }
}

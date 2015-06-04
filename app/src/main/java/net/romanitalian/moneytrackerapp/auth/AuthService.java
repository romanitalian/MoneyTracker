/**
 * Copyright (c) 2015 Bookmate.
 * All Rights Reserved.
 * <p/>
 * Author: Dmitry Gordeev <netimen@dreamindustries.co>
 * Date:   29.05.15
 */
package net.romanitalian.moneytrackerapp.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new Authenticator(this).getIBinder();
    }
}

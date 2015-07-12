package net.romanitalian.moneytrackerapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;

import net.romanitalian.moneytrackerapp.R;

public class Uerror {
    public static void showAlert(ActionBarActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.not_all_fields_are_filled);
        builder.setCancelable(true);

        builder.setNegativeButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

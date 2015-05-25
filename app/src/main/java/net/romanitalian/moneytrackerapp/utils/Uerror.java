package net.romanitalian.moneytrackerapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;

public class Uerror {
    public static void showAlert(ActionBarActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Error: any field(s) is(are) empty");
        builder.setCancelable(true);

        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

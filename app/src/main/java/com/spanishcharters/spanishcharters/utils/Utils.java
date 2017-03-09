package com.spanishcharters.spanishcharters.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.spanishcharters.spanishcharters.R;

import java.util.Locale;

public class Utils {
    public static enum MessageType {

        DIALOG,
        TOAST,
        SNACK,
        NONE
    }


    // Shows the user a message of the given type (toast, snackbar, dialog or none)
    // If the type is dialog, a title must be provided.
    public static void showMessage(Context ctx, String msg, MessageType type, String title) {

        if ( type== MessageType.NONE ) {
            return;
        }

        else if ( type== MessageType.TOAST ) {
            Toast.makeText(ctx,msg, Toast.LENGTH_LONG).show();
        }

        else if ( type== MessageType.SNACK ) {
            View rootView = ( (Activity)ctx ).getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(rootView, msg, Snackbar. LENGTH_LONG).show();
        }

        else if ( type== MessageType.DIALOG ) {

            AlertDialog dialog = new AlertDialog.Builder(ctx).create();
            dialog.setTitle(title);
            dialog.setMessage(msg);

            // OK buton
            dialog.setButton(
                    ctx.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );

            dialog.show();
        }
    }


    // Shows the user a dialog with Cancel/Accept buttons, and the corresponding closures for both buttons
    public static void showCancelAcceptDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener acceptListener) {

        if (acceptListener == null)
            acceptListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            };

        if (cancelListener == null)
            cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            };

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(ctx.getResources().getString(android.R.string.ok), acceptListener);
        builder.setNegativeButton(ctx.getResources().getString(android.R.string.cancel), cancelListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Shows the user a dialog with Accept button only, and the corresponding closure
    public static void showAcceptDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener acceptListener) {

        if (acceptListener == null)
            acceptListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            };

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(ctx.getResources().getString(android.R.string.ok), acceptListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Returns a new indeterminate, non-cancelable progress dialog with a given message and title
    public static ProgressDialog newProgressDialog(Context ctx, String msg) {

        final ProgressDialog pDialog = new ProgressDialog(ctx);
        pDialog.setTitle( ctx.getString(R.string.defaultProgressTitle) );
        pDialog.setMessage(msg);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        return pDialog;
    }

    // Determines the language code ("en", "es", etc.) for the current system language
    public static String systemLanguage()
    {
        return Locale.getDefault().getLanguage();
    }
}

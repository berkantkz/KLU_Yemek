package io.github.berkantkz.klu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by berkantkz on 23.08.2018.
 * # KLU_Yemek
 */

public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        startLoading();

    }

    public void startLoading() {
        if (isNetworkAvailable()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            noNetwork();
        }
    }



    public void noNetwork() {
        new AlertDialog.Builder(StartActivity.this, R.style.DialogTheme)
                .setTitle("Bağlantı yok")
                .setMessage("Kullanılabilir bir bağlantı mevcut değil")
                .setPositiveButton("TEKRAR DENE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startLoading();
                    }
                })
                .setNegativeButton("ÇIKIŞ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.cybus.utilshelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;


public class UtilsHelper {
    private static final String TAG = UtilsHelper.class.getName();

    public static void showToastMsg(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {

        }
    }

    public static void showSnackbar(View view, String msg) {
        try {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } catch (Exception ignored) {

        }
    }

    public static Boolean isValidURL(String URL) {
        return Patterns.WEB_URL.matcher(URL).matches();
    }


}

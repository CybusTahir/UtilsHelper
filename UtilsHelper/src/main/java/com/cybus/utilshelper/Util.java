package com.cybus.utilshelper;

import android.app.Activity;
import android.app.Dialog;
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


public class Util {
    private static final String TAG = Util.class.getName();

    public static Util instance;
    private Context context;

    private Util(Context context) {
        this.context = context;
    }

    public static Util getInstance(Context context) {
        if (instance == null) {
            instance = new Util(context);
        }

        return instance;
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.findViewById(android.R.id.content).getWindowToken(), 0);
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    public static void showToastMsg(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {

        }
    }

    public void showSnackbar(View view, String msg) {
        try {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } catch (Exception ignored) {

        }
    }

    public Boolean isValidURL(String URL) {
        return Patterns.WEB_URL.matcher(URL).matches();
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidInput(String input) {
        try {
            double inputValue = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException ignore) {
            System.out.println("Invalid input");
            return false;
        }
    }


    public boolean isValidMobile(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }

    public void playRingTone() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context, notification);
            if (!ringtone.isPlaying()) {
                ringtone.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isValidString(String value) {
        return !(value == null || value.isEmpty());
    }

    public void launchURL(String url) {
        if (!url.equals("") && URLUtil.isValidUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }

    public void launchShareIntent(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void printHashKey() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.d("DEBUG_KEY", " Hash Key: " + hashKey);
            }
        } catch (Exception e) {
            Log.e("DEBUG_KEY", "printHashKey()", e);
        }
    }

}

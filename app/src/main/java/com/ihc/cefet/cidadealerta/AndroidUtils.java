package com.ihc.cefet.cidadealerta;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class AndroidUtils {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static void getApplicationSignature(Context context) {
        try {
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //=================================================================================================================

    public static void hideKeyboard(Context context, View view) {
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //=================================================================================================================

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //=================================================================================================================

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //=================================================================================================================

    public static String getTextET(EditText target) {
        return target.getText().toString();
    }

    //=================================================================================================================

    public static String getTextTX(TextView target) {
        return target.getText().toString();
    }

    //=================================================================================================================

    public static boolean validEditText(EditText target) {
        return !TextUtils.isEmpty(getTextET(target));
    }

    //=================================================================================================================
    //=================================================================================================================

    public static void alertDialog(Context context, int title, int message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.colorAccent))
                .content(message)
                .positiveText(android.R.string.ok)
                .typeface("Montserrat-Regular.ttf", "Montserrat-Light.ttf")
                .show();
    }

    public static void alertDialog(Context context, String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.colorAccent))
                .content(message)
                .positiveText(android.R.string.ok)
                .typeface("Montserrat-Regular.ttf", "Montserrat-Light.ttf")
                .show();
    }

    //=================================================================================================================
    //=================================================================================================================

    public static void changeStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, color));
        }
    }

    public static void setDrawableRight(Context context, EditText textView, int drawableRight){
        Drawable img = context.getResources().getDrawable( drawableRight );
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
    }

    //=================================================================================================================
    //=================================================================================================================

    public static String getCleanText(String text) {
        text = text.replaceAll("[^0-9]*", "");
        return text;
    }

    public static void showSnackBarOk(final Context context, View view, int title, View.OnClickListener cb) {
        try {
            Snackbar snackBar = Snackbar.make(view, title, Snackbar.LENGTH_LONG);
            snackBar.setAction(context.getText(R.string.ok), cb);
            snackBar.setActionTextColor(Color.WHITE);
            snackBar.show();
        } catch(Exception e){
            Log.i("AndroidUtils", "Erro no Snackbar");
        }
    }

    public static String getCertificateSHA1Fingerprint(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    public static void changeSwipeVisibility(final SwipeRefreshLayout swipe, final boolean enabled){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                swipe.setRefreshing(enabled);
            }

        }, 100);
    }

    public static void changeToTheme(Activity activity) {
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D+","");
        if ((cpf == null) || (cpf.length() != 11)) return false;

        if (cpf.equalsIgnoreCase("11111111111") || cpf.equalsIgnoreCase("22222222222") || cpf.equalsIgnoreCase("33333333333")
                || cpf.equalsIgnoreCase("44444444444") || cpf.equalsIgnoreCase("55555555555") || cpf.equalsIgnoreCase("66666666666")
                || cpf.equalsIgnoreCase("77777777777") || cpf.equalsIgnoreCase("88888888888") || cpf.equalsIgnoreCase("99999999999") || cpf.equalsIgnoreCase("00000000000"))
            return false;

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }


//    public static void onActivityCreateSetTheme(Activity activity) {
//        if(CiscoApp.isLogged()) {
//            activity.setTheme(R.style.MainActivityTheme);
//        } else {
//            activity.setTheme(R.style.MainActivityTheme_NotLogged);
//        }
//    }

}

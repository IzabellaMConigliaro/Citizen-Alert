package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

/**
 * Created by izabellamelendezconigliaro on 29/06/16.
 */
public class DialogUtils {
    public static void showSingleChoice(Context context, String title, List<String> items, int selected, final MaterialDialog.ListCallbackSingleChoice listener) {
        new MaterialDialog.Builder(context)
                .items(items)
                .title(title)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .itemsCallbackSingleChoice(selected, listener)
                .positiveText(R.string.ok)
                .show();
    }

    public static void showBasic(Context context, int title, int content, final MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.colorPrimary)
                .content(content)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .callback(callback)
                .show();
    }

    public static void showDialog(Context context, String title, String content, String confirmButton, String denyButton, final MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.mainTextColor)
                .content(content)
                .positiveText(confirmButton)
                .negativeText(denyButton)
                .onPositive(callback)
                .show();
    }

    public static void showDialog(Context context, int title, String content, int confirmButton, int denyButton, final MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.mainTextColor)
                .content(content)
                .positiveText(confirmButton)
                .negativeText(denyButton)
                .onPositive(callback)
                .show();
    }

    public static void showBasic(Context context, int content, int positive, int negative, final MaterialDialog.ButtonCallback callback) {
        showBasic(context, content, positive, negative, true, callback);
    }

    public static void showBasic(Context context, int content, int positive, int negative, boolean cancelable, final MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .callback(callback)
                .cancelable(cancelable)
                .show();
    }

    public static void showBasic(Context context, String title, String content, String positive, String negative, boolean cancelable, final MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .contentGravity(GravityEnum.CENTER)
                .titleGravity(GravityEnum.CENTER)
                .buttonsGravity(GravityEnum.CENTER)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .callback(callback)
                .cancelable(cancelable)
                .show();
    }

    public static void showBasic(Context context, int content, int positive, int negative, int neutral, boolean cancelable, final MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .neutralText(neutral)
                .callback(callback)
                .cancelable(cancelable)
                .show();
    }

    public static void showBasicOk(Context context, int title, int content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.colorPrimary)
                .content(content)
                .positiveText(R.string.ok)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                    }
                })
                .show();
    }

    public static void showBasicOk(Context context, int content, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .content(content)
                .neutralText(R.string.ok)
                .buttonsGravity(GravityEnum.CENTER)
                .contentGravity(GravityEnum.CENTER)
                .onPositive(callback)
                .show();
    }

    public static void showBasicOK(Context context, int content, final MaterialDialog.ButtonCallback callback) {
        showBasicOK(context, content, true, callback);
    }

    public static void showBasicOK(Context context, int content, boolean cancelable, final MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(R.string.ok)
                .cancelable(false)
                .callback(callback)
                .cancelable(cancelable)
                .show();
    }

    public static MaterialDialog getCustomView(Context context, int view) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(view, false)
                .build();

        return dialog;
    }
}

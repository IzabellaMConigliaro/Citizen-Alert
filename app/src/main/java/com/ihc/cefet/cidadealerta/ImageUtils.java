package com.ihc.cefet.cidadealerta;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class ImageUtils {

    private static final String TAG = "ImageUtils";
    public static final String BUNDLE_IMAGE = "CONTENT";

    public static String getImageFromFacebookUrl(String facebookId){
        return "https://graph.facebook.com/" + facebookId + "/picture?type=large";
    }

    public static Intent openGallery(Activity activity, int requestCode){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        try {
            activity.startActivityForResult(gallery, requestCode);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    }

    public static String getPathFromUri(Context context, Uri fileUri) {

        try {

            String uriString = fileUri.toString();
            if (uriString.contains("file:///")) {
                return fileUri.getPath();
            } else {
                String res = null;
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(fileUri, proj, null, null, null);
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    res = cursor.getString(column_index);
                }
                cursor.close();
                return res;
            }
        }catch (Exception e){
            return "";
        }
    }

    public static Bitmap getBitmapFromImagePath(String path) {
        File image = new File(path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
    }

    public static void checkPermissionAndStartCamera(Context context) {
        final String permission = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((BaseActivity) context, permission)) {
                showPermissionRationaleDialog(context.getString(R.string.camera_permission), permission, context);
            } else {
                requestForPermission(permission, context);
            }
        } else {
            launchCamera(context);
        }
    }

    private static void showPermissionRationaleDialog(final String message, final String permission, final Context context) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestForPermission(permission, context);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .create()
                .show();
    }

    private static void requestForPermission(final String permission, Context context) {
        ActivityCompat.requestPermissions((BaseActivity) context, new String[]{permission}, Constants.REQUEST_CAMERA_PERMISSION);
    }

    public static void launchCamera(Context context) {
        Intent startCustomCameraIntent = new Intent(context, CameraActivity.class);
        ((BaseActivity) context).startActivityForResult(startCustomCameraIntent, Constants.REQUEST_CAMERA);
    }

    public static void launchCrop(Uri uri, Context context) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setAutoZoomEnabled(false)
                .setActivityMenuIconColor(Color.GREEN)
                .setAllowRotation(true)
                .setFixAspectRatio(true)
                .setMinCropResultSize(30, 30)
                .setActivityMenuIconColor(Color.WHITE)
                .setActivityTitle(context.getString(R.string.title_crop))
                .start((BaseActivity) context);
    }


    public static void pickPhotoFromGallery(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            ((BaseActivity) context).startActivityForResult(intent, Constants.PICK_PHOTO_CODE);
        }
    }

    public static RoundedBitmapDrawable getRoundedImage(Context context, String path, Point mSize) {
        Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(path, mSize.x, mSize.x);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);

        return circularBitmapDrawable;
    }
}

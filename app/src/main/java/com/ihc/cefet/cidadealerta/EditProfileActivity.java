package com.ihc.cefet.cidadealerta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by izabellamelendezconigliaro on 26/09/16.
 */
public class EditProfileActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.banner)
    ImageView banner;
    @Bind(R.id.user_avatar)
    ImageView userAvatar;
    @Bind(R.id.name_tv)
    MaterialEditText nameTv;
    @Bind(R.id.cpf_tv)
    MaterialEditText cpfTv;
    @Bind(R.id.email_tv)
    MaterialEditText emailTv;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.camera)
    ImageView camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Editar Perfil", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        nameTv.setText(getResources().getString(R.string.user_name));
        cpfTv.setText(getResources().getString(R.string.user_cpf));
        emailTv.setText(getResources().getString(R.string.user_email));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.delete) {
            DeleteProfileDialog.show(getSupportFragmentManager(), EditProfileActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        btnLogin.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 2000);
    }

    @OnClick(R.id.camera)
    public void onClickCamera() {
        DialogUtils.showBasic(this, R.string.choose_pic_type, R.string.camera, R.string.gallery, new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                ImageUtils.checkPermissionAndStartCamera(EditProfileActivity.this);
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                ImageUtils.pickPhotoFromGallery(EditProfileActivity.this);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case Constants.REQUEST_CAMERA:
                ImageUtils.launchCrop(data.getData(), this);
                break;

            case Constants.PICK_PHOTO_CODE:
                ImageUtils.launchCrop(data.getData(), this);
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                Glide.with(this)
                        .load(result.getUri().getPath())
                        .bitmapTransform(new BlurTransformation(this))
                        .into(banner);

                Glide.with(this)
                        .load(result.getUri().getPath())
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(userAvatar);

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

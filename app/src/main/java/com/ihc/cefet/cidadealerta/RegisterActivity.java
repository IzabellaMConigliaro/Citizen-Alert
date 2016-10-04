package com.ihc.cefet.cidadealerta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by izabellamelendezconigliaro on 26/09/16.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.addPicture)
    LinearLayout addPicture;
    @Bind(R.id.name_tv)
    MaterialEditText nameTv;
    @Bind(R.id.cpf_tv)
    MaterialEditText cpfTv;
    @Bind(R.id.email_tv)
    MaterialEditText emailTv;
    @Bind(R.id.password_tv)
    MaterialEditText passwordTv;
    @Bind(R.id.terms_of_use_btn)
    TextView termsOfUseBtn;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.user_avatar)
    ImageView userAvatar;

    private boolean isPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Cadastro", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        String cpfMask = "###.###.###-##";
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener(cpfMask, cpfTv);
        cpfTv.addTextChangedListener(maskCPF);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.terms_of_use_btn, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.terms_of_use_btn:
                openActivity(TermsOfUseActivity.class);
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(nameTv.getText().toString().trim())) {
                    nameTv.setError("Insira seu nome completo");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(nameTv);
                } else if (TextUtils.isEmpty(cpfTv.getText().toString().trim())) {
                    cpfTv.setError("Insira seu CPF");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(cpfTv);
                } else if (!AndroidUtils.isValidCPF(cpfTv.getText().toString().trim())) {
                    cpfTv.setError("Insira um CPF válido");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(cpfTv);
                } else if (!AndroidUtils.isValidEmail(emailTv.getText().toString().trim())) {
                    emailTv.setError("Insira um email válido");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(emailTv);
                } else if (TextUtils.isEmpty(passwordTv.getText().toString())) {
                    passwordTv.setError("Insira sua senha");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(passwordTv);
                } else {
                    btnLogin.setVisibility(View.GONE);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            closeAllActivitiesAndOpenNew(MainActivity.class);
                            finish();
                        }
                    }, 2000);
                }
                break;
        }
    }

    @OnTouch(R.id.password_tv)
    boolean passwordTouch(View v, MotionEvent event) {

        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (passwordTv.getRight() - passwordTv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                if (AndroidUtils.validEditText(passwordTv)) {
                    if (isPassword) {
                        isPassword = false;
                        passwordTv.setTransformationMethod(null);
                        AndroidUtils.setDrawableRight(RegisterActivity.this, passwordTv, R.drawable.ic_no_eye);
                    } else {
                        isPassword = true;
                        passwordTv.setTransformationMethod(new PasswordTransformationMethod());
                        AndroidUtils.setDrawableRight(RegisterActivity.this, passwordTv, R.drawable.ic_eye);
                    }
                }
                return false;
            }
        }
        return false;
    }

    @OnClick(R.id.addPicture)
    public void onClick() {
        DialogUtils.showBasic(this, R.string.choose_pic_type, R.string.camera, R.string.gallery, new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                ImageUtils.checkPermissionAndStartCamera(RegisterActivity.this);
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                ImageUtils.pickPhotoFromGallery(RegisterActivity.this);
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

                //addPicture.setVisibility(View.GONE);

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                Glide.with(this)
                        .load(result.getUri().getPath())
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_user)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(userAvatar);

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

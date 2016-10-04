package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by izabellamelendezconigliaro on 27/09/16.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.email_tv)
    MaterialEditText emailTv;
    @Bind(R.id.password_tv)
    MaterialEditText passwordTv;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.forgot_password_btn)
    TextView forgotPasswordBtn;
    @Bind(R.id.register_btn)
    TextView registerBtn;

    private boolean isPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        if (!AndroidUtils.isValidEmail(emailTv.getText().toString().trim())) {
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

                    openActivity(MainActivity.class);
                    finish();
                }
            }, 2000);
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
                        AndroidUtils.setDrawableRight(LoginActivity.this, passwordTv, R.drawable.ic_no_eye);
                    } else {
                        isPassword = true;
                        passwordTv.setTransformationMethod(new PasswordTransformationMethod());
                        AndroidUtils.setDrawableRight(LoginActivity.this, passwordTv, R.drawable.ic_eye);
                    }
                }
                return false;
            }
        }
        return false;
    }

    @OnClick({R.id.forgot_password_btn, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgot_password_btn:
                openActivity(ForgotPasswordActivity.class);
                break;
            case R.id.register_btn:
                openActivity(RegisterActivity.class);
                break;
        }
    }
}

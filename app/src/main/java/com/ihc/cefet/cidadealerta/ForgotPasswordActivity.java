package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by izabellamelendezconigliaro on 26/09/16.
 */
public class ForgotPasswordActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.email_tv)
    MaterialEditText emailTv;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.toolbar_container)
    FrameLayout toolbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Esqueci minha senha", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
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

    @OnClick(R.id.btn_login)
    public void onClick() {
        if (!AndroidUtils.isValidEmail(emailTv.getText().toString().trim())) {
            emailTv.setError("Insira um email válido");
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(emailTv);
        } else {
            btnLogin.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    SendLinkEmailDialog.show(getSupportFragmentManager(), ForgotPasswordActivity.this);
                }
            }, 1000);
        }
    }
}

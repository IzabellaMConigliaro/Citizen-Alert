package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by izabellamelendezconigliaro on 28/09/16.
 */
public class ProfileActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.banner)
    ImageView banner;
    @Bind(R.id.user_avatar)
    ImageView userAvatar;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.cpf_tv)
    TextView cpfTv;
    @Bind(R.id.email_tv)
    TextView emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Perfil", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        nameTv.setText(Html.fromHtml(getResources().getString(R.string.name, getResources().getString(R.string.user_name))));
        cpfTv.setText(Html.fromHtml(getResources().getString(R.string.cpf, getResources().getString(R.string.user_cpf))));
        emailTv.setText(Html.fromHtml(getResources().getString(R.string.email, getResources().getString(R.string.user_email))));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.edit) {
            openActivity(EditProfileActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        btnLogin.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                closeAllActivitiesAndOpenNew(LoginActivity.class);
                finish();
            }
        }, 2000);
    }
}

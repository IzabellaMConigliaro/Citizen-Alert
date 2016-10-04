/**
 * Copyright 2014-present Amberfog
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.version)
    TextView version;
    @Bind(R.id.terms_of_use_btn)
    TextView termsOfUseBtn;
    @Bind(R.id.privacy_policies_btn)
    TextView privacyPoliciesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("About", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        version.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
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

    @OnClick({R.id.terms_of_use_btn, R.id.privacy_policies_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.terms_of_use_btn:
                openActivity(TermsOfUseActivity.class);
                break;
            case R.id.privacy_policies_btn:
                openActivity(PrivacyPolicyActivity.class);
                break;
        }
    }
}

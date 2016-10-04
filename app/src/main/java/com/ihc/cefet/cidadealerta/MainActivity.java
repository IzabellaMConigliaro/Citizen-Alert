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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Bind(R.id.fragment)
    FrameLayout fragment;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private static MenuDrawerFragment mDrawerFragment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment, MainFragment.newInstance(null));
            trans.commit();
        }

        setSupportActionBar(toolbar);
        configActionBar(R.string.cidade_alerta, true);
        toolbar.setTitle(R.string.cidade_alerta);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        swipe.setColorSchemeResources(R.color.colorPrimary);
        AndroidUtils.changeSwipeVisibility(swipe, true);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                AndroidUtils.changeSwipeVisibility(swipe, false);
            }
        }, 100);
        swipe.setEnabled(false);

        setUpNavigation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        switch (id) {
            case R.id.filter:
                FilterDialog.show(getSupportFragmentManager(), MainActivity.this);
                break;
            case R.id.refresh:
                AndroidUtils.changeSwipeVisibility(swipe, true);
                AndroidUtils.changeSwipeVisibility(swipe, true);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        AndroidUtils.changeSwipeVisibility(swipe, false);
                    }
                }, 2000);
                break;
            case R.id.notification:
                openActivityForResult(NotificationActivity.class, 2001);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigation() {
        mDrawerFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);
        mDrawerFragment.setUp(mDrawerLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CAApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
//        if (isConnected) {
//            getCurrentFragment().haveConnection();
//        } else {
//            getCurrentFragment().noConnection();
//        }
    }

    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment)).getAdapter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2001) {
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_notification));
        }
    }

}

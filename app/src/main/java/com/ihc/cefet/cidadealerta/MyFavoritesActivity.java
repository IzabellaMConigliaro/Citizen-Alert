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
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyFavoritesActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener,
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private SimpleFoldingCellListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Ocorrências Favoritas", true);
        toolbar.setTitle(R.string.cidade_alerta);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        AndroidUtils.changeSwipeVisibility(swipe, true);

        final ArrayList<Item> items = Item.getTestingList();
        for (Item i : items) {
            i.setFavorited(true);
        }
        adapter = SimpleFoldingCellListAdapter.newInstance(this, items);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(layoutManager);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //recyclerView.setAdapter(adapter);

                AndroidUtils.changeSwipeVisibility(swipe, false);
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CAApp.getInstance().setConnectivityListener(this);
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
//        if (isConnected) {
//            getCurrentFragment().haveConnection();
//        } else {
//            getCurrentFragment().noConnection();
//        }
    }

    @Override
    public SimpleFoldingCellListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onRefresh() {
        AndroidUtils.changeSwipeVisibility(swipe, true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                AndroidUtils.changeSwipeVisibility(swipe, false);
            }
        }, 2000);
    }
}

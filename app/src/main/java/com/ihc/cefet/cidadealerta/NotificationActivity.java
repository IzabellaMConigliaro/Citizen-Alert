package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by izabellamelendezconigliaro on 26/09/16.
 */
public class NotificationActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener,
        SwipeRefreshLayout.OnRefreshListener {


@Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private LinearLayoutManager layoutManager;
    private NotificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Notificações", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = Item.getTestingList();
        items.addAll(Item.getTestingList());

        adapter = new NotificationAdapter (items, this);

        recyclerView.invalidate();

        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        AndroidUtils.changeSwipeVisibility(swipe, true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recyclerView.setAdapter(adapter);

                AndroidUtils.changeSwipeVisibility(swipe, false);
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.configuration) {
            NotificationDialog.show(getSupportFragmentManager(), NotificationActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onRefresh() {
        AndroidUtils.changeSwipeVisibility(swipe, true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                AndroidUtils.changeSwipeVisibility(swipe, false);
            }
        }, 1000);
    }
}

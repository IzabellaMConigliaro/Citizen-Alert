package com.ihc.cefet.cidadealerta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by izabellamelendezconigliaro on 23/08/16.
 */
public class MenuDrawerFragment extends Fragment {

    @Bind(R.id.user_avatar)
    ImageView userAvatar;
    @Bind(R.id.header)
    RelativeLayout header;
    @Bind(R.id.home)
    TextView home;
    @Bind(R.id.my_issues)
    TextView myIssues;
    @Bind(R.id.my_favorites)
    TextView myFavorites;
    @Bind(R.id.write_issue)
    TextView writeIssue;
    @Bind(R.id.about)
    TextView about;
    @Bind(R.id.contact_us)
    TextView contactUs;
    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.bgNavigation)
    ScrollView bgNavigation;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_menu_drawer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    public void setUp(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.mDrawerToggle = new ActionBarDrawerToggle(this.getActivity(), this.mDrawerLayout,
                R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        this.mDrawerLayout.post(new Runnable() {
            public void run() {
                MenuDrawerFragment.this.mDrawerToggle.syncState();
            }
        });
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerToggle.onOptionsItemSelected(item);
            return true;
        }

        return false;
    }

    public void setActionBarTitle(int title) {
        this.setActionBarTitle(this.getString(title));
    }

    public void setActionBarTitle(CharSequence title) {
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(title);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) this.getActivity()).getSupportActionBar();
    }

    public boolean isDrawerOpen() {
        return this.mDrawerLayout != null && this.mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public boolean isDrawerClosed() {
        return !this.isDrawerOpen();
    }

    public void openDrawer() {
        if (this.mDrawerLayout != null) {
            this.mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (this.mDrawerLayout != null) {
            this.mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.header, R.id.home, R.id.my_issues, R.id.my_favorites, R.id.write_issue, R.id.about, R.id.contact_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header:
                break;
            case R.id.home:
                closeDrawer();
                break;
            case R.id.my_issues:
                break;
            case R.id.my_favorites:
                ((BaseActivity)getActivity()).openActivity(MyFavoritesActivity.class);
                closeDrawer();
                break;
            case R.id.write_issue:
                break;
            case R.id.about:
                break;
            case R.id.contact_us:
                break;
        }
    }
}

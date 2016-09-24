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

import butterknife.ButterKnife;

/**
 * Created by izabellamelendezconigliaro on 23/08/16.
 */
public class MenuDrawerFragment extends Fragment {

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


//    @OnClick({R.id.login_btn, R.id.username, R.id.about_btn, R.id.events_btn, R.id.contact_btn, R.id.news_btn,
//            R.id.material_btn, R.id.favorites_btn, R.id.products_btn, R.id.services_btn, R.id.training_btn, R.id.bgNavigation})
//    public void onClick(View view) {
//        closeDrawer();
//
//        switch (view.getId()) {
//            case R.id.login_btn:
//                ((BaseActivity) getActivity()).openActivity(LoginActivity.class);
//                break;
//            case R.id.username:
//                ((BaseActivity) getActivity()).openActivity(ProfileActivity.class);
//                break;
//            case R.id.about_btn:
//                ((BaseActivity) getActivity()).openActivity(AboutActivity.class);
//                break;
//            case R.id.events_btn:
//                ((BaseActivity) getActivity()).openActivity(EventsActivity.class);
//                break;
//            case R.id.contact_btn:
//                ((BaseActivity) getActivity()).openActivity(ContactActivity.class);
//                break;
//            case R.id.material_btn:
//                ((BaseActivity) getActivity()).openActivity(MaterialsActivity.class);
//                break;
//            case R.id.favorites_btn:
//                break;
//            case R.id.products_btn:
//                break;
//            case R.id.services_btn:
//                break;
//            case R.id.training_btn:
//                break;
//            case R.id.news_btn:
//                break;
//        }
//    }
}

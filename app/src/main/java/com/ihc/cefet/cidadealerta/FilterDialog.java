package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by izabellamelendez on 28/04/16.
 */
public class FilterDialog extends DialogFragment {

    @Bind(R.id.cancel_btn)
    TextView cancelBtn;
    @Bind(R.id.filter_btn)
    TextView filterBtn;
    @Bind(R.id.recyclerview_categories)
    RecyclerView recyclerviewCategories;
    @Bind(R.id.recyclerview_status)
    RecyclerView recyclerviewStatus;

    private static final String CUSTOM_DIALOG = "custom_dialog";
    private static Context mContext;
    private static List<String> categories;
    private static List<String> status;


    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;

    private FilterAdapter adapter;
    private FilterAdapter adapter2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeDialog);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }

        getDialog().getWindow().setWindowAnimations(
                R.style.ThemeDialog);

    }

    public static FilterDialog show(FragmentManager fragmentManager, Context context) {
        FilterDialog dialog = new FilterDialog();
        dialog.setComplexVariable(context);

        if (((BaseActivity) context).isActivityRunning) {
            dialog.show(fragmentManager, CUSTOM_DIALOG);
        }

        return dialog;
    }

    public void setComplexVariable(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filter, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager2 = new LinearLayoutManager(getActivity());

        recyclerviewCategories.setLayoutManager(layoutManager);
        recyclerviewStatus.setLayoutManager(layoutManager2);

        categories = Arrays.asList(getResources().getStringArray(R.array.categories));
        status = Arrays.asList(getResources().getStringArray(R.array.status));


        adapter = new FilterAdapter (categories);

        recyclerviewCategories.setAdapter(adapter);
        recyclerviewCategories.invalidate();

        adapter2 = new FilterAdapter (status);

        recyclerviewStatus.setAdapter(adapter2);
        recyclerviewStatus.invalidate();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void dismiss() {
        ((BaseActivity) mContext).isShowingDialog = false;
        super.dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.filter_btn)
    public void filter() {
        dismiss();

        //(((MainActivity) mContext).getCurrentFragment()).updateFragment();
    }
}

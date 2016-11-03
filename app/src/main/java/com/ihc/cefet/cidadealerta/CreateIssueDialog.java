package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by izabellamelendez on 01/02/16.
 */
public class CreateIssueDialog extends DialogFragment {

    @Bind(R.id.btOK)
    TextView btOK;

    private static Context mContext;
    private static final String CUSTOM_DIALOG = "custom_dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeDialog);
    }

    public static void show(FragmentManager fragmentManager, Context context) {
        CreateIssueDialog dialog = new CreateIssueDialog();
        dialog.setComplexVariable(context);

        if (((BaseActivity) context).isActivityRunning) {
            dialog.show(fragmentManager, CUSTOM_DIALOG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_issue_approved, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick(R.id.btOK)
    public void cancel() {
        ((BaseActivity) mContext).finish();
        onDestroyView();
    }

    public void setComplexVariable(Context var) {
        mContext = var;
    }

}

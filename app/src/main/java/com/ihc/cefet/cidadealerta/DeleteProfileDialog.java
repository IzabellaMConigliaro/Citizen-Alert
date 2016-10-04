package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
public class DeleteProfileDialog extends DialogFragment {

    @Bind(R.id.btDeletar)
    TextView btDeletar;
    @Bind(R.id.btCancel)
    TextView btCancel;

    private static Context mContext;
    private static final String CUSTOM_DIALOG = "custom_dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeDialog);
    }

    public static void show(FragmentManager fragmentManager, Context context) {
        DeleteProfileDialog dialog = new DeleteProfileDialog();
        dialog.setComplexVariable(context);

        if (((BaseActivity) context).isActivityRunning) {
            dialog.show(fragmentManager, CUSTOM_DIALOG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_delete_profile, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void setComplexVariable(Context var) {
        mContext = var;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btDeletar, R.id.btCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btDeletar:
                btDeletar.setVisibility(View.INVISIBLE);
                btCancel.setBackground(getResources().getDrawable(R.drawable.style_dialog_button_disable));

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ((BaseActivity) mContext).closeAllActivitiesAndOpenNew(LoginActivity.class);
                        onDestroyView();
                    }
                }, 2000);
                break;
            case R.id.btCancel:
                dismiss();
                onDestroyView();
                break;
        }
    }
}

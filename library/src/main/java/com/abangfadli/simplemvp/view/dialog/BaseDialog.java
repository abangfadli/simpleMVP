package com.abangfadli.simplemvp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.WindowManager;

/**
 * Created by ahmadfadli on 3/11/16.
 */
public abstract class BaseDialog extends AppCompatDialog {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;

    public BaseDialog(Context context) {
        super(context, 0);
        mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onInitView();
        onInitState(savedInstanceState);
        onInitListener();
    }

    protected void onInitView() {}

    protected void onInitState(Bundle savedInstanceState) {}

    protected void onInitListener() {}


    @Override
    protected void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        getWindow().setAttributes(lp);
    }
}

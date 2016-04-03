package com.abangfadli.simplemvp.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import com.abangfadli.simplemvp.lifecycle.PresenterLifecycleDelegate;
import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.presenter.PresenterFactory;
import com.abangfadli.simplemvp.presenter.PresenterOwner;
import com.abangfadli.simplemvp.view.IView;

/**
 * Created by ahmadfadli on 3/15/16.
 */
public abstract class SimpleDialog<P extends IPresenter>
        extends AppCompatDialog
        implements IView, PresenterOwner<P>, PresenterFactory<P> {

    protected final String TAG = this.getClass().getSimpleName();

    private static final String PARENT_STATE_KEY = "parent_state";
    private static final String PRESENTER_STATE_KEY = "presenter_state";

    protected Context mContext;

    private PresenterLifecycleDelegate<P> presenterDelegate = new PresenterLifecycleDelegate<>(this);

    public SimpleDialog(Context context) {
        this(context, 0);
    }

    public SimpleDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    public final P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public abstract P createPresenter();

    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putBundle(PARENT_STATE_KEY, super.onSaveInstanceState());
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSavePresenter());

        return bundle;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenterDelegate.onResume(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenterDelegate.onPause(((Activity)mContext).isFinishing());
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state.getBundle(PARENT_STATE_KEY));
        presenterDelegate.onRestorePresenter(state.getBundle(PRESENTER_STATE_KEY));
    }

}

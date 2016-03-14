package com.abangfadli.simplemvp.view.viewgroup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.abangfadli.simplemvp.lifecycle.PresenterLifecycleDelegate;
import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.view.IView;
import com.abangfadli.simplemvp.presenter.PresenterFactory;
import com.abangfadli.simplemvp.presenter.PresenterOwner;

/**
 * Created by ahmadfadli on 3/11/16.
 */
public abstract class SimpleRelativeLayout<P extends IPresenter>
        extends BaseRelativeLayout
        implements IView, PresenterOwner<P>, PresenterFactory<P> {

    protected final String TAG = this.getClass().getSimpleName();
    private static final String PARENT_STATE_KEY = "parent_state";
    private static final String PRESENTER_STATE_KEY = "presenter_state";

    protected Context mContext;

    private PresenterLifecycleDelegate<P> presenterDelegate = new PresenterLifecycleDelegate<>(this);

    public SimpleRelativeLayout(Context context) {
        this(context, null);
    }

    public SimpleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    public final P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public abstract P createPresenter();

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(PARENT_STATE_KEY, super.onSaveInstanceState());
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSavePresenter());

        return bundle;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            presenterDelegate.onResume(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenterDelegate.onPause(((Activity) mContext).isFinishing());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle)state;
        super.onRestoreInstanceState(bundle.getParcelable(PARENT_STATE_KEY));
        presenterDelegate.onRestorePresenter(bundle.getBundle(PRESENTER_STATE_KEY));
    }
}

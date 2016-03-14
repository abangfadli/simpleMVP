package com.abangfadli.simplemvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abangfadli.simplemvp.lifecycle.PresenterLifecycleDelegate;
import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.view.IView;
import com.abangfadli.simplemvp.presenter.PresenterFactory;
import com.abangfadli.simplemvp.presenter.PresenterOwner;

/**
 * Created by ahmadfadli on 1/28/16.
 */
public abstract class SimpleActivity<P extends IPresenter>
        extends AppCompatActivity
        implements IView, PresenterOwner<P>, PresenterFactory<P> {

    protected final String TAG = this.getClass().getSimpleName();
    private static final String PRESENTER_BUNDLE_KEY = "presenter_bundle";

    private PresenterLifecycleDelegate<P> presenterDelegate = new PresenterLifecycleDelegate<>(this);

    @Override
    public final P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenterDelegate.onRestorePresenter(savedInstanceState.getBundle(PRESENTER_BUNDLE_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterDelegate.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterDelegate.onPause(isFinishing());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_BUNDLE_KEY, presenterDelegate.onSavePresenter());
    }
}

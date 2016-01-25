package com.abangfadli.simplemvp.lifecycle;

import android.os.Bundle;

import com.abangfadli.simplemvp.presenter.LightMvpPresenter;
import com.abangfadli.simplemvp.view.ViewWithPresenter;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public final class PresenterLifecycleManager<P extends LightMvpPresenter> {

    private static final String PRESENTER_STATE_KEY = "presenter_state";
    private static final String PRESENTER_ID_KEY = "presenter_id";

    protected ViewWithPresenter<P> viewWithPresenter;
    protected P presenter;
    private Bundle bundle;

    public PresenterLifecycleManager(ViewWithPresenter<P> viewWithPresenter) {
        this.viewWithPresenter = viewWithPresenter;
    }

    public void onRestorePresenter(Bundle savedInstanceState) {
        bundle = savedInstanceState;
        if (presenter != null)
            throw new IllegalArgumentException("onRestoreInstanceState() should be called before onResume()");

        this.bundle = savedInstanceState;
        getPresenter();
    }

    public void onResume(Object view) {
        //noinspection unchecked
        presenter.attachView(view);
    }

    public void onPause(boolean isFinishing) {
        presenter.detachView(isFinishing);

        if(isFinishing) {
            presenter.destroy();
            presenter = null;
        }
    }

    public void onSavePresenter(Bundle outState) {
        if(presenter != null) {
            Bundle presenterBundle = new Bundle();
            presenter.saveState(presenterBundle);
            outState.putBundle(PRESENTER_STATE_KEY, presenterBundle);
            outState.putString(PRESENTER_ID_KEY, presenter.getId());
            PresenterHolder.getInstance().add(presenter);
        }
    }

    public P getPresenter() {
        if(presenter == null) {
            if (bundle != null) {
                presenter = PresenterHolder.getInstance().get(bundle.getString(PRESENTER_ID_KEY));
            }

            if (presenter == null) {
                presenter = viewWithPresenter.createPresenter();
                presenter.loadState(bundle.getBundle(PRESENTER_STATE_KEY));
            }
        }
        return presenter;
    }
}

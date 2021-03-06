package com.abangfadli.simplemvp.lifecycle;

import android.os.Bundle;

import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.view.IView;
import com.abangfadli.simplemvp.presenter.PresenterFactory;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public final class PresenterLifecycleDelegate<P extends IPresenter> {

    private static final String PRESENTER_STATE_KEY = "presenter_state";
    private static final String PRESENTER_ID_KEY = "presenter_id";

    protected PresenterFactory<P> presenterFactory;
    protected P presenter;
    private Bundle bundle;

    public PresenterLifecycleDelegate(PresenterFactory<P> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    public void onRestorePresenter(Bundle savedInstanceState) {
        bundle = savedInstanceState;
        if (presenter != null) {
            throw new IllegalArgumentException("onRestoreInstanceState() should be called before onResume()");
        }

        this.bundle = savedInstanceState;
        getPresenter();
    }

    public void onResume(IView view) {
        getPresenter().attachView(view);
    }

    public void onPause(boolean isFinishing) {
        presenter.detachView(isFinishing);

        if(isFinishing) {
            presenter.destroy();
            presenter = null;
        }
    }

    public Bundle onSavePresenter() {
        Bundle bundle = new Bundle();

        if(presenter != null) {
            Bundle presenterBundle = new Bundle();
            presenter.saveState(presenterBundle);
            bundle.putBundle(PRESENTER_STATE_KEY, presenterBundle);
            bundle.putString(PRESENTER_ID_KEY, presenter.getId());
            PresenterStorage.getInstance().add(presenter);
        }

        return bundle;
    }

    public P getPresenter() {
        if(presenter == null) {
            if (bundle != null) {
                presenter = PresenterStorage.getInstance().get(bundle.getString(PRESENTER_ID_KEY));
            }

            if (presenter == null) {
                presenter = presenterFactory.createPresenter();
                if (presenter == null) {
                    throw new RuntimeException("Null Presenter. Did you return null on createPresenter()?");
                }
                if (bundle != null) {
                    presenter.loadState(bundle.getBundle(PRESENTER_STATE_KEY));
                }
            }
        }
        return presenter;
    }
}

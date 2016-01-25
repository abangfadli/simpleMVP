package com.abangfadli.simplemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abangfadli.simplemvp.lifecycle.PresenterLifecycleManager;
import com.abangfadli.simplemvp.presenter.LightMvpPresenter;
import com.abangfadli.simplemvp.presenter.MvpPresenter;
import com.abangfadli.simplemvp.view.ViewWithPresenter;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public abstract class MvpActivity<P extends LightMvpPresenter>
        extends AppCompatActivity
        implements ViewWithPresenter<P> {

    private PresenterLifecycleManager<P> presenterManager = new PresenterLifecycleManager<>(this);

    @Override
    public final P getPresenter() {
        return presenterManager.getPresenter();
    }

    @Override
    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterManager.onRestorePresenter(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterManager.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterManager.onPause(isFinishing());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterManager.onSavePresenter(outState);
    }
}

package com.abangfadli.simplemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abangfadli.simplemvp.lifecycle.PresenterLifecycleDelegate;
import com.abangfadli.simplemvp.view.PresenterFactory;
import com.abangfadli.simplemvp.view.IView;
import com.abangfadli.simplemvp.view.IViewWithPresenter;

/**
 * Created by ahmadfadli on 1/28/16.
 */
public abstract class SimpleActivity<V extends IView, P extends SimplePresenter<V>>
        extends AppCompatActivity
        implements IViewWithPresenter<P>, PresenterFactory<V, P> {

    private PresenterLifecycleDelegate<V, P> presenterDelegate = new PresenterLifecycleDelegate<>(this);

    @Override
    public final P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterDelegate.onRestorePresenter(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterDelegate.onResume((V)this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterDelegate.onPause(isFinishing());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterDelegate.onSavePresenter(outState);
    }
}

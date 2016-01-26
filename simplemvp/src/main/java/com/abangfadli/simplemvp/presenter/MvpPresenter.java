package com.abangfadli.simplemvp.presenter;

import android.os.Bundle;

import com.abangfadli.simplemvp.view.ViewWithPresenter;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public interface MvpPresenter<V> {
    String getId();

    void attachView(V view);

    void detachView(boolean isFinishing);

    void loadState(Bundle state);

    void saveState(Bundle state);

    void destroy();
}

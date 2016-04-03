package com.abangfadli.simplemvp.presenter;

import android.os.Bundle;

import com.abangfadli.simplemvp.view.IView;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public interface IPresenter<V extends IView> {
    String getId();

    void attachView(V view);

    void detachView(boolean isFinishing);

    void loadState(Bundle state);

    void saveState(Bundle state);

    void destroy();

    void addOnDestroyListener(PresenterDestroyListener listener);

    void removeOnDestroyListener(PresenterDestroyListener listener);


}

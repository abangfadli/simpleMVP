package com.abangfadli.simplemvp.view;

import com.abangfadli.simplemvp.presenter.IPresenter;

/**
 * Created by ahmadfadli on 1/28/16.
 */
public interface PresenterFactory<V extends IView, P extends IPresenter<V>> {
    P createPresenter();
}

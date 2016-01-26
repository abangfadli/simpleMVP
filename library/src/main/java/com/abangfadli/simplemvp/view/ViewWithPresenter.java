package com.abangfadli.simplemvp.view;

import com.abangfadli.simplemvp.presenter.MvpPresenter;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public interface ViewWithPresenter<P extends MvpPresenter> {
    P getPresenter();

    P createPresenter();
}

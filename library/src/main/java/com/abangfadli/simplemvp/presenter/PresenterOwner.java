package com.abangfadli.simplemvp.presenter;

import com.abangfadli.simplemvp.presenter.IPresenter;

/**
 * Created by ahmadfadli on 1/28/16.
 */
public interface PresenterOwner<P extends IPresenter> {
    P getPresenter();
}

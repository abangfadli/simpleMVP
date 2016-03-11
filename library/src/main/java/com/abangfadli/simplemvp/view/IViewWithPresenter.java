package com.abangfadli.simplemvp.view;

import com.abangfadli.simplemvp.presenter.IPresenter;

/**
 * Created by ahmadfadli on 1/28/16.
 */
public interface IViewWithPresenter<P extends IPresenter>
        extends IView {

    P getPresenter();
}

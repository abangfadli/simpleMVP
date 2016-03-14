package com.abangfadli.simplemvp.presenter;

import android.os.Bundle;

import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.presenter.PresenterDestroyListener;
import com.abangfadli.simplemvp.view.IView;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SimplePresenter<V extends IView> implements IPresenter<V> {

    protected final String TAG = this.getClass().getSimpleName();

    private final String id;
    protected WeakReference<V> view;

    private CopyOnWriteArrayList<PresenterDestroyListener> onDestroyListeners;


    public SimplePresenter() {

        id = TAG + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
        onDestroyListeners = new CopyOnWriteArrayList<>();
    }

    //================================================================================
    // PUBLIC METHOD
    //================================================================================
    @Override
    public final String getId() {
        return id;
    }

    @Override
    public void attachView(V view) {
        this.view = new WeakReference<>(view);
        onViewAttached();
    }

    @Override
    public void detachView(boolean isFinishing) {
        onDetachView();
        view = null;
        onViewDetached();
    }

    public void destroy() {
        for (PresenterDestroyListener onDestroyListener : onDestroyListeners) {
            onDestroyListener.onDestroy();
        }

        onDestroy();
    }

    @Override
    public void addOnDestroyListener(PresenterDestroyListener listener) {
        onDestroyListeners.add(listener);
    }

    @Override
    public void removeOnDestroyListener(PresenterDestroyListener listener) {
        onDestroyListeners.remove(listener);
    }

    @Override
    public void loadState(Bundle state) {
        onCreate(state);
    }

    @Override
    public void saveState(Bundle state) {
        onSave(state);
    }


    //================================================================================
    // LIFE CYCLE
    //================================================================================
    protected void onCreate(Bundle savedState) {
    }

    protected void onSave(Bundle outState) {
    }

    protected void resetState() {
    }

    protected void onViewAttached() {
    }

    protected void onDetachView() {
    }

    protected void onViewDetached() {
    }

    protected void onDestroy() {
    }

    //================================================================================
    // ACCESSOR
    //================================================================================
    public V getView() {
        return view == null ? null : view.get();
    }
}

package com.abangfadli.simplemvp.lifecycle;

import android.support.v4.util.ArrayMap;

import com.abangfadli.simplemvp.presenter.SimplePresenter;
import com.abangfadli.simplemvp.presenter.PresenterDestroyListener;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public class PresenterHolder {

    private static PresenterHolder instance;

    private ArrayMap<String, SimplePresenter> presenters;


    private PresenterHolder(){
        presenters = new ArrayMap<>();
    }

    public static PresenterHolder getInstance() {
        if(instance == null) {
            instance = new PresenterHolder();
        }
        return instance;
    }


    public void add(SimplePresenter presenter) {
        final String id = presenter.getId();
        presenters.put(id, presenter);

        presenter.addOnDestroyListener(new PresenterDestroyListener() {
            @Override
            public void onDestroy() {
                presenters.remove(id);
            }
        });
        // TODO: Add presenter on destroy listener
    }

    public <P extends SimplePresenter> P get(String id) {
        if(presenters.containsKey(id)) {
            return (P) presenters.get(id);
        }

        return null;
    }

    public <P extends SimplePresenter> P getAndRemove(String id) {
        if(presenters.containsKey(id)) {
            return (P) presenters.remove(id);
        }

        return null;
    }

    public void clear() {
        presenters.clear();
    }
}

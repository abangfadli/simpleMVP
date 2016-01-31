package com.abangfadli.simplemvp.lifecycle;

import android.support.v4.util.ArrayMap;

import com.abangfadli.simplemvp.presenter.IPresenter;
import com.abangfadli.simplemvp.presenter.PresenterDestroyListener;
import com.abangfadli.simplemvp.SimplePresenter;

/**
 * Created by ahmadfadli on 1/13/16.
 */
public class PresenterHolder {

    private static PresenterHolder instance;

    private ArrayMap<String, IPresenter> presenters;


    private PresenterHolder(){
        presenters = new ArrayMap<>();
    }

    public static PresenterHolder getInstance() {
        if(instance == null) {
            instance = new PresenterHolder();
        }
        return instance;
    }


    public void add(IPresenter presenter) {
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

    public <P extends IPresenter> P get(String id) {
        if(presenters.containsKey(id)) {
            return (P) presenters.get(id);
        }

        return null;
    }

    public <P extends IPresenter> P getAndRemove(String id) {
        if(presenters.containsKey(id)) {
            return (P) presenters.remove(id);
        }

        return null;
    }

    public void clear() {
        presenters.clear();
    }
}

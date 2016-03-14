package com.abangfadli.simplemvp.view.viewgroup;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ahmadfadli on 3/11/16.
 */
public abstract class BaseLinearLayout extends android.widget.LinearLayout {

    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;


    public BaseLinearLayout(Context context) {
        this(context, null);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        onInitView();
        onInitState();
        onInitListener();
    }

    // Intended to be overridden by subclasses
    protected void onInitView() {}
    protected void onInitState() {}
    protected void onInitListener() {}
}

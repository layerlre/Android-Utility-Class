package com.layernet.adapter;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by layer on 28/8/2558.
 * Custom GridLayout addView adapter
 */
public abstract class AddViewAdapter {

    private LinearLayout linearLayout;

    public AddViewAdapter(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    protected abstract View getView(int position);

    protected abstract int getCount();

    public void renderView(){
        if (linearLayout.getChildCount()>0)
            linearLayout.removeAllViews();
        for (int position = 0; position < getCount(); position++) {
            linearLayout.addView(getView(position));
        }
    }

}
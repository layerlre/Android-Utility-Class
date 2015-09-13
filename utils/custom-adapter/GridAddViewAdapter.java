package com.layernet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by layer on 28/8/2558.
 * Custom GridLayout addView adapter
 */
public abstract class GridAddViewAdapter {



    private GridLayout gridLayout;

    public GridAddViewAdapter(GridLayout gridLayout) {
        this.gridLayout = gridLayout;
    }

    public abstract int getColumn();


    protected abstract View getView(int position);

    protected abstract int getCount();

    public void renderView(){
        this.gridLayout = gridLayout;
        if (gridLayout.getChildCount()>0)
            gridLayout.removeAllViews();
        gridLayout.setColumnCount(getColumn());
        for (int position = 0; position < getCount(); position++) {
            gridLayout.addView(getView(position));
        }
    }

}

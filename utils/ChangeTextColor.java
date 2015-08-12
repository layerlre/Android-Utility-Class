package utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChangeTextColor {

    public ChangeTextColor() {
        super();

    }

    public void setTextColor(int newcolor, ViewGroup vGroup) {
        int count = vGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = vGroup.getChildAt(i);
            if (v instanceof TextView) {
                checkColor(newcolor, v);
            } else if (vGroup.getChildAt(i) instanceof ViewGroup) {
                setChildGroup(newcolor, (ViewGroup) vGroup.getChildAt(i));
            }
        }
    }

    private void setChildGroup(int newcolor, ViewGroup vGroup) {
        int count = vGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = vGroup.getChildAt(i);
            if (v instanceof TextView) {
                checkColor(newcolor, v);
            } else if (vGroup.getChildAt(i) instanceof ViewGroup) {
                setChildGroup(newcolor, (ViewGroup) vGroup.getChildAt(i));
            }
        }
    }

    private void checkColor(int newcolor, View v) {
        ((TextView) v).setTextColor(newcolor);
    }


}

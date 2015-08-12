package utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class ScreenUtils {

	Activity activity;

	public ScreenUtils(Activity activity) {
		super();
		this.activity = activity;
	}

	public int getScreenWidth(){
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;
	}
	
	public int getScreenHeight(){
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.y;
	}
}

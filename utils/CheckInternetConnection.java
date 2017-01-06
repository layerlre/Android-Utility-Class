package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {
	
	private static CheckInternetConnection instance;

	public static CheckInternetConnection getInstance(){
		if (instance==null)
			instance =  new CheckInternetConnection();
		return instance;
	}
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) return false;
		if (!i.isConnected()) return false;
		if (!i.isAvailable()) return false;
		return true;

	}
}

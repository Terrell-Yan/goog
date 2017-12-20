package com.example.goog.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    //�ж�����״̬
	public static boolean getNetstae(Context context){
		ConnectivityManager mnager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=mnager.getActiveNetworkInfo();
		if (info==null) {
			return false;
		}
		return info.isConnected();
	}
}

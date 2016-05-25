package com.szittom.picturtool.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.szittom.picturtool.app.App;

public class T{

	private static Handler handler = new Handler(Looper.getMainLooper());
	private static Toast toast = null;
	private static Object synObj = new Object();

	public static void showToast(String msg) {
		showToast(App.getInstance().getApplicationContext(), msg);
	}

	public static void showToastLong(String msg) {
		showToastLong(App.getInstance().getApplicationContext(), msg);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
	 * @version 2012-5-22 上午11:13:10
	 * @param act
	 * @param msg
	 */
	public static void showToast(final Context act, final String msg) {
		showMessage(act, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * @param act
	 * @param msg
	 */
	public static void showToastLong(final Context act, final String msg) {
		showMessage(act, msg, Toast.LENGTH_LONG);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * @param act
	 * @param msg
	 */
	public static void showToast(final Context act, final int msg) {
		showMessage(act, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * @param act
	 * @param msg
	 */
	public static void showToastLong(final Context act, final int msg) {
		showMessage(act, msg, Toast.LENGTH_LONG);
	}

	/**
	 * Toast发送消息
	 * @param act
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final Context act, final int msg,
								   final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {

					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.setText(msg);
								toast.setDuration(len);
							} else {
								toast = Toast.makeText(act, msg, len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * Toast发送消息
	 * @param act
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final Context act, final String msg,
								   final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {

					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.setText(msg);
								toast.setDuration(len);
							} else {
								toast = Toast.makeText(act, msg, len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * 关闭当前Toast
	 */
	public static void cancelCurrentToast() {
		if (toast != null) {
			toast.cancel();
		}
	}
}

package com.example.andigitalfoursquare.common;

import com.example.andigitalfoursquare.util.InternetConnectivityUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;

public class ANDigitalAppConfig {
	private static ANDigitalAppConfig uniqInstance;

	

	private ANDigitalAppConfig() {
	}

	public static synchronized ANDigitalAppConfig getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new ANDigitalAppConfig();
		}
		return uniqInstance;
	}

	

	public boolean isOnline() {
		// flag for Internet connection status
		Boolean isInternetPresent = false;

		// Connection detector class
		InternetConnectivityUtil cd = new InternetConnectivityUtil(AppController.getContext());

		if (cd.isConnectingToInternet()) {
			isInternetPresent = true;
		} else {
			isInternetPresent = false;

			// TODO - Error message dialog box
		}

		return isInternetPresent;

	}

	
	public static void ShowMessageDialogBox(Activity activity, String title, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

		// set title

		alertDialogBuilder.setTitle(title);
		// alertDialogBuilder.setIcon(R.drawable.ic_delete);

		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)
		/*
		 * .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener()
		 * { public void onClick(DialogInterface dialog,int id) { // if this
		 * button is clicked, close // current activity
		 * MainActivity.this.finish(); } })
		 */
		.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}
}

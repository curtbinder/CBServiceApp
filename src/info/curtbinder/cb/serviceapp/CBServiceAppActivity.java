package info.curtbinder.cb.serviceapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CBServiceAppActivity extends Activity {
	// Message to listen for
	public static final String CB_MESSAGE_INTENT = "info.curtbinder.cb.service.CB_MESSAGE";
	// Name of field for extra data
	public static final String CB_MESSAGE_STRING = "CB_MESSAGE_STRING";

	TextView tv;

	MyReceiver r;
	IntentFilter f;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tv = (TextView) findViewById(R.id.textView1);

		// Create the receiver and intent filter when the app is created
		r = new MyReceiver();
		f = new IntentFilter(CB_MESSAGE_INTENT);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// unregister the receiver when the app is paused
		// we don't want to know about the updates if we are not visible
		unregisterReceiver(r);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// register the receiver
		// tell the system that we want to be notified of the message
		// indicated by the filter
		registerReceiver(r, f);
	}

	public void updateDisplay(String msg) {
		tv.setText(msg);
	}

	class MyReceiver extends BroadcastReceiver {
		// Receiver class that listens for the message from the system
		@Override
		public void onReceive(Context context, Intent intent) {
			// Called when we receive the message from the system
			Log.d("MyReceiver", "onReceive");
			// Grab the extra data and do something with it
			updateDisplay(intent.getStringExtra(CB_MESSAGE_STRING));
		}
	}
}
package com.elprog.emitter.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
		String response=intent.getStringExtra("response");
		Intent i = new Intent();
        i.setClassName("com.elprog.emitter", "com.elprog.emitter.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("response",response);
        context.startActivity(i);
    }
}


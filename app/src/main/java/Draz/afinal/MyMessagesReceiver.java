package Draz.afinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MyMessagesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the service when the alarm is triggered
        Intent serviceIntent = new Intent(context, MyMessageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);

        } else {
            context.startService(serviceIntent);
        }
    }
}
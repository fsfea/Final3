package Draz.afinal.bkg_alram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;

import java.text.SimpleDateFormat;

public class MyMessagesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the service when the alarm is triggered
        Intent serviceIntent = new Intent(context, MyMessageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            context.startForegroundService(serviceIntent);
             SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("phoneNo", null, "sms message", null, null);

        } else {
            context.startService(serviceIntent);
        }
    }
}
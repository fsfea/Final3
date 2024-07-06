package Draz.afinal.bkg_alram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import Draz.afinal.data.MyMessage.MyMessages;

public class MyMessagesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the service when the alarm is triggered
        // Retrieve phone number and message from intent extras
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String message = intent.getStringExtra("message");

          MyMessages msg= (MyMessages) intent.getExtras().get("msg");
        // Send SMS
        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage(msg.getContact_phone(), null, msg.getText(), null, null);
        Toast.makeText(context, "sendeind"+message+" to"+ msg.getContact_phone(), Toast.LENGTH_SHORT).show();


    }
}
package Draz.afinal.bkg_alram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MyMessagesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the service when the alarm is triggered
        // Retrieve phone number and message from intent extras
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String message = intent.getStringExtra("message");

        // Send SMS
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(context, "sendeind"+message+" to"+ phoneNumber, Toast.LENGTH_SHORT).show();

        // Alternatively, if you want to open WhatsApp, you can use the following code
        /*
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+phoneNumber)+"@s.whatsapp.net");
        context.startActivity(sendIntent);
        */
//        Intent serviceIntent = new Intent(context, MyMessageService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            context.startForegroundService(serviceIntent);
//             SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("phoneNo", null, "sms message", null, null);
//
//        } else {
//            context.startService(serviceIntent);
//        }
    }
}
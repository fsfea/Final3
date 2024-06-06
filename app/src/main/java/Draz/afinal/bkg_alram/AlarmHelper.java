package Draz.afinal.bkg_alram;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import Draz.afinal.data.MyMessage.MyMessages;

public class AlarmHelper {
    public static void setAlarm(Context context, long timeMls, MyMessages messages) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MyMessagesReceiver.class);
        intent.putExtra("msg",messages);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE);
        AlarmManager.AlarmClockInfo a=new AlarmManager.AlarmClockInfo(timeMls,pendingIntent);
        alarmManager.setAlarmClock(a, pendingIntent);
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MyMessagesReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

}

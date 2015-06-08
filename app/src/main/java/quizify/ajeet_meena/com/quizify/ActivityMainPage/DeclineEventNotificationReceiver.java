package quizify.ajeet_meena.com.quizify.ActivityMainPage;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class DeclineEventNotificationReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        context.getSharedPreferences("prefs", context.MODE_PRIVATE).edit().putInt("event", -1).commit();
        try
        {
            MainActivity.refreshProgmatically();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent i = new Intent(context, OnAlarmReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Message.message(context, "Declined");
    }
}
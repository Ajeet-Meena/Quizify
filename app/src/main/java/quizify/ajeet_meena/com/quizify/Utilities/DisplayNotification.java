package quizify.ajeet_meena.com.quizify.Utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import quizify.ajeet_meena.com.quizify.ActivityMain.MainActivity;
import quizify.ajeet_meena.com.quizify.ActivityMain.NotificationReceiver;
import quizify.ajeet_meena.com.quizify.ActivityMain.OnAlarmReceive;
import quizify.ajeet_meena.com.quizify.R;


/**
 * Display Notification class makes notification easy
 */
public class DisplayNotification
{

    Context context;
    NotificationManager mNotificationManager;

    public DisplayNotification(Context context)
    {
        this.context = context;
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Make Notificaition To Decline an Event.
     *
     * @param event_name      Title of Notificaition
     * @param duration        Content in Notificaiton i.e Start time to end time
     * @param NOTIFICATION_ID Notification id, i.e event id
     */
    public void makeDeclineNotification(int event_id,String event_name, String event_start_time, int duration, int NOTIFICATION_ID)
    {
        String content = event_start_time + ": " + duration / 60 + " minutes";
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent actionIntent = new Intent(context, NotificationReceiver.class);
        actionIntent.putExtra("from", "decline event");

        actionIntent.setAction(Intent.ACTION_VIEW);
        actionIntent = actionIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        actionIntent.putExtra("event_id",event_id);
        PendingIntent declinePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, actionIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(event_name)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.medal)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon)).addAction(R.drawable.decline_icon, "Decline", declinePendingIntent);
        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            n = builder.build();
        } else
        {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(NOTIFICATION_ID, n);
    }

    /**
     * Notificaition with GCM push message
     *
     * @param event_id
     * @param event_name
     * @param event_start_time
     * @param duration
     * @param NOTIFICATION_ID
     */
    public void makeGCMNotification(int event_id, String event_name, String event_start_time, int duration, int NOTIFICATION_ID)
    {
        String content = event_start_time + " :" + duration / 60 + " minutes";
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent actionIntent = new Intent(context, NotificationReceiver.class);
        actionIntent.putExtra("from", "participate event");
        actionIntent.putExtra("event_id", event_id);
        actionIntent.putExtra("event_name", event_name);
        actionIntent.putExtra("event_start_time", event_start_time);
        actionIntent.putExtra("duration", duration);
        actionIntent.setAction(Intent.ACTION_VIEW);

        actionIntent = actionIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        PendingIntent declinePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, actionIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(event_name)
                .setContentText(content).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.medal)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon)).addAction(R.mipmap.tick_participate, "Participate", declinePendingIntent);

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            n = builder.build();
        } else
        {
            n = builder.getNotification();
        }
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify("gcm",NOTIFICATION_ID, n);
    }

    public void makeResultNotification(int event_id, String event_name, int NOTIFICATION_ID)
    {

        Intent intent = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityResult.MainActivity.class);

        intent.putExtra("from", "notification");
        intent.putExtra("event_id", event_id);
        intent.putExtra("event_name", event_name);
        String content = "Your played quiz " + event_name + " results are now declared!";

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(event_name)
                .setContentText(content).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.medal)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon));

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            n = builder.build();
        } else
        {
            n = builder.getNotification();
        }
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFICATION_ID, n);
    }

    /**
     * To clear Notificaition
     *
     * @param id notificaiton id to be cleared
     */
    public void clearNotification(int id)
    {
        mNotificationManager.cancel(id);
    }

    /**
     * To clear All Notificaiton
     */
    public void clearAllNotification()
    {
        mNotificationManager.cancelAll();
    }

    /**
     * Alarm which invoke examination activity even if application is closed
     * Generate a Notification showing information about alarm
     * @param seconds seconds after alarm has to be invoke
     * @param event_id event_id to be passed for next activity
     * @param event_name event_name to be passed for next activity
     * @param duration event duration to be passed for next activity
     */
    public void setupAlarm(int seconds,int event_id,String event_name,int duration)
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnAlarmReceive.class);
        intent.putExtra("event_id", event_id);
        intent.putExtra("event_name", event_name);
        intent.putExtra("event_duration", duration);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, event_id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Message.message(context, "Participated");
    }

    /**
     * Cancel Alarm
     * @param event_id
     */
    public void cancelAlarm(int event_id)
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnAlarmReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, event_id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Message.message(context, "Declined");
        alarmManager.cancel(pendingIntent);
    }

}
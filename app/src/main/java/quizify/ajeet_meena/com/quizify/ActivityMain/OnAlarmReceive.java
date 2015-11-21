package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import quizify.ajeet_meena.com.quizify.Utilities.Message;

/**
 * TO open an event after event start time if participated.
 * Start event even if app is closed.
 */
public class OnAlarmReceive extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        Intent i = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityQuiz.MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();


        if (extras != null)
        {
            i.putExtra("event_name", extras.getString("event_name"));
            i.putExtra("event_id", extras.getInt("event_id"));
            i.putExtra("event_duration", extras.getInt("event_duration"));

            context.startActivity(i);
        } else
        {
            Message.message(context, "An error occurred");
        }

        // ((Activity) context).finish();
    }

}
package quizify.ajeet_meena.com.quizify.Utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import quizify.ajeet_meena.com.quizify.ActivityMainPage.DeclineEventNotificationReceiver;
import quizify.ajeet_meena.com.quizify.ActivityMainPage.MainActivity;
import quizify.ajeet_meena.com.quizify.R;

public class DisplayNotification implements Runnable
{

    Context mContext;
    NotificationManager mNotificationManager;
    int NOTIFICATION_ID = 1;
    String title = null;
    String content = null;
    boolean hide;


    public DisplayNotification(Context mContext, String title, String content, boolean hide)
    {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        this.title = title;
        this.content = content;
        this.hide = hide;
    }

    @Override
    public void run()
    {
        makeNotification(mContext);
    }

    private void makeNotification(Context context)
    {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent declineIntent = new Intent(context, DeclineEventNotificationReceiver.class);

        declineIntent.setAction(Intent.ACTION_VIEW);
        declineIntent = declineIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);


        PendingIntent declinePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, declineIntent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.medal)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.medal)).addAction(0, "DECLINE", declinePendingIntent);

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            n = builder.build();
        } else
        {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        if (!hide)
            mNotificationManager.notify(NOTIFICATION_ID, n);
        else
            mNotificationManager.cancelAll();
    }
}
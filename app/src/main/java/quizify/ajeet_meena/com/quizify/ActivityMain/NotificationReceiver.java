package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import quizify.ajeet_meena.com.quizify.Utilities.DisplayNotification;
import quizify.ajeet_meena.com.quizify.Utilities.Message;
import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;

public class NotificationReceiver extends BroadcastReceiver
{
    Context context;
    DisplayNotification displayNotification;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        displayNotification = new DisplayNotification(context);
        OnlineHelper onlineHelper = new OnlineHelper(context);
        String from=null;
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            from = bundle.getString("from");
        }
        if(from.equals("decline event"))
        {
            // send event id only

            int event_id = bundle.getInt("event_id");
            displayNotification.cancelAlarm(event_id);
            displayNotification.clearNotification(event_id);
            SingleRowEvent.removePref(event_id, context);
            onlineHelper.incDecParticipant(null, null, "decrement", "out-app", event_id);
            Message.message(context, "Declined");
            refreshUi();
            context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
        else
        {
            // send single row
            int event_id =bundle.getInt("event_id");
            int duration = bundle.getInt("duration");
            String event_name = bundle.getString("event_name");
            String event_start_time = bundle.getString("event_start_time");
            SingleRowEvent singleRowEvent = new SingleRowEvent(event_id,event_name,event_start_time,duration);
            if(singleRowEvent.isValidEvent(singleRowEvent.eventStartTime))
            {
                if(!singleRowEvent.isEventColliding(context))
                {
                    singleRowEvent.setPrefs(context);
                    displayNotification.makeDeclineNotification(event_id, event_name, event_start_time, duration, event_id);
                    displayNotification.setupAlarm(singleRowEvent.getTimeRemain(), event_id, event_name, duration);
                    onlineHelper.incDecParticipant(null, null, "increment", "out-app", event_id);
                    refreshUi();
                }
                else
                {
                    context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                    Message.message(context, "Failed, An event is colliding");
                }
            }
            else {
                displayNotification.clearNotification(singleRowEvent.event_id);
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                Message.message(context, "Event No longer Valid");
            }
        }
    }

    private void refreshUi()
    {
        try
        {
            UiHelper.swipeRefresh(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
package quizify.ajeet_meena.com.quizify.ActivityMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SingleRowRecentActivity
{
    String message;
    String eventName;
    boolean isDeclared;
    String announcementMessage;
    Date date;
    int eventId;

    public SingleRowRecentActivity(boolean isDeclared, String date, String eventName, int eventId)
    {
        this.isDeclared = isDeclared;
        this.eventName = eventName;
        this.eventId = eventId;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH);
            this.date = dateFormat.parse(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public void setMessages()
    {
        if(isDeclared)
        {
            message = "Your gave event " + eventName +" is now Declared!" ;
            announcementMessage = "Declared";
        }
        else
        {
            message = "You finished an event "+ eventName;
            announcementMessage = "Undeclared";
        }
    }
}

package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ajeet Meena on 18-06-2015.
 */
public class SingleRowEvent
{
    public String eventName;
    public Date eventStartTime;
    public int noOfParticipant;
    public int event_id;
    public boolean button_state = false; // button state (participate or decline)
    int difficulty;
    int no_question;
    public int duration;
    int event_type;

    public SingleRowEvent(String eventName, Date eventStartTime, int noOfParticipant, int event_id, int difficulty, int no_question, int duration, int event_type)
    {
        this.event_id = event_id;
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.noOfParticipant = noOfParticipant;
        this.event_id = event_id;
        this.difficulty = difficulty;
        this.no_question = no_question;
        this.duration = duration;
        this.event_type = event_type;
    }

    // used for push notification
    SingleRowEvent(int event_id, String eventName, String eventStartTime, int duration)
    {
        this.event_id = event_id;
        this.eventName = eventName;
        this.duration = duration;

        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            this.eventStartTime = dateFormat.parse(eventStartTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * getArrayList to show in dialog
     *
     * @return
     */
    public ArrayList<String> getArrayList()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("EVENT NAME: " + eventName);
        list.add("START TIME: " + eventStartTime);
        list.add("DURATION: " + duration/60 +" minutes");
        list.add("RULES: " + event_type);
        list.add("TOTAL QUESTION: " + no_question);
        list.add("DIFFICULTY: " + difficulty);
        list.add("PARTICIPANT: " + noOfParticipant);
        return list;
    }


    public void setButton_state(boolean state)
    {
        this.button_state = state;
    }

    public void setPrefs(Context context)
    {
        context.getSharedPreferences("state", Context.MODE_PRIVATE).edit().putBoolean(Integer.toString(event_id), true).commit();
        context.getSharedPreferences("duration", Context.MODE_PRIVATE).edit().putInt(Integer.toString(event_id), duration).commit();
        context.getSharedPreferences("event_start_time", Context.MODE_PRIVATE).edit().putString(Integer.toString(event_id), eventStartTime.toString()).commit();
    }

    public static void removePref(int event_id, Context context)
    {
        context.getSharedPreferences("state", Context.MODE_PRIVATE).edit().remove(Integer.toString(event_id)).commit();
        context.getSharedPreferences("duration", Context.MODE_PRIVATE).edit().remove(Integer.toString(event_id)).commit();
        context.getSharedPreferences("event_start_time", Context.MODE_PRIVATE).edit().remove(Integer.toString(event_id)).commit();
    }

    public boolean getButton_state()
    {
        return button_state;
    }


    public void loadButtonState(SharedPreferences sharedPreferences)
    {
        this.button_state = sharedPreferences.getBoolean(Integer.toString(event_id), false);
    }

    /**
     * To get time remaining (event start time - current time)
     *
     * @return
     */
    public int getTimeRemain()
    {
        return (int) (eventStartTime.getTime() - new Date().getTime()) / 1000;
    }

    /**
     * Return if event is valid or not means if date is before current date then return 0
     *
     * @return
     */
    public static boolean isValidEvent(Date eventStartTime)
    {
        if (new Date().compareTo(eventStartTime) < 0)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isEventColliding(Context context)
    {
        SharedPreferences sharedPreferencesDuration = context.getSharedPreferences("duration", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesStartTime = context.getSharedPreferences("event_start_time", Context.MODE_PRIVATE);
        Date startA, endA, startB = null, endB = null;
        startA = eventStartTime;
        endA = new Date(startA.getTime() + duration * 1000);
        Map<String, ?> allEntries3 = sharedPreferencesStartTime.getAll();
        for (Map.Entry<String, ?> entry : allEntries3.entrySet())
        {
            if(entry.getKey().equals(Integer.toString(event_id)))
                continue;
            try
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                startB = dateFormat.parse(entry.getValue().toString());
                endB = new Date(startB.getTime() + sharedPreferencesDuration.getInt(entry.getKey(), 0)*1000);
            } catch (ParseException e)
            {
                e.printStackTrace();
            } finally
            {
                if (startA.compareTo(endB) <= 0 && endA.compareTo(startB) >= 0)
                {
                    Log.d("compare date", "A" + startA.toString() + "A" + endA.toString() + "B" + startB.toString() + "B" + endB.toString());
                    return true;
                }
            }
        }
        return false;
    }


    public static void removeInvalidEventPrefs(Context context)
    {
        SharedPreferences sharedPreferencesState = context.getSharedPreferences("state",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesDuration = context.getSharedPreferences("duration",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesStartTime = context.getSharedPreferences("event_start_time", Context.MODE_PRIVATE);
        Date eventStartTime = null;
        Map<String, ?> allEntries3 = sharedPreferencesStartTime.getAll();
        for (Map.Entry<String, ?> entry : allEntries3.entrySet())
        {
            try
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
                eventStartTime = dateFormat.parse(entry.getValue().toString());
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            if(eventStartTime!=null && !isValidEvent(eventStartTime))
            {
                String key = entry.getKey();
                sharedPreferencesStartTime.edit().remove(key).commit();
                sharedPreferencesDuration.edit().remove(key).commit();
                sharedPreferencesState.edit().remove(key).commit();
            }
        }


    }
}

package quizify.ajeet_meena.com.quizify.ActivityMainPage;


import java.util.ArrayList;
import java.util.Date;

public class SingleRowEvent
{
    public String Event_name;
    Date eventStartTime;
    int No_of_participant;
    public int event_id;
    public int button_state = 0;
    int difficulty;
    int no_question;
    public int duration;
    int event_type;

    public SingleRowEvent(String Event_name, Date Event_start_time, int No_of_participant, int event_id, int difficulty, int no_question, int duration, int event_type)
    {
        this.event_id = event_id;
        this.Event_name = Event_name;
        this.eventStartTime = Event_start_time;
        this.No_of_participant = No_of_participant;
        this.event_id = event_id;
        this.difficulty = difficulty;
        this.no_question = no_question;
        this.duration = duration;
        this.event_type = event_type;
    }

    public ArrayList<String> getArrayList()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("EVENT NAME: " + Event_name);
        list.add("START TIME: " + eventStartTime);
        list.add("DURATION: " + duration);
        list.add("RULES: " + event_type);
        list.add("TOTAL QUESTION: " + no_question);
        list.add("DIFFICULTY: " + difficulty);
        list.add("PARTICIPANT: " + No_of_participant);
        return list;
    }

    public void setButton_state(int state)
    {
        this.button_state = state;
    }

    public int getTimeRemain()
    {
        return (int) (eventStartTime.getTime() - new Date().getTime()) / 1000;
    }

    public boolean checkValidEvent()
    {

        if (new Date().compareTo(eventStartTime) < 0)
        {
            return true;
        } else
        {
            return false;
        }
    }


}
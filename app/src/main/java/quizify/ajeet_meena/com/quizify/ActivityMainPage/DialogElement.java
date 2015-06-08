package quizify.ajeet_meena.com.quizify.ActivityMainPage;

public class DialogElement
{

    String event_name;
    String start_time;
    int duration;
    int type;
    int no_of_question;
    int difficulty;
    int no_of_participant;

    public DialogElement(String event_name, String start_time, int duration, int type,
                         int no_of_question, int difficulty, int no_of_participant)
    {

        this.event_name = event_name;
        this.start_time = start_time;
        this.duration = duration;
        this.type = type;
        this.no_of_question = no_of_participant;
        this.difficulty = difficulty;
        this.no_of_participant = no_of_participant;

    }

}

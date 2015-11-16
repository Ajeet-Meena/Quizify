package quizify.ajeet_meena.com.quizify.ActivityQuiz;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable
{
    ArrayList<Question> list;
    int event_id;
    int duration;
    String event_name;
    int numberOfQuestions;
    int currentPosition = 0;

    Quiz()
    {
        list = new ArrayList<>();
    }

    public int getEvent_id()
    {
        return event_id;
    }

    public void setEvent_id(int event_id)
    {
        this.event_id = event_id;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String getEvent_name()
    {
        return event_name;
    }

    public void setEvent_name(String event_name)
    {
        this.event_name = event_name;
    }

    public int getNoOfQuestions()
    {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions()
    {
        this.numberOfQuestions = list.size();
    }

    void update_response(int currentPosition,int response)
    {
        list.get(currentPosition).setResponse(response);
    }

    int getResponse(int currentPosition)
    {
        return list.get(currentPosition).getResponse();
    }


    public void addQuestion(Question question)
    {
        list.add(question);
    }

    String getQuestionText(int currentPosition)
    {
        return list.get(currentPosition).question;
    }

    String[] getOptions(int currentPosition)
    {
        return list.get(currentPosition).options;
    }

    void incrementCurrentPosition()
    {
        this.currentPosition++;
        if (this.currentPosition >= this.numberOfQuestions)
        {
            this.currentPosition = 0;
        }
    }

    void decrementCurrentPosition()
    {
        this.currentPosition--;
        if (this.currentPosition < 0)
        {
            this.currentPosition = this.numberOfQuestions - 1;
        }
    }

    void jumpCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public int getCorrectAnswered()
    {
        int correct = 0;
        for (int i = 0; i < this.numberOfQuestions; i++)
        {
            if (list.get(i).getResponse() == Integer.parseInt(list.get(i).answer))
            {
                correct++;
            }
        }
        return correct;
    }

    public int getWrongAnswer()
    {
        int wrong = 0;
        for (int i = 0; i < this.numberOfQuestions; i++)
        {
            if (!(list.get(i).getResponse() == Integer.parseInt(list.get(i).answer)))
            {
                if(list.get(i).getResponse()!=-1)
                wrong++;
            }
        }
        return wrong;
    }

    public int getUnAttempted()
    {
        int unattempt = 0;
        for (int i = 0; i < this.numberOfQuestions; i++)
        {
            if (list.get(i).getResponse() == -1)
            {
                unattempt++;
            }
        }
        return unattempt;
    }

    public void setNoOfQuestion()
    {
        this.numberOfQuestions = list.size();
    }

    public int correctWrongUnAnswered(int currentPosition)
    {
        if(list.get(currentPosition).getResponse() == -1)
        {
            return 3;
        }
        else if(list.get(currentPosition).getResponse()==Integer.parseInt(list.get(currentPosition).answer))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public String getSolution()
    {
        return list.get(currentPosition).solution;
    }

    public String getAnswer()
    {
        return Integer.toString(Integer.parseInt(list.get(currentPosition).answer)+1);
    }
}

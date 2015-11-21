package quizify.ajeet_meena.com.quizify.ActivityQuiz;


import java.io.Serializable;

public class Question implements Serializable
{


    int number_of_option;
    String[] options;
    String question;
    String answer;
    String solution;
    int q_id;
    int response = -1;

    public Question(String question, String[] option, String answer, int q_id,String solution)
    {
        this.number_of_option = option.length;
        this.q_id = q_id;
        this.options = option;
        this.question = question;
        this.answer = answer;
        this.solution = solution;
    }

    public void setResponse(int response)
    {
        this.response = response;
    }

    public int getResponse()
    {
        return response;
    }
}

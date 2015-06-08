package quizify.ajeet_meena.com.quizify.ActivityExamination;


public class Question
{


    int number_of_option;
    String[] options;
    String question;
    String answer;
    int q_id;

    Question(String question, String[] option, String answer, int q_id)
    {
        this.number_of_option = option.length;
        this.q_id = q_id;
        this.options = option;
        this.question = question;
        this.answer = answer;
    }


}

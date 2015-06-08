package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.Database.DatabaseOperator;

public class DatabaseInterface
{

    int number_of_questions;

    int current_position_of_question = 0;

    ArrayList<Question> list;

    Context context;
    DatabaseOperator vivzHelper;

    DatabaseInterface(Context context)
    {

        list = new ArrayList<Question>();
        vivzHelper = new DatabaseOperator(context);

        this.context = context;

    }

    void set_no_of_questions(int num)
    {
        this.number_of_questions = num;
    }

    // Intialize Vivz helper

    int i = 0;

    long update_response(int q_id, int response)
    {
        return vivzHelper.insertResponse(response, q_id);
    }

    int get_response(int q_id)
    {
        return vivzHelper.getResponseData(q_id);
    }


    void add_question(Question question)
    {


        Log.d("called", "called");
        list.add(question);

        vivzHelper.insertData(question.question, question.answer, question.q_id, question.options);
    }

    String get_question_text(int position)
    {

        String data;
        data = vivzHelper.getData(list.get(position).q_id);
        return data;
    }

    ArrayList<String> get_options(int position)
    {
        ArrayList<String> Data;
        Data = vivzHelper.getOptionData(list.get(position - 1).q_id);


        return Data;

    }

    void increment_current_position()
    {
        this.current_position_of_question++;
        if (this.current_position_of_question >= this.number_of_questions)
        {
            this.current_position_of_question = 0;
        }
    }

    void decrement_current_position()
    {
        this.current_position_of_question--;
        if (this.current_position_of_question < 0)
        {
            this.current_position_of_question = this.number_of_questions - 1;
        }
    }

    void jump_current_position(int current_position_of_question)
    {
        this.current_position_of_question = current_position_of_question;
    }

    int get_correct_answered()
    {
        int correct = 0;
        for (int i = 0; i < this.number_of_questions; i++)
        {

            if (vivzHelper.getResponseData(list.get(i).q_id) == vivzHelper.getQuestionAnswer(list.get(i).q_id))
            {

                correct++;
            }
        }
        return correct;
    }

    int get_wronged_answered()
    {
        int wrong = 0;
        for (int i = 0; i < this.number_of_questions; i++)
        {

            if (!(vivzHelper.getResponseData(list.get(i).q_id) == vivzHelper.getQuestionAnswer(list.get(i).q_id)))
            {

                wrong++;
            }
        }
        return wrong;
    }

    int get_unattempt_answere()
    {
        int unattempt = 0;
        for (int i = 0; i < this.number_of_questions; i++)
        {

            if (vivzHelper.getResponseData(list.get(i).q_id) == -1)
            {
                unattempt++;
            }
        }
        return unattempt;
    }
}

package quizify.ajeet_meena.com.quizify.ActivityQuiz;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.GridViewWithHeaderAndFooter;

public class UiHelper
{

    static private TextView textView;
    static private RadioGroup radioGroup;
    private static GridViewWithHeaderAndFooter gridView;
    static private Quiz quiz;
    private static SingleRowNavigation row;
    private static Context context;
    static private DrawerLayout drawerLayout;
    private static ViewGroup header;
    static NavigationAdapter adapter;
    private static int untouched = R.drawable.untouched;
    private static int unanswered = R.drawable.unanswered;
    private static int answered = R.drawable.tick;
    private static int marked = R.drawable.star;
    private static int wrong = R.drawable.wrong;
    static ToggleButton toggleButton;
    static int[] radioIdArray = {R.id.radio0, R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4};
    static boolean isAnswerSheet = false;
    static TextView textViewSolution;
    static TextView textViewAnswer;


    UiHelper(TextView textView, final RadioGroup radioGroup, final Quiz quiz, NavigationAdapter adapter, GridViewWithHeaderAndFooter gridView, DrawerLayout drawerLayout, Toolbar toolbar, TextView textViewAnswer, TextView textViewSolution, final Context context, boolean isAnswerSheet)
    {
        UiHelper.textView = textView;
        UiHelper.radioGroup = radioGroup;
        UiHelper.quiz = quiz;
        UiHelper.drawerLayout = drawerLayout;
        UiHelper.context = context;
        this.gridView = gridView;
        UiHelper.adapter = adapter;
        this.isAnswerSheet = isAnswerSheet;
        UiHelper.textViewAnswer = textViewAnswer;
        UiHelper.textViewSolution = textViewSolution;
        if (isAnswerSheet)
        {
            for (int k = 0; k < radioGroup.getChildCount(); k++)
            {
                ((RadioButton) radioGroup.getChildAt(k)).setEnabled(false);
            }
        }
        toggleButton = (ToggleButton) toolbar.findViewById(R.id.chkState);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {

                    updateAdapter(quiz.currentPosition, marked);

                } else
                {

                    updateAdapter(quiz.currentPosition, unanswered);

                    check();
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                int i;
                if (checkedId == radioIdArray[0])
                {
                    i = 0;
                } else if (checkedId == radioIdArray[1])
                {
                    i = 1;
                } else if (checkedId == radioIdArray[2])
                {
                    i = 2;
                } else if (checkedId == radioIdArray[3])
                {
                    i = 3;
                } else if (checkedId == radioIdArray[4])
                {
                    i = 4;
                } else
                {
                    i = -1;
                }
                quiz.update_response(quiz.currentPosition, i);
                check();
            }
        });
    }

    static void initializeAdapter()
    {
        for (int i = 0; i < quiz.getNoOfQuestions(); i++)
        {
            if (isAnswerSheet)
            {
                int response = quiz.correctWrongUnAnswered(i);
                if (response == 1) row = new SingleRowNavigation(i, answered);
                else if (response == 2) row = new SingleRowNavigation(i, wrong);
                else row = new SingleRowNavigation(i, unanswered);

            } else row = new SingleRowNavigation(i, untouched);

            adapter.addSingleRow(row);
        }
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (isAnswerSheet)
        {
            header = (ViewGroup) inflater.inflate(R.layout.header_quiz2, gridView, false);
        } else
        {
            header = (ViewGroup) inflater.inflate(R.layout.header_quiz, gridView, false);
        }
        gridView.addHeaderView(header);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    static void change_radio_text(String[] option)
    {
        int i = 0;
        String string;
        int response = quiz.getResponse(quiz.currentPosition);
        if (response == -1)
        {
            clear();
        } else radioGroup.check(radioIdArray[quiz.getResponse(quiz.currentPosition)]);
        while (i < 5)
        {
            string = option[i];
            if (!string.equals("null"))
            {
                (radioGroup.getChildAt(i)).setVisibility(View.VISIBLE);
                ((RadioButton) radioGroup.getChildAt(i)).setText(string);
            } else
            {
                (radioGroup.getChildAt(i)).setVisibility(View.GONE);
            }

            i++;
        }
    }


    public static void initial()
    {
        initializeAdapter();
        quiz.currentPosition = 0;
        textView.setText("Q" + (quiz.currentPosition + 1) + ". " + quiz.getQuestionText(quiz.currentPosition));
        change_radio_text(quiz.getOptions(quiz.currentPosition));
        check();
    }

    void next()
    {
        quiz.incrementCurrentPosition();
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        getTextView().setText("Q" + (quiz.currentPosition + 1) + ". " + quiz.getQuestionText(quiz.currentPosition));
        change_radio_text(quiz.getOptions(quiz.currentPosition));
        check();
    }

    void prev()
    {

        quiz.decrementCurrentPosition();
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        getTextView().setText("Q" + (quiz.currentPosition + 1) + ". " + quiz.getQuestionText(quiz.currentPosition));
        change_radio_text(quiz.getOptions(quiz.currentPosition));
        check();

    }

    static void jump(int position)
    {
        quiz.jumpCurrentPosition(position);
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        getTextView().setText("Q" + (quiz.currentPosition + 1) + ". " + quiz.getQuestionText(quiz.currentPosition));
        change_radio_text(quiz.getOptions(quiz.currentPosition));
        check();
    }

    public static TextView getTextView()
    {
        return textView;
    }

    public static void close()
    {
        UiHelper.drawerLayout.closeDrawer(Gravity.RIGHT);

    }

    public static int check()
    {
        if (isAnswerSheet)
        {
            textViewAnswer.setText(quiz.getAnswer());
            textViewSolution.setText(quiz.getSolution());

            if (quiz.correctWrongUnAnswered(quiz.currentPosition) == 1)
            {
                textView.setBackgroundResource(R.color.green);
            } else if (quiz.correctWrongUnAnswered(quiz.currentPosition) == 2)
            {
                textView.setBackgroundResource(R.color.google_red);
            } else
            {
                textView.setBackgroundResource(R.color.color_second);
            }
        }
        if (adapter.getResId(quiz.currentPosition) == marked)
        {
            toggleButton.setChecked(true);
        } else
        {
            toggleButton.setChecked(false);
            if (radioGroup.getCheckedRadioButtonId() == -1)
            {

                updateAdapter(quiz.currentPosition, unanswered);
                // return 0;
            } else
            {
                updateAdapter(quiz.currentPosition, answered);
            }
        }
        return 0;
    }

    public static void updateAdapter(int position, int resource_id)
    {
        if(!isAnswerSheet)
            adapter.getItem(position).changeResourceId(resource_id);
            adapter.notifyDataSetChanged();
    }

    public static void clear()
    {
        quiz.update_response(quiz.currentPosition, -1);
        radioGroup.clearCheck();
    }

    public int getCurrentQid()
    {
        return quiz.list.get(quiz.currentPosition).q_id;
    }


}

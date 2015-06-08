package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.GridViewWithHeaderAndFooter;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class UiHelper
{

    static private TextView textView;
    static private RadioGroup radioGroup;
    private static GridViewWithHeaderAndFooter gridView;
    static private DatabaseInterface databaseInterface;
    static private int number_of_question = 0;
    private static SingleRowNavigation row;
    private static Context context;
    static private DrawerLayout drawerLayout;
    private static ViewGroup header;
    static NavigationAdapter adapter;
    private static int untouched = R.drawable.untouched;
    private static int unanswered = R.drawable.unanswered;
    private static int answered = R.drawable.tick;
    private static int marked = R.drawable.star;
    static ToggleButton toggleButton;
    static int[] radioIdArray = {R.id.radio0, R.id.radio1, R.id.radio2,
            R.id.radio3, R.id.radio4};

    UiHelper(TextView textView, RadioGroup radioGroup,
             final DatabaseInterface databaseInterface,
             NavigationAdapter adapter, GridViewWithHeaderAndFooter gridView,
             DrawerLayout drawerLayout, Toolbar toolbar, final Context context)
    {
        UiHelper.textView = textView;
        UiHelper.radioGroup = radioGroup;
        UiHelper.databaseInterface = databaseInterface;
        UiHelper.drawerLayout = drawerLayout;
        UiHelper.context = context;
        this.gridView = gridView;
        UiHelper.adapter = adapter;
        this.number_of_question = databaseInterface.number_of_questions;


        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.test, gridView, false);


        toggleButton = (ToggleButton) toolbar.findViewById(R.id.chkState);

        toggleButton
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {

                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked)
                    {

                        if (isChecked)
                        {

                            updateAdapter(
                                    databaseInterface.current_position_of_question,
                                    marked);

                        } else
                        {

                            updateAdapter(
                                    databaseInterface.current_position_of_question,
                                    unanswered);

                            check();


                        }
                    }
                });


        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // TODO Auto-generated method stub
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
                if (i != -1)
                    databaseInterface.update_response(databaseInterface.current_position_of_question, i);


                check();

            }
        });


        // radioGroup.check(radioIdArray[3]);
    }

    static void initializeAdapter()
    {
        for (int i = 0; i < databaseInterface.number_of_questions; i++)
        {
            row = new SingleRowNavigation(i, untouched);
            adapter.add(row);

        }
        setHeader();
        gridView.setAdapter(adapter);
    }

    private static void setHeader()
    {

        gridView.addHeaderView(header);

        // TODO Auto-generated method stub

    }

    static void change_radio_text(ArrayList<String> arrayList)
    {

        // Log.d(A)
        //clear();

        int i = 0;
        String string;

//        radioGroup.check(radioIdArray[databaseInterface.get_response(databaseInterface.current_position_of_question)]);
        while (i < 5)
        {
            string = arrayList.get(i);
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


    static void initial()
    {

        initializeAdapter();


        textView.setText("Q"
                + (databaseInterface.current_position_of_question + 1)
                + ". "
                + databaseInterface
                .get_question_text(databaseInterface.current_position_of_question));


        change_radio_text(databaseInterface
                .get_options(databaseInterface.current_position_of_question + 1));

        check();


        // updateAdapter(databaseInterface.current_position_of_question+1,unanswered);

    }

    void next()
    {

        databaseInterface.increment_current_position();
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.abc_fade_in));
        getTextView()
                .setText(
                        "Q"
                                + (databaseInterface.current_position_of_question + 1)
                                + ". "
                                + databaseInterface
                                .get_question_text(databaseInterface.current_position_of_question));
        change_radio_text(databaseInterface
                .get_options(databaseInterface.current_position_of_question + 1));
        check();

    }

    void prev()
    {

        databaseInterface.decrement_current_position();
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.abc_fade_in));
        getTextView()
                .setText(
                        "Q"
                                + (databaseInterface.current_position_of_question + 1)
                                + ". "
                                + databaseInterface
                                .get_question_text(databaseInterface.current_position_of_question));
        change_radio_text(databaseInterface
                .get_options(databaseInterface.current_position_of_question + 1));
        check();

    }

    static void jump(int position)
    {
        databaseInterface.jump_current_position(position);
        UiHelper.textView.setAnimation(AnimationUtils.loadAnimation(context,
                R.anim.abc_fade_in));
        getTextView()
                .setText(
                        "Q"
                                + (databaseInterface.current_position_of_question + 1)
                                + ". "
                                + databaseInterface
                                .get_question_text(databaseInterface.current_position_of_question));
        change_radio_text(databaseInterface
                .get_options(databaseInterface.current_position_of_question + 1));

        check();


    }

    public static TextView getTextView()
    {
        return textView;
    }

    public static void setTextView(TextView textView)
    {
        UiHelper.textView = textView;
    }

    public static void close()
    {
        UiHelper.drawerLayout.closeDrawer(Gravity.RIGHT);

    }

    public static int check()
    {

        if (adapter.getResId(databaseInterface.current_position_of_question) == marked)
        {
            toggleButton.setChecked(true);
        } else
        {
            toggleButton.setChecked(false);
            if (radioGroup.getCheckedRadioButtonId() == -1)
            {

                updateAdapter(databaseInterface.current_position_of_question,
                        unanswered);
                // return 0;
            } else
            {


                updateAdapter(databaseInterface.current_position_of_question,
                        answered);
            }
        }


        return 0;
    }

    public static void updateAdapter(int position, int resource_id)
    {
        adapter.updateView(position, resource_id);
        // gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    static String timeConversion(int totalSeconds)
    {

        int seconds;

        int minutes;
        int hours;

        hours = totalSeconds / 3600;
        minutes = (totalSeconds % 3600) / 60;
        seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    public static void clear()
    {
        // TODO Auto-generated method stub
        databaseInterface.update_response(databaseInterface.current_position_of_question, -1);
        radioGroup.clearCheck();

    }

    static void submit()
    {
        Message.message(context, "" + databaseInterface.get_correct_answered());
        Intent intent = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityResult.MainActivity.class);

        intent.putExtra("answered_correct", databaseInterface.get_correct_answered());
        intent.putExtra("answered_unattempt", databaseInterface.get_unattempt_answere());
        intent.putExtra("aswered_wrong", databaseInterface.get_wronged_answered());
        intent.putExtra("event_name", MainActivity.event_name);
        intent.putExtra("number_of_question", databaseInterface.number_of_questions);
        //check_currect_question();
        context.startActivity(intent);

        ((MainActivity) context).finish();
    }


}

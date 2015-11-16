package quizify.ajeet_meena.com.quizify.Utilities;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.ActivityMain.UiHelper;
import quizify.ajeet_meena.com.quizify.R;

/**
 * CountDown Timer Used in Activity Main
 */
public class CountDownTimer extends android.os.CountDownTimer
{

    Context context;
    TextView textView;
    String timeRemain = "Internet Unavailable";
    ListView listView;
    int position;

    public CountDownTimer(long startTime, long interval, Context context, ListView listView, int position)
    {
        super(startTime, interval);
        this.context = context;
        this.position = position;
        this.listView = listView;
    }


    @Override

    public void onFinish()
    {
        UiHelper.swipeRefresh(null);
    }


    @Override

    public void onTick(long millisUntilFinished)
    {
        timeRemain = timeConversion((int) (millisUntilFinished / 1000));
        View view = listView.getChildAt(position -
                listView.getFirstVisiblePosition());
        if (view != null)
        {
            textView = (TextView) view.findViewById(R.id.time_remaining);
            if(textView!=null)
            textView.setText(timeRemain);
        }
    }

    public String timeConversion(int totalSeconds)
    {
        int seconds;
        int minutes;
        int hours;
        hours = totalSeconds / 3600;
        minutes = (totalSeconds % 3600) / 60;
        seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
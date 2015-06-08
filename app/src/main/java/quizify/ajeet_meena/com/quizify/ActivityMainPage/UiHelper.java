package quizify.ajeet_meena.com.quizify.ActivityMainPage;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.Date;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.EventCountDownTimer;

/**
 * Created by Ajeet Meena on 20-04-2015.
 */
public class UiHelper
{
    static Context context;
    static EventCountDownTimer[] countDownTimer;
    static Adapter adapter;
    static ListView listView;


    UiHelper(Context context, Adapter adapter, ListView listView)
    {
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;


        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor((context).getResources().getColor(R.color.status_bar));
        }
    }

    final static void InitializeCountDownTimer()
    {
        countDownTimer = new EventCountDownTimer[adapter.getCount()];

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                for (int i = 0; i < adapter.getCount(); i++)
                {
                    long diffSeconds;
                    diffSeconds = Math.abs(adapter.getList().get(i).eventStartTime.getTime() - new Date().getTime());
                    countDownTimer[i] = new EventCountDownTimer(diffSeconds + 4000, 1000, context, listView, i + 1);
                    countDownTimer[i].start();
                }

            }
        }, 2000);

    }

    static void DestroyOldCountDownTimer() throws Throwable
    {
        if (countDownTimer != null)
        {
            for (int i = 0; i < countDownTimer.length; i++)
                countDownTimer[i].cancel();
            countDownTimer = null;
        }

    }

}

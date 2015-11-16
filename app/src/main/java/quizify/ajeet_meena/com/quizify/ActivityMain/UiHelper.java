package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CountDownTimer;
import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;

/**
 * UiHelper Contain static method which are used in other classes
 * Also contain countDownTimer which update time remaining in single row of list view every second
 */
public class UiHelper
{
    static Context context;
    static CountDownTimer[] countDownTimer;
    static OnlineEventAdapter onlineEventAdapter;
    static ListView listView;
    static OnlineHelper onlineHelper;

    UiHelper(Context context, OnlineEventAdapter onlineEventAdapter, ListView listView, OnlineHelper onlineHelper)
    {
        this.onlineHelper = onlineHelper;
        this.context = context;
        this.onlineEventAdapter = onlineEventAdapter;
        this.listView = listView;


        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor((context).getResources().getColor(R.color.status_bar));
        }
    }

    /**
     * Initialize CountDownTimer array with size of list also with initial value
     * Used when swipe down is used
     */
    public final static void InitializeCountDownTimer()
    {
        countDownTimer = new CountDownTimer[onlineEventAdapter.getCount()];
        for (int i = 0; i < onlineEventAdapter.getCount(); i++)
        {
            long diffSeconds;
            diffSeconds = Math.abs(onlineEventAdapter.getList().get(i).getTimeRemain() * 1000);
            countDownTimer[i] = new CountDownTimer(diffSeconds, 1000, context, listView, i + 1);
            countDownTimer[i].start();
        }

    }

    /**
     * Used when new list is rendered when swipe down is used
     *
     * @throws Throwable
     */
    public static void DestroyCountDownTimer() throws Throwable
    {
        if (countDownTimer != null)
        {
            for (int i = 0; i < countDownTimer.length; i++)
                countDownTimer[i].cancel();
            countDownTimer = null;
        }

    }

    /**
     * When swipe refresh is used
     * @param mSwipeRefreshLayout
     */
    public static void swipeRefresh(SwipeRefreshLayout mSwipeRefreshLayout)
    {
        onlineEventAdapter.clearList();
        onlineHelper.getOnlineEvent(onlineEventAdapter, mSwipeRefreshLayout);
        SingleRowEvent.removeInvalidEventPrefs(context);
    }

}

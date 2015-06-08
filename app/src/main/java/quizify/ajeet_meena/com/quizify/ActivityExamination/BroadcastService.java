package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class BroadcastService extends Service
{

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "your_package_name.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer cdt = null;
    int duration;


    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

    }

    @Override
    public void onDestroy()
    {

        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            duration = extras.getInt("event_duration");
        }
        Log.d("duration ", "" + duration);
        cdt = new CountDownTimer(duration * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish()
            {
                UiHelper.submit();

            }
        };

        cdt.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0)
    {

        return null;
    }
}
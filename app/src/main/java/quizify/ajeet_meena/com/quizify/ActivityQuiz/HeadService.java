package quizify.ajeet_meena.com.quizify.ActivityQuiz;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;

/**
 * Foreground service. It creates a window view. The pending broadcastIntent allows to go
 * back to the settings activity.
 */
public class HeadService extends Service
{
    WindowManager windowManager;
    static TextView textView;
    boolean mHasDoubleClicked = false;
    long lastPressTime;
    LinearLayout head;
    CountDownTimer cdt = null;
    int duration = 0;
    int firstId = -1;
    Button button;

    public static final String COUNTDOWN_BR = "countDownTimer";
    Intent broadcastIntent = new Intent(COUNTDOWN_BR);

    @Override
    public void onCreate()
    {
        super.onCreate();

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        head = (LinearLayout) layoutInflater.inflate(R.layout.window, null);
        textView = (TextView) head.findViewById(R.id.timeId);
        button = (Button) head.findViewById(R.id.button1);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        windowManager.addView(head, params);

        try
        {
            head.setOnTouchListener(new View.OnTouchListener()
            {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {

                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:

                            // Get current time in nano seconds.
                            long pressTime = System.currentTimeMillis();

                            //If double click...
                            if (pressTime - lastPressTime <= 300)
                            {

                            } else
                            { // If not double click....
                                mHasDoubleClicked = false;
                            }
                            lastPressTime = pressTime;
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX
                                    + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY
                                    + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(head, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e)
        {
            // TODO: handle exception
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId)
    {
        if(firstId==-1)
        {
            firstId = startId;
        }
        if(firstId==startId)
        {
            Bundle extras = intent.getExtras();
            if (extras != null)
            {
                duration = extras.getInt("event_duration");
            }
            cdt = new CountDownTimer(duration * 1000, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    textView.setText(timeConversion((int) (millisUntilFinished / 1000)));
                }

                @Override
                public void onFinish()
                {
                    sendBroadcast(broadcastIntent);
                    button.setText("Loading...");
                    button.setTextColor(Color.parseColor("#009688"));
                }
            };
            cdt.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        stopForeground(true);
        head.setVisibility(View.GONE);
        cdt.cancel();
        super.onDestroy();
    }

    public void finish(View view)
    {
        sendBroadcast(broadcastIntent);
        button.setText("Loading...");
        button.setTextColor(Color.parseColor("#009688"));
    }

    String timeConversion(int totalSeconds)
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
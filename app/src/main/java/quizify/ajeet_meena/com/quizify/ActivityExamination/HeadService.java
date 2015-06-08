package quizify.ajeet_meena.com.quizify.ActivityExamination;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;

/**
 * Foreground service. It creates a head view. The pending intent allows to go
 * back to the settings activity.
 */
public class HeadService extends Service
{

    private final static int FOREGROUND_ID = 999;
    private WindowManager windowManager;
    static TextView textView;


    boolean mHasDoubleClicked = false;
    long lastPressTime;


    private LinearLayout chatHead;

    //private HeadLayer headLayer;

    @Override
    public IBinder onBind(Intent intent)
    {

        return null;

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        String event_name = intent.getExtras().getString("event_name");
        Intent activityIntent = new Intent(this, quizify.ajeet_meena.com.quizify.ActivityExamination.MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        chatHead = (LinearLayout) layoutInflater.inflate(R.layout.head, null);
        textView = (TextView) chatHead.findViewById(R.id.timeId);


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= 16)
        {
            Notification notification = new Notification.Builder(this)
                    .setContentTitle(event_name)
                    .setContentText("An event is online")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();
            startForeground(FOREGROUND_ID, notification);
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        windowManager.addView(chatHead, params);

        try
        {
            chatHead.setOnTouchListener(new View.OnTouchListener()
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
                            windowManager.updateViewLayout(chatHead, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e)
        {
            // TODO: handle exception
        }


        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy()
    {

        stopForeground(true);
        chatHead.setVisibility(View.GONE);
        super.onDestroy();
    }



}
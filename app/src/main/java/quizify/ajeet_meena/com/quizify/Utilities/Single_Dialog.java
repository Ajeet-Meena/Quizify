package quizify.ajeet_meena.com.quizify.Utilities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import quizify.ajeet_meena.com.quizify.ActivityMainPage.OnAlarmReceive;
import quizify.ajeet_meena.com.quizify.ActivityMainPage.SingleRowEvent;
import quizify.ajeet_meena.com.quizify.ActivityMainPage.ViewHolder;
import quizify.ajeet_meena.com.quizify.R;


public class Single_Dialog extends DialogFragment implements
        View.OnClickListener, ListView.OnItemClickListener
{

    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST = "list";
    private static final String KEY_NEGATIVEBUTTON = "negativeButton";
    private static final String KEY_POSITIVEBUTTON = "positiveButton";

    private TextView dialogTitle;
    private ListView dialogList;
    private Button dialogNegativeButton;
    private Button dialogPositiveButton;


    Context context;

    static SharedPreferences.Editor editor;
    static ViewHolder holder;
    static int state;

    static SingleRowEvent singleRowEvent;

    public static Single_Dialog newInstance(Context context, String title,
                                            SingleRowEvent singleRowEvent, String negativeButton,
                                            String positiveButton, ViewHolder holder, int state)
    {

        Single_Dialog f = new Single_Dialog(context);
        Single_Dialog.holder = holder;
        Single_Dialog.state = state;
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putStringArrayList(KEY_LIST, singleRowEvent.getArrayList());
        args.putString(KEY_NEGATIVEBUTTON, negativeButton);
        args.putString(KEY_POSITIVEBUTTON, positiveButton);
        f.setArguments(args);
        editor = context.getSharedPreferences("prefs", context.MODE_PRIVATE).edit();

        Single_Dialog.singleRowEvent = singleRowEvent;
        return f;
    }

    public Single_Dialog(Context context)
    {
        this.context = context;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder;


        builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog_single, null);

        dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);
        dialogList = (ListView) dialogView.findViewById(R.id.dialogContent);
        dialogNegativeButton = (Button) dialogView
                .findViewById(R.id.dialogButtonNegative);
        dialogPositiveButton = (Button) dialogView
                .findViewById(R.id.dialogButtonPositive);

        dialogTitle.setText(getArguments().getString(KEY_TITLE));
        dialogNegativeButton.setText(getArguments().getString(
                KEY_NEGATIVEBUTTON));
        dialogPositiveButton.setText(getArguments().getString(
                KEY_POSITIVEBUTTON));

        dialogList.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getArguments()
                .getStringArrayList(KEY_LIST)));
        dialogList.setOnItemClickListener(this);

        dialogNegativeButton.setOnClickListener(this);
        dialogPositiveButton.setOnClickListener(this);


        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;

        return dialog;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.dialogButtonNegative)
        {
            dismiss();
        }
        if (v.getId() == R.id.dialogButtonPositive)
        {
            SharedPreferences prefs = context.getSharedPreferences("prefs", context.MODE_PRIVATE);

            if (prefs.getInt("event", -1) == singleRowEvent.event_id || prefs.getInt("event", -1) == -1)
            {
                if (state == 0)
                {
                    holder.setButtonParticipateState(1);
                    editor.putInt("event", singleRowEvent.event_id);
                    editor.commit();
                    //setupAlarm(singleRowEvent.getTimeRemain());
                    setupAlarm(2);
                    Handler mHandler = new Handler();
                    Context appContext = context.getApplicationContext();
                    mHandler.post(new DisplayNotification(appContext, getArguments().getStringArrayList(KEY_LIST).get(0).replace("EVENT NAME: ", ""), getArguments().getStringArrayList(KEY_LIST).get(1).replace("START TIME: ", ""), false));

                } else
                {
                    holder.setButtonParticipateState(0);
                    editor.putInt("event", -1);
                    editor.commit();
                    cancelAlarm();
                    Handler mHandler = new Handler();
                    Context appContext = context.getApplicationContext();
                    mHandler.post(new DisplayNotification(appContext, "Speed And Time", "4:00 AM - 5:00 AM", true));

                }
                dismiss();
            } else
            {
                Message.message(context, "Only a single event can be participated at a time");
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {

    }

    private void setupAlarm(int seconds)
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnAlarmReceive.class);
        intent.putExtra("event_id", singleRowEvent.event_id);
        intent.putExtra("event_name", singleRowEvent.Event_name);
        intent.putExtra("event_duration", singleRowEvent.duration);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm()
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnAlarmReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Message.message(context, "Declined");
        alarmManager.cancel(pendingIntent);
    }

}
package quizify.ajeet_meena.com.quizify.ActivityMainPage;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;

public class ViewHolder
{

    TextView event_name;

    TextView TimeRemaining;

    Button buttonParticipate;


    ViewHolder(View v)
    {

        this.event_name = (TextView) v.findViewById(R.id.textView2);

        this.TimeRemaining = (TextView) v.findViewById(R.id.time_remaining);

        this.buttonParticipate = (Button) v.findViewById(R.id.participate);

    }

    public void set_text(String Event_name, String time_remain)
    {
        this.event_name.setText(Event_name);
        this.TimeRemaining.setText(time_remain);

    }

    public void setButtonParticipateState(int state)
    {
        if (state == 0)
        {
            this.buttonParticipate.setText("PARTICIPATE");
            this.buttonParticipate.setTextColor(Color.parseColor("#0070A8"));
        } else if (state == 1)
        {
            this.buttonParticipate.setText("DECLINE");
            this.buttonParticipate.setTextColor(Color.parseColor("#ff5252"));
        }


    }


}
package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;

/**
 * Created by Ajeet Meena on 18-06-2015.
 */
public class ViewHolder {

    TextView event_name;
    TextView TimeRemaining;
    Button buttonParticipate;
    TextView participants;

    ViewHolder(View v) {
        this.event_name = (TextView) v.findViewById(R.id.textView2);
        this.TimeRemaining = (TextView) v.findViewById(R.id.time_remaining);
        this.buttonParticipate = (Button) v.findViewById(R.id.participate);
        this.participants = (TextView) v.findViewById(R.id.participants);
    }

    public void setHolder(SingleRowEvent singleRowEvent) {
        this.event_name.setText(singleRowEvent.eventName);
        //this.TimeRemaining.setText(singleRowEvent.getTimeRemain());
        this.participants.setText(Integer.toString(singleRowEvent.noOfParticipant));
    }

    public void setButtonParticipateState(boolean state) {
        if (!state) {
            this.buttonParticipate.setText("Participate");
            this.buttonParticipate.setTextColor(Color.parseColor("#0070A8"));
        } else if (state) {
            this.buttonParticipate.setText("Decline");
            this.buttonParticipate.setTextColor(Color.parseColor("#ff5722"));
        }
    }

    public void loadingButtonState() {
        this.buttonParticipate.setText("Loading...");
        this.buttonParticipate.setTextColor(Color.parseColor("#009688"));
    }

    public void setParticipant(SingleRowEvent singleRowEvent) {
        this.participants.setText(Integer.toString(singleRowEvent.noOfParticipant));
    }
}

package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CircularImageView;
import quizify.ajeet_meena.com.quizify.Utilities.CommonParam;

/**
 * Created by Ajeet Meena on 24-06-2015.
 */
public class FragmentOne extends Fragment {

    TextView textViewScore;
    TextView textViewCorrect;
    TextView textViewWrong;
    TextView textViewUnattempt;
    TextView textViewRank;
    CircularImageView imageView;
    OnlineHelper onlineHelper;
    LinearLayout linearLayout;
    int eventId;
    int rank = 0;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            rank = intent.getExtras().getInt("rank", 0);
            textViewRank.setText("Rank: "+rank);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle extras) {
        View v = inflater.inflate(R.layout.profile_info, container, false);
        initializeViews(v);
        setView();

        eventId = getArguments().getInt("eventId",-1);
        if(eventId!=-1) {
            onlineHelper = new OnlineHelper(getActivity());
            onlineHelper.getDashBoard(eventId, textViewRank, textViewCorrect, textViewScore, textViewUnattempt, textViewWrong, imageView);
        }

        return v;
    }

    private void setView() {
        textViewRank.setText("Rank: Undeclared");
        linearLayout.setVisibility(View.GONE);
    }


    void initializeViews(View v) {
        textViewRank = (TextView) v.findViewById(R.id.user_name);
        textViewScore = (TextView) v.findViewById(R.id.score);
        textViewCorrect = (TextView) v.findViewById(R.id.correct_attemted);
        textViewUnattempt = (TextView) v.findViewById(R.id.unattempted);
        textViewWrong = (TextView) v.findViewById(R.id.wrong_attempted);
        imageView = (CircularImageView) v.findViewById(R.id.profile_pic);
        linearLayout = (LinearLayout) v.findViewById(R.id.layout_event_played);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(CommonParam.BRAODCAST_RANK));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}

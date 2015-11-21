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
import android.widget.ListView;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CommonParam;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

/**
 * Created by Ajeet Meena on 24-06-2015.
 */
public class FragmentTwo extends Fragment {

    ListView listView;
    CustomAdapter customAdapter;
    OnlineHelper onlineHelper;
    TextView textView;
    int eventId;
    String from;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (eventId != -1) {
                if (intent.hasExtra("event_id") && intent.getExtras().getInt("event_id") == eventId) {
                    Message.message(getActivity(), "Result are Declared!");
                    textView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    onlineHelper.getScoreBoard(eventId, listView, customAdapter);
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle extras) {
        View v = inflater.inflate(R.layout.fragment_score_board, container, false);
        initializeViews(v);
        setViews();
        onlineHelper = new OnlineHelper(getActivity());
        customAdapter = new CustomAdapter(getActivity());
        eventId = getArguments().getInt("eventId", -1);
        from = getArguments().getString("from");
        if (from != null &&  (from.equals("notification")) ) {
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            onlineHelper.getScoreBoard(eventId, listView, customAdapter);
        }
        return v;
    }

    private void setViews() {
        listView.setVisibility(View.GONE);
    }

    private void initializeViews(View view) {
        listView = (ListView) view.findViewById(R.id.list_view);
        textView = (TextView) view.findViewById(R.id.message);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(CommonParam.BROADCAST_SCORE_BOARD));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}

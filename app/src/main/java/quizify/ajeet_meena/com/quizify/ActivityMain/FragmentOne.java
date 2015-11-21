package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;

/**
 * Created by Ajeet Meena on 26-06-2015.
 */
public class FragmentOne extends Fragment{

    ListView list_view;
    SwipeRefreshLayout mSwipeRefreshLayout;
    OnlineHelper onlineHelper;
    UiHelper uiHelper;
    OnlineEventAdapter onlineEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_online_event,container,false);
        initializeViews(view);
        onlineEventAdapter = new OnlineEventAdapter(getActivity(), getFragmentManager());
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_event, list_view, false);
        list_view.addHeaderView(header);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.swipe_dow_refresh, list_view, false);
        list_view.addFooterView(footer);
        list_view.setAdapter(onlineEventAdapter);
        onlineHelper = new OnlineHelper(getActivity());
        onlineHelper.getOnlineEvent(onlineEventAdapter, mSwipeRefreshLayout);
        uiHelper = new UiHelper(getActivity(), onlineEventAdapter, list_view, onlineHelper);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                UiHelper.swipeRefresh(mSwipeRefreshLayout);
            }

        });
        return view;
    }

    private void initializeViews(View view)
    {
        list_view = (ListView) view.findViewById(R.id.online_quiz_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
    }
}

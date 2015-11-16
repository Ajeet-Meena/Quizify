package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;

public class FragmentTwo extends Fragment
{
    OnlineHelper onlineHelper;
    ListView listView;
    RecentActivityAdapter recentActivityAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recent_activity,container,false);
        initializeView(view);
        recentActivityAdapter = new RecentActivityAdapter(getActivity());
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_event, listView, false);
        listView.addHeaderView(header);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.swipe_dow_refresh, listView, false);
        listView.addFooterView(footer);
        listView.setAdapter(recentActivityAdapter);
        onlineHelper = new OnlineHelper(getActivity());
        onlineHelper.getRecentActivity(recentActivityAdapter, swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                onlineHelper.getRecentActivity(recentActivityAdapter, swipeRefreshLayout);
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position!=0 && position!=recentActivityAdapter.getCount()+1)
                {
                    Intent intent = new Intent(getActivity(), quizify.ajeet_meena.com.quizify.ActivityResult.MainActivity.class);
                    intent.putExtra("event_name",recentActivityAdapter.getItem(position-1).eventName);
                    intent.putExtra("event_id",recentActivityAdapter.getItem(position-1).eventId);
                   if(recentActivityAdapter.getItem(position-1).isDeclared)
                    intent.putExtra("from","notification");
                    else
                   intent.putExtra("from","nothing");
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void initializeView(View view)
    {
        listView = (ListView) view.findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
    }
}

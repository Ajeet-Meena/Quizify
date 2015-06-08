package quizify.ajeet_meena.com.quizify.ActivityMainPage;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import quizify.ajeet_meena.com.quizify.GCM.GCMhelper;
import quizify.ajeet_meena.com.quizify.GCM.QuickstartPreferences;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CircularImageView;
import quizify.ajeet_meena.com.quizify.Utilities.Message;


public class MainActivity extends ActionBarActivity
{
    static Adapter adapter;
    public ListView list_view;
    static OnlineHelper onlineHelper;
    UiHelper uiHelper;
    SwipeRefreshLayout mSwipeRefreshLayout = null;

    Toolbar toolbar;
    TextView userName;
    CircularImageView profilePic;

    TextView eventPlayed;
    TextView score;
    TextView correctAttempted;
    TextView wrongAttempted;
    TextView unattempted;
    GCMhelper gcMhelper;

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_quiz_list);
        gcMhelper = new GCMhelper(this);
       // gcMhelper.registerGCM();
        mManager = getSupportFragmentManager();
        initialize();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quizify");
        mManager = getSupportFragmentManager();
        adapter = new Adapter(this, mManager);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.openDrawer(Gravity.LEFT);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.gap, list_view, false);
        list_view.addHeaderView(header);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.swipe_dow_refresh, list_view, false);
        list_view.addFooterView(footer);

        onlineHelper = new OnlineHelper(this, adapter, list_view);
        onlineHelper.loadContent();
        onlineHelper.loadProfileInfo(userName, profilePic, eventPlayed, score, correctAttempted, wrongAttempted, unattempted);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        refresh();

        uiHelper = new UiHelper(this, adapter, list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(gcMhelper.getmRegistrationBroadcastReceiver(),
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gcMhelper.getmRegistrationBroadcastReceiver());
        super.onPause();
    }


    private void initialize()
    {

        FacebookSdk.sdkInitialize(getApplicationContext());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list_view = (ListView) findViewById(R.id.online_quiz_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        userName = (TextView) findViewById(R.id.user_name);
        profilePic = (CircularImageView) findViewById(R.id.profile_pic);
        eventPlayed = (TextView) findViewById(R.id.event_played);
        score = (TextView) findViewById(R.id.score);
        correctAttempted = (TextView) findViewById(R.id.correct_attemted);
        wrongAttempted = (TextView) findViewById(R.id.wrong_attempted);
        unattempted = (TextView) findViewById(R.id.unattempted);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {

            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Message.message(this, "Press BACK again to exit");

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void refresh()
    {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                refreshProgmatically();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    public static void refreshProgmatically()
    {
        adapter.clearList();
        onlineHelper.loadContent();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}

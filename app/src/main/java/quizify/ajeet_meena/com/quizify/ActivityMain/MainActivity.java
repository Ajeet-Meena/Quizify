package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.CircularImageView;
import quizify.ajeet_meena.com.quizify.Utilities.GCM.GCMhelper;
import quizify.ajeet_meena.com.quizify.Utilities.GCM.QuickstartPreferences;
import quizify.ajeet_meena.com.quizify.Utilities.Message;
import quizify.ajeet_meena.com.quizify.Utilities.SlidingTabLayout;
import quizify.ajeet_meena.com.quizify.Utilities.Standard_Dialog;

/**
 * Activity is responsible for showing online event in list view and for user info
 */
public class MainActivity extends ActionBarActivity
{
    DrawerLayout mDrawerLayout;
    FragmentManager mManager;
    Toolbar toolbar;
    CircularImageView profilePic;
    TextView userName;
    TextView eventPlayed;
    TextView score;
    TextView correctAttempted;
    TextView wrongAttempted;
    TextView unattempted;
    GCMhelper gcMhelper;
    OnlineHelper onlineHelper;

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout slidingTabLayout;
    CharSequence Titles[] = {"Online Event", "Recent Activity"};
    int Numboftabs = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.abc_fade_in, R.anim.seamless_out_transition);

        setContentView(R.layout.activity_main);
        initialize();
        onlineHelper = new OnlineHelper(this);
        gcMhelper = new GCMhelper(this);
        gcMhelper.initializeRegisterReceiver();
        mManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quizify");
        mDrawerLayout.openDrawer(Gravity.END);
        onlineHelper.getProfileInfo(userName, profilePic, eventPlayed, score, correctAttempted, wrongAttempted, unattempted);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        viewPager.setAdapter(viewPagerAdapter);
        slidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the slidingTabLayout Space Evenly in Available width
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)
            {
                return getResources().getColor(R.color.google_white);
            }
        });
        slidingTabLayout.setViewPager(viewPager);

        loadAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainpagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout)
        {
            //Check if user is currently logged in
            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null)
            {
                //log out
                gcMhelper.unregister();
                LoginManager.getInstance().logOut();
            }
        } else if (id == R.id.subscribe)
        {
            onlineHelper.SubscribeTopics(gcMhelper);
        } else if (id == R.id.invite)
        {
            try
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Quizify ! It's awesome a app");
                String sAux = "\nI just played this awesome app which let me play online quizes with other online players and friends. It helps me prepare for civil examinations and college placements \n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=quizify.ajeet_meena.com.quizify \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose One"));
            } catch (Exception e)
            { //e.toString();
            }

        } else if (id == R.id.about)
        {
            Standard_Dialog standard_dialog = Standard_Dialog.newInstance("About Quizify", "Quizify is an online examination portal which let you play online quizzes with other online players or friends. It's a total free platform(No Advertisement!) and best for those who are preparing for CAT, IES, SSC, BANK, College Placements and other government sector examination. Best Wishes, Quizify Developer.", "BUG REPORT/SUGGEST", "OK");
            standard_dialog.show(mManager, "dialog");
        } else if (id == R.id.view_profile)
        {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
            {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else
            {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        boolean isTimeAuto;
        if (Build.VERSION.SDK_INT < 17)
        {
            isTimeAuto = (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1);
        } else
        {
            isTimeAuto = (android.provider.Settings.Global.getInt(getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0) == 1);
        }
        if (!isTimeAuto)
        {
            Standard_Dialog standard_dialog = Standard_Dialog.newInstance("WARNING!", "We recommend you to use Network Provider Time for best experience. Wrong date and time might cause ambiguous experience.", "CANCEL", "OPEN SETTINGS");
            standard_dialog.show(mManager, "dialog");
        }
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(gcMhelper.getmRegistrationBroadcastReceiver(), new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gcMhelper.getmRegistrationBroadcastReceiver());
        super.onPause();
    }

    public void refresh(View view)
    {
        UiHelper.swipeRefresh(null);
    }


    private void initialize()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        userName = (TextView) findViewById(R.id.user_name);
        profilePic = (CircularImageView) findViewById(R.id.profile_pic);
        eventPlayed = (TextView) findViewById(R.id.event_played);
        score = (TextView) findViewById(R.id.score);
        correctAttempted = (TextView) findViewById(R.id.correct_attemted);
        wrongAttempted = (TextView) findViewById(R.id.wrong_attempted);
        unattempted = (TextView) findViewById(R.id.unattempted);
        viewPager = (ViewPager) findViewById(R.id.pager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);

    }

    private void loadAnimation()
    {
        userName.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom));
        profilePic.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
    }

    /**
     * Double Press to Exit
     */
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
        Message.message(this, "Press Back Again to Exit");

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

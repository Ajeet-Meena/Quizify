package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import quizify.ajeet_meena.com.quizify.ActivityQuiz.Quiz;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.Message;
import quizify.ajeet_meena.com.quizify.Utilities.SlidingTabLayout;


public class MainActivity extends ActionBarActivity
{
    Toolbar toolbar;
    FragmentManager mManager;
    ViewPager pager;
    ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout slidingTabLayout;
    CharSequence Titles[] = {"DASH BOARD", "SCORE BOARD"};
    int Numboftabs = 2;
    ShareDialog shareDialog;
    Quiz quiz;
    int eventId;
    String eventName = "Quizify";
    String from;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        extras = getIntent().getExtras();
        if (getIntent().hasExtra("from") && extras != null)
        {
            from = extras.getString("from");
            if (from.equals("quiz"))
            {
                quiz = (Quiz) extras.getSerializable("quiz");
                eventId = quiz.getEvent_id();
                eventName = quiz.getEvent_name();
            }
            else
            {
                eventId = extras.getInt("event_id");
                eventName = extras.getString("event_name");
            }
        }
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, eventId, from);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(viewPagerAdapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the slidingTabLayout Space Evenly in Available width
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)
            {
                return getResources().getColor(R.color.google_white);
            }
        });
        slidingTabLayout.setViewPager(pager);

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.status_bar));
        }

        mManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Results");
        toolbar.setSubtitle(Html.fromHtml("<font color='#cccccc'>" + eventName + "</font>"));
        mManager = getSupportFragmentManager();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, quizify.ajeet_meena.com.quizify.ActivityMain.MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(!from.equals("quiz"))
        {
            MenuItem item = menu.findItem(R.id.answer_sheet);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.share:
            {

                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder().setContentTitle("I played this awesome app named Quizify").setContentDescription("I played an Event Aptitude with other online players and scored 10 points on Quizify. Please Like there page").setContentUrl(Uri.parse("https://www.facebook.com/pages/Quizify/727631154012057")).build();
                    shareDialog.show(linkContent);
                }
                return true;
            }
            case R.id.answer_sheet:
            {
                Intent intent = new Intent(this, quizify.ajeet_meena.com.quizify.ActivityQuiz.MainActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                return true;
            }
            case R.id.rate_app:
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try
                {
                    intent.setData(Uri.parse("market://details?id=com.ajeet_meena.material"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e)
                {
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?[Id]"));
                    startActivity(intent);
                    Message.message(this, "Could not open Android market, please install the market app");
                }
                return true;
            }
            case android.R.id.home:
            {
                Intent intent = new Intent(this, quizify.ajeet_meena.com.quizify.ActivityMain.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}



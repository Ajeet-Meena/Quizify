package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.GridViewWithHeaderAndFooter;

public class MainActivity extends ActionBarActivity
{
    SharedPreferences sharedpreferences;
    Editor editor;
    public static final String MyPREFERENCES = "EventPrefs";
    OnlineHelper onlineHelper;
    DatabaseInterface databaseInterface;
    NavigationAdapter adapter;
    GridViewWithHeaderAndFooter gridView;
    DrawerLayout drawerLayout;
    TextView QuestionTextView;
    RadioGroup radioGroup;
    Toolbar toolbar;
    UiHelper uiInterface;
    static String event_name;
    int event_id;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            event_id = extras.getInt("event_id");
            event_name = extras.getString("event_name");
            duration = extras.getInt("event_duration");
        }
        databaseInterface = new DatabaseInterface(
                this);
        onlineHelper = new OnlineHelper(this, databaseInterface, event_id);
        onlineHelper.loadEventData();
        sharedpreferences = getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putBoolean("check", true);
        editor.commit();
        if (sharedpreferences.getBoolean("check",
                false))
        {
            Intent intent = new Intent(this, BroadcastService.class);
            intent.putExtra("event_duration", duration);

            startService(intent);
            startHeadService();
            registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(event_name);
        }
        intialize();
        adapter = new NavigationAdapter(this);
        uiInterface = new UiHelper(QuestionTextView, radioGroup,
                databaseInterface, adapter, gridView, drawerLayout, toolbar,
                this);
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    public void next(View view)
    {
        uiInterface.next();
    }

    public void prev(View view)
    {
        uiInterface.prev();
    }

    public void intialize()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView1);
        QuestionTextView = (TextView) findViewById(R.id.textView1);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

    }

    public void clear_selections(View view)
    {
        UiHelper.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
        {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else
        {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver br = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            updateGUI(intent);
        }
    };

    private void updateGUI(Intent intent)
    {
        if (intent.getExtras() != null)
        {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            HeadService.textView.setText("" + UiHelper.timeConversion((int) millisUntilFinished / 1000));
        }

    }

    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(br);
        stopService(new Intent(this, BroadcastService.class));
        stopHeadService();
        editor.putBoolean("check", false);
        editor.commit();
        super.onDestroy();
    }

    private void startHeadService()
    {
        Intent i = new Intent(this, HeadService.class);
        i.putExtra("event_name", event_name);
        startService(i);
    }

    private void stopHeadService()
    {
        stopService(new Intent(this, HeadService.class));
    }


}

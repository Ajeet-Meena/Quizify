package quizify.ajeet_meena.com.quizify.ActivityQuiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.GridViewWithHeaderAndFooter;

public class MainActivity extends ActionBarActivity
{
    OnlineHelper onlineHelper;
    Quiz quiz;
    NavigationAdapter adapter;
    GridViewWithHeaderAndFooter gridView;
    DrawerLayout drawerLayout;
    TextView QuestionTextView;
    Button button;
    RadioGroup radioGroup;
    Toolbar toolbar;
    UiHelper uiHelper;
    ToggleButton toggleButton;
    LinearLayout linearLayout;
    TextView textViewSolution;
    TextView textViewAnswer;

    boolean isAnswerSheet = false;

    private BroadcastReceiver br = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            onlineHelper.postResult(quiz);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initializeViews();
        Bundle extras = getIntent().getExtras();
        isAnswerSheet = getIntent().hasExtra("from");
        adapter = new NavigationAdapter(this);
        onlineHelper = new OnlineHelper(this);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
        }

        if (isAnswerSheet)
        {
            quiz = (Quiz) extras.getSerializable("quiz");
            quiz.setNumberOfQuestions();
            toggleButton.setVisibility(View.GONE);
            toolbar.setSubtitle(Html.fromHtml("<font color='#cccccc'>" + "Answer Sheet" + "</font>"));
            button.setText("Report this Question");
            button.setTextColor(getResources().getColor(R.color.google_red));
            uiHelper = new UiHelper(QuestionTextView, radioGroup, quiz, adapter, gridView, drawerLayout, toolbar, textViewAnswer, textViewSolution, this,true);
            UiHelper.initial();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            linearLayout.setVisibility(View.VISIBLE);
        } else
        {

            quiz = new Quiz();
            if (extras != null)
            {
                quiz.setEvent_name(extras.getString("event_name"));
                quiz.setEvent_id(extras.getInt("event_id"));
                quiz.setDuration(extras.getInt("event_duration"));
            }
            onlineHelper.getQuizData(quiz);
            uiHelper = new UiHelper(QuestionTextView, radioGroup, quiz, adapter, gridView, drawerLayout, toolbar,textViewAnswer,textViewSolution, this, false);
        }
        getSupportActionBar().setTitle(quiz.getEvent_name());
        drawerLayout.openDrawer(Gravity.END);
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.status_bar));
        }
    }

    public void next(View view)
    {
        uiHelper.next();
    }

    public void prev(View view)
    {
        uiHelper.prev();
    }

    public void initializeViews()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView1);
        QuestionTextView = (TextView) findViewById(R.id.textView1);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toggleButton = (ToggleButton) toolbar.findViewById(R.id.chkState);
        button = (Button) findViewById(R.id.clear);
        linearLayout = (LinearLayout) findViewById(R.id.layout_solution);
        textViewSolution = (TextView) findViewById(R.id.solution);
        textViewAnswer = (TextView) findViewById(R.id.answer);
    }

    public void clear_selections(View view)
    {
        if(isAnswerSheet)
        {
            onlineHelper.reportQuestion(uiHelper.getCurrentQid());
        }
        else
        {
            UiHelper.clear();
        }
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
        int id = item.getItemId();
        if(id == R.id.menu_item)
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
        {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else
        {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
        else if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if(isAnswerSheet)
        {
            super.onBackPressed();
            finish();
        }
        else
        {
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(br, new IntentFilter(HeadService.COUNTDOWN_BR));
    }

    @Override
    public void onDestroy()
    {
        stopHeadService();
        unregisterReceiver(br);
        super.onDestroy();
    }

    private void stopHeadService()
    {
        stopService(new Intent(this, HeadService.class));
    }
}

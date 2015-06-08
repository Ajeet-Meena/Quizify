package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.Message;


public class MainActivity extends ActionBarActivity
{

    TextView textViewScore;
    TextView textViewCorrect;
    TextView textViewWrong;
    TextView textViewUnattempt;
    ImageView imageView;
    ProgressDialog pDialog;
    Profile profile;

    int unattempted;
    int correct;
    int wrong;
    String event_name;
    int number_of_questions;
    OnlineHelper onlineHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        intialize();
        if (extras != null)
        {
            unattempted = extras.getInt("answered_unattempt", -1);
            correct = extras.getInt("answered_correct", -1);
            wrong = extras.getInt("aswered_wrong", -1);
            event_name = extras.getString("event_name");
            number_of_questions = extras.getInt("number_of_question", -1);
        }

        textViewScore.setText(correct + "/" + number_of_questions);
        textViewWrong.setText("" + wrong);
        textViewUnattempt.setText("" + unattempted);
        textViewCorrect.setText("" + correct);
        new LongOperation().execute("");


    }

    void intialize()
    {
        textViewScore = (TextView) findViewById(R.id.score);
        textViewCorrect = (TextView) findViewById(R.id.correct);
        textViewUnattempt = (TextView) findViewById(R.id.unattempted);
        textViewWrong = (TextView) findViewById(R.id.wrong);
        imageView = (ImageView) findViewById(R.id.profile_pic);

    }


    public void participate_more(View view)
    {

        Intent intent = new Intent(this, quizify.ajeet_meena.com.quizify.ActivityMainPage.MainActivity.class);
        startActivity(intent);
    }

    private class LongOperation extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            profile = Profile.getCurrentProfile().getCurrentProfile();

            if (profile != null)
            {

            } else
            {

            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result)
        {
            Message.message(MainActivity.this, profile.getFirstName());

            pDialog.dismiss();
            onlineHelper = new OnlineHelper(MainActivity.this, profile.getFirstName(), profile.getId(), correct);
            onlineHelper.PostContent();

        }

        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching Information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
        }
    }
}



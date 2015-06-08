package quizify.ajeet_meena.com.quizify.ActivityMainPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quizify.ajeet_meena.com.quizify.JSON.JSONParser;
import quizify.ajeet_meena.com.quizify.Utilities.CircularImageView;
import quizify.ajeet_meena.com.quizify.Utilities.Connectivity;
import quizify.ajeet_meena.com.quizify.Utilities.DownloadImageTask;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class OnlineHelper
{

    private ProgressDialog pDialog;
    JSONParser jParser;
    Context context;
    Connectivity connectivity;



    private static String url_all_events = "http://23.229.177.24/quizify/get_all_events.php";
    private static String url_profile_info = "http://23.229.177.24/quizify/get_profile_info.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EVENTS = "events";
    private static final String EVENT_ID = "event_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_START_TIME = "event_start_time";
    private static final String EVENT_DURATION = "event_duration";
    private static final String EVENT_TYPE = "event_type";
    private static final String EVENT_NO_QUESTION = "no_question";
    private static final String EVENT_DIFFICULTY = "difficulty";
    private static final String EVENT_NO_PARTICIPANT = "no_of_participant";
    private static final String EVENT_PLAYED = "event_played";
    private static final String SCORE = "score";
    private static final String CORRECT_ATTEMPTED = "correct_attempted";
    private static final String WRONG_ATTEMPTED = "wrong_attempted";
    private static final String UNATTEMPTED = "unattempted";
    String user_name = "User Name";
    Uri profile_uri;
    TextView userName;
    CircularImageView profilePic;
    JSONArray channels = null;
    Adapter adapter;
    ListView listView;
    SingleRowEvent singleRowEvent;
    TextView eventPlayed;
    TextView score;
    TextView correctAttempted;
    TextView wrongAttempted;
    TextView unattempted;

    String event_played = "0";
    String score_ = "0";
    String correct_attempted = "0";
    String wrong_attempted = "0";
    String un_attempted = "0";


    public OnlineHelper(Context context, Adapter adapter,
                        ListView listView)
    {
        this.adapter = adapter;
        this.listView = listView;
        this.context = context;

        jParser = new JSONParser();
        connectivity = new Connectivity(context);
    }


    public void loadContent()
    {
        if (connectivity.isNetworkAvailable())
        {
            new LoadAllEvents().execute();

        } else
        {
            Message.message(context, "Network Error");
        }
    }

    public void loadProfileInfo(TextView userName, CircularImageView profilePic, TextView eventPlayed, TextView score, TextView correctAttempted, TextView wrongAttempted, TextView unattempted)
    {
        this.userName = userName;
        this.profilePic = profilePic;
        this.eventPlayed = eventPlayed;
        this.score = score;
        this.correctAttempted = correctAttempted;
        this.wrongAttempted = wrongAttempted;
        this.unattempted = unattempted;
        if (connectivity.isNetworkAvailable())
        {
            new LoadProfileInfo().execute();

        } else
        {
            Message.message(context, "Network Error");
        }
    }

    public void processFinish()
    {
        listView.setAdapter(adapter);
        try
        {
            UiHelper.DestroyOldCountDownTimer();
        } catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        UiHelper.InitializeCountDownTimer();
    }

    class LoadAllEvents extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Getting online events....");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param)
        {
            // TODO Auto-generated method stub

            List<NameValuePair> params1 = new ArrayList<>();

            JSONObject json;

            json = jParser.makeHttpRequest(url_all_events, "GET", params1);

            // TODO Auto-generated catch block

            if (json != null)
            {
                Log.d("All Products: ", json.toString());
                try
                {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1)
                    {
                        channels = json.getJSONArray(TAG_EVENTS);
                        for (int i = 0; i < channels.length(); i++)
                        {
                            JSONObject c = channels.getJSONObject(i);
                            int id = Integer.parseInt(c.getString(EVENT_ID));
                            String name = c.getString(EVENT_NAME);
                            String start_time = c.getString(EVENT_START_TIME);
                            int duration = Integer.parseInt(c
                                    .getString(EVENT_DURATION));
                            int event_type = Integer.parseInt(c
                                    .getString(EVENT_TYPE));
                            int no_question = Integer.parseInt(c
                                    .getString(EVENT_NO_QUESTION));
                            int difficulty = Integer.parseInt(c
                                    .getString(EVENT_DIFFICULTY));
                            int no_participant = Integer.parseInt(c
                                    .getString(EVENT_NO_PARTICIPANT));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date dateStart = dateFormat.parse(start_time);
                            //Long d = new Date().getTime()+ new Date(20000).getTime();
                            singleRowEvent = new SingleRowEvent(name, dateStart, no_participant, id, difficulty, no_question, duration, event_type);
                            if (singleRowEvent.checkValidEvent())
                            {
                                Log.d("is valid","Valid event");
                                adapter.add_singleRow(singleRowEvent);
                            }
                        }
                    } else
                    {

                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                return "0";
            }
            return null;
        }

        protected void onPostExecute(String result)
        {

            if (result == "0")
                Message.message(context, "Connection TimeOut");

            pDialog.dismiss();
            processFinish();

        }
    }

    private class LoadProfileInfo extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... params)
        {
            String user_id = null;
            Profile profile = Profile.getCurrentProfile().getCurrentProfile();
            if (profile != null)
            {
                user_name = profile.getName();
                profile_uri = profile.getProfilePictureUri(128, 128);
                user_id = profile.getId();
            }
            List<NameValuePair> user_id_param = new ArrayList<>();
            user_id_param.add(new BasicNameValuePair("user_id", user_id));
            JSONObject jsonObject = jParser.makeHttpRequest(url_profile_info, "GET", user_id_param);
            try
            {
                //Log.d("PROFILE_INFO", jsonObject.toString());
                if (jsonObject.getInt(TAG_SUCCESS) == 1)
                {
                    JSONArray profile_info = jsonObject
                            .getJSONArray("profile_info");
                    JSONObject object = profile_info.getJSONObject(0);
                    event_played = object.getString(EVENT_PLAYED);
                    score_ = object.getString(SCORE);
                    correct_attempted = object.getString(CORRECT_ATTEMPTED);
                    wrong_attempted = object.getString(WRONG_ATTEMPTED);
                    un_attempted = object.getString(UNATTEMPTED);
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result)
        {
            userName.setText(user_name);
            eventPlayed.setText(event_played);
            score.setText(score_);
            correctAttempted.setText(correct_attempted);
            wrongAttempted.setText(wrong_attempted);
            unattempted.setText(un_attempted);
            new DownloadImageTask(profilePic).execute(profile_uri.toString());

        }

        @Override
        protected void onPreExecute()
        {


        }

    }


}
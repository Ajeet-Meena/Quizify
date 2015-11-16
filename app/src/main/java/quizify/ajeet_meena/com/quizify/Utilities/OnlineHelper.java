package quizify.ajeet_meena.com.quizify.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.Locale;
import java.util.Map;

import quizify.ajeet_meena.com.quizify.ActivityMain.OnlineEventAdapter;
import quizify.ajeet_meena.com.quizify.ActivityMain.RecentActivityAdapter;
import quizify.ajeet_meena.com.quizify.ActivityMain.SingleRowEvent;
import quizify.ajeet_meena.com.quizify.ActivityMain.SingleRowRecentActivity;
import quizify.ajeet_meena.com.quizify.ActivityMain.UiHelper;
import quizify.ajeet_meena.com.quizify.ActivityMain.ViewHolder;
import quizify.ajeet_meena.com.quizify.ActivityQuiz.HeadService;
import quizify.ajeet_meena.com.quizify.ActivityQuiz.Question;
import quizify.ajeet_meena.com.quizify.ActivityQuiz.Quiz;
import quizify.ajeet_meena.com.quizify.ActivityResult.MainActivity;
import quizify.ajeet_meena.com.quizify.ActivityResult.SingleRowScore;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.GCM.GCMhelper;
import quizify.ajeet_meena.com.quizify.Utilities.JSON.JSONParser;

/**
 * Online Helper for LoginCheck and to post user info
 */
public class OnlineHelper {
    ProgressDialog pDialog;
    JSONParser jParser;
    Context context;
    Connectivity connectivity;

    public OnlineHelper(Context context) {
        this.context = context;
        jParser = new JSONParser();
        connectivity = new Connectivity(context);
    }

    public void createUser(String user_id, String user_name, String first_name, String last_name, String email, String profile_pic_url) {
        context.getSharedPreferences("prefs", context.MODE_PRIVATE).edit().putString("user_id", user_id).commit();
        if (connectivity.isNetworkAvailable()) {
            new CreateUser(user_id, user_name, first_name, last_name, email, profile_pic_url).execute();
        } else {
            Message.message(context, "Network error");
        }
    }

    public void loginCheck() {
        if (connectivity.isNetworkAvailable()) {
            LoginSplash loginSplash = new LoginSplash();
            loginSplash.execute();
        } else {
            Message.message(context, "Network error");
        }

    }

    public void getOnlineEvent(OnlineEventAdapter onlineEventAdapter, SwipeRefreshLayout mSwipeRefreshLayout) {
        if (connectivity.isNetworkAvailable()) {
            new GetOnlineEvents(onlineEventAdapter,mSwipeRefreshLayout).execute();

        } else {
            Message.message(context, "Network Error");
        }
    }

    public void getProfileInfo(TextView userName, CircularImageView profilePic, TextView eventPlayed, TextView score, TextView correctAttempted, TextView wrongAttempted, TextView unattempted) {

        if (connectivity.isNetworkAvailable()) {
            new GetProfileInfo(userName, profilePic, eventPlayed, score, correctAttempted, wrongAttempted, unattempted).execute();

        } else {
            Message.message(context, "Network Error");
        }
    }

    public void incDecParticipant(SingleRowEvent singleRowEvent, ViewHolder holder, String what_to_do, String from, int event_id) {
        if (connectivity.isNetworkAvailable()) {
            if (from.equals("in-app"))
                new IncDecParticipant(singleRowEvent, holder, what_to_do).execute();
            else
                new IncDecParticipant(what_to_do, event_id).execute();
        } else {
            Message.message(context, "Network Error");
        }
    }

    public void sendTokenToServer(String token) {
        if (connectivity.isNetworkAvailable()) {
            new SendRegistrationToken().execute(token);
        } else {
            Message.message(context, "You may not receive Push Notification");
            Message.message(context, "Login Again!");
        }
    }

    public void SubscribeTopics(GCMhelper gcMhelper) {
        if (connectivity.isNetworkAvailable()) {
            new SubscribeTopics(gcMhelper).execute();
        } else {
            Message.message(context, "This Operation Require Internet Connection!");
        }
    }

    public void getQuizData(Quiz quiz) {
        if (connectivity.isNetworkAvailable()) {
            new GetQuizData(quiz).execute();
        } else {
            Message.message(context, "Network Error");
        }
    }

    public void postResult(Quiz quiz) {
        if (connectivity.isNetworkAvailable()) {
            new PostResult(quiz).execute();
        } else {
            Message.message(context, "Network Error");
        }
    }

    public void getScoreBoard(int event_id, ListView listView, quizify.ajeet_meena.com.quizify.ActivityResult.CustomAdapter customAdapter) {
        if (connectivity.isNetworkAvailable()) {
            new GetScoreBoard(event_id, listView, customAdapter).execute();
        } else
            Message.message(context, "An Error Occurred!");
    }

    public void updateResult(Quiz quiz) {
        if (connectivity.isNetworkAvailable()) {
            new UpdateProfile(quiz).execute();
        } else {
            Message.message(context, "Network Error");
        }
    }

    public void getDashBoard(int eventId, TextView textViewRank, TextView textViewCorrect, TextView textViewScore, TextView textViewUnattempt, TextView textViewWrong, CircularImageView imageView) {
        if (connectivity.isNetworkAvailable()) {
            new GetDashBoard(eventId,textViewRank,textViewCorrect,textViewScore,textViewUnattempt,textViewWrong,imageView).execute();
        } else {
            Message.message(context, "Network error");
        }
    }

    public void getRecentActivity(RecentActivityAdapter recentActivityAdapter, SwipeRefreshLayout swipeRefreshLayout)
    {
        if(connectivity.isNetworkAvailable())
        {
            new GetRecentActivity(recentActivityAdapter,swipeRefreshLayout).execute();
        }
        else
        {
            Message.message(context, "Network error");
        }
    }

    public void reportQuestion(int q_id)
    {
        if(connectivity.isNetworkAvailable())
        {
            new ReportQuestion(q_id).execute();
        }
        else
        {
            Message.message(context,"Network Error");
        }
    }

    public void reportBugSuggest(String message)
    {
        if(connectivity.isNetworkAvailable())
        {
               new SuggestBugReport().execute(message);
        }
        else
        {
            Message.message(context,"Network Error");
        }
    }

    class CreateUser extends AsyncTask<String, String, Boolean> {
        String user_id, user_name, first_name, last_name, email, profile_pic_url;

        public CreateUser(String user_id, String user_name, String first_name, String last_name, String email, String profile_pic_url) {
            this.user_id = user_id;
            this.user_name = user_name;
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.profile_pic_url = profile_pic_url;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Gathering up required information....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... param) {
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            params1.add(new BasicNameValuePair(CommonParam.USER_ID, user_id));
            params1.add(new BasicNameValuePair(CommonParam.USER_NAME, user_name));
            params1.add(new BasicNameValuePair(CommonParam.FIRST_NAME, first_name));
            params1.add(new BasicNameValuePair(CommonParam.LAST_NAME, last_name));
            params1.add(new BasicNameValuePair(CommonParam.EMAIL, email));
            params1.add(new BasicNameValuePair(CommonParam.PROFILE_PIC_URL, profile_pic_url));
            json = jParser.makeHttpRequest(CommonParam.url_create_user, "POST", params1);

            if (json != null) {
                return true;
            } else {
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {

            if (result == false)
                Message.message(context, "Network Error");

            pDialog.dismiss();
            new GCMhelper(context).register();
        }
    }

    class LoginSplash extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            if (Profile.getCurrentProfile().getCurrentProfile() != null)
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                Intent intent = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityMain.MainActivity.class);
                context.startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityLogin.MainActivity.class);
                context.startActivity(intent);
            }

            ((Activity) context).finish();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;
        }

    }

    class SendRegistrationToken extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String token = params[0];
            String userId = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "0");
            if (userId.equals("0"))
                return false;
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            params1.add(new BasicNameValuePair(CommonParam.TOKEN, token));
            params1.add(new BasicNameValuePair(CommonParam.USER_ID, userId));
            json = jParser.makeHttpRequest(CommonParam.url_register, "POST", params1);
            try {
                int success = json.getInt(CommonParam.TAG_SUCCESS);
                if (success == 1)
                    return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean)
                Message.message(context, "Please Login Again");
        }
    }

    class SubscribeTopics extends AsyncTask<Void, Void, String[]> {
        GCMhelper gcMhelper;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        boolean[] checkedItems;

        SubscribeTopics(GCMhelper gcMhelper) {
            this.gcMhelper = gcMhelper;
            sharedPreferences = context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String[] allTags;
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json1;
            json1 = jParser.makeHttpRequest(CommonParam.url_all_tags, "GET", params1);
            if (json1 != null) {
                try {
                    int success = json1.getInt(CommonParam.TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray Tags = json1.getJSONArray("tag");
                        allTags = new String[Tags.length()];
                        for (int i = 0; i < Tags.length(); i++) {
                            JSONObject tag = Tags.getJSONObject(i);
                            String tag_name = tag.getString("tag_name");
                            allTags[i] = tag_name;
                            Log.d("tag_name : ", allTags[i]);
                        }
                        return allTags;
                    } else {
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final String[] allTags) {
            super.onPostExecute(allTags);
            pDialog.dismiss();
            if (allTags != null) {

                Dialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Subscribed Notification");
                int j = allTags.length;
                checkedItems = new boolean[j];
                for (int i = 0; (i < j); i++) {
                    checkedItems[i] = sharedPreferences.getBoolean(allTags[i], false);
                }

                builder.setMultiChoiceItems(allTags, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    editor.putBoolean(allTags[selectedItemId], true);
                                } else {
                                    editor.putBoolean(allTags[selectedItemId], false);
                                }
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                editor.commit();
                                ArrayList<String> subscriptionList = new ArrayList<>();
                                ArrayList<String> unSubscriptionList = new ArrayList<>();
                                Map<String, ?> allEntries = sharedPreferences.getAll();
                                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                                    if (entry.getValue().equals(true)) {
                                        subscriptionList.add(entry.getKey().toString());
                                    } else {
                                        unSubscriptionList.add(entry.getKey().toString());
                                    }
                                }
                                gcMhelper.subscribeTopic(subscriptionList, unSubscriptionList);
                                Message.message(context,"Settings may take few seconds to apply.");
                            }
                        });
                dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
                dialog.show();
            } else
                Message.message(context, "Network Error");
        }
    }

    class GetOnlineEvents extends AsyncTask<String, String, String> {
        OnlineEventAdapter onlineEventAdapter;
        SwipeRefreshLayout mSwipeRefreshLayout;

        public GetOnlineEvents(OnlineEventAdapter onlineEventAdapter, SwipeRefreshLayout mSwipeRefreshLayout) {
            this.onlineEventAdapter = onlineEventAdapter;
            this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if(mSwipeRefreshLayout!=null)
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... param) {

            List<NameValuePair> params1 = new ArrayList<>();

            JSONObject json;

            json = jParser.makeHttpRequest(CommonParam.url_all_events, "GET", params1);


            if (json != null) {
                Log.d("All Products: ", json.toString());
                try {
                    int success = json.getInt(CommonParam.TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray channels = json.getJSONArray(CommonParam.TAG_EVENTS);
                        for (int i = 0; i < channels.length(); i++) {
                            JSONObject c = channels.getJSONObject(i);
                            int id = Integer.parseInt(c.getString(CommonParam.EVENT_ID));
                            String name = c.getString(CommonParam.EVENT_NAME);
                            String start_time = c.getString(CommonParam.EVENT_START_TIME);
                            int duration = Integer.parseInt(c
                                    .getString(CommonParam.EVENT_DURATION));
                            int event_type = Integer.parseInt(c
                                    .getString(CommonParam.EVENT_TYPE));
                            int no_question = Integer.parseInt(c
                                    .getString(CommonParam.EVENT_NO_QUESTION));
                            int difficulty = Integer.parseInt(c
                                    .getString(CommonParam.EVENT_DIFFICULTY));
                            int no_participant = Integer.parseInt(c
                                    .getString(CommonParam.EVENT_NO_PARTICIPANT));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            Date dateStart = dateFormat.parse(start_time);
                            //Long d = new Date().getTime()+ new Date(20000).getTime();
                            SingleRowEvent singleRowEvent = new SingleRowEvent(name, dateStart, no_participant, id, difficulty, no_question, duration, event_type);
                            if (SingleRowEvent.isValidEvent(singleRowEvent.eventStartTime)) {
                                Log.d("is valid", "Valid event");
                                onlineEventAdapter.add_singleRow(singleRowEvent);
                            }
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                return "0";
            }

            return null;
        }

        protected void onPostExecute(String result) {

            if (result == "0") {
                Message.message(context, "Network Error");
            }
            else {
                try {
                    UiHelper.DestroyCountDownTimer();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                UiHelper.InitializeCountDownTimer();
            }
            if(mSwipeRefreshLayout!=null)
            mSwipeRefreshLayout.setRefreshing(false);
            onlineEventAdapter.notifyDataSetChanged();
        }
    }

    class GetProfileInfo extends AsyncTask<String, Void, String[]> {
        TextView eventPlayed;
        TextView score;
        TextView correctAttempted;
        TextView wrongAttempted;
        TextView userName;
        CircularImageView profilePic;
        TextView unattempted;


        public GetProfileInfo(TextView userName, CircularImageView profilePic, TextView eventPlayed, TextView score, TextView correctAttempted, TextView wrongAttempted, TextView unattempted) {
            this.userName = userName;
            this.profilePic = profilePic;
            this.eventPlayed = eventPlayed;
            this.score = score;
            this.correctAttempted = correctAttempted;
            this.wrongAttempted = wrongAttempted;
            this.unattempted = unattempted;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String user_name = "User Name";
            String profile_uri = null;
            String event_played;
            String score;
            String correct_attempted;
            String wrong_attempted;
            String unattempted;
            String user_id = null;
            Profile profile = Profile.getCurrentProfile().getCurrentProfile();
            if (profile != null) {
                user_name = profile.getName();
                profile_uri = profile.getProfilePictureUri(128, 128).toString();
                user_id = profile.getId();
            }

                    String[] result = {user_name, event_played, score, correct_attempted, wrong_attempted, unattempted, profile_uri};
                    return result;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                userName.setText(result[0]);
                eventPlayed.setText(result[1]);
                score.setText(result[2]);
                correctAttempted.setText(result[3]);
                wrongAttempted.setText(result[4]);
                unattempted.setText(result[5]);
                new DownloadImageTask(profilePic).execute(result[6]);
            } else {
                Message.message(context, "Network Error");
            }

        }
    }

    class IncDecParticipant extends AsyncTask<String, Void, Boolean> {

        SingleRowEvent singleRowEvent;
        ViewHolder viewHolder;
        String what_to_do;
        DisplayNotification displayNotification;
        String from;
        int event_id;

        public IncDecParticipant(SingleRowEvent singleRowEvent, ViewHolder viewHolder, String what_to_do) {
            this.singleRowEvent = singleRowEvent;
            this.viewHolder = viewHolder;
            this.what_to_do = what_to_do;
            this.event_id = singleRowEvent.event_id;
            displayNotification = new DisplayNotification(context);
            this.from = "in-app";
        }

        public IncDecParticipant(String what_to_do, int event_id) {
            this.what_to_do = what_to_do;
            this.from = "out-app";
            this.event_id = event_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (from.equals("in-app"))
                viewHolder.loadingButtonState();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            params1.add(new BasicNameValuePair(CommonParam.WHAT_TO_DO, what_to_do));
            params1.add(new BasicNameValuePair(CommonParam.EVENT_ID, Integer.toString(event_id)));
            json = jParser.makeHttpRequest(CommonParam.url_inc_dec_participant, "POST", params1);
            try {
                int success = json.getInt(CommonParam.TAG_SUCCESS);
                if (success == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (from.equals("in-app")) {
                if (what_to_do.equals("increment")) {
                    viewHolder.setButtonParticipateState(true);
                    singleRowEvent.setButton_state(true);
                    singleRowEvent.setPrefs(context);
                    singleRowEvent.noOfParticipant++;
                    viewHolder.setParticipant(singleRowEvent);
                    displayNotification.makeDeclineNotification(singleRowEvent.event_id, singleRowEvent.eventName, singleRowEvent.eventStartTime.toString(), singleRowEvent.duration, singleRowEvent.event_id);
                    displayNotification.setupAlarm(singleRowEvent.getTimeRemain(), singleRowEvent.event_id, singleRowEvent.eventName, singleRowEvent.duration);
                } else {
                    viewHolder.setButtonParticipateState(false);
                    singleRowEvent.setButton_state(false);
                    singleRowEvent.removePref(singleRowEvent.event_id, context);
                    singleRowEvent.noOfParticipant--;
                    viewHolder.setParticipant(singleRowEvent);
                    displayNotification.clearNotification(singleRowEvent.event_id);
                    displayNotification.cancelAlarm(singleRowEvent.event_id);
                }
            }
            if (!result) {
                Message.message(context, "An Error Occurred");
            }
        }
    }

    class GetQuizData extends AsyncTask<String, String, String> {
        int number_of_question = 0;
        Quiz quiz;

        public GetQuizData(Quiz quiz) {
            this.quiz = quiz;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            Question question;
            List<NameValuePair> params1 = new ArrayList<>();
            params1.add(new BasicNameValuePair(CommonParam.EVENT_ID, "" + quiz.getEvent_id()));
            JSONObject json;
            json = jParser.makeHttpRequest(CommonParam.url_event_data, "GET", params1);
            if (json != null) {
                try {
                    int success = json.getInt(CommonParam.TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray jsonArray;
                        jsonArray = json.getJSONArray(CommonParam.TAG_PRODUCTS);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            int q_id = Integer.parseInt(c.getString(CommonParam.QUESTION_ID));
                            String answer = c.getString(CommonParam.ANSWER);
                            String question_text = c.getString(CommonParam.QUESTION_TEXT);
                            String solution = c.getString(CommonParam.SOLUTION);
                            String[] option = new String[5];
                            for (int j = 0; j < 5; j++) {
                                option[j] = c.getString("option" + (j + 1));
                            }
                            question = new Question(question_text, option, answer, q_id,solution);
                            quiz.addQuestion(question);
                            number_of_question++;
                        }
                    } else {
                        return "0";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return "0";

            }
            return null;
        }

        protected void onPostExecute(String result) {

            if (result == "0")
                Message.message(context, "Network Error");
            quiz.setNumberOfQuestions();
            Intent i = new Intent(context, HeadService.class);
            i.putExtra("event_duration", quiz.getDuration());
            context.startService(i);
            quizify.ajeet_meena.com.quizify.ActivityQuiz.UiHelper.initial();
        }
    }

    class PostResult extends AsyncTask<Void, Void, Boolean> {
        Quiz quiz;

        PostResult(Quiz quiz) {
            this.quiz = quiz;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            String user_id = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "null");
            if (user_id.equals("null")) {
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    user_id = profile.getId();
                    context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putString("user_id", user_id).apply();
                }
            }
            params1.add(new BasicNameValuePair(CommonParam.USER_ID, user_id));
            params1.add(new BasicNameValuePair(CommonParam.EVENT_ID, Integer.toString(quiz.getEvent_id())));
            params1.add(new BasicNameValuePair(CommonParam.SCORE, Integer.toString(quiz.getCorrectAnswered())));
            params1.add(new BasicNameValuePair(CommonParam.CORRECT_ATTEMPTED, Integer.toString(quiz.getCorrectAnswered())));
            params1.add(new BasicNameValuePair(CommonParam.WRONG_ATTEMPTED, Integer.toString(quiz.getWrongAnswer())));
            params1.add(new BasicNameValuePair(CommonParam.UNATTEMPTED, Integer.toString(quiz.getUnAttempted())));
            params1.add(new BasicNameValuePair(CommonParam.EVENT_NAME, quiz.getEvent_name()));
            json = jParser.makeHttpRequest(CommonParam.url_post_result, "POST", params1);
            try {
                int success = json.getInt(CommonParam.TAG_SUCCESS);
                if (success == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                updateResult(quiz);

            } else {
                //TODO: If result is not post successfully, i.e start event activity
                Message.message(context, "An Error Occurred!");
            }
        }
    }

    class GetScoreBoard extends AsyncTask<Void, Void, Boolean> {
        int event_id;
        ListView listView;
        quizify.ajeet_meena.com.quizify.ActivityResult.CustomAdapter customAdapter;

        GetScoreBoard(int event_id, ListView listView, quizify.ajeet_meena.com.quizify.ActivityResult.CustomAdapter customAdapter) {
            this.event_id = event_id;
            this.listView = listView;
            this.customAdapter = customAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            List<NameValuePair> params1 = new ArrayList<>();
            params1.add(new BasicNameValuePair(CommonParam.EVENT_ID, Integer.toString(event_id)));
            JSONObject json;

            String my_id = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "null");
            if (my_id.equals("null")) {
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    my_id = profile.getId();
                }
            }
            json = jParser.makeHttpRequest(CommonParam.url_get_score_board, "GET", params1);
            if (json != null) {
                try {
                    int rank = 1;
                    JSONArray jsonArray = json.getJSONArray("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user_id = jsonObject.getString(CommonParam.USER_ID);
                        if(my_id.equals(user_id))
                        {
                            Intent intent = new Intent(CommonParam.BRAODCAST_RANK);
                            intent.putExtra("rank",rank);
                            context.sendBroadcast(intent);
                        }
                        String score = jsonObject.getString(CommonParam.SCORE);
                        String user_name = jsonObject.getString(CommonParam.USER_NAME);
                        customAdapter.addSingleRow(new SingleRowScore(Integer.parseInt(score), rank++, user_id, user_name));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                listView.setAdapter(customAdapter);
            } else {
                Message.message(context, "An Error Occurred!");
            }
        }
    }

    class UpdateProfile extends AsyncTask<Void, Void, Boolean> {
        Quiz quiz;

        UpdateProfile(Quiz quiz) {
            this.quiz = quiz;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            String user_id = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "null");
            if (user_id.equals("null")) {
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    user_id = profile.getId();
                    context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putString("user_id", user_id).apply();
                }
            }
            params1.add(new BasicNameValuePair(CommonParam.USER_ID, user_id));
            params1.add(new BasicNameValuePair(CommonParam.SCORE, Integer.toString(quiz.getCorrectAnswered())));
            params1.add(new BasicNameValuePair(CommonParam.CORRECT_ATTEMPTED, Integer.toString(quiz.getCorrectAnswered())));
            params1.add(new BasicNameValuePair(CommonParam.WRONG_ATTEMPTED, Integer.toString(quiz.getWrongAnswer())));
            params1.add(new BasicNameValuePair(CommonParam.UNATTEMPTED, Integer.toString(quiz.getUnAttempted())));
            json = jParser.makeHttpRequest(CommonParam.url_update_profile, "POST", params1);
            try {
                int success = json.getInt(CommonParam.TAG_SUCCESS);
                if (success == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("quiz", quiz);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("from", "quiz");
                intent.putExtras(bundle);
                context.startActivity(intent);
            } else {
                Message.message(context, "Failed to Update Profile");
            }
            context.stopService(new Intent(context, HeadService.class));
            ((Activity) context).finish();
        }
    }

    class GetDashBoard extends AsyncTask<Void, Void, Boolean> {
        int eventId;
        String correctAttempted;
        String wrongAttempted;
        String unAttempted;
        String score;
        TextView textViewRank;
        TextView textViewCorrect;
        TextView textViewScore;
        TextView textViewUnattempt;
        TextView textViewWrong;
        CircularImageView imageView;


        public GetDashBoard(int eventId, TextView textViewRank, TextView textViewCorrect, TextView textViewScore, TextView textViewUnattempt, TextView textViewWrong, CircularImageView imageView) {

            this.eventId = eventId;
            this.textViewRank = textViewRank;
            this.textViewCorrect = textViewCorrect;
            this.textViewScore = textViewScore;
            this.textViewUnattempt = textViewUnattempt;
            this.textViewWrong = textViewWrong;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            String user_id = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "null");
            if (user_id.equals("null")) {
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    user_id = profile.getId();
                }
            }
            if (user_id == null) {
                return false;
            } else {
                new DownloadImageTask(imageView).execute("https://graph.facebook.com/"+user_id+"/picture?height=128&width=128");
                params1.add(new BasicNameValuePair(CommonParam.USER_ID, user_id));
                params1.add(new BasicNameValuePair(CommonParam.EVENT_ID, Integer.toString(eventId)));
                json = jParser.makeHttpRequest(CommonParam.url_get_result, "GET", params1);
                if (json != null) {

                    try {
                        Log.d("getResult",json.toString());
                        JSONArray jsonArray = json
                                .getJSONArray("result");
                        JSONObject object = jsonArray.getJSONObject(0);
                        score = object.getString(CommonParam.SCORE);
                        correctAttempted = object.getString(CommonParam.CORRECT_ATTEMPTED);
                        wrongAttempted = object.getString(CommonParam.WRONG_ATTEMPTED);
                        unAttempted = object.getString(CommonParam.UNATTEMPTED);
                        return true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                textViewWrong.setText(wrongAttempted);
                textViewCorrect.setText(correctAttempted);
                textViewScore.setText(score);
                textViewUnattempt.setText(unAttempted);
            } else {
                Message.message(context, "An Error Occurred!");
            }
        }
    }

    class GetRecentActivity extends AsyncTask<Void, Void, Boolean> {


        RecentActivityAdapter recentActivityAdapter;
        SwipeRefreshLayout swipeRefreshLayout;
        public GetRecentActivity(RecentActivityAdapter recentActivityAdapter, SwipeRefreshLayout swipeRefreshLayout)
        {
            this.recentActivityAdapter = recentActivityAdapter;
            this.swipeRefreshLayout = swipeRefreshLayout;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
            recentActivityAdapter.clearList();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;
            String user_id = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("user_id", "null");
            if (user_id.equals("null")) {
                Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                if (profile != null) {
                    user_id = profile.getId();
                }
            }
            if (user_id == null) {
                return false;
            } else {

                params1.add(new BasicNameValuePair(CommonParam.USER_ID, user_id));
                json = jParser.makeHttpRequest(CommonParam.url_get_recent_activity, "GET", params1);
                if (json != null) {

                    try {
                        Log.d("getResult",json.toString());
                        JSONArray jsonArray = json
                                .getJSONArray(CommonParam.TAG_ACTIVITY);
                        int length = jsonArray.length() ;
                        for(int i=0;i<length;i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String eventName = object.getString(CommonParam.EVENT_NAME);
                            int eventId = Integer.parseInt(object.getString(CommonParam.EVENT_ID));
                            String string = object.getString(CommonParam.IS_DECLARED);
                            boolean isDeclared = string.equals("1");
                            String date = object.getString(CommonParam.TIME_UPDATED);
                            recentActivityAdapter.addSingleRow(new SingleRowRecentActivity(isDeclared,date,eventName,eventId));
                        }
                        return true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {

            } else {
                //Message.message(context, "An Error Occurred!");
            }
            recentActivityAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class ReportQuestion extends AsyncTask<Void, Void, Boolean>
    {

        private final int q_id;

        public ReportQuestion(int q_id)
        {
            this.q_id = q_id;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            List<NameValuePair> params1 = new ArrayList<>();
            params1.add(new BasicNameValuePair(CommonParam.QUESTION_ID, Integer.toString(q_id)));
            jParser.makeHttpRequest(CommonParam.url_report_this_question, "POST", params1);
            return null;
        }
    }

    class SuggestBugReport extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... params)
        {
            List<NameValuePair> params1 = new ArrayList<>();
            params1.add(new BasicNameValuePair(CommonParam.MESSAGE, params[0]));
            jParser.makeHttpRequest(CommonParam.url_report_bug_suggest, "POST", params1);
            return null;
        }
    }
}



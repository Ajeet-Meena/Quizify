package quizify.ajeet_meena.com.quizify.ActivityLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.facebook.Profile;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quizify.ajeet_meena.com.quizify.ActivityMainPage.MainActivity;
import quizify.ajeet_meena.com.quizify.Database.DatabaseOperator;
import quizify.ajeet_meena.com.quizify.GCM.GCMhelper;
import quizify.ajeet_meena.com.quizify.JSON.JSONParser;
import quizify.ajeet_meena.com.quizify.Utilities.Connectivity;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class OnlineHelper
{

    private ProgressDialog pDialog;
    JSONParser jParser;
    Context context;
    Connectivity connectivity;


    private static String url_create_user = "http://23.229.177.24/quizify/create_user.php";

    private static final String TAG_SUCCESS = "success";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String PROFILE_PIC_URL = "profile_pic_url";
    String user_id;
    String user_name;
    String first_name;
    String last_name;
    String email;
    String profile_pic_url;
    GCMhelper gcMhelper;


    OnlineHelper(Context context)
    {

        this.context = context;
        jParser = new JSONParser();
        connectivity = new Connectivity(context);



    }

    public void createUser(String id, String user_name, String first_name, String last_name, String email, String profile_pic_url)
    {
        this.user_id = id;
        context.getSharedPreferences("prefs",context.MODE_PRIVATE).edit().putString("user_id", id).commit();
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.profile_pic_url = profile_pic_url;
        gcMhelper = new GCMhelper(context);
        gcMhelper.registerGCM();
        if (connectivity.isNetworkAvailable())
        {

            CreateUser createUser = new CreateUser();
            createUser.execute();
        } else
        {
            Message.message(context, "Network Unavailable");
        }
    }

    public void loginCheck()
    {
        if (connectivity.isNetworkAvailable())
        {
            LoginCheck loginCheck = new LoginCheck();
            loginCheck.execute();
        } else
        {
            Message.message(context, "Network Unavailable");
        }

    }


    class CreateUser extends AsyncTask<String, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Gathering up required information....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... param)
        {
            // TODO Auto-generated method stub

            List<NameValuePair> params1 = new ArrayList<>();
            JSONObject json;

            params1.add(new BasicNameValuePair(USER_ID, user_id));
            params1.add(new BasicNameValuePair(USER_NAME, user_name));
            params1.add(new BasicNameValuePair(FIRST_NAME, first_name));
            params1.add(new BasicNameValuePair(LAST_NAME, last_name));
            params1.add(new BasicNameValuePair(EMAIL, email));
            params1.add(new BasicNameValuePair(PROFILE_PIC_URL, profile_pic_url));
            json = jParser.makeHttpRequest(url_create_user, "POST", params1);

            // TODO Auto-generated catch block
            if (json != null)
            {

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
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

    private class LoginCheck extends AsyncTask<String, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(String... params)
        {
            new DatabaseOperator(context);
            if (Profile.getCurrentProfile().getCurrentProfile() != null)
            {
                return true;

            } else
            {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if (result == true)
            {
                Intent intent = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityMainPage.MainActivity.class);
                ((Activity) context).finish();
                context.startActivity(intent);
            }
            pDialog.dismiss();

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }


}



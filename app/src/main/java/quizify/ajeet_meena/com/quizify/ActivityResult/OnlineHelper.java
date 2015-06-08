package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quizify.ajeet_meena.com.quizify.JSON.JSONParser;
import quizify.ajeet_meena.com.quizify.Utilities.Connectivity;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

/**
 * Created by Ajeet Meena on 26-04-2015.
 */
public class OnlineHelper
{

    String first_name, user_id;
    int score;
    Context context;
    Connectivity connectivity;
    ProgressDialog pDialog;
    JSONParser jParser;
    private static final String USER_ID = "user_id";
    private static final String SCORE = "score";
    private static final String FIRST_NAME = "first_name";
    private static String url_create_user = "http://23.229.177.24/quizify/insert_result.php";
    private static final String TAG_SUCCESS = "success";


    OnlineHelper(Context context, String first_name, String user_id, int score)
    {
        this.first_name = first_name;
        this.user_id = user_id;
        this.score = score;
        this.context = context;
        connectivity = new Connectivity(context);
        pDialog = new ProgressDialog(context);
        jParser = new JSONParser();

    }

    void PostContent()
    {
        if (connectivity.isNetworkAvailable())
            (new PostResult()).execute();
        else
            Message.message(context, "Internet unavailable");

    }

    void GetContent()
    {

    }

    class PostResult extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Channels....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... param)
        {
            // TODO Auto-generated method stub

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            JSONObject json = null;


            params1.add(new BasicNameValuePair(USER_ID, user_id));
            params1.add(new BasicNameValuePair(SCORE, "" + score));
            params1.add(new BasicNameValuePair(FIRST_NAME, first_name));


            json = jParser.makeHttpRequest(url_create_user, "POST", params1);

            // TODO Auto-generated catch block

            if (json != null)
            {
                Log.d("All Products: ", json.toString());

                try
                {

                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1)
                    {


                    } else
                    {

                        Log.d("asdf", "No Products");
                    }
                } catch (JSONException e)
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


        }
    }


}

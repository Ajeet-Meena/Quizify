package quizify.ajeet_meena.com.quizify.ActivityExamination;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quizify.ajeet_meena.com.quizify.JSON.JSONParser;
import quizify.ajeet_meena.com.quizify.Utilities.Connectivity;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class OnlineHelper
{

    private ProgressDialog pDialog;
    JSONParser jParser;
    Context context;
    Connectivity connectivity;

    JSONArray jsonArray = null;


    private static String url_event_data = "http://23.229.177.24/quizify/get_event_data.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "questions";
    private static final String QUESTION_ID = "q_id";
    private static final String QUESTION_TEXT = "question_text";
    private static final String ANSWER = "answer";
    private static final String NO_OF_OPTION = "no_of_option";
    private static final String EVENT_ID = "event_id";
    int event_id;
    int number_of_question = 0;
    DatabaseInterface databaseInterface;

    Question question;

    OnlineHelper(Context context, DatabaseInterface databaseInterface, int event_id)
    {
        this.event_id = event_id;
        this.context = context;
        jParser = new JSONParser();
        connectivity = new Connectivity(context);
        this.databaseInterface = databaseInterface;

    }


    public void loadEventData()
    {
        if (connectivity.isNetworkAvailable())
        {

            GetEventData result_from_server = new GetEventData();
            result_from_server.execute();
        } else
        {

            Message.message(context, "Network Unavailable");
        }
    }


    class GetEventData extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {

            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please Wait....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... param)
        {
            // TODO Auto-generated method stub


            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair(EVENT_ID, "" + event_id));

            JSONObject json = null;


            json = jParser.makeHttpRequest(url_event_data, "GET", params1);

            // TODO Auto-generated catch block

            if (json != null)
            {


                try
                {

                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1)
                    {
                        jsonArray = json.getJSONArray(TAG_PRODUCTS);


                        for (int i = 0; i < jsonArray.length(); i++)
                        {

                            JSONObject c = jsonArray.getJSONObject(i);

                            int q_id = Integer.parseInt(c.getString(QUESTION_ID));
                            String answer = c.getString(ANSWER);
                            String question_text = c.getString(QUESTION_TEXT);

                            String[] option = new String[5];
                            for (int j = 0; j < 5; j++)
                            {
                                option[j] = c.getString("option" + (j + 1));
                            }

                            question = new Question(question_text, option, answer, q_id);
                            databaseInterface.add_question(question);
                            number_of_question++;


                        }
                    } else
                    {


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
            databaseInterface.set_no_of_questions(number_of_question);

            pDialog.dismiss();
            UiHelper.initial();

        }
    }

}

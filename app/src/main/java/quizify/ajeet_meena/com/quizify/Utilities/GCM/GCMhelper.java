/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quizify.ajeet_meena.com.quizify.Utilities.GCM;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;

import quizify.ajeet_meena.com.quizify.ActivityMain.MainActivity;
import quizify.ajeet_meena.com.quizify.R;
import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class GCMhelper
{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";


    private BroadcastReceiver mRegistrationBroadcastReceiver;


    Context context;

    public GCMhelper(Context context)
    {
        this.context = context;

    }


    public void initializeRegisterReceiver()
    {
        mRegistrationBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken)
                {
                    // if success
                    //OnlineHelper onlineHelper = new OnlineHelper()

                } else
                {
                    Message.message(context,"Failed Registration,Please Login Again");
                    // if failed
                }
            }
        };

        checkPlayServices();
    }

    public void subscribeTopic(ArrayList<String> subscriptionList, ArrayList<String> unSubscriptionList)
    {
        String[] strings1 = new String[subscriptionList.size()];
        String[] strings2 = new String[unSubscriptionList.size()];
        for(int i=0;i<subscriptionList.size();i++)
        {
            strings1[i] = subscriptionList.get(i).replaceAll("\\s","");
        }
        for(int i=0;i<unSubscriptionList.size();i++)
        {
            strings2[i] = unSubscriptionList.get(i).replaceAll("\\s","");
        }
        Intent intent = new Intent(context, RegistrationIntentService.class);
        intent.putExtra("subscribe_topic",strings1);
        intent.putExtra("un_subscribe_topic",strings2);
        context.startService(intent);
    }

    public void register()
    {
        new RegisterUnregister().execute("register");
    }

    public void unregister()
    {
        new RegisterUnregister().execute("unregister");
    }


    public BroadcastReceiver getmRegistrationBroadcastReceiver()
    {
        return mRegistrationBroadcastReceiver;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity)context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else
            {
                Log.i(TAG, "This device is not supported.");
                ((Activity)context).finish();
            }
            return false;
        }
        return true;
    }

    class RegisterUnregister extends AsyncTask<String,Void,Boolean>
    {
        ProgressDialog progressDialog;
        String from;

        @Override
        protected Boolean doInBackground(String... params)
        {
            from=params[0];
            if(from=="unregister")
            {
                try
                {
                    GoogleCloudMessaging.getInstance(context).unregister();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
            else
            {
                try
                {
                    GoogleCloudMessaging.getInstance(context).register(context.getResources().getString(R.string.gcm_defaultSenderId));
                } catch (IOException e)
                {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result)
            {
                if(from=="unregister")
                {
                    Intent i = new Intent(context, quizify.ajeet_meena.com.quizify.ActivityLogin.MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ((Activity)context).finish();
                    context.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ((Activity)context).finish();
                    context.startActivity(i);
                }
            }
            else
            {
                Message.message(context,"WARNING! You may not receive notification");
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }


}

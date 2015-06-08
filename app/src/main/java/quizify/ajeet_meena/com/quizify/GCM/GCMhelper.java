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

package quizify.ajeet_meena.com.quizify.GCM;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

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


    public void registerGCM()
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
                } else
                {
                    // if failed
                }
            }
        };


        if (checkPlayServices())
        {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(context, RegistrationIntentService.class);
            context.startService(intent);
        }
    }

    public BroadcastReceiver getmRegistrationBroadcastReceiver()
    {
        return mRegistrationBroadcastReceiver;
    }

    // @Override
    //protected void onResume()
    //{
      //  super.onResume();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
          //      new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    //}

//    @Override
  //  protected void onPause()
    //{
      //  LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        //super.onPause();
    //}

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

}

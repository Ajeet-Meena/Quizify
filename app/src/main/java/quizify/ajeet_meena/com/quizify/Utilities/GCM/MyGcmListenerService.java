/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quizify.ajeet_meena.com.quizify.Utilities.GCM;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import quizify.ajeet_meena.com.quizify.Utilities.CommonParam;
import quizify.ajeet_meena.com.quizify.Utilities.DisplayNotification;

public class MyGcmListenerService extends GcmListenerService
{
    public static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + "check");

        if(data!=null)
        {
            if(from.contains("topics"))
            {
                int event_id = Integer.parseInt(data.getString("event_id"));
                String event_name = data.getString("event_name");
                String event_start_time = data.getString("event_start_time");
                int duration = Integer.parseInt(data.getString("duration"));
                new DisplayNotification(this).makeGCMNotification(event_id, event_name, event_start_time, duration, 0);
            }
            else
            {
                int event_id = Integer.parseInt(data.getString("event_id"));
                String event_name = data.getString("event_name");
                new DisplayNotification(this).makeResultNotification(event_id,event_name,event_id);
                Intent broadcastIntent = new Intent(CommonParam.BROADCAST_SCORE_BOARD);
                broadcastIntent.putExtra("event_id",event_id);
                sendBroadcast(broadcastIntent);
            }
        }
    }
    // [END receive_message]
}

package quizify.ajeet_meena.com.quizify.ActivityLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import quizify.ajeet_meena.com.quizify.R;


public class FragmentThree extends Fragment
{

    private CallbackManager mCallbackManager;
    AccessTokenTracker tracker;
    ProfileTracker profileTracker;
    String first_name;
    String last_name;
    String user_name;
    String user_id;
    String profile_pic_url;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            Profile profile = Profile.getCurrentProfile();
            getProfileInfo(profile);
        }


        @Override
        public void onCancel()
        {

        }

        @Override
        public void onError(FacebookException e)
        {

        }
    };

    public void getProfileInfo(Profile profile)
    {

        if (profile != null)
        {

            first_name = profile.getFirstName();
            last_name = profile.getLastName();
            user_name = profile.getName();
            user_id = profile.getId();
            profile_pic_url = profile.getProfilePictureUri(128, 128).toString();

        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        tracker = intializeTokenTracker();
        profileTracker = intializeProfileTracker();
        tracker.startTracking();
        profileTracker.startTracking();
    }

    private ProfileTracker intializeProfileTracker()
    {
        return new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile old, Profile newProfile)
            {

                getProfileInfo(newProfile);

            }
        };
    }

    private AccessTokenTracker intializeTokenTracker()
    {
        return new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken old, AccessToken newToken)
            {

                final ProgressDialog pDialog = new ProgressDialog(getActivity());
                GraphRequest request = GraphRequest.newMeRequest(
                        newToken,
                        new GraphRequest.GraphJSONObjectCallback()
                        {

                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response)
                            {
                                pDialog.dismiss();


                                String email = null;

                                try
                                {
                                    email = object.getString("email");

                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                                OnlineHelper onlineHelper = new OnlineHelper(getActivity());
                                onlineHelper.createUser(user_id, user_name, first_name, last_name, email, profile_pic_url);
                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,location");

                request.setParameters(parameters);

                pDialog.setMessage("Fetching Information...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);

                pDialog.show();
                request.executeAsync();

            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        getProfileInfo(profile);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        tracker.stopTracking();
        profileTracker.stopTracking();
    }

}

package quizify.ajeet_meena.com.quizify;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.FacebookSdk;

import quizify.ajeet_meena.com.quizify.Utilities.OnlineHelper;


public class splash extends Activity
{
    OnlineHelper onlineHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_login);

        setContentView(R.layout.splash);
        FacebookSdk.sdkInitialize(getApplicationContext());
        onlineHelper = new OnlineHelper(this);
        onlineHelper.loginCheck();
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.infinite_rotate));
    }
}
